from ursina import *
import random
import mysql.connector
app = Ursina()

GAME_START = 0
GAME_PLAY = 1
GAME_OVER = 2

game_set =  GAME_PLAY

def get_username():
    with open('src/usuario.txt', 'r') as file:
        return file.read().strip()

def add_score(score):
    usuario = get_username()
    
    conn = mysql.connector.connect(
        host='192.168.56.102',
        port=3306,
        user='maraka',
        password='1560',
        database='quboo'
    )
    c = conn.cursor()

    query = """
        UPDATE Juegos
        SET Puntos_juego = %s
        WHERE Id_usuario = (SELECT Id_usuario FROM Usuarios WHERE Nombre_usuario = %s)
        AND Nombre_juego = 'jumpman'
        AND Puntos_juego < %s
    """
    values = (score, usuario, score)
    c.execute(query, values)

    conn.commit()
    conn.close()

def add_coin():
    usuario = get_username()
    
    conn = mysql.connector.connect(
        host='192.168.56.102',
        port=3306,
        user='maraka',
        password='1560',
        database='quboo'
    )
    c = conn.cursor()
    
    query = "UPDATE Usuarios SET Monedas_juego = Monedas_juego + 1 WHERE Nombre_usuario = %s"
    
    value = (usuario,)
    c.execute(query,value)
    
    conn.commit()
    conn.close()

class Player(Entity):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.model = 'cube'
        self.color = color.orange
        self.scale_y = 1
        self.speed = 200
        self.dy = 0
        self.gravity = -0.01
        self.jump_speed = 0.7
        self.grounded = False
        self.collider = 'box'
        self.score = 0
        self.current_ground = None

    def update(self):
        
        self.dy += self.gravity
        self.y += self.dy
        
        self.grounded = False
        for ground in grounds:
            if self.grounded:
                if self.is_on_ground(ground) and (not self.grounded or ground.y > self.y):
                    if self.x + self.scale_x / 2 >= ground.x - ground.scale_x / 2 and self.x - self.scale_x / 2 <= ground.x + ground.scale_x / 2:
                        self.grounded = True
                        break
            else:
                if self.dy < 0:
                    if self.y - self.scale_y / 2 <= ground.y + ground.scale_y / 2 and self.y - self.scale_y / 2 >= ground.y:
                        if self.x + self.scale_x / 2 >= ground.x - ground.scale_x / 2 and self.x - self.scale_x / 2 <= ground.x + ground.scale_x / 2:
                            self.grounded = True
                            self.dy = 0
                            self.y = ground.y + ground.scale_y / 2 + self.scale_y / 2
                            break
        if self.grounded:
            if ground is not self.current_ground:
                self.score += 10
                self.current_ground = ground
                add_score(self.score)
            
            self.y = ground.y + (ground.scale_y + self.scale_y) / 2
            self.dy = 0
            if held_keys['space']:
                self.dy = self.jump_speed
                self.grounded = False

        self.x += held_keys['d'] * .1 * time.dt * self.speed
        self.x -= held_keys['a'] * .1 * time.dt * self.speed

        if self.dy > 0:
            distance = camera.y - self.y
            if abs(distance) > 0:
                camera.y = lerp(camera.y, self.y, 0.02)
        
        if self.y < bar_dead.y:
            global game_set
            game_set = GAME_OVER
            game_over_text.enabled = True
            
        dist_2 = bar_dead.y - self.y
        if abs(dist_2) > 30:
            bar_dead.y = lerp(bar_dead.y, self.y, 0.01)
    
    def input(self, key):
        if key == 'q' or key == 'Q':
            if game_set == GAME_OVER:
                add_score(self.score)
                if self.score >= 200:
                    add_coin()
                exit()

player = Player()
camera.position = (0, 5, -90)

grounds = []

num_platforms = 100
platform_spacing = 10
min_width = 7
max_width = 10
min_x = -15
max_x = 15

ground1 = Entity(model='cube', color=color.green, scale=(15, 1, 10), y=-10, collider='box')
grounds.append(ground1)

for i in range(num_platforms):
    platform_width = random.randint(min_width, max_width)
    platform_x = random.uniform(min_x, max_x)
    ground = Entity(model='cube', color=color.green, scale=(platform_width, 1, 10), position=(platform_x, -1 + i * platform_spacing, 0), collider='box')
    grounds.append(ground)

bar_dead = Entity(model='cube', color=color.red, scale=(100, 0.1, 1), y=-30)
game_over_text = Text(text='Game Over', position=(0,0), scale=2, color=color.red, enabled=False)
def update():
    if game_set == GAME_OVER:
        return
app.run()