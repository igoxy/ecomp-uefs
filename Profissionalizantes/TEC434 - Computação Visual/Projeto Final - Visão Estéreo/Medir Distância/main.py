# -*- coding: utf-8 -*-

import cv2
import numpy as np
import imutils
import calibracao as clb

# ------------ Métodos -----------
def obter_mascara(frame, cor):
    blur = cv2.GaussianBlur(frame,(5,5),0)              # Aplica blur na imagem

    frame_hsv = cv2.cvtColor(blur, cv2.COLOR_BGR2HSV)   # Converte do RGB para HSV

    #Objetos azuis
    azul_alto = np.array([255, 255, 255])               # Parte inferior da cor azul no espectro HSV
    azul_baixo = np.array([60, 110, 50])                # Parte superior da cor azul no espectro HSV
    
    #Objetos amarelos
    amarelo_alto = np.array([45, 255, 255])             # Parte superior da cor azul no espectro HSV
    amarelo_baixo = np.array([22, 93, 0])               # Parte inferior da cor azul no espectro HSV
    
    if cor == 1:                                        # Cor amarela
        mascara = cv2.inRange(frame_hsv, amarelo_baixo, amarelo_alto)
    elif cor == 2:                                      # Cor azul
        mascara = cv2.inRange(frame_hsv, azul_baixo, azul_alto)

    # Efetua uma abertura para remover ruídos
    mascara = cv2.erode(mascara, None, iterations=2)    # Erosão com duas interações
    mascara = cv2.dilate(mascara, None, iterations=2)   # Dilatação com duas interações

    return mascara                                      # Retorna a máscara

def calcular_distancia(centro_direito, centro_esquerdo, frame_direito, frame_esquerdo, dist_cameras, angulo_visao):

    # Obter a distância focal em pixels
    _, largura_direita, _ = frame_direito.shape         # Obtém a largura da imagem direita
    _, largura_esquerda, _ = frame_esquerdo.shape       # Obtém a largura da imagem esquerda

    if largura_direita == largura_esquerda:             # Verifica se a largura das duas imagens são iguais
        dist_focal_pixel = (largura_direita * 0.5) / np.tan(angulo_visao * 0.5 * np.pi/180)     # Obtém a distância focal em pixels
    else:
        print('As imagens devem ter a mesma largura!')

    coord_direita = centro_direito[0]               # Obtém a coordena do pixel do centro do objeto na imagem da direita - coordenada x
    coord_esquerda = centro_esquerdo[0]             # Obtém a coordena do pixel do centro do objeto na imagem da esquerda - coordenada x

    disparidade = coord_esquerda-coord_direita      # Calcula a disparidade entre a imagem da direita e da esquerda (em pixels) - disparidade no eixo x

    # Calcula a distância
    distancia = (dist_cameras*dist_focal_pixel)/disparidade            # Distância até o objeto em centímetros 

    return abs(distancia)                                              # Retona a distância em valor absoluto (positivo)

def obter_centro_objeto(frame, mask):

    contornos = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)   # Obtém os contornos da imagem
    
    # Obtém somente os contornos da tupla retornada pelo método de achar contornos do openCV
    contornos = imutils.grab_contours(contornos)   # Método utilizado porque a depende da versão do openCV pode ser retornado 2 ou 3 informações                                          
    centro = None

    if len(contornos) > 0:
        maior_contorno = max(contornos, key=cv2.contourArea)            # Obtém o maior contorno
        ((x, y), raio) = cv2.minEnclosingCircle(maior_contorno)         # Encontra o menor circulo que cobre o contorno 
        M = cv2.moments(maior_contorno)                                 # Encontra o momento do contorno/objeto - a partir disso, pode ser obtido o centro do contorno
        centro = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))   # Encontra o centro do contorno/objeto - Cx = M10/M00 | Cy = M01/M00

        if raio > 15:   # Definimos um raio minimo do circulo que cobre o contorno. Isso para não pegar objetos muito pequenos
            cv2.circle(frame, (int(x), int(y)), int(raio), (0, 0, 255), 2)      # Desenha um circulo em volta do objeto
            cv2.circle(frame, centro, 5, (0, 0, 0), -1)                         # Desenha um circulo no centro do objeto

    return centro
# ------------------- Fim métodos ------------

# Constantes
AMARELO = 1     # Indica que a máscara deve ser para a cor amarelo
AZUL = 2        # Indica que a máscara deve ser para a cor azul

distancia_entre_cameras = 9     # Distância entre as câmeras em centímetros
angulo_visao_cameras = 45.22    # Ângulo de visão das câmeras em graus

# Contador para reiniciar o vídeo
contador_frame = 0

camera_direita = cv2.VideoCapture('direitaAmarelo.avi')     # Para o objeto azul mudar o vídeo para: 'direitaAzul.avi'
camera_esquerda = cv2.VideoCapture('esquerdaAmarelo.avi')   # Para o objeto azul mudar o vídeo para: 'esquerdaAzul.avi'

while camera_direita.isOpened() and camera_esquerda.isOpened():
    sucesso_direito, frame_direito = camera_direita.read()
    sucesso_esquerdo, frame_esquerdo = camera_esquerda.read()

    contador_frame += 1                 # Conta os frames para poder reiniciar  o vídeo
    
    if (contador_frame == camera_esquerda.get(cv2.CAP_PROP_FRAME_COUNT) and contador_frame == camera_direita.get(cv2.CAP_PROP_FRAME_COUNT)):    # Verifica se ambos os vídeos chegaram ao final
        contador_frame = 0              # Reseta o contador
        camera_direita.set(cv2.CAP_PROP_POS_FRAMES, 0)      # Reinicia o vídeo da câmera direita
        camera_esquerda.set(cv2.CAP_PROP_POS_FRAMES, 0)     # Reinicia o vídeo da câmera esquerda

    # Se um algum dos dois vídeos não for aberto, para a aplicação
    if sucesso_direito==False or sucesso_esquerdo==False:                    
        break
    else:    # Se o vídeo for aberto
        # Calibra a imagem
        frame_direito, frame_esquerdo = clb.calibrar_imagem(frame_direito, frame_esquerdo)  # Calibra os frames de acordo com as calibrações das câmeras
        
        # Obtém a máscara - ALTERAR O VALOR DA COR DE ACORDO COM O A COR DO OBJETO DESAJADO - para azul coloque AZUL
        mascara_direita = obter_mascara(frame_direito, cor=AMARELO)    # Obtém a máscara para o frame direito da imagem - o segundo parâmetro indica a cor desejada: 1 = amarelo; 2 = azul
        mascara_esquerda = obter_mascara(frame_esquerdo, cor=AMARELO)    # Obtém a máscara para o frame esquerdo da imagem - o segundo parâmetro indica a cor desejada: 1 = amarelo; 2 = azul

        # Aplica a máscara na imagem
        objeto_direita = cv2.bitwise_and(frame_direito, frame_direito, mask=mascara_direita)     # Aplica um 'end' no frame direito para obter somente os elementos que detém a cor desejada
        objeto_esquerda = cv2.bitwise_and(frame_esquerdo, frame_esquerdo, mask=mascara_esquerda)   # Aplica um 'end' no frame esquerdo para obter somente os elementos que detém a cor desejada

        # Encontra objeto desejado para medir
        centro_direito = obter_centro_objeto(frame_direito, mascara_direita)         # Encontra o centro do objeto no frame direito
        centro_esquerdo  = obter_centro_objeto(frame_esquerdo, mascara_esquerda)     # Encontra o centro do objeto no frame esquerdo

        # ----------------- Calculo da distância do objeto -----------------
        if np.all(centro_direito) == None or np.all(centro_esquerdo) == None:           # Verifica se o objeto não foi detectado na imagem 
            cv2.putText(frame_direito, "Não foi detectado o objeto na imagem", (75,50), cv2.FONT_HERSHEY_PLAIN, 0.7, (0,0,255),2)
            cv2.putText(frame_esquerdo, "Não foi detectado o objeto na imagem", (75,50), cv2.FONT_HERSHEY_PLAIN, 0.7, (0,0,255),2)
        else:           # Se foi detectado
            distancia = calcular_distancia(centro_direito, centro_esquerdo, frame_direito, frame_esquerdo, distancia_entre_cameras, angulo_visao_cameras)
            cv2.putText(frame_direito, f"Distancia: {str(round(distancia,3))} cm", (180,50), cv2.FONT_HERSHEY_DUPLEX, 0.7, (0,0,255),2)    #cv2.putText(frame_right, "Distance: " + str(round(depth,3)) + " cm", (200,50), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (124,252,0),2)
            cv2.putText(frame_esquerdo, f"Distancia: {str(round(distancia,3))} cm", (180,50), cv2.FONT_HERSHEY_DUPLEX, 0.7, (0,0,255),2)     #cv2.putText(frame_left, "Distance: " + str(round(depth,3)) + " cm", (200,50), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (124,252,0),2)
                         
        # ----------------- Mostra as imagens -----------------
        cv2.imshow("Camera direita", frame_direito)                     # Mostra a imagem da esqueda
        cv2.imshow("Camera esquerda", frame_esquerdo)                   # Mostra a imagem da direita
        cv2.imshow("Mascara camera direita", mascara_direita)           # Mostra a imagem da direita apenas com os elementos da cor selecionada (imagem binária)
        cv2.imshow("Mascara camera esquerda", mascara_esquerda)         # Mostra a imagem da esquerda apenas com os elementos da cor selecionada (imagem binária)
        cv2.imshow("Objeto camera direita", objeto_direita)             # Mostra a imagem da direita apenas com os elementos da cor selecionada
        cv2.imshow("Objeto camera esquerda", objeto_esquerda)           # Mostra a imagem da esquerda apenas com os elementos da cor selecionada

        # Pressione q para fechar
        if cv2.waitKey(15) == ord('q'):
            break

cv2.destroyAllWindows()