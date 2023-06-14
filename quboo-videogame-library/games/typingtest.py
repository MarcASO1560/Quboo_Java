import pygame
import random
import sys
import mysql.connector

from sympy import fps

pygame.init()
WINDOW_WIDTH = 800
WINDOW_HEIGHT = 600
TEXT_COLOR = (255, 255, 255)
BACKGROUND_COLOR = (0, 0, 0)
FPS = 60
FONT_NAME = pygame.font.match_font('arial')

with open('games/words/words.txt', 'r') as f:
    WORDS = [line.strip() for line in f]

screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))

class Word:
    def __init__(self, word):
        self.word = word
        self.text = pygame.font.Font(FONT_NAME, 20).render(word, True, TEXT_COLOR) 
        self.width = self.text.get_width()
        self.height = self.text.get_height()
        self.x = random.randrange(WINDOW_WIDTH - self.width)
        self.y = 0 - self.height
    def draw(self, screen):
        screen.blit(self.text, (self.x, self.y))
    def update(self):
        self.y += 1

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
        AND Nombre_juego = 'typingtest'
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

def game_loop():
    clock = pygame.time.Clock()
    input_word = ""
    words = []
    score = 0
    missed_words = 0

    while True:
        screen.fill(BACKGROUND_COLOR)

        if random.random() < 0.02:
            word = Word(random.choice(WORDS))
            words.append(word)

        for word in words:
            word.draw(screen)
            word.update()

            if word.y > WINDOW_HEIGHT:
                words.remove(word)
                missed_words += 1

        if missed_words >= 10:
            add_score(score)
            if score >= 100:
                add_coin()
            return

        input_text = pygame.font.Font(FONT_NAME, 20).render(input_word, True, TEXT_COLOR)
        screen.blit(input_text, (10, WINDOW_HEIGHT - 60))

        score_text = pygame.font.Font(FONT_NAME, 20).render(f"Score: {score}", True, TEXT_COLOR)
        screen.blit(score_text, (10, 10))

        missed_words_text = pygame.font.Font(FONT_NAME, 20).render(f"Missed: {missed_words}", True, TEXT_COLOR)
        screen.blit(missed_words_text, (10, 40))

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_BACKSPACE:
                    input_word = input_word[:-1]
                elif event.key == pygame.K_RETURN:
                    if input_word in [word.word for word in words]:
                        words = [word for word in words if word.word != input_word]
                        score += 10
                    input_word = ""
                else:
                    input_word += event.unicode

        pygame.display.update()
        clock.tick(FPS)
game_loop()