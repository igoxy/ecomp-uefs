# -*- coding: utf-8 -*-
import pandas as pd
from pandas import DataFrame
import socket
import json
import threading

# portas: 5006 a 5009

class ReceiveServidor():

    __colecao_threads = {}  #pool de threads

    def __init__(self, host: str, port: int):
        self.__host = host                                              # Endereço (IP) do servidor
        self.__port = port                                              # Porta que servidor ouvirá
        self.__tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Cria um socket IPv4 e TCP


    def start(self):
        """ DOC """
        
        endpoint = (self.__host, self.__port)                           # Endereço e porta do servidor
        try:
            self.__tcp.bind(endpoint)                                   # Seta o endereço e porta do servidor
            self.__tcp.listen()                                         # Especifica o número de conexões não aceitas que o sistema permitirá antes de recusar novas conexões.
            print(f"Servidor de dados iniciado na porta: {self.__port}")
            while True:
                conexao, cliente = self.__tcp.accept()                  # Aguarda uma nova conexão com o servidor
                self.__colecao_threads[cliente] = threading.Thread(     # Cria uma thread para a conexão de cada hidrômetro 
                    target=self.__get_Dados, args=(conexao, cliente))
                self.__colecao_threads[cliente].start()                 # Inicia a thread para a conexão de cada hidrômetro
        except Exception as ex:                                         # Caso ocorra algum erro durante a conexão de um hidrômetro 
            print("Erro ao iniciar o servidor. Causa: ", ex.args)       # Informa o erro

# --------- Métodos privados ----------
    def __get_Dados(self, conexao, cliente):
        """ Recebe os dados do hidrômetro """
        try:
            while True:
                dados = conexao.recv(1024)                              # Recebe os dados do hidrômetro por meio do socket
                if not dados:                                           # Se o cliente (hidrômetro) for desconetado/desligado não há mais mensagens chegando
                    break                                               # O while é encerrado e a thread de conexão do servidor com aquele hidrômetro é encerrada
                print(cliente, dados.decode("utf-8"))               
                dados_json = json.loads(dados.decode("utf-8"))          # Converte a string no padrão Json em dicionário
                self.__armazenarDados(dados_json)                       # Chama o método para o armazenamento dos dados recebidos
                
            print ("Finalizando conexao do cliente ", cliente)
            conexao.close()                                             # Finaliza a conexão com do cliente
        except Exception as ex:                                         # Caso ocorra algum erro na conexão
            print("Erro na conexão. Causa: ", ex.args)                  # Informa o erro
            conexao.close()                                             # Fecha a conexão
    
    def __armazenarDados(self, dados: dict):
        """ Método responsável por armazenar os dados recebidos dos hidrômetros no banco """
        try:
            df = pd.read_csv(f"{dados['matricula']}.csv", sep=',')                  # Tenta abrir o arquivo csv relacionado a matricula
            dados['consumoFatura'] = self.__consumo_fatura(df, dados, False)        
            dados['valor'] = self.__gasto_fatura(df, dados, 1.0, False)
            dados['contaGerada'] = False
            dados['pago'] = "-"
            self.__alerta_vazamento(dados)                                          # Verifica se há um alerta de possível vazamento do endereço recebido
            df = self.__inserir_dados(df, dados, False)                             # Adiciona os dados recebidos do hidrômetro na base de dados                                            
            df.to_csv(f"{dados['matricula']}.csv", sep=',', index=False)            # Salva os dados em uma arquivo csv
        except FileNotFoundError:  
            dados['consumoFatura'] = self.__consumo_fatura (None, dados, True)      # Se não existir um arquivo de dados associado ao cliente (matricula)
            dados['contaGerada'] = False 
            dados['pago'] = "-"
            dados['valor'] = self.__gasto_fatura(None, dados, 1.0, True)            # Adiciona as informações se a conta foi gerada a partir dessa leitura e se está paga
            self.__alerta_vazamento(dados)                                          # Verifica se há um alerta de possível vazamento do endereço recebido
            df = self.__inserir_dados(None, dados, True)                            # Cria um novo dataframe                           
            df.to_csv(f"{dados['matricula']}.csv", sep=',', index=False)            # Cria um arquivo csv com os dados
        except Exception as ex:                                                     # Caso ocorra algum erro inesperado
            print("Erro ao armazenar dados. Causa: ", ex.args)                      # Informa o erro

    def __inserir_dados(self, df: DataFrame, dados: dict, primeiro_valor: bool):
        """ Método responsável por inserir os dados no dataframe """
        try:
            if primeiro_valor:                                                         # Se for o primeiro valor a ser inserido no banco de dados
                df = pd.DataFrame(dados, index=[0])                                    # Cria um novo dataframe para os dados
                return df                                                              # Retorna o dataframe
            else:                                                                      # Senão for o primerio dado a ser inserido no banco de dados
                nova_linha = pd.DataFrame(dados, index=[0])                            # Cria a nova linha a ser inserida na tabela de dados                                       
                df = pd.concat([nova_linha, df[:]]).reset_index(drop=True)             # Insere os dados recebidos no inicio da lista - Concatena
                return df                                                              # Retorna o dataframe novo
        except Exception as ex:                                                        # Caso ocorra um erro ao adicionar o novo dado no dataframe
            print("Erro ao inserir dados no DataFrame. Causa", ex.args)                     
            return None                                                                # Retorna um elemento None

    def __gasto_fatura(self, df: DataFrame, dados: dict, taxa: float, primeiro_valor: bool):
        """ Método responsável por fazer o calculo do gasto """
        if (primeiro_valor):                                                                    # Se for o primerio valor a ser inserido no banco de dados
            valor_gasto = float(dados['consumo'])*taxa
            return valor_gasto
        else:                                                                                   # Caso não seja o primerio valor a ser inserido no banco de dados
            medida_antiga = bool(df.iloc[0]['contaGerada'])
            if medida_antiga:
                valor_gasto = (float(dados['consumo']) - float(df.iloc[0]['consumo']))*taxa     # Encontra o valor gasto atual (caso o valor anterior tenha sido contabilizado para uma fatura anterior)
                return valor_gasto
            else:
                valor_gasto = ((float(dados['consumo'])) - (float(df.iloc[0]['consumo']))*taxa) + float(df.iloc[0]['valor'])  # Encontra o valor gasto atual (caso o valor anterior não tenha sido contabilizado para uma fatura anterior)
                return valor_gasto
    
    def __consumo_fatura(self, df: DataFrame, dados: dict, primeiro_valor: bool):
        """ Método responsável por computar o consumo da fatura atual do cliente """
        if (primeiro_valor):                                                                    # Se for o primerio consumo a ser inserido no banco de dados
            consumo_fatura = float(dados['consumo'])
            return consumo_fatura
        else:                                                                                   # Caso não seja o primerio consumo a ser inserido no banco de dados
            medida_antiga = bool(df.iloc[0]['contaGerada'])
            if medida_antiga:
                consumo_fatura = float(dados['consumo']) - float(df.iloc[0]['consumo'])         # Encontra o consumo atual (caso o valor anterior tenha sido contabilizado para uma fatura anterior)
                return consumo_fatura
            else:  
                consumo_fatura = (float(dados['consumo']) - float(df.iloc[0]['consumo'])) + float(df.iloc[0]['consumoFatura'])    # Encontra o consumo atual (caso o valor anterior não tenha sido contabilizado para uma fatura anterior)
                return consumo_fatura
    
    def __alerta_vazamento(self, dados: dict):
        """ Método responsável por identificar mensagens de possíveis vazamentos recebidas do hidrômetro """
        if (bool(dados['possivelVazamento'])):                                                  # Verifica se recebeu o alerta de possível vazamento
            df = pd.read_csv("usuarios.csv", sep=',')                                           # Abre o arquivo do banco de dados dos usuários
            indice_usuario = (df.index[df['matricula'] == int(dados['matricula'])]).tolist()[0] # Obtém o indice que refencia o usuário no dataframe de usuários
            df.at[indice_usuario, 'possivelVazamento'] = True                                   # Indica que há um possível vazamento no endereço do usuário
            df.to_csv("usuarios.csv", sep=',', index=False)                                     # Salva as informações no banco de dados csv
        else:
            df = pd.read_csv("usuarios.csv", sep=',')                                           # Abre o arquivo do banco de dados dos usuários
            indice_usuario = (df.index[df['matricula'] == int(dados['matricula'])]).tolist()[0] # Obtém o indice que refencia o usuário no dataframe de usuários
            df.at[indice_usuario, 'possivelVazamento'] = False                                  # Indica que há um possível vazamento no endereço do usuário
            df.to_csv("usuarios.csv", sep=',', index=False)                                     # Salva as informações no banco de dados csv