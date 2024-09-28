''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Autor: Igor Figueredo Soares
Componente Curricular: Algoritmos I
Concluido em: 27/03/2020
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação. 
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

# -*- coding: utf-8 -*-

''' Bibliotecas e métodos importados '''

import hashlib
from os import system, name
import pickle


''' Inicialização das variávies, listas e dicionários'''

opcao = None
l_tarefas = [{}, {}, {}]
contador_id = 0


''' Funções e Módulos '''

def limpar_tela():
    ''' Limpa a tela '''
    if name == 'nt':
        system('cls')  # Limpar tela no Windows
    else:
        system('clear')  # Limpar tela no Linux

def menu_principal():
    ''' Opções do menu inicial '''
    print('{:=^30}'.format(' Menu principal '))
    print('1 - Cadastrar novo usuário\n2 - Logar no sistema\n3 - Sair do programa')
    opcao = input('Insira a opção desejada: ')
    limpar_tela()
    return opcao

def cadastrar_usuario_menu():
    ''' Menu de cadastro de usuário '''
    verificador = 1  # Verificador utilizado para indicar se o nome de usuário já foi cadastrado [1 = Sim; 0 = Não]
    while verificador == 1:
        print('{:=^50}'.format(' Cadastrar novo usuário '))
        nome = input('Insira o nome de usuário: ').upper()
        senha = input('Insira a senha: ')
        verificador = verificador_usuario(nome)  # Verifica se o nome de usuário existe
        if verificador == 1:
            print('\nNome de usuário já existente, tente outro!')
            input('Tecle enter para tentar novamente.')
            limpar_tela()
    u = Usuario(nome, senha)  # Cria o objeto usuário
    u.cadastrar_usuario()  # Cadastra o usuário no sistema

def logar_sistema_menu():
    print('{:=^20}'.format(' Logar no sistema '))
    usuario_login = input('Nome de usuário: ').upper()
    senha_login = input('Senha: ')
    user_login = Usuario(usuario_login, senha_login)  # Instância os dados do usuário para efetuar o login
    verificador_login = user_login.fazer_login()  # Tenta fazer o login do usuário        
    return verificador_login, usuario_login

def menu_de_tarefas():
    ''' Menu de opções das tarefas '''
    limpar_tela()
    print('{:=^50}'.format(' Menu de tarefas '))
    print('1 - Cadastrar nova Tarefas\n2 - Visualizar Tarefas\n3 - Alterar Tarefa\n4 - Excluir Tarefa\n5 - Sair')
    opcao_menu_tarefas = input('Insira a opção desejada: ')
    return opcao_menu_tarefas

def verificador_usuario(nickname):
    ''' Verifica se o nome de usuário já esta cadastrado '''
    try:
        verificador = 0  # Indicador de existência do nome de usuário - 0 = não existe; 1 = existe
        cadastrados = open('usuarios.txt', 'r')  # Abertura do arquivo
        #print(cadastrados.read())
        for linha in cadastrados:
            veri = linha.split('->')  # Variável utilizada para a verificação
            if veri[0] == nickname:
                verificador = 1  # Atribuição pra indicar que o nome do usuário já existe
        cadastrados.close()  # Fecha o arquivo
    except FileNotFoundError:
        verificador = 0  # Indicador que usuário não existe
    return verificador

def verificar_senha_usuario(usuario, senha):
    ''' Verifica se a senha e o usuário correspondem '''
    try:
        verificador = 0  # Indica se os dados inseridos são válidos - 0 = não válidos; 1 = válidos
        cadastrados = open('usuarios.txt', 'r')  # Abertura do arquivo
        for linha in cadastrados:
            veri2 = (linha.rstrip('\n')).split('->')  # Variável utilizada para a verificação
            if veri2[0] == usuario and veri2[1] == senha:
                verificador = 1  # Atribuição pra indicar que os dados são verdadeiros
        cadastrados.close()  # Fecha o arquivo
    except FileNotFoundError:
        verificador = 0  # Indica que os dados não existem, ou seja, não foram cadastrados ainda
    return verificador

def gerar_id(nome_usuario):
    ''' Verifica o arquivo de tarefas existe, caso exista, verifica os IDs das tarefas cadastradas
    e gera o ID para o cadastro de uma nova tarefa. Se o arquivo de tarefa não existir, 
    ele é criado e o primeiro ID é gerado ''' 
    
    try:   # Tenta abrir o arquivo binário para verificar a existência do arquivo
        arquivo_tarefa = open('{}.dat'.format(nome_usuario), 'rb')  # Abre o arquivo binário de tarefas
        arq_tarefas = pickle.load(arquivo_tarefa)
        try:
            verificar_id1 = arq_tarefas[0].keys()  # Pega os IDs das tarefas de alta prioridade do usuário
            id_contador1 = max(verificar_id1)  # Encontra o ultimo ID utilizado na fila de alta prioridade
        except ValueError:  # Se não existir um valor retorna 0
            id_contador1 = 0
        try:  # Verifica se há tarefas de prioridade média
            verificar_id2 = arq_tarefas[1].keys()  # Pega os IDs das tarefas de média prioridade do usuário
            id_contador2 = max(verificar_id2)  # Encontra o ultimo ID utilizado na fila de média prioridade
        except ValueError:  # Se não existir um valor retorna 0
            id_contador2 = 0
        try:  # Verifica se há tarefas de prioridade média
            verificar_id3 = arq_tarefas[2].keys()  # Pega os IDs das tarefas de baixa prioridade do usuário
            id_contador3 = max(verificar_id3)  # Encontra o ultimo ID utilizado na fila de média prioridade
        except ValueError:
            id_contador3 = 0
        arquivo_tarefa.close()  # Fecha o arquivo binário de tarefas
        id_contador = max([id_contador1, id_contador2, id_contador3])  # Encontra o valor da ultima ID utilizada de todas as tarefas
    except FileNotFoundError:  # Se o arquivo não existir ele é criado
        arq_tarefas = open('{}.dat'.format(nome_usuario), 'wb')  # Abre o arquivo binário
        lista_tarefas = [{}, {}, {}]  # Dicionário 1: Alta prioridade; Dicionário 2: Média prioridade; Dicionário 3: Baixa prioridade
        pickle.dump(lista_tarefas, arq_tarefas)  # Insere o arquivo os dados no arquivo binário
        arq_tarefas.close()  # Fecha o arquivo binário de tarefas
        id_contador = 0
    return id_contador + 1

def adicionar_tarefa(usuario, tarefa):
    ''' Adiciona a tarefa ao arquivo de tarefas ''' 
    arq = open('{}.dat'.format(usuario), 'rb')
    lis_tarefa = pickle.load(arq)
    arq.close()
    if tarefa.prioridade == 'Alta':
        lis_tarefa[0][tarefa.id_tarefa] = tarefa
    elif tarefa.prioridade == 'Média':
        lis_tarefa[1][tarefa.id_tarefa] = tarefa
    elif tarefa.prioridade == 'Baixa':
        lis_tarefa[2][tarefa.id_tarefa] = tarefa
    arq2 = open('{}.dat'.format(usuario), 'wb')
    pickle.dump(lis_tarefa, arq2)
    arq2.close()

def cadastrar_tarefa(nome_usuario):
    ''' Cadastrar nova tarefa '''
    limpar_tela()
    titulo = input('Insira o título da terefa: ')
    descricao = input('Insira a descrição da tarefa: ')
    prioridade = 0
    while prioridade < 1 or prioridade > 3:
        print('Prioridade:\n1 - Alta\n2 - Média\n3 - Baixa')
        prioridade = int(input('Insira a prioridade: '))
        if prioridade < 1 or prioridade > 3:
            input('Opção inválida. Tecle enter para tentar novamente!')
    if prioridade == 1:
        prioridade = 'Alta'
    elif prioridade == 2:
        prioridade = 'Média'
    elif prioridade == 3:
        prioridade = 'Baixa'
    contador_id = gerar_id(nome_usuario)  # Indica o ID da tarefa
    tarefa1 = Tarefa(prioridade, titulo, descricao, contador_id)  # Cria a nova tarefa
    adicionar_tarefa(nome_usuario, tarefa1)  # Adiciona a tarefa a lista de tarefas do usuário

def visualizar_tarefa(tarefas_dicio, id_tarefas, tipo_prioridade):
    ''' Mostra as tarefas de determinada prioridade '''
    for item in id_tarefas:  # Mostra as tarefas de alta prioridade em ordem de ID
        print('#ID: {}\n'.format(tarefas_dicio[tipo_prioridade][item].id_tarefa))
        print('Título: {}\n'.format(tarefas_dicio[tipo_prioridade][item].titulo))
        print('Descrição: {}\n'.format(tarefas_dicio[tipo_prioridade][item].descricao))
        print('Prioridade: {}\n'.format(tarefas_dicio[tipo_prioridade][item].prioridade))
        print('-------------------------------------------------')
    return len(tarefas_dicio[tipo_prioridade])  # Retorna a quantidade de tarefas cadastradas na prioridade em questão

def menu_visualizar_tarefas(nome_arquivo_visualizar):
    ''' Vizualiza todas as tarefas do usuário '''
    limpar_tela()
    try:
        try:  # Verifica a existência do arquivo
            arq_aberto = open('{}.dat'.format(nome_arquivo_visualizar), 'rb')
            taref = pickle.load(arq_aberto)
            arq_aberto.close()
            print('{:=^30}'.format(' Tarefas '))
            id_alta = sorted(taref[0].keys())  # Guarda em uma lista as ID's/chaves das tarefas de alta prioridade
            qtd = visualizar_tarefa(taref, id_alta, 0)
            id_media = sorted(taref[1].keys())  # Guarda em uma lista as ID's/chaves das tarefas de média prioridade
            qtd += visualizar_tarefa(taref, id_media, 1)
            id_baixa = sorted(taref[2].keys())  # Guarda em uma lista as ID's/chaves das tarefas de baixa prioridade
            qtd += visualizar_tarefa(taref, id_baixa, 2)
            if qtd == 0:  # Verifica se a quantidade de tarefas de qualquer prioridade é igual a zero
                print('Não há tarefas cadastradas.')
        except FileNotFoundError:  # Se não existir o arquivo, um novo é criado
            print('Não há tarefas cadastradas.')
    except EOFError:  # Se o arquivo tiver em branco adiciona os dicionários de tarefas vazios ao arquivo de tarefa do usuário
        print('Não há tarefas cadastradas.')
        arq_aberto = open('{}.dat'.format(nome_arquivo_visualizar), 'wb')
        lista_tarefas2 = [{}, {}, {}]
        pickle.dump(lista_tarefas2, arq_aberto)
        arq_aberto.close()
    input('\nTecle enter para voltar ao menu principal')

def inserir_tarefa_editada(todas_tarefas, usuario_editar):
    ''' Adiciona a tarefa editada de volta a lista de tarefas '''
    try:
        arquivo_editar_tarefa = open('{}.dat'.format(usuario_editar), 'wb')
        pickle.dump(todas_tarefas, arquivo_editar_tarefa)
        arquivo_editar_tarefa.close()
    except FileNotFoundError:
        pass

def editar_tarefa_menu(nome_arquivo):
    ''' Menu de edição de tarefas '''
    try:  # Verifica a existência do arquivo de tarefas do usuário
        editar_novamente = None
        while editar_novamente != 2:
            editar_novamente = 0
            limpar_tela()
            print('{:=^50}'.format(' Editar tarefa '))
            arq_editar = open('{}.dat'.format(nome_arquivo), 'rb')
            tarefa_editar = pickle.load(arq_editar)
            arq_editar.close()
            inserir_id = True  # Variável utilizada para controle de ID inserido para a edição de tarefas
            while inserir_id:
                try:
                    id_editar = int(input('Insira o ID da tarefa para editar: '))
                    inserir_id = False
                except ValueError:
                    print('O tipo de dado inserido é inválido. Os IDs são consistiudos somente de números inteiros.')
                    input('\nTecle enter para tentar novamente.')
                    limpar_tela()
                    print('{:=^50}'.format(' Editar tarefa '))
            limpar_tela()
            try:
                editar_tarefa = tarefa_editar[0].pop(id_editar)
                editar_tarefa.alterar()
            except KeyError:
                try:
                    editar_tarefa = tarefa_editar[1].pop(id_editar)
                    editar_tarefa.alterar()
                except KeyError:
                    try:
                        editar_tarefa = tarefa_editar[2].pop(id_editar)
                        editar_tarefa.alterar()
                    except KeyError:
                        while editar_novamente < 1 or editar_novamente > 2:  # Loop para evitar que seja inserido um valor fora da faixa
                            limpar_tela()
                            print('Id não encontrado\n')
                            print('Deseja tentar novamente:\n   1 - Sim\n   2 - Não/voltar o menu de tarefas')
                            editar_novamente = int(input('Insira a opção desejada: '))
            if editar_novamente == 0:
                editar_novamente = 2
                if editar_tarefa.prioridade == 'Alta':  # Se a tarefa editada for de alta prioridade, retorna a tarefa para o dicionário de alta prioridade
                    tarefa_editar[0][id_editar] = editar_tarefa
                elif editar_tarefa.prioridade == 'Média':  # Se a tarefa editada for de média prioridade, retorna a tarefa para o dicionário de média prioridade
                    tarefa_editar[1][id_editar] = editar_tarefa
                elif editar_tarefa.prioridade == 'Baixa':  # Se a tarefa editada for de baixa prioridade, retorna a tarefa para o dicionário de baixa prioridade
                    tarefa_editar[2][id_editar] = editar_tarefa
                inserir_tarefa_editada(tarefa_editar, nome_arquivo)
                print('\nTarefa editada com sucesso!\n')
                input('Tecle enter para voltar ao menu de tarefas.')        
    except FileNotFoundError:  # Se não existir o arquivo de tarefas do usuário
        print('Não há tarefas cadastradas.\n')
        input('Tecle enter para voltar ao menu de tarefas.')

def excluir_tarefa(nome_arquivo_tarefas):
    ''' Exclui a tarefa desejada '''
    try:
        limpar_tela()
        arq_excluir = open('{}.dat'.format(usuario_login), 'rb')
        tarefa_excluir = pickle.load(arq_excluir)
        arq_excluir.close()
        
        tratar_erro_deletar = True
        while tratar_erro_deletar == True:
            print('{:=^30}'.format(' Excluir tarefa '))
            try:
                deletar = int(input('Insira a ID da tarefa que deseja excluir: '))
                tratar_erro_deletar = False
            except ValueError:
                limpar_tela()
                print('O tipo de dado inserido é inválido. Os IDs são consistiudos somente de números inteiros.')
                input('\nTecle enter para tentar novamente.')
                limpar_tela()
        try:
            tarefa_excluir[0].pop(deletar)
            print('Exclusão completa.')
            input('\nTecle enter para voltar ao menu de tarefas')
        except KeyError:
            try:
                tarefa_excluir[1].pop(deletar)
                print('Exclusão completa.')
                input('\nTecle enter para voltar ao menu de tarefas')
            except KeyError:
                try:
                    tarefa_excluir[2].pop(deletar)
                    print('Exclusão completa.')
                    input('\nTecle enter para voltar ao menu de tarefas')
                except KeyError:
                    print('ID de tarefa não encontrado.')
                    input('\nTecle enter para voltar ao menu de tarefas.')
        arq_excluir = open('{}.dat'.format(usuario_login), 'wb')  # Abre o arquivo para gravar a nova lista de tarefas/ caso não haja o ID não é alterado a lista de tarefas
        pickle.dump(tarefa_excluir, arq_excluir)
        arq_excluir.close()
    except FileNotFoundError:
        print('Não há tarefas cadastradas.')
        input('\nTecle enter para voltar ao menu.')


''' Classes '''

class Tarefa:
    ''' Essa classe apresenta os atributos e os métodos das tarefas '''

    def __init__(self, prioridade, titulo, descricao, id_tarefa):  # Método inicializador - Atributos
        self.prioridade = prioridade
        self.titulo = titulo
        self.descricao = descricao
        self.id_tarefa = id_tarefa

    def alterar(self):
        ''' Altera as informações da tarefa '''

        tratar_erro_editar = True  # Atribuição para entrar no loop
        while tratar_erro_editar == True:
            try:    
                print('{:=^50}'.format(' Editar tarefa '))
                print('1 - Alterar título\n2 - Alterar descrição\n3 - Alterar prioridade')
                alterar_op = int(input('Insira a opção desejada: '))
                limpar_tela()
                if alterar_op == 1:
                    tratar_erro_editar = False
                    print('{:=^50}'.format(' Alterar título de tarefa '))
                    self.titulo = input('\nInsira o novo título desejado: ')
                elif alterar_op == 2:
                    tratar_erro_editar = False
                    print('{:=^50}'.format(' Alterar a descrição de tarefa '))
                    self.descricao = input('\nInsira a nova descrição desejada: ')
                elif alterar_op == 3:
                    tratar_erro_editar = False
                    prioridade_ed = 0
                    while prioridade_ed < 1 or prioridade_ed > 3:
                        print('{:=^50}'.format(' Alterar prioridade de tarefa '))
                        print('1 - Alta\n2 - Média\n3 - Baixa')
                        prioridade_ed = int(input('\nInsira a nova prioridade para a tarefa: '))
                        if prioridade_ed == 1:
                            self.prioridade = 'Alta'
                        elif prioridade_ed == 2:
                            self.prioridade = 'Média'
                        elif prioridade_ed == 3:
                            self.prioridade = 'Baixa'
                        else:
                            print('Valor inserido inválido.\n')
                            input('Tecle enter para tentar novamente.')
                        limpar_tela()
            except ValueError:
                limpar_tela()
                input('Opção inválida. Tecle enter para tentar novamente.')
                limpar_tela()
class Usuario: 
    ''' A classe apresenta os atributos do usuário e os métodos possíveis de serem utilizados '''

    def __init__(self, nome, senha):  # Inicializadora - Atributos
        self.nome = nome
        self.senha = hashlib.sha256(senha.encode('utf-8')).hexdigest()


    def cadastrar_usuario(self):
        ''' Cadastro de novo usuário '''
        
        users = open('usuarios.txt', 'a')
        users.write('{}->{}\n'.format(self.nome, self.senha))
        users.close()
        #arq = open('usuarios.txt', 'r')
        #print(arq.read())
        #input()
        #arq.close()
        input('\nUsuário cadastrado com sucesso!. Tecle enter para voltar ao menu principal.')
        limpar_tela()


    def fazer_login(self):
        ''' Permite o usuário efetuar o login na sua conta do sistema '''

        verificador2 = verificar_senha_usuario(self.nome, self.senha)
        if verificador2 == 0:
            tentar_novamente = 0  # Atribuição para entrar no loop, usado para solicitar uma nova tentativa do usuário ou voltar ao menu principal
            while tentar_novamente < 1 or tentar_novamente > 2:
                limpar_tela()
                print('Nome de usuário ou senha inválidos.')
                print('\n1 - Tentar novamente\n2 - Voltar ao menu principal\n')
                tentar_novamente = int(input('Insira a opção desejada: '))
                limpar_tela()
                if tentar_novamente == 2:
                    verificador2 = 2  # Atribuição para sair do loop e voltar ao menu principal
        return verificador2


''' Programa principal '''

while opcao != '3':
    opcao = menu_principal()

    if opcao == '1':  # CADASTRO DE NOVO USUÁRIO
        cadastrar_usuario_menu()
    elif opcao == '2':  # LOGAR NO SISTEMA
        verificador2 = 0  # Verificador para logar no sistema (0 = dados incorretos; 1 = dados corretos/sucesso no login; 2 = sai do loop e volta para o menu principal)
        while verificador2 == 0:
            verificador2, usuario_login = logar_sistema_menu()  # A variável 'verificador2' recebe o indicativo se os dados dos usuário correspondem; a variável 'usuario_login' recebe o nome de usuário logado       
            if verificador2 == 1:  # Se o login for bem sucedido entra no sistema
                menu_tarefas = 0  # Atribuição para entrar no loop que evita a inserção de valores fora da faixa
                while menu_tarefas != '5':
                    menu_tarefas = menu_de_tarefas()
                    if menu_tarefas == '1':  # CADASTRAR UMA NOVA TAREFA
                        cadastrar_tarefa(usuario_login)
                    elif menu_tarefas == '2':  # VISUALIZAR TAREFAS  
                        menu_visualizar_tarefas(usuario_login)
                    elif menu_tarefas == '3':  # EDITAR TAREFAS
                        editar_tarefa_menu(usuario_login)
                    elif menu_tarefas == '4':  # EXCLUIR TAREFAS
                        excluir_tarefa(usuario_login)
                    elif menu_tarefas == '5':  # SAIR DA CONTA DO USUÁRIO
                        usuario_login = None  # Atribui um 'valor' nulo a variável
                    else:  # CASO A OPÇÃO INSERIDA SEJA INVÁLIDA
                        limpar_tela()
                        print('Opção inválida.\nTecle enter para voltar ao menu inicial.')
                        input()
                limpar_tela()
    elif opcao == '3':  # SAIR DO PROGRAMA
        limpar_tela()
    else:  # CASO O VALOR INSERIDO NÃO SE ENCONTRE NA FAIXA
        input('Opção insirada inválida.\nTecle enter para voltar ao menu principal.')
        limpar_tela()