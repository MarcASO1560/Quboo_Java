from ursina import *
from random import randint
import mysql.connector

app = Ursina()

GAME_START = 0
GAME_PLAY = 1
GAME_OVER = 2

grid_size = 20

snake_color = color.rgb(0,255,106)
food_color = color.rgb(200,0,0)
area_color = color.rgb(0,135,81)
danger_color = color.rgb(29,43,83) 
text_color = color.rgb(255,241,232) 
background_color = color.rgb(0,0,0) 
wall_color = color.rgb(39, 174, 96) 

snake = []
food = None
direction = Vec3(1,0,0)
next_direction = Vec3(1,0,0)
score = 0
game_state = GAME_START
score_text = None
game_over_text = None
start_game_button = None
game_area = None
walls = []

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
        AND Nombre_juego = 'snake'
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

def new_game():
    global snake, food, direction, next_direction, score, score_text, game_state, game_over_text, start_game_button, game_area, walls
    destroy(score_text)
    destroy(game_over_text)
    destroy(start_game_button)
    for part in snake:
        destroy(part)
    if food:
        destroy(food)
    for wall in walls:
        destroy(wall)
    snake = [Entity(model='cube', color=snake_color, position=(0, 0, 0))]
    snake.insert(0, Entity(model='cube', color=snake_color, position=snake[0].position + direction))
    snake.insert(0, Entity(model='cube', color=snake_color, position=snake[0].position + direction))
    food = Entity(model='sphere', color=food_color, position=(randint(-grid_size + 1, grid_size - 1), 0, randint(-grid_size + 1, grid_size - 1)), scale=0.5)
    direction = Vec3(1,0,0)
    next_direction = Vec3(1,0,0)
    score = 0
    score_text = Text(text=f'Score: {score}', position=(-0.8,0.45), scale=2, color=text_color)
    game_area = Entity(model='cube', color=area_color, scale=(grid_size*2, 0.1, grid_size*2), position=(0, -0.5, 0))
    walls = [
        Entity(model='cube', color=wall_color, scale=(0.1, 1, grid_size*2), position=(-grid_size-0.5, 0, 0)),
        Entity(model='cube', color=wall_color, scale=(0.1, 1, grid_size*2), position=(grid_size+0.5, 0, 0)),
        Entity(model='cube', color=wall_color, scale=(grid_size*2, 1, 0.1), position=(0, 0, -grid_size-0.5)),
        Entity(model='cube', color=wall_color, scale=(grid_size*2, 1, 0.1), position=(0, 0, grid_size+0.5))
    ]
    game_state = GAME_PLAY

def game_over():
    global game_over_text, start_game_button, game_state
    game_state = GAME_OVER
    game_over_text = Text(text=f'Game Over\nFinal Score: {score}', position=(0,0.1), origin=(0,0), color=text_color)
    add_score(score)
    if score >= 200:
        add_coin()
    start_game_button = Button(text='Start Game', color=snake_color, position=(0,-0.2), scale=(0.2, 0.1), on_click=new_game)

start_game_button = Button(text='Start Game', color=snake_color, position=(0,0), scale=(0.2, 0.1), on_click=new_game)
def update():
    global direction, next_direction, score, score_text
    if game_state != GAME_PLAY:
        return
    if held_keys['a']:
        next_direction = Vec3(-1, 0, 0)
    elif held_keys['d']:
        next_direction = Vec3(1, 0, 0)
    elif held_keys['w']:
        next_direction = Vec3(0, 0, 1)
    elif held_keys['s']:
        next_direction = Vec3(0, 0, -1)
    if next_direction + direction != Vec3(0,0,0):
        direction = next_direction
    if time.time() % 0.1 < time.dt: 
        if snake[0].position == food.position:
            snake.insert(0, Entity(model='cube', color=snake_color, position=snake[0].position + direction))
            food.position = (randint(-grid_size + 1, grid_size - 1), 0, randint(-grid_size + 1, grid_size - 1))
            score += 10
            add_score(score)
            score_text.text = f'Score: {score}'
        else:
            for i, s in reversed(list(enumerate(snake))):
                s.position = snake[i-1].position if i > 0 else s.position + direction
        if snake[0].position in [s.position for s in snake[1:]] or abs(snake[0].x) == grid_size or abs(snake[0].z) == grid_size:
            game_over()
camera.position = (0, 110, 0)
camera.rotation_x = 90
window.color = background_color

app.run()