import pygame
import random
import sys
import mysql.connector

premio = 0
# Configuración inicial.
pygame.init()
TcK = pygame.time.Clock()

# Contador de puntuaciones.
c1 = 0
c2 = 0

# Colores.
BLANCO = (255, 255, 255)
ROJO = (255,0,0)
AZUL = (0,0,255)
NEGRO = (0,0,0)

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
        AND Nombre_juego = 'pong'
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
# Configuración de pantalla.
anc, alt = 600, 400
jajaja = True
ventana = pygame.display.set_mode((anc, alt))

# Objetos del juego.
anc_bar, alt_bar = 15, 80
pelotaD = 15
jug_bar = pygame.Rect(
    0 + 15, alt // 2 - alt_bar // 2, anc_bar, alt_bar
    )
graciosete = pygame.Rect(
    anc - anc_bar - 15, alt // 2 - alt_bar // 2, anc_bar, alt_bar
    )
pelota = pygame.Rect(
    anc // 2 - pelotaD // 2, alt // 2 - pelotaD // 2, pelotaD, pelotaD
    )

# Fast as fuck.
vel_bar = 5
vel_bar_op = 3
pelota_vel_x, pelota_vel_y = 2, 2

font = pygame.font.Font(None, 36)

def draw_score():
    player_score_text = font.render(str(c2), True, BLANCO)
    opponent_score_text = font.render(str(c1), True, BLANCO)
    ventana.blit(player_score_text, (anc // 4, 10))
    ventana.blit(opponent_score_text, (anc - anc // 4, 10))


# Boton cerrar el juego
jajaja = True
while jajaja:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
    
    # Movimiento del jugador.
    tecla = pygame.key.get_pressed()
    if tecla[pygame.K_w] and jug_bar.top > 0:
        jug_bar.y -= vel_bar
    if tecla[pygame.K_s] and jug_bar.bottom < alt:
        jug_bar.y += vel_bar
    
    # Movimiento del hijo de p!#().
    if graciosete.centery > pelota.centery and graciosete.top > 0:
        graciosete.y -= vel_bar_op
    if graciosete.centery < pelota.centery and graciosete.bottom < alt:
        graciosete.y += vel_bar_op
    # Movimiento de la pelota.
    pelota.x += pelota_vel_x
    pelota.y += pelota_vel_y
    
    # Colisión con las paredes.
    if pelota.top <= 0:
        pelota_vel_y *= -1
        pelota_vel_y += 0.2
    if pelota.bottom >= alt:
        pelota_vel_y *= -1
        pelota_vel_y -= 0.2
        
    # Colision contra las barras
    if pelota.colliderect(jug_bar):
        pelota_vel_x *= -1
        pelota_vel_x = pelota_vel_x + 0.5
        vel_bar += 0.15
    if pelota.colliderect(graciosete):
        pelota_vel_x *= -1
        pelota_vel_x = pelota_vel_x - 0.5
        vel_bar += 0.01
    if pelota.left <= 0:
        pelota.x = anc // 2 - pelotaD // 2
        pelota.y = anc // 2 - pelotaD // 2
        pelota_vel_x *= -1
        c1+=1
        pelota_vel_y = 2
        pelota_vel_x = 2
        vel_bar = 5
    if pelota.right >= anc:
        pelota.x = anc // 2 - pelotaD // 2
        pelota.y = anc // 2 - pelotaD // 2
        pelota_vel_x *= -1
        c2+=10
        pelota_vel_y = 2
        pelota_vel_x = 2
        vel_bar = 5
    
    if c1 == 5:
        if c2 > 50 or c2 == 50:
            add_coin()
        add_score(c2)
        exit()
    
    # Añadir todas las cosas y aplicar los colores a la ventana.
    ventana.fill(NEGRO)
    pygame.draw.rect(ventana, AZUL, jug_bar)
    pygame.draw.rect(ventana, ROJO, graciosete)
    pygame.draw.ellipse(ventana, BLANCO, pelota)
    pygame.draw.aaline(ventana, BLANCO, (anc // 2, 0), (anc // 2, alt))
    draw_score()
    
    # Actualizar la pantalla.
    pygame.display.flip()
    TcK.tick(60)