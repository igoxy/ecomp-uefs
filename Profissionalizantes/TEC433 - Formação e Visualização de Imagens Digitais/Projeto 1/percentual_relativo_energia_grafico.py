# -*- coding: utf-8 -*-

import cv2
from matplotlib import pyplot as plt

# Lista de nomes de arquivo das imagens de continente e seus respectivos nomes de continente
continent_files = {"imagens/Fig0112(1)(top-canada).tif": "Canadá",
                   "imagens/Fig0112(2)(2nd-from-top-USA).tif": "América do Norte",
                   "imagens/Fig0112(3)(3rd-from-top-Central-Amer).tif": "América Central",
                   "imagens/Fig0112(4)(4th-from-top-South-Amer).tif": "América do Sul",
                   "imagens/Fig0113(1)(left-africa-europe).tif": "África/Europa",
                   "imagens/Fig0113(2)(center-russia).tif": "Rússia",
                   "imagens/Fig0113(3)(right-South-East-Asia).tif": "Sudeste Asiático",
                   "imagens/Fig0113(4)(right-bottom-Australia).tif": "Austrália"}

# Dicionário de nomes de continente para intensidade média de pixel
continent_intensities = {}

# Soma das intensidades de pixel branco de todas as imagens
total_white_pixels = 0

# Itera sobre os arquivos de continente e soma as intensidades de branco
for file, continent in continent_files.items():
    img = cv2.imread(file, cv2.IMREAD_GRAYSCALE)
    hist = cv2.calcHist([img], [0], None, [256], [0, 256]) #define os parametros de uma imagem monocromatica
    
    # Calcula a soma das intensidades de pixel branco da imagem
    # A expressão hist[255] retorna um array numpy contendo a contagem de pixels com intensidade 255, ou seja, o número de pixels brancos na imagem.
    # A segunda parte [0] é usada para acessar o valor na primeira posição desse array, que é a contagem de pixels brancos na imagem. Essa
    # contagem é então armazenada na variável white_pixels, que é usada para calcular a porcentagem de energia de cada continente 
    # no código posteriormente.
    white_pixels = hist[255][0]
    total_white_pixels += white_pixels
    
    continent_intensities[continent] = white_pixels

# Calcula o percentual relativo de energia para cada continente
continent_energy_percentages = {}
for continent, intensity in continent_intensities.items():
    energy_percentage = (intensity / total_white_pixels) * 100
    continent_energy_percentages[continent] = energy_percentage

# Imprime o percentual relativo de energia de cada continente com o nome associado da imagem
for file, continent in continent_files.items():
    energy_percentage = continent_energy_percentages[continent]
    print(f"O percentual relativo de energia de {continent} eh {energy_percentage:.2f}% em relacao ao arquivo {file}.")


# Encontra o continente com a maior porcentagem de energia
continent_mais_industrializado = max(continent_energy_percentages, key=continent_energy_percentages.get)

# Obtém o valor da porcentagem de energia do continente mais industrializado
porcentagem_mais_industrializado = continent_energy_percentages[continent_mais_industrializado]
print('\n')
# Imprime o resultado
print(f"A partir disso, podemos inferir que o continente mais industrializado eh a {continent_mais_industrializado} com uma porcentagem de energia de {porcentagem_mais_industrializado:.2f}%.")


# -- plota o gráfico com a porcentagem de energia relativa de cada continente --

values_porcentagens = [] # Array para guarda os valores de cada continente para exibir no topo da barra
for item in continent_energy_percentages.values():
    values_porcentagens.append(round(item, 2))  # Arredonda o valor da porcentagem para duas casas decimais

plt.title("Percentual relativo de energia")     # Título do gráfico
plt.xlabel("Continente")    # Eixo X
plt.ylabel("% de energia")  # Eixo y
plt_bar = plt.bar(*zip(*continent_energy_percentages.items()))          # Obtém os continentes, as porcentagens relativas a cada um e plota o gráfico
plt.bar_label(plt_bar, labels=values_porcentagens, label_type='edge')   # Adiciona a porcentagem de cada continente ao topo de sua respectiva barra
plt.show()  # Exibe o gráfico