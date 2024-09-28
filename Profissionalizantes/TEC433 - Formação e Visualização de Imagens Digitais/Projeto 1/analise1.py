import cv2
import numpy as np

# Carrega a imagem em escala de cinza
img = cv2.imread('imagens/Fig0112(2)(2nd-from-top-USA).tif')
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# Calcula o histograma da imagem
hist = cv2.calcHist([gray], [0], None, [256], [0, 256])

# Calcula o total de pixels na imagem
total_pixels = gray.shape[0] * gray.shape[1]

print(f'total: {total_pixels}')

# Calcula o percentual de intensidade de pixels para cada nível de intensidade
percentages = hist / total_pixels * 100

print(f'branco: {hist[255]}')
print(f'preto: {hist[0]}')
print(f'histogram: {hist[0]/hist[255]}')

# Exibe os resultados
for i in range(len(percentages)):
    print(f"Intensidade {i}: {percentages[i][0]:.2f}%")