import cv2
import numpy as np
import glob

imagens = glob.glob('imagens\\*tif')

valor_gamma = 1.5

print(imagens)
#src = 'imagens\Fig0106(a)(bone-scan-GE).tif'

def gammaCorrection(src, gamma):
    invGamma = 1 / gamma

    table = [((i / 255) ** invGamma) * 255 for i in range(256)]
    table = np.array(table, np.uint8)

    return cv2.LUT(src, table)

for imagem in imagens:
    img = cv2.imread(imagem)
    gammaImg = gammaCorrection(img, valor_gamma) #2.5
    negativa = ~img
    ret, binarizada = cv2.threshold(img, 128, 255, cv2.THRESH_BINARY)

    cv2.imshow('Original image', img)
    cv2.imshow('Imagem com correcao gama', gammaImg)
    cv2.imshow('Imagem negativa', negativa)
    cv2.imshow('Imagem binarizada', binarizada)
    cv2.waitKey(0)
    cv2.destroyAllWindows()