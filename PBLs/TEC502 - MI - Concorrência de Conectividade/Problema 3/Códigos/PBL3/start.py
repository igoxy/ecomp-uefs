# -*- coding: utf-8 -*-

from flask import Flask, render_template, request, redirect, url_for, Response
from Pedido import Pedido
import pandas as pd
from pandas import DataFrame
import requests
import socket
import json
from time import sleep
import threading

# ----- Variáveis ------
token = 0                       # Token de permissão para acesso a região crítica
ativo = False                   # Indica que o sistema de token ring está ativo
headers = {'Content-type': 'application/json'}  # Cabeçalho requisição
servidores = []                 # Servidores das lojas
endereco_vizinho = []           # Armazena o endereço do marketplace vizinho para passar o token
fila_compras = []               # Fila de compras para serem processadas
itens_compra_atual = []         # Lista que guarda os itens a serem retirados do estoque da compra que está processando atualmente

# ----- Dataframes -----
df_estoque = None               # Dataframe para salvar os produtos e seus estoques
df_catalago = None              # Dataframe para salvar os produtos do catalago
df_vendidos = None              # Dataframe para salvar os produtos vendidos

# ------ APP FLASK ------
app = Flask(__name__)

administradores = [{'login': 'admin', 'senha': 'admin'}]    

# ----- Fim itens de teste ------

# ------ Métodos ADMIN ------
def login_administrador(login):
    for item in administradores:
        if (item['login'] == login['login'] and item['senha'] == login['senha']):       # Verifica se o usuário e senha é igual a algum cadastrado no sistema
            return True
    return False

# ----- Fim métodos ----

# -------------------------------- Página WEB --------------------------------
# ------ Página inicial ---------
@app.route("/", methods=['GET', 'POST'])     # Página inicial
def index():
    global produtos_catalago
    produtos_catalago = df_catalago.to_dict('index')
    if request.method == 'POST':
        if request.form.get('comprar') == 'Comprar':
            if len(request.form.getlist('produto')) > 0:            # Verifica se pelo menos um produto foi selecionado
                produtos = list(request.form.getlist('produto'))
                json_produtos = json.dumps(obter_hosts_itens(produtos))
                return redirect(url_for('compra', produtos=json_produtos))  # Retorna a página com tudo OK
            else:
                return render_template('index.html', produtos=produtos_catalago, erro=False, mensagem='Selecione pelo menos um item')  # Retorna a págia com uma mensagem de erro
    else:
        return render_template('index.html', produtos=produtos_catalago, erro=False, mensagem='')                      # Retorna a págia com a mensagem de tudo OK

def obter_hosts_itens(produtos: list) -> dict:
    produtos_dicio = {}
    this = str(socket.gethostbyname(socket.gethostname()))
    for item in servidores:
        produtos_dicio[item] = []       # Cria um dicionário com os hosts e a lista de itens
    produtos_dicio['this'] = []         # Adiona o marketplace atual (host) e a lista de itens para ele
    
    for produto in produtos:            # Percorre a lista de produtos selecionados pelo cliente | a lista tem apenas os códigos do produtos
        indice = __obter_indice_usuario(int(produto), df_catalago)
        host = df_catalago.iloc[indice]['host']
        if host == this:        # Se o host do produto for esse marketplace
            host = 'this'       # Substitui o IP do host pela marcação this
        for item in produtos_dicio.keys():              # Percorre o dicionário por meio das suas chaves
            if item == host:                            # Se encontrar o host do produto
                produtos_dicio[item].append(produto)    # Adiciona o produto a lista de produtos
    return produtos_dicio

# -------- FIM página inicial ------

# ------- Página de compra ------
@app.route("/carrinho/compra", methods=['GET', 'POST'])
def compra():
    global fila_compras
    produtos = json.loads(request.args['produtos'])     # Lista de produtos do cliente
    if (request.method == 'POST'):
        if request.form.get('enviar') == 'Confirmar':   # Verifica se o botão foi acionado
            cpf = request.form.get('cpf')               # Obtém o cpf inserido
            endereco = request.form.get('endereco')     # Obtém o endereço inserido
            email = request.form.get('email')           # Obtém o email inserido
            dados = {'cpf': cpf, 'endereco': endereco, 'email': email}    # Cria o dicionário de dados
            pedido = Pedido(produtos, dados)            # Cria um pedido
            fila_compras.append(pedido)                 # Adiciona o pedido a fila de pedidos
            while pedido in fila_compras:   # Verifica se o pedido ainda está na lista de pedidos a serem processados
                sleep(1)                    # Espera 1 segundo
            if pedido.aprovado:             # Verifica se o pedido foi aprovado ou não
                return redirect(url_for('compra_aprovada'))
            else:
                return render_template('compra.html', mensagem_erro = "Não foi possível concluir a compra. Verifique seu(s) item(ns) e tente novamente!" , produtos=json.dumps(produtos))
    else:
        return render_template('compra.html', produtos=json.dumps(produtos))

@app.route("/carrinho/compra_aprovada", methods=['GET'])
def compra_aprovada():
    return render_template('compra_aprovada.html')

# ------- Página administrador -------
@app.route("/adm_login", methods=['GET', 'POST'])
def adm_login():
    if (request.method == 'POST'):
        if request.form.get('enviar') == 'Entrar':      # Verifica se o botão foi acionado
            login = request.form.get('login')           # Obtém o login inserido
            senha = request.form.get('senha')           # Obtém a senha inserida
            dados = {'login': login, 'senha': senha}    # Cria o dicionário de dados
            resposta = login_administrador(dados)       # Cria o dicionário de dados
            if (resposta):   
                return redirect(url_for('adm_home'))    # Se foi True redireciona para a página inicial do adm
            else:                                       # Se ocorrer um erro
                return render_template('adm_login.html', mensagem_erro = "Usuário ou senha incorreta. Tente novamente.")    # Mostra a mensagem de erro
    else:
        return render_template('adm_login.html')

@app.route("/adm_home", methods=['GET', 'POST'])
def adm_home():
    global produtos_estoque
    produtos_estoque = df_estoque.to_dict('index')
    if request.method == 'POST':
        if request.form.get('botao') == 'Adicionar item':
            return redirect(url_for('add_item'))
        elif request.form.get('botao') == 'Editar item':
            codigo = int(request.form.get('produto'))     # Obtém o código do produto selecionado
            return redirect(url_for('editar_item', codigo=codigo))
        elif request.form.get('botao') == 'Itens vendidos':
            return redirect(url_for('produtos_vendidos'))
        else:
            return render_template('adm_home.html', produtos=produtos_estoque)
    else:
        return render_template('adm_home.html', produtos=produtos_estoque)

@app.route("/adm_home/produtos_vendidos", methods=['GET', 'POST'])
def produtos_vendidos():
    produtos_vendidos = df_vendidos.to_dict('index')
    if request.method == 'POST':
        if request.form.get('botao') == 'Voltar':
            return redirect(url_for('adm_home'))
        else:
            return render_template('produtos_vendidos.html', produtos=produtos_vendidos)
    else:
        return render_template('produtos_vendidos.html', produtos=produtos_vendidos)
        

@app.route("/adm_home/add_item", methods=['GET', 'POST'])
def add_item():
    if request.method == 'POST':
        if request.form.get('botao') == 'Adicionar':
            new_codigo = int(request.form.get('codigo'))                # Obtém o nome inserido
            new_nome = request.form.get('nome')                         # Obtém o nome inserido
            new_preco = float(request.form.get('preco'))                # Obtém o preço inserida
            new_quantidade = int(request.form.get('quantidade'))        # Obtém o preço inserida
            
            new_item_estoque = {'codigo': new_codigo, 'nome': new_nome, 'preco': new_preco, 'quantidade': new_quantidade}   # Cria um dicionário com as informações dos itens para armazenar no estoque
            new_item_catalago = {'codigo': new_codigo, 'nome': new_nome, 'preco': new_preco, 'host': str(socket.gethostbyname(socket.gethostname()))}   # Cria um dicionário para as informações dos itens para armazenar no catálago
            resultado = salvar_produto(new_item_catalago, new_item_estoque)     # Salva os produtos
            
            if resultado:
                return render_template('add_item.html', mensagem='Produto cadastrado com sucesso!')
            else:
                return render_template('add_item.html', mensagem='Não foi possível cadastrar o produto!')
        elif request.form.get('botao') == 'Voltar':                     # Caso clique no botão de voltar
            return redirect(url_for('adm_home'))
    else:
        return render_template('add_item.html')

@app.route("/adm_home/editar_item/<codigo>", methods=['GET', 'POST'])
def editar_item(codigo):
    indice = __obter_indice_usuario(codigo, df_estoque)
    if request.method == 'POST':
        if request.form.get('botao') == 'Salvar':
            at_nome = request.form.get('nome')                    # Obtém o nome inserido
            at_preco = float(request.form.get('preco'))           # Obtém o preço inserida
            at_quantidade = int(request.form.get('quantidade'))    # Obtém o preço inserida
            resposta = editar_produto(int(codigo), at_quantidade)
            if resposta:
                return render_template('editar_item.html', codigo=codigo, nome=at_nome, preco=at_preco, quantidade=at_quantidade, mensagem='Produto atualizado com sucesso!')
            else:
                old_quantidade = int(df_estoque.iloc[indice]['quantidade'])
                return render_template('editar_item.html', codigo=codigo, nome=at_nome, preco=at_preco, quantidade=old_quantidade, mensagem='Não possível atualizar o produto!')
        elif request.form.get('botao') == 'Voltar':
            return redirect(url_for('adm_home'))
        else:
            return render_template('editar_item.html', codigo=codigo, nome=nome_produto, preco=preco_produto, quantidade=at_quantidade, mensagem='')
    else:
        nome_produto = str(df_estoque.iloc[indice]['nome'])
        preco_produto = float(df_estoque.iloc[indice]['preco'])
        quantidade_produto = int(df_estoque.iloc[indice]['quantidade'])
        return render_template('editar_item.html', codigo=codigo, nome=nome_produto, preco=preco_produto, quantidade=quantidade_produto, mensagem='')

def editar_produto(codigo, quantidade):
    try:
        indice = __obter_indice_usuario(codigo, df_estoque)                 # Obtém o indice do item no dataframe
        df_estoque.at[indice, 'quantidade'] = quantidade                    # Atualiza a quantidade
        df_estoque.to_csv('estoque.csv', sep=',', index=False)              # Atualiza o arquivo csv
        return True                                                         # Se o produto foi atualizado com sucesso
    except:                                                                 # Se ocorrer algum erro
        return False                                                        # Retorna False

# ------- Página administrador -------
# -------------------------------- Fim Página WEB --------------------------------

# -------------------------------- API --------------------------------
# Rotas da API
@app.route("/api/receber_produto",  methods=['POST'])
def receber_produto():
    produto = request.get_json()
    resultado = salvar_produto_recebido(produto)
    if resultado:
        return Response(status=200)     # Caso consiga salvar o produto
    else:
        return Response(status=500)     # Caso ocorra algum erro

@app.route("/api/compra/solicitacao", methods=['PUT'])
def compra_solicitacao():
    itens = json.loads(request.get_json())          # Obtém os itens
    resp = __verificacao_estoque_itens(itens)   # Verifica se os itens tem estoque
    if resp:                                    # Se a resposta for verdade
        return Response(status=200)             # Responde com OK
    else:                                       # Senão
        return Response(status=500)             # Responde com erro

@app.route("/api/compra/retirar_estoque", methods=['PUT'])
def retirar_estoque():
    try:
        infos = json.loads(request.get_json())
        if (infos['comando'] == 2):                 # Se o comando for para não retirar do estoque
            itens_compra_atual.clear()              # Limpa a lista de itens
            return Response(status=200)             # Responde com OK
        else:
            resposta = __remover_itens_estoque(infos['dados'])  # Remover os itens do estoque
            if resposta:
                return Response(status=200)         # Responde com OK
    except Exception as ex:
        return Response(status=500)                 # Caso ocorra algum erro


# -------------------------------- Métodos da API --------------------------------
# Métodos da API
def salvar_produto(produto_catalago: dict, produto_estoque: dict):      # !!! THREAD?
    resultado = __enviar_item_cadastrado(produto_catalago)                              # Envia o produto cadastrado para os outros sites
    if resultado:                                                                       # Se o resultado for True, armazena o novo produto
        global df_estoque
        global df_catalago

        df_new_estoque = pd.DataFrame(produto_estoque, index=[0])
        df_estoque = pd.concat([df_new_estoque, df_estoque]).reset_index(drop=True)     # Adiciona o item ao dataframe de estoque   --  Ordem: cod | nome | preco | quantidade
        df_estoque.to_csv('estoque.csv', sep=',', index=False)                          # Salva o dataframe de estoque atualizado
        
        df_new_catalago = pd.DataFrame(produto_catalago, index=[0])
        df_catalago = pd.concat([df_new_catalago, df_catalago]).reset_index(drop=True)   # Adiciona o item ao dataframe de catalago --  Ordem: cod | nome | preco | host
        df_catalago.to_csv('catalago.csv', sep=',', index=False)                         # Salva o dataframe de catalago atualizado
    return resultado                                                                     # retorna o resultado

def salvar_produto_recebido(produto: dict):          # Ordem: cod | nome | preco | host
    try:
        global df_catalago
        df_new_produto = pd.DataFrame(produto, index=[0])                               # Cria um dataframe para o item ser adicionado ao dataframe de catálago
        df_catalago = pd.concat([df_new_produto, df_catalago]).reset_index(drop=True)   # Adiciona o item ao dataframe de catálogo --  Ordem: cod | nome | preco | host
        df_catalago.to_csv('catalago.csv', sep=',', index=False)                        # Salva o dataframe de catalago atualizado
        return True                                                 # Retorna True
    except:
        return False                                                # Retorna False

def carregar_catalago():
    """ Método responsável por carregar o catálago de produtos """
    try:
        df_catalago = pd.read_csv('catalago.csv')
    except:
        df_catalago = pd.DataFrame(columns=['codigo', 'nome', 'preco', 'host'])
        df_catalago.to_csv('catalago.csv', sep=',', index=False)
    finally:
        return df_catalago

def carregar_estoque():
    """ Método responsável por carregar o estoque de produtos """
    try:
        df_estoque = pd.read_csv('estoque.csv')
    except:
        df_estoque = pd.DataFrame(columns=['codigo', 'nome', 'preco', 'quantidade'])
        df_estoque.to_csv('estoque.csv', sep=',', index=False)
    finally:
        return df_estoque

def carregar_vendidos():
    """ Método responsável por carregar os produtos vendidos """
    try:
        df_vendidos = pd.read_csv('vendidos.csv')
    except:
        df_vendidos = pd.DataFrame(columns=['codigo', 'nome', 'preco', 'quantidade', 'cpf', 'endereco']) # CPF e Endereço é do comprador
        df_vendidos.to_csv('vendidos.csv', sep=',', index=False)
    finally:
        return df_vendidos

def __enviar_item_cadastrado(produto_catalago: dict):
    resultado = True                # Inicializa como True
    for servidor in servidores:     # Percorre todos os servidores das lojas
        resposta = requests.post(f'http://{servidor}:5000/api/receber_produto', json=produto_catalago, headers=headers)  # Envia o produto para as lojas e aguarda a resposta  -- Ordem: cod | nome | preco | host
        if resposta.status_code != 200:                                                 # Se a resposta for diferente de OK
            resultado = False                                                           # Atribui False a resultado
            break                                                                       # Encerra o loop
    return resultado

# -------------------------------- Fim API --------------------------------

# ------------------------------- Sistema distribuído --------------------------------
@app.route("/sistema/cadastrar_servidores", methods=['POST', 'GET'])
def cadastrar_servidores():
    if (request.method == 'POST'):
        if request.form.get('enviar') == 'Entrar':  # Verifica se o botão foi acionado
            ip = request.form.get('ip')         # Obtém ip inserido
            servidores.append(ip)               # Adiciona o IP a lista de sevidores para poder enviar o catálago 
            if len(endereco_vizinho) < 1:
                endereco_vizinho.append(ip)     # Armazena o IP do vizinho
                mensagem = "O IP foi armazenado como vizinho com sucesso!"
            else:
                mensagem = "O IP foi armazenado com sucesso!"
            return render_template('cadastrar_servidores.html', mensagem_erro=mensagem)    # Mostra a mensagem de erro
        else:
            return render_template('cadastrar_servidores.html')
    else:
        return render_template('cadastrar_servidores.html')

@app.route("/sistema/ativar_sistema", methods=['POST', 'GET'])
def ativar_sistema():
    if (request.method == 'POST'):
        if request.form.get('ativar') == 'Ativar':  # Verifica se o botão foi acionado
            if (ativo):             # Verifico se o sistema já está ativo
                return render_template('ativar_sistema.html', mensagem_erro="Não é possível fazer isso. O sistema já está ativo!")    # Tentativa de ativar o sistema com o sistema já ativo
            else:
                resultado = iniciar_token_ring()        # Ativa a passagem de token
                if resultado:                
                    return render_template('ativar_sistema.html', mensagem_erro="Sistema ativo!")    # Sistema ativo
                else:
                    return render_template('ativar_sistema.html', mensagem_erro="Erro ao ativar o sistema. Verifique se o nó já tem o endereço do seu vizinho!")   # Erro ao tentar iniciar o sistema
        else:
            return render_template('ativar_sistema.html')
    else:
        return render_template('ativar_sistema.html')

@app.route("/sistema/token", methods=['PUT'])
def recebe_token():
    token_recebido = request.get_json()
    if token_recebido['Token'] == 1:
        global token
        token = 1
        usar_token = threading.Thread(target=utilizar_token)    # Cria uma thread para utilizar a região crítica
        usar_token.daemon = True            # Encerra a thread se o programa principal for fechado
        usar_token.start()                  # Incia a thread
        return Response(status=200)         # Caso consiga obter o token
    else:
        return Response(status=500)         # Caso tenha algum problema com o token

# ------------------------------- Métodos sistema distribuído -------------------------
def iniciar_token_ring():
    """ Inicia o sistema de passagem de token """
    if len(endereco_vizinho) > 0:
        resposta = requests.put(f'http://{endereco_vizinho[0]}:5000/sistema/token', json={"Token": 1}, headers=headers)
        if resposta.status_code == 200:
            global ativo        
            ativo = True            # Identifica que o sistema de token ring está ativo
            return True
    return False

def utilizar_token():
    """ Utiliza o token - região crítica """
    global token
    global fila_compras
    if token == 1:                  # Verifica se está com o token
        if len(fila_compras) > 0:   # Verifica se há compras na fila para ser processada
            processar_compra()      # Processa a compra
        else:                       # Se não houver compras para serem processadas, passa o token
            sleep(0.5)              # Dorme por 0.5 segundo antes de enviar o token
            passar_token()          # Passa o token

def passar_token():
    """ Envia o token para o vizinho """
    global token
    token_passado = False           # Veriável de controle
    while not token_passado:        # Verifica se o token já foi passado
        resposta = requests.put(f'http://{endereco_vizinho[0]}:5000/sistema/token', json={"Token": 1}, headers=headers)
        if resposta.status_code == 200: # Se o token for passado
            token = 0                   # Define o token para 0 (ou seja, o token não está mais neste nó)
            token_passado = True        # Indica que o token foi passado
            print("Token passado com sucesso!")
        else:                           # Caso não consiga passar o token
            print("Não foi possível passar o Token.")
            sleep(2)                    # Aguarda 2 segundos e tenta passar o token novamente
            print("Tentando passar o Token novamente!")

def processar_compra():
    """ Processa a compra do inicio da fila """
    global fila_compras                                         # Fila que armazena as compras
    pedido = fila_compras[0]                                    # Obtém o primeiro pedido da fila
    itens = pedido.itens                                        # Obtém os itens do pedido
    dados = pedido.dados                                        # Obtém os dados do cliente do pedido
    resposta = __enviar_solicitacao_compra(itens)               # Faz a solicitação de compra - verifica se tem os itens em estoque
    if resposta:                                                # Verifica a solicitação de compra pode ser atendida
        resultado = __retirar_itens_estoque(itens, dados, 1)    # Solicita a remoção dos itens do estoque - o 1 significa que o item deve ser removido
        if resultado:
            pedido.aprovado = True                              # Pedido aprovado
        else:
            pedido.aprovado = False                             # Pedido negado
    else:
        resultado = __retirar_itens_estoque(itens, dados, 2)    # Cancela a remoção dos itens do estoque - o 2 significa que os itens não devem ser removidos, compra cancelada
        pedido.aprovado = False             # Pedido negado
    fila_compras.pop(0)                     # Remove o primerio item da fila - concluí a compra
    passar_token()                          # Passa o token

# ------ Solicitação de compra -----
def __enviar_solicitacao_compra(hosts_itens: dict):
    for host in hosts_itens.keys():
        if host != 'this':
            resposta = requests.put(f'http://{host}:5000/api/compra/solicitacao', json=json.dumps(hosts_itens[host]), headers=headers)   # Envia como lista
            if resposta.status_code != 200:     # Verifica se o o marketplace enviou um OK para a compra dos itens solicitados
                return False                    # Caso não tenha mandado OK, cancela a compra. 
        else:
            resp = __verificacao_estoque_itens(hosts_itens[host])    # Verifica se o item do marketplace tem estoque
            if not resp:             # Se a reposta for False
                return False         # A compra não pode ser concluída porque não há estoque do produto
    return True                      # Se todos os marketplaces derem OK

# ------ Verifica se o marketplace tem estoque -------
def __verificacao_estoque_itens(itens: list):
    """ Verifica se os itens solicitados tem estoque """
    global df_estoque               # DataFrame de estoque
    global itens_compra_atual       # Variável que armazena os possíveis itens a serem removidos do estoque
    itens_compra_atual = itens      # Recebe a lista de itens para se a compra for confirmada, remover do estoque
    for item in itens:              # Percorre os itens das compras
        indice_item = __obter_indice_usuario(item, df_estoque)      # Obtém o indice do item no DataFrame
        if (int(df_estoque.iloc[indice_item]['quantidade']) < 1):   # Verifica se o item tem estoque, ou seja, se o estoque não é menor que 1
            return False                                            # Retorna False
    return True                                                     # Se todos os itens tiverem estoque, retorna True

# ------- Solicita a remoção dos itens do estoque dos marketplaces ------
def __retirar_itens_estoque(hosts_itens: dict, dados: dict, comando: int):
    """ Envia a informação para os marketplaces para a quantidade dos itens do estoque"""
    for host in hosts_itens.keys():
        if host != 'this':
            resposta = requests.put(f'http://{host}:5000/api/compra/retirar_estoque', json=json.dumps({'comando': comando, 'dados': dados}), headers=headers)   # Envia o comando de remover itens do estoque - 1 = retirar itens do estoque | 2 = não remover itens e limpar lista de itens da compra
            if resposta.status_code != 200:     # Verifica se o o marketplace enviou um OK para a compra dos itens solicitados
                return False                    # Caso não tenha mandado OK, cancela a compra. 
        else:
            if comando == 2:                    # Verifica se o comando informado foi 2
                global itens_compra_atual       # Itens da compra atual
                itens_compra_atual.clear()      # Limpa a lista de compras atual
            else:
                resp = __remover_itens_estoque(dados)    # Remove do estoque
                if not resp:             # Se a reposta for False
                    return False         # A compra não pode ser concluída porque não foi possível tirar os itens do estoque
    return True                      # Se todos os marketplaces derem OK

# ----- Remove os itens do estoque ------
def __remover_itens_estoque(dados: dict):
    """ Remove os itens da compra do estoque e adiciona ao dataframe de itens vendidos """
    try:
        global itens_compra_atual       # Lista de itens da compra que está sendo processada atualmente - os itens são os códigos do produtos
        global df_estoque               # DataFrame de estoque de produtos
        for item in itens_compra_atual:                                         # Percorre a lista de compras
            indice = __obter_indice_usuario(item, df_estoque)                   # Obtém o indice do item no dataframe
            if not __item_vendido(indice, df_estoque, dados):                   # Verifica se tudo ocorreu bem
                return False                                                    # Senão, retorna falso
            quantidade_atual = int(df_estoque.iloc[indice]['quantidade'])       # Obtém a quantidade atual do item
            df_estoque.at[indice, 'quantidade'] = quantidade_atual - 1                 # Remove 1 da quantidade do item - estoque é a quarta coluna (logo igual index 3)
        df_estoque.to_csv('estoque.csv', sep=',', index=False)                  # Atualiza o arquivo csv
        itens_compra_atual.clear()                                              # Limpa o vetor de itens    
        return True
    except:
        return False

# ------- Adiciona o item removido ao dataframe de itens vendidos --------
def __item_vendido(indice: int, df_estoque: DataFrame, dados: dict):
    """ Adiciona o item ao dataframe de itens vendidos """ 
    try:
        global df_vendidos
        itens = {'codigo': int(df_estoque.iloc[indice]['codigo']), 'nome': str(df_estoque.iloc[indice]['nome']), 'preco': float(df_estoque.iloc[indice]['preco']), 'quantidade': 1, 'cpf': dados['cpf'], 'endereco': dados['endereco']}
        df_new = pd.DataFrame(itens, index=[0])
        df_vendidos = pd.concat([df_new, df_vendidos]).reset_index(drop=True)     # Adiciona o item ao dataframe de vendidos --  Ordem: cod | nome | preco | quantidade | cpf | endereco
        df_vendidos.to_csv('vendidos.csv', sep=',', index=False)                  # Salva o dataframe de catalago atualizado
        return True
    except Exception as ex:
        return False

# ----- Obtém o indice dos usuários ------
def __obter_indice_usuario(codigo: int, df: DataFrame):
        """ Método responsável por retornar o indice do usuário no DataFrame """
        return (df.index[df['codigo'] == int(codigo)]).tolist()[0]
# ------------------------------- Fim Sistema distribuído --------------------------------

# ------------------------------- Métodos processamento de compras -------------------------------
# Roda app
if __name__ == '__main__':
    df_catalago = carregar_catalago()
    df_estoque = carregar_estoque()
    df_vendidos = carregar_vendidos()

    app.run(host='0.0.0.0', debug=False)