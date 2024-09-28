# -*- coding: utf-8 -*-
from Hidrometro import Hidrometro
from Interface import Interface
from Servidor import Servidor
import threading
import pickle


#Diretório
caminho_arquivo =  "./data/consumo.bin"

def carregar_dados() -> float:
    try: #Tenta abrir o arquivo que armazena o valor do consumo do usuário
        arquivo = open(caminho_arquivo, "rb")
        consumo = pickle.load(arquivo)
        arquivo.close()
        return consumo
    except : #Se o arquivo não existir, ou seja, se o cliente for novo, o consumo inicial será 0
        return 0.0

#Dados de entrada
endereco_servidor = str(input("Informe o endereço do servidor para o hidrômetro se conectar: "))
matricula_h = str(input("Insira a matrícula do hidrômetro: "))
usuario = str(input("insira o nome do cliente relacionado ao hidrômetro: "))

#Criação dos objetos
hidrometro = Hidrometro(nome_usuario=usuario, matricula=matricula_h, host=endereco_servidor, consumo=carregar_dados())
interface = Interface(hidrometro, "1200x600", "Hidrômetro")
servidor = Servidor(hidrometro=hidrometro, interface=interface)

#Criação das Threads
thread_contabilizar_gasto = threading.Thread(target=hidrometro.calcular_consumo)
thread_enviar_dados = threading.Thread(target=hidrometro.enviar_dados)

thread_servidor = threading.Thread(target=servidor.conectar)
thread_servidor.daemon = True

#Inicialização das Threads
thread_contabilizar_gasto.start()
thread_enviar_dados.start()
thread_servidor.start()

#Inicialização da interface
interface.iniciar_interface()

#Finalização das Threads
hidrometro.stop()  #Funciona para parar o contabilização do consumo
exit()