#coding: utf-8
""" Bibliotecas """
import random
from os import system, name

""" Criação da função usada para limpar a tela """
def clear():
    if name == "nt":
        system("cls")
    else:
        system("clear")

opcao = None; recorde = [0,None]
jogador1 = [None]*4; jogador2 = [None]*4 #Gera uma lista para o nome e cartas para cada jogador

while opcao != "3": #Entrada nas opções do jogo 
    clear() #Limpa a tela
    print("{:=^65}".format(" Seja bem vindo ao Super Trunfo "),"\n")
    print("\t\t\tMenu inicial \n")
    print("\t\t1 - Iniciar o jogo.\n\t\t2 - Informações sobre o jogo.\n\t\t3 - Sair do jogo.\n") #Tela inicial de opções.
    opcao = (input("Digite a opção desejada: ")) #Recebe a opção desejada.
    clear()
    
    if opcao == "1": #Entrada no jogo
        jogador1[3] = input("Digite o apelido do jogador 1: ")
        jogador2[3] = input("Digite o apelido do jogador 2: ")
        rodadas = int(input('\nO mínimo de rodadas permitidas são 3.\nInsira a quantidade de rodadas desejada: ')) 
        clear()
        if rodadas < 3:
            rodadas = 3
            print("Número de rodadas inserido menor que o permitido.\nO jogo terá três rodadas, o mínimo permito.")
        jogador_inicial = random.randint(1,2) #Escolha aleatória de quem iniciará a partida
        """ Cria listas para cada carta, usada para guardar os atributos e os nomes de cada carta """
        carta1 = [None]*3 + ["Monstro 1"]; carta2 = [None]*3 + ["Monstro 2"]; carta3 = [None]*3 + ["Monstro 3"];
        carta4 = [None]*3 + ["Monstro 4"]; carta5 = [None]*3 + ["Monstro 5"]; carta6 = [None]*3 + ["Monstro 6"];
        """ Cria uma lista 'CARTA' com todas as cartas para embaralhar as mesmas """
        cartas = [carta1, carta2, carta3, carta4, carta5, carta6]
        """ Tupla com os pontos associados aos atributos, usados na para a pontuação dos jogadores """
        pontos_atributos = (10,20,30) #Atributo 1 = 10 pontos; atributos 2 = 20 pontos; atributo 3 = 30 pontos;
        nomes_atributos = ["Atributo 1", "Atributo 2", "Atributo 3"] #Lista usada para guardar os nomes e os números dos atributos
        pontos_jogador1 = 0; pontos_jogador2 = 0 #Variáveis para o armazenamento da pontuação dos respectivos jogadores
        
        """ Indica o jogador a iniciar o jogo """
        if jogador_inicial == 1: 
            print(jogador1[3], "inicia o jogo.")
            input("\nPressione a tecla enter para iniciar!")    
        elif jogador_inicial == 2:                             
            print(jogador2[3], "inicia o jogo.")
            input("\nPressione a tecla enter para iniciar!")   
        clear()
        while rodadas > 0: #Início das rodadas (e do jogo propriamente dito)
            cont1 = 0 #Contador utilizado para percorrer todas as 'cartas' do lista cartas
            #Laço de repetição usado para gerar os valores aleatórios dos atributos
            while cont1 < 6:
                cartas[cont1][0] = random.randint(1,100)
                cartas[cont1][1] = random.randint(1,100)
                cartas[cont1][2] = random.randint(1,100)
                cont1 += 1

            random.shuffle(cartas)           #Embaralha as cartas
            jogador1[0:3] = cartas[0:3]     #Distribui as cartas com seus atributos ao jogador 1
            jogador2[0:3] = cartas[3:6]     #Distribui as cartas com seus atributos ao jogador 1
            rodadas -= 1       #Contador de rodadas
            verificador = 0     #Variável para a verificar quais jogadores já jogaram na rodada
            jogador = jogador_inicial  #Variável auxiliar para a manipulação de informações do jogador inicial da rodada
            while  verificador < 2: #Laço para garantir que todos os jogadores joguem na rodada
                if jogador == 1: #Estrutura para a vez de jogar do jogador 1
                    cont3 = 0 #Contador auxiliar para percorrer elementos dentro das listas (jogador1 e jogador2) 
                    print("{:=^40}".format(" Cartas do jogador {} ").format(jogador1[3]),"\n")
                    for item in jogador1[0:3]: #Imprime suas cartas e seus atributos para o jogador 1
                        print("\n", jogador1[cont3][3]," - ", jogador1[3])
                        print("\tAtributo 1: ", jogador1[cont3][0])
                        print("\tAtributo 2: ", jogador1[cont3][1])
                        print("\tAtributo 3: ", jogador1[cont3][2])
                        cont3 += 1
                    verificador += 1
                    
                    if verificador == 1: #verifica se o jogador 2 não jogou / jogador 1 inicia a rodada
                        jogador = 2 #Indica que o jogador 2 ainda deve jogar
                        atributo_p1 = -1; carta_p1 = -1 #Atribuição de -1 para entrar nos laços de tratamento de erro 
                        while atributo_p1 < 0 or atributo_p1 > 2: #Evita que o usuário insira um número fora da faixa
                            atributo_p1 = (int(input("\nEscolha o atributo desejado: ")) - 1)
                        while carta_p1 < 0 or carta_p1 > 2: #Evita que o usuário insira um número fora da faixa
                            carta_p1 = (int(input("Selecione a carta desejada: ")) - 1)
                        clear()
                        print("\n",jogador2[3],"tecle enter para confirmar que está na frente da tela!")
                        input() #Recebe a confirmação do jogador
                        clear() 
                    else: #Caso o jogador 1 seja o segundo a jogar na rodada
                        jogador = None #Atribuição para que o jogador 2 não jogue novamente na rodada
                        print("\nO atributo escolhido por {} foi: {}".format(jogador2[3], nomes_atributos[atributo_p2])) #Indica o atributo escolhido pelo jogador 2
                        carta_p1 = -1  #Atribuição de -1 para entrar nos laços de tratamento de erro
                        while carta_p1 < 0 or carta_p1 > 2: #Evita que o usuário insira um número fora da faixa
                            carta_p1 = (int(input("Selecione a carta desejada: ")) - 1)
                        atributo_p1 = atributo_p2 #Indica o atributo que deve ser buscado na lista da carta selecionada do jogador 1
                        jogador_inicial = 1 #Atribuição para que alterna o jogador que inicia próxima rodada (jogador oposto da atual rodada)

                if jogador == 2: #Estrutura para a vez de jogar do jogador 1
                    cont3 = 0 #Contador auxiliar para percorrer elementos dentro das listas (jogador1 e jogador2) 
                    print("{:=^40}".format(" Cartas do jogador {} ").format(jogador2[3]),"\n")
                    for item in jogador2[0:3]: #Imprime suas cartas e seus atributos para o jogador 2
                        print("\n", jogador2[cont3][3]," - ", jogador2[3])
                        print("\tAtributo 1: ", jogador2[cont3][0])
                        print("\tAtributo 2: ", jogador2[cont3][1])
                        print("\tAtributo 3: ", jogador2[cont3][2])
                        cont3 = cont3 + 1
                    verificador = verificador + 1
                    
                    if verificador < 2: #Verificador se o jogador 1 já jogou, caso não tenha jogador entra no laço / escolha de atributos e cartas jogador 2
                        jogador -= 1   #Garantir que o jogador 1 jogue na rodada
                        atributo_p2 = -1; carta_p2 = -1 #Atribuição de -1 para entrar nos laços de tratamento de erro 
                        while atributo_p2 < 0 or atributo_p2 > 2: #Evita que o jogador insira um valor fora da faixa
                            atributo_p2 = (int(input("\nEscolha o atributo desejado: ")) - 1)
                        while carta_p2 < 0 or carta_p2 > 2: #Evita que o jogador insira um valor fora da faixa
                            carta_p2 = (int(input("Selecione a carta deseja: ")) - 1)
                        clear() 
                        print("\n",jogador1[3],"tecle enter para confirmar que está na frente da tela!")
                        input() #Recebe a confirmação do jogador
                        clear()
                    else: #Caso o jogador 2 seja o segundo jogador a jogar / escolha da carta
                        print("\nO atributo escolhido por {} foi: {}".format(jogador1[3], nomes_atributos[atributo_p1])) #Indica o atributo escolhido pelo jogador 1
                        carta_p2 = -1 #Atribuição de -1 para entrar nos laços de tratamento de erro
                        while carta_p2 < 0 or carta_p2 > 2: #Evita que o jogador insira um valor fora da faixa
                            carta_p2 = (int(input("Selecione a carta desejada: ")) - 1)
                        atributo_p2 = atributo_p1 #Indica o atributo que deve ser buscado na lista da carta selecionada do jogador 2
                        jogador_inicial = 2 #Atribuição para que alterna o jogador que inicia próxima rodada (jogador oposto da atual rodada)
            clear()
            print(jogador1[3]," - ",nomes_atributos[atributo_p1],"= {}.".format(jogador1[carta_p1][atributo_p1]),"\n") #Imprime o atributo e o valor deste do jogador 1 da rodada
            print(jogador2[3]," - ",nomes_atributos[atributo_p2],"= {}.".format(jogador2[carta_p2][atributo_p2]),"\n") #Imprime o atributo e o valor deste do jogador 2 da rodada
            if jogador1[carta_p1][atributo_p1] > jogador2[carta_p2][atributo_p2]: #Verifica se o jogador 1 venceu a rodada
                print("O jogador ",jogador1[3],"venceu a rodada!") #Indicador de vitória jogador 1
                pontos_jogador1 = pontos_atributos[atributo_p1] + pontos_jogador1 #Atribui a pontuação ao jogador 1
            elif jogador1[carta_p1][atributo_p1] < jogador2[carta_p2][atributo_p2]: #erifica se o jogador 1 venceu a rodada
                print("O jogador",jogador2[3], "venceu a rodada!") #Indicador de vitória jogador 2
                pontos_jogador2 = pontos_atributos[atributo_p2] + pontos_jogador2 #Atribui a pontuação ao jogador 2
            elif jogador1[carta_p1][atributo_p1] == jogador2[carta_p2][atributo_p2]: #Indica empate da rodada. Ninguém ganha ponto
                print("Empate!! \nAtributos com mesmos valores.")
            input("\nTecle enter para continuar.")
            clear()
            if jogador_inicial == 1 and rodadas != 0:
                print("É a vez de {} jogar.\n\nTecle enter para confirma que está na frente da tela!".format(jogador1[3]))
                input()
            elif jogador_inicial == 2 and rodadas !=0:
                print("É a vez de {} jogar.\n\nTecle enter para confirma que está na frente da tela!".format(jogador2[3]))
                input()
            clear()    
        if pontos_jogador1 > pontos_jogador2: #Verificador se o jogador 1 venceu a partida
            print("{} venceu a partida com {} pontos!".format(jogador1[3], pontos_jogador1))
            print("\nO jogador {} fez {} pontos.".format(jogador2[3], pontos_jogador2))
            if pontos_jogador1 > recorde[0]: #Verifica se o jogador 1 quebrou o recorde
                recorde[0] = pontos_jogador1
                recorde[1] = jogador1[3]
                print("\n\n{:=^40}".format(" Novo recorde!!! "),"\n\n",recorde[1],"é o novo recordista com",recorde[0],"pontos.")
        elif pontos_jogador2 > pontos_jogador1: #Verifica se o jogador 2 venceu a partida
            print("{} venceu a partida com {} pontos!".format(jogador2[3], pontos_jogador2))
            print("\nO jogador {} fez {} pontos.".format(jogador1[3], pontos_jogador1))
            if pontos_jogador2 > recorde[0]: #Verifica e o jogador 2 quebrou o recorde
                recorde[0] = pontos_jogador2
                recorde[1] = jogador2[3]
                print("\n\n{:=^40}".format(" Novo recorde!!! "),"\n\n",recorde[1],"é o novo recordista com",recorde[0],"pontos.") 
        elif pontos_jogador1 == pontos_jogador2: #Caso aconteça empate
            print("Pontuação", jogador1[3],":",pontos_jogador1,"\n")
            print("Pontuação",jogador2[3],":",pontos_jogador2,"\n")
            print("Empate!! Não houve vencedores nesta partida!")
        print("\n\n1 - Voltar ao menu inicial\n2 - Sair do jogo")
        opcao = input("\nDigite a opção deseja: ")
        if opcao == "2":
            opcao = "3"
    elif opcao == "2": #Entrada em SOBRE o jogo.
        clear()
        print("{:=^50}".format(" Sobre o jogo "),"\n\n")
        print("1 - Voltar ao menu inicial\n2 - Sair do jogo")
        opcao = input("\nDigite a opção desejada: ")
        if opcao == "2":
            opcao = "3" 
    if (opcao == "3"): #Saída do jogo
        clear()
        print("Tchau!\nAté a próxima.")
