import cv2
import numpy as np
from matplotlib import pyplot as plt

# Carrega a imagem
imagem = cv2.imread('images\\lena-ruidosa-prof-dd.tiff', 0)

# Aplica a Transformada de Fourier
transformada = np.fft.fft2(imagem)
transformada_shifted = np.fft.fftshift(transformada)    # Obtém a transformada da imagem com ruído
espectro = 20 * np.log(np.abs(transformada_shifted))

#Aplica o filtro notch no espectro da imagem
espectro_rmv = transformada_shifted   # Obtém a transformada 'centralizada'
espectro_rmv[128,28] = 0              # Coordenada 1 para aplicação do filtro
espectro_rmv[128, 228] = 0            # Coordenada 2 para aplicação do filtro

#transformada inversa
imagem_sem_ruido_ishift = np.fft.ifftshift(espectro_rmv)
imagem_sem_ruido = np.fft.ifft2(imagem_sem_ruido_ishift)

#transformada da imagem sem ruído
transformada_s_ruido = np.fft.fft2(np.uint8((imagem_sem_ruido)))        # Obtém a transformada da imagem sem ruído
transformada_shifted_s_ruido = np.fft.fftshift(transformada_s_ruido)    # Faz um shift na transformada
espectro_sem_ruido = 20 * np.log(np.abs(transformada_shifted_s_ruido))  # Coloca a transformada em escala logarítmica para visualizar melhor o espectro

#salvando imagem sem ruido
#cv2.imwrite('lena-sem-ruido.png', np.real(imagem_sem_ruido))

# Plota a imagem original e o espectro de frequência
plt.subplot(2,2,1), plt.imshow(np.real(imagem), cmap='gray')
plt.title('Imagem com Ruído'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,2), plt.imshow(np.real(imagem_sem_ruido), cmap='gray')
plt.title('Imagem sem ruído'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,3), plt.imshow(espectro, cmap='gray')
plt.title('Espectro da Imagem com Ruído'), plt.xticks([]), plt.yticks([])
plt.subplot(2,2,4), plt.imshow(espectro_sem_ruido, cmap='gray')
plt.title('Espectro da Imagem sem ruído'), plt.xticks([]), plt.yticks([])
plt.show()