import cv2
import numpy as np

# Lista de nomes de arquivo das imagens de continente
continent_files = ["imagens/Fig0112(1)(top-canada).tif",
                    "imagens/Fig0112(2)(2nd-from-top-USA).tif",
                      "imagens/Fig0112(3)(3rd-from-top-Central-Amer).tif", 
                    "imagens/Fig0112(4)(4th-from-top-South-Amer).tif", 
                      "imagens/Fig0113(1)(left-africa-europe).tif",
                        "imagens/Fig0113(2)(center-russia).tif",
                        "imagens/Fig0113(3)(right-South-East-Asia).tif",
                        "imagens/Fig0113(4)(right-bottom-Australia).tif"]

# Dicionário de nomes de continente para intensidade média de pixel
continent_intensities = {}

# Itera sobre os arquivos de continente e calcula a intensidade média de pixel para cada imagem
for file in continent_files:
    img = cv2.imread(file, 0)
    hist = cv2.calcHist([img], [0], None, [256], [0, 256])
    intensity = np.mean(img)
    continent = file.split('.')[0].capitalize()
    continent_intensities[continent] = intensity


# Imprime as intensidades de cada continente
for continent, intensity in continent_intensities.items():
    print(f"A intensidade média de pixel em {continent} é {intensity:.2f}.")
# Encontra o continente com a maior intensidade média de pixel e imprime o nome do continente
most_industrialized_continent = max(continent_intensities, key=continent_intensities.get)
print(f"{most_industrialized_continent} é o continente mais industrializado de acordo com a luminosidade.")
