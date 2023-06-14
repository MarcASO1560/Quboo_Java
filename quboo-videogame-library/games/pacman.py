from ursina import *
import random
import time
import mysql.connector

app = Ursina()

GAME_START = 0
GAME_PLAY = 1
GAME_OVER = 2

DELAY = 0.002

grid_size = 10

pacman_color = color.rgb(255,255,0)
food_color = color.rgb(255,255,150)
area_color = color.rgb(0,0,30)
wall_color = color.rgb(0, 0, 96)
text_color = color.rgb(255,241,232)
background_color = color.rgb(0,0,0)

maze =  [
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
  [1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1],
  [1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1],
  [1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1],
  [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
  [1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1],
  [1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1],
  [1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1],
  [1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,0,1,3,3,3,3,3,3,3,3,3,3,3,1,0,1,1,1,1,1,1,1,1,1],
  [1,0,0,0,0,0,0,0,0,0,1,3,1,1,1,1,1,1,1,1,1,3,1,0,0,0,0,0,0,0,0,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,3,1,1,1,1,1,1,1,1,1,3,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,3,3,1,1,3,3,3,3,3,1,1,3,3,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,3,1,1,3,3,3,3,3,1,1,3,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,3,1,1,1,1,3,1,1,1,1,3,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,0,0,0,0,0,0,0,0,1,3,3,3,3,3,3,3,3,3,3,3,1,0,0,0,0,0,0,0,0,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
  [1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1],
  [1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1],
  [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
]

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
        AND Nombre_juego = 'pacman'
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

pacman = None
foods = []
direction = Vec3(1,0,0)
next_direction = Vec3(1,0,0)
score = 0
game_state = GAME_START
score_text = None
game_over_text = None
start_game_button = None
game_area = None
walls = []
big_foods = []
big_food_points = 20

def new_game():
    global pacman, foods, big_foods, ghosts, ghost_directions, direction, next_direction, score, score_text, game_state, game_over_text, start_game_button, game_area, walls
    ghost_positions = [(i, j) for i, row in enumerate(maze) for j, value in enumerate(row) if value == 3]
    if len(ghost_positions) > 4:
        ghost_positions = ghost_positions[:4]
    ghosts = [Entity(model='cube', 
                     color=color.rgb(255, 0, 0), 
                     position=pos, 
                     scale=0.8) for pos in ghost_positions]
    
    ghost_directions = [(0, 0, 0) for _ in ghost_positions]
    destroy(score_text)
    destroy(game_over_text)
    destroy(start_game_button)
    if pacman:
        destroy(pacman)
    for food in foods:
        destroy(food)
    for big_food in big_foods:
        destroy(big_food)
    for wall in walls:
        destroy(wall)
    empty_positions = []
    empty_positions = []
    big_food_positions = []
    for i in range(len(maze)):
        for j in range(len(maze[i])):
            if maze[i][j] == 0:
                empty_positions.append((j - grid_size / 2, 0, i - grid_size / 2))
                
            elif maze[i][j] == 2:
                big_food_positions.append((j - grid_size / 2, 0, i - grid_size / 2))
                
    pacman = Entity(model='sphere', 
                    color=pacman_color, 
                    position=(10, 0, 2), scale=0.9)
    
    foods = [Entity(model='sphere', 
                    color=food_color, 
                    position=pos, 
                    scale=0.2) for pos in empty_positions]
    
    big_foods = [Entity(model='sphere', 
                        color= food_color, 
                        position=pos, 
                        scale=0.6) for pos in big_food_positions]
    
    direction = Vec3(1,0,0)
    next_direction = Vec3(1,0,0)
    score = 0
    score_text = Text(text=f'Score: {score}', 
                      position=(-0.8,0.45), 
                      scale=2, 
                      color=text_color)
    
    game_area = Entity(model='cube', 
                       color=area_color, 
                       scale=(grid_size*3.3,0.6, grid_size*2.7), 
                       position=(11, -0.5, 9))
    
    walls = []
    for i in range(len(maze)):
        for j in range(len(maze[i])):
            if maze[i][j] == 1:
                walls.append(Entity(model='cube', 
                                    color=wall_color, 
                                    scale=(1, 1, 1), 
                                    position=(j-grid_size/2, 0, i-grid_size/2)))
                
    game_state = GAME_PLAY


ghosts = []
ghost_directions = []

def move_ghosts():
    global ghosts, ghost_directions, pacman
    for i in range(len(ghosts)):
        ghost = ghosts[i]
        direction = ghost_directions[i]
        possible_directions = [(1, 0, 0), (-1, 0, 0), (0, 0, 1), (0, 0, -1)]
        new_position = ghost.position + direction
        if not is_valid_position(new_position):
            random.shuffle(possible_directions)
            for new_direction in possible_directions:
                new_position = ghost.position + new_direction
                if is_valid_position(new_position):
                    direction = new_direction
                    break
        ghost.position += direction
        ghost_directions[i] = direction
        if ghost.position == pacman.position:
            game_over()
        time.sleep(DELAY)

def is_valid_position(pos):
    i = int(pos.z + grid_size / 2)
    j = int(pos.x + grid_size / 2)
    if i < 0 or j < 0 or i >= len(maze) or j >= len(maze[0]) or maze[i][j] == 1:
        return False
    else:
        return True

WALL = 1

DIRECTIONS = [(0, 1), (0, -1), (1, 0), (-1, 0)]

def move_ghost(ghost, maze):
    x, y = ghost.position

    possible_directions = []

    for dx, dy in DIRECTIONS:
        new_x, new_y = x + dx, y + dy
        if maze[new_x][new_y] != WALL:
            possible_directions.append((dx, dy))

    dx, dy = random.choice(possible_directions)

    ghost.position = (x + dx, y + dy)

for ghost in ghosts:
    move_ghost(ghost, maze)

def is_valid_position(pos):
    i = int(pos.z + grid_size / 2)
    j = int(pos.x + grid_size / 2)
    if i == 0 or i == 3 or i == 2 or i >= len(maze) or j < 0 or j >= len(maze[0]):
        return False
    if maze[i][j] == 1:
        return False
    return True

def game_over():
    global game_over_text, start_game_button, game_state
    game_state = GAME_OVER
    game_over_text = Text(text=f'Game Over\nFinal Score: {score}', 
                          position=(0,0.1), 
                          origin=(0,0), 
                          color=text_color)
    add_score(score)
    add_coin()
    
    start_game_button = Button(text='Start Game', 
                               color=pacman_color, 
                               position=(0,-0.2), 
                               scale=(0.2, 0.1), 
                               on_click=new_game)

start_game_button = Button(text='Start Game', 
                           color=pacman_color, 
                           position=(0,0), 
                           scale=(0.2, 0.1), 
                           on_click=new_game)

def update():
    if game_state == GAME_PLAY:
        move_ghosts()
    global direction, next_direction, score, score_text
    move_ghosts()
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
    next_position = pacman.position + next_direction
    if not any(wall.position == next_position for wall in walls):
        direction = next_direction
    next_position = pacman.position + direction
    if any(wall.position == next_position for wall in walls):
        direction = Vec3(0, 0, 0)
    if time.time() % 0.1 < time.dt:
        pacman.position += direction
        for food in foods:
            if pacman.position == food.position:
                destroy(food)
                foods.remove(food)
                score += 1
                score_text.text = f'Score: {score}'
                break
        for big_food in big_foods:
            if pacman.position == big_food.position:
                destroy(big_food)
                big_foods.remove(big_food)
                score += big_food_points
                score_text.text = f'Score: {score}'
                break
        if not foods and not big_foods:
            game_over()

camera.position = (10, 80, 10)
camera.rotation_x = 90

window.color = background_color

app.run()