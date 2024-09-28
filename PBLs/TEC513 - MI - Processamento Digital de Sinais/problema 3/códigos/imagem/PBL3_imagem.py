# Código em Python para efetuar o borramento do rosto do indivíduo em uma imagem

# Imports
import cv2
import numpy as np
from matplotlib import pyplot as plt

# Função para criar o filtro no domínio da frequência
def make_filter(image, corte):
    M, N = image.shape # Tamanho da imagem
    filtro = np.zeros((M,N), dtype=np.float32)
    for u in range(M):
        for v in range(N):
            D = np.sqrt((u-M/2)**2 + (v-N/2)**2)
            if D <= corte:
                filtro[u,v] = 1
            else:
                filtro[u,v] = 0
    return filtro
    
        
# Carrega a imagem
imagem = cv2.imread('lena 256x256.tif', cv2.IMREAD_GRAYSCALE)

# Fazendo uma cópia da imagem original para a exibição
imagem_original = np.copy(imagem)

# Extraindo a região do Rosto na imagem
sub_imagem = imagem[100:100+95, 107:107+70]     # [y,x]
rosto_plot = np.copy(sub_imagem)                # Criando uma cópia da região do rosto para plotagem

# Aplica a Transformada de Fourier no subimagem do rosto
transformada = np.fft.fft2(sub_imagem)  # Obtém a transformada da região do rosto
transformada_shifted = np.fft.fftshift(transformada)    
espectro_rosto = 20*np.log(np.abs(transformada_shifted))   # Aplicando a logaritmo para plotar o espectro

# Criando o filtro para ser aplicado no dominio da frequência
filter = make_filter(sub_imagem, 3)

# Aplica a filtragem na frequência
espectro_mask = transformada_shifted * filter

# Transformada inversa
imagem_filtrada = np.fft.ifftshift(espectro_mask)
imagem_filtrada1 = np.fft.ifft2(imagem_filtrada)

# Trocando o rosto na imagem - Inserindo o rosto borrado na imagem original
imagem[100:100+95, 107:107+70] = np.real(imagem_filtrada1)

# Salvando a imagem com o rosto borrado
cv2.imwrite('lena_final.png', np.real(imagem))

# Plota a imagem original e o espectro de frequência
plt.subplot(5,1,1), plt.imshow(imagem_original, cmap='gray')
plt.title('Imagem original'), plt.xticks([]), plt.yticks([])

plt.subplot(5,1,2), plt.imshow(rosto_plot, cmap='gray')
plt.title('Rosto'), plt.xticks([]), plt.yticks([])

plt.subplot(5,1,3), plt.imshow(np.real(espectro_rosto), cmap='gray')
plt.title('Espectro do rosto'), plt.xticks([]), plt.yticks([])

plt.subplot(5,1,4), plt.imshow(filter, cmap='gray')    # Filtro ideal aplicado na frequência
plt.title('Filtro passa-baixas no domínio da frequência utilizado'), plt.xticks([]), plt.yticks([])

plt.subplot(5,1,5), plt.imshow(imagem, cmap='gray')
plt.title('Imagem final com o rosto borrado'), plt.xticks([]), plt.yticks([])
plt.show()