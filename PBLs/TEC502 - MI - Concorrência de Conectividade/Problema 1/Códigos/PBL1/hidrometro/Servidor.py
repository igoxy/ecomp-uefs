# -*- coding: utf-8 -*-
import socket
from Hidrometro import Hidrometro
from Interface import Interface


class Servidor:

    #CONSTANTES
    HOST = ''              # Endereco IP do Servidor
    PORT = 5035            # Porta que o Servidor está ouvindo

    #Flag de parada do servidor
    parar = 0

    def __init__(self, hidrometro: Hidrometro, interface: Interface):
        self.__hidrometro = hidrometro
        self.__interface = interface

    
    def conectar(self):
        """ Método para conectar o servidor do hidrômetro com o servidor de dados para o recebimento de informações do servidor """
        tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        orig = (self.HOST, self.PORT)
        tcp.bind(orig)
        tcp.listen(1)
        while True:
            try:
                con, cliente = tcp.accept()
                
                print ("Concetado por", cliente)
                while True:
                    msg = con.recv(1024)
                    if not msg: 
                        break
                    print (cliente, ": ", msg.decode("utf-8"))

                    if msg.decode("utf-8") == "1": #1 - indica deve desativar o cliente, ou seja, o fornecimento de água foi suspenso
                        self.__hidrometro.set_vazao(0)
                        self.__interface.desabilitar_vazao()
                        self.__interface.set_desligado(True)
                    elif msg.decode("utf-8") == "2" and self.__interface.get_desligado():
                        self.__interface.set_desligado(False)
                        self.__interface.habilitar_vazao()
                print ("Finalizando conexao do cliente", cliente)
                con.close()
            except:
                print("Erro na conexão")
    
    def stop(self):
        """ Método para parar o servidor do hidrômetro """
        self.parar = 1