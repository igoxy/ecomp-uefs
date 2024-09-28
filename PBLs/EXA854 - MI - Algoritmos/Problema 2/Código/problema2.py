''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Autor: Igor Figueredo Soares
Componente Curricular: Algoritmos I
Concluido em: 04/02/2020
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação. 
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

# -*- coding: utf-8 -*-

''' Bibliotecas e métodos importados '''

import datetime
from os import system, name

''' Declaração de variáveis, listas e dicionários ''' 

med_dermato = 'Drª. Silvia Melo'
med_endo = 'Dr. Fernando Santos'
med_orto = 'Drª. Maria do Carmo Silva'

verificador_atendimento_dermato = 1  # Verificador se há alguém em atendimento na fila dermatologista (1 = Sim, 2 = Não)
verificador_atendimento_endo = 1  # Verificador se há alguém em atendimento na fila endocrinologista (1 = Sim, 2 = Não)
verificador_atendimento_orto = 1  # Verificador se há alguém em atendimento na fila ortopedista (1 = Sim, 2 = Não)

opcao_menu = None  # Variável de seleção de opção do menu principal

contador = [0, 0, 0, 0, 0, 0]  # Contador filas 
''' Contador tem a seguinte configuração:
1º elemento: contador pacientes de dermatologia comuns
2º elemento: contador pacinetes de dermatologia preferenciais
3º elemento: contador pacientes de endocrinologia comuns
4º elemento: contador pacientes de endocrinologia preferenciais 
5º elemento: contador pacinetes de ortopedia comuns
6º elemento: contador pacinetes de ortopedia preferenciais'''

consultorio_dermato = {}  # Dicionário para armazenar pacientes em fila do consultório de dermatologia
consultorio_endo = {}  # Dicionário para armazenar pacientes em fila do consultório de endocrinologia
consultorio_orto = {}  # Dicionário para armazenar pacientes em fila do consultório de ortopedia

dermatologia_pref = []; dermatologia_comum = [] # lista para filas
endocrinologia_pref = []; endocrinologia_comum = [] # lista para filas
ortopedista_pref = []; ortopedista_comum = [] # lista para filas

dermatologia_atendidos_pref = []; endocrinologia_atendidos_pref = []; ortopedista_atendidos_pref = [] # Listas de pacientes preferenciais atentidos em cada consultório
dermatologia_atendidos_comum = []; endocrinologia_atendidos_comum = []; ortopedista_atendidos_comum = [] # Listas de pacientes comuns atentidos em cada consultório

''' Funções '''

def clear(): # Limpar tela
    ''' Função utilzada para limpar a tela durante a execução do programa. '''

    if name == "nt":
        system("cls") #Windows
    else:
        system("clear") #Linux

def menu():  # Opções do menu principal
    ''' Imprime as opções do menu principal do programa. '''

    print('{:=^40}'.format(' Menu principal '))
    print('\n1 - Emitir nova senha\n2 - Chamar paciente para atendimento')
    print('3 - Pular paciente\n4 - Encerrar uma consulta\n5 - Fila de espera')
    print('6 - Exibir pacientes atendidos\n7 - Tempo médio de espera dos pacientes')
    print('8 - Exibir tempo médio de atendimento dos pacientes\n9 - Sair')
    opcao = input('\nInsira a opção desejada: ')
    
    return opcao

def menu_senha():  # Opções de cadastro
    ''' Utilizada imprimir e capturar as opções de cadastro dos pacientes '''

    especialidade_paciente = 0; tipo_paciente = 0  #PARA TRATAMENTO DE ERRO - MUDAR 
    print('{:=^30}'.format(' Cadastro de paciente '))
    print('\nEspecialidades:\n\n1 - Determatologia\n2 - Endocrinologia\n3 - Ortopedista')
    while especialidade_paciente < 1 or especialidade_paciente > 3:
        especialidade_paciente = int(input('\nInsira a especialidade que o paciente deseja se consultar: '))
    clear()
    print('\nTipo de paciente:\n\n1 - Comum\n2 - Preferencial')
    while tipo_paciente < 1 or tipo_paciente > 2:
        tipo_paciente = int(input('\nInsira o tipo de paciente: '))
    clear()
    nome_paciente = (input('\nInsira o nome do paciente: ')).upper()

    return especialidade_paciente, tipo_paciente, nome_paciente

def gerar_senha(especialidade, tipo, contador):  # Gerador de senha
    ''' Essa função é utilizada para gerar as senhas dos pacientes.
    Ela recebe como parâmetro a especialidade do paciente, o tipo e a
    lista com o contador de pacientes cadastrados de consultório e tipo.'''

    if especialidade == 1:  # Determatologia
        especialidade = 'Dermatologia'
        if tipo == 1:      #  Paciente comum 
            contador[0] += 1
            senha = 'CD' + str(contador[0]).zfill(3)   
        else:       # Paciente preferencial
            contador[1] += 1
            senha = 'PD' + str(contador[1]).zfill(3)
    elif especialidade == 2:    #  Endocrinologia
        especialidade = 'Endocrinologia'
        if tipo == 1:      #  Paciente comum 
            contador[2] += 1
            senha = 'CE' + str(contador[2]).zfill(3)
        else:       # Paciente preferencial
            contador[3] += 1
            senha = 'PE' + str(contador[3]).zfill(3)
    else:  # Ortopedista
        especialidade = 'Ortopedia'
        if tipo == 1:      #  Paciente comum
            contador[4] += 1
            senha = 'CO' + str(contador[4]).zfill(3)
        else:       # Paciente preferencial
            contador[5] += 1
            senha = 'PO' + str(contador[5]).zfill(3)
    
    if tipo == 1:
        tipo = 'Comum'
    else:
        tipo = 'Preferencial'
    hora = input('\nInsira a hora atual (no formato HH:MM): ')
    
    return senha, hora, especialidade, tipo

def chamar_paciente(fila_comum, fila_preferencial, pacientes, verificador_chamada):  # Indicador de paciente a ser atendido
    ''' Função utilizada para decidir o paciente a ser chamado em determinado consultório.
    Recebe como parâmetros, a fila comum, a fila preferencial, e os dados dos pacientes do
    consultório desejado. Recebe também a variável verificadora do consultório para a decisão
    do paciente (comum ou prefrencial) a ser chamado.'''

    if len(fila_comum) > 0 and len(fila_preferencial) > 0: # Verifica se há pacientes pacientes e preferenciais
        comparador_status1 = pacientes[fila_preferencial[0]][3]  # Variável recebe o status do primeiro paciente da fila preferencial
        comparador_status2 = pacientes[fila_comum[0]][3] # Variável recebe o status do primeiro paciente da fila comum
        if comparador_status1 == 'Em espera' and comparador_status2 == 'Em espera': # Verifica se nenhum dos pacientes esta em atendimento, para evitar que dois pacientes estejam em atendimento no mesmo consultório
            comparador_hora_p = pacientes[fila_preferencial[0]][4] # Armazena o horário de chegada do primeiro paciente da fila preferecial
            comparador_hora_c = pacientes[fila_comum[0]][4] # Armazena o horário de chegada do primeiro paciente da fila comum
            if verificador_chamada == 2 and comparador_hora_c < comparador_hora_p: # Verifica se o paciente comum chegou primeiro que o paciente preferencial
                verificador_chamada = 1
                print('Senha: {} - {} - Consultório de {}.'.format(fila_comum[0], pacientes[fila_comum[0]][0], pacientes[fila_comum[0]][1]))
                input('\nTecle enter para confirmar.')
                hora_entrada_consul =  input('\nInsira a hora de entrada na consulta (no formato HH:MM): ') # Variável recebe a hora que o paciente iniciou o atendimento
                pacientes[fila_comum[0]].append(hora_entrada_consul) # Adiciona a hora de entra no consultório nas informações do paciente
                pacientes[fila_comum[0]][3] = 'Em atendimento' # Muda o status do paciente para em atendimento
            else:
                verificador_chamada = 2
                print('Senha: {} - {} - Consultório de {}.'.format(fila_preferencial[0], pacientes[fila_preferencial[0]][0], pacientes[fila_preferencial[0]][1]))
                input('Tecle enter para confirmar.')
                hora_entrada_consul = input('\nInsira a hora de entrada na consulta (no formato HH:MM): ')
                pacientes[fila_preferencial[0]].append(hora_entrada_consul) # Adiciona a hora de entra no consultório nas informações do paciente
                pacientes[fila_preferencial[0]][3] = 'Em atendimento' # Muda o status do paciente para em atendimento
        else:
            print('Não é possível chamar o próximo paciente, ainda há paciente em atendimento no consultório.')
    elif len(fila_preferencial) > 0 and pacientes[fila_preferencial[0]][3] == 'Em espera':
        verificador_chamada = 2
        print('Senha: {} - {} - Consultório de {}.'.format(fila_preferencial[0], pacientes[fila_preferencial[0]][0], pacientes[fila_preferencial[0]][1]))
        input('Tecle enter para confirmar.')
        hora_entrada_consul = input('\nInsira a hora de entrada na consulta (no formato HH:MM): ') 
        pacientes[fila_preferencial[0]].append(hora_entrada_consul) # Adiciona a hora de entra no consultório nas informações do paciente
        pacientes[fila_preferencial[0]][3] = 'Em atendimento' # Muda o status do paciente para em atendimento
    elif len(fila_preferencial) > 0 and pacientes[fila_preferencial[0]][3] == 'Em atendimento':
        print('Não é possível chamar o próximo paciente, ainda há paciente em atendimento no consultório.')
    elif len(fila_comum) > 0 and pacientes[fila_comum[0]][3] == 'Em espera':
        verificador_chamada = 1
        print('Senha: {} - {} - Consultório de {}.'.format(fila_comum[0], pacientes[fila_comum[0]][0], pacientes[fila_comum[0]][1]))
        input('\nTecle enter para confirmar.')
        hora_entrada_consul = input('\nInsira a hora de entrada na consulta (no formato HH:MM): ')  # Variável recebe a hora que o paciente iniciou o atendimento
        pacientes[fila_comum[0]].append(hora_entrada_consul) # Adiciona a hora de entra no consultório nas informações do paciente
        pacientes[fila_comum[0]][3] = 'Em atendimento' # Muda o status do paciente para em atendimento
    elif len(fila_comum) > 0 and pacientes[fila_comum[0]][3] == 'Em atendimento':
        print('Não é possível chamar o próximo paciente, ainda há paciente em atendimento no consultório.')
        print('Tecle enter para voltar ao menu principal.')
    else:
        print('Não há pacientes na fila de espera desse consultório.')
        input('Tecle enter para voltar ao menu princial.')
    clear()
    return verificador_chamada

def pular_paciente(fila, consultorio):  # Remove paciente da fila de espera
    ''' Essa função é utilizada para a remoção de pacientes desistentes.
    Recebe como parâmetros: a fila do consultório e os dados dos pacientes
    do consultório que o paciente vai ser retirado. '''

    clear()
    contador1 = 1  # Variável para sinalizar os pacientes e facilitar a escolha
    contador2 = 0  # Contadora de pacientes em atendimento (deve variar entre 0 e 1)
    if len(fila) > 0 and consultorio[fila[0]][3] == 'Em espera':
        for item in fila: # Imprime a lista dos pacientes
            if consultorio[item][3] == 'Em espera': # Verifica paciente em atendimento e não mostra o mesmo
                print('{} - Senha: {} - {}'. format(contador1, item, consultorio[item][0]))
                contador1 += 1
            elif consultorio[item][3] == 'Em atendimento':
                contador2 += 1 # Recebe um indicador de paciente em atendimento para corrir a fila
        retirar = int(input('Insira o número do paciente que deseja retirar: '))
        if retirar <= len(fila) - contador2 and retirar > 0: # Verifica se o número inserido pelo usuário esta dentro da faixa
            retirar = retirar - 1 + contador2 # Correção para o indice certo na lista
            retirar = fila.pop(retirar)  # Retira o paciente da fila seleciona/indica o paciente a ser retirado do sistema
            consultorio.pop(retirar)  # Retira o paciente do sistema
            input('Paciente removido.')
        else:
            print('\nPaciente inexistente.\n')
            input('\nTecle enter para voltar.\n')
            clear()
    else:
        print('\nNão há pacientes na fila.\n')
        input('\nTecle enter para voltar.\n')
        clear()

def encerrar_con(consultorio, fila_comum, fila_preferencial, atendidos_comum, atendidos_preferencial,med):  # Conclui consulta
    ''' Função utilizada para encerrar o atendimento de um paciente em determinado consultório.
    Reecebe como parâmetros: os dados dos pacientes do consultório desejado; as listas das filas de espera comum e preferencial;
    a lista de pacientes atendidos comuns e preferenciais; e o nome do médico do consultório em questão.
    Essa função além de encerrar a consulta, insere o paciente na lista de atendidos.'''

    indicador_paciente = 0 
    if len(fila_comum) > 0:
        if consultorio[fila_comum[0]][3] == 'Em atendimento': # Verificador se o paciente em atendimento é comum
            print('Paciente em atendimento\n')
            print('Senha: {} - {} - {}'.format(fila_comum[0], consultorio[fila_comum[0]][0], consultorio[fila_comum[0]][1]))
            print('\nDeseja finalizar a consulta deste paciente?\n1 - Sim      2 - Não/Sair')
            confirmacao = int(input('Insira a opção desejada: '))
            if confirmacao == 1:
                consultorio[fila_comum[0]][3] = 'Atendido'
                hora_saida_consul = input('\nInsira a hora de término da consulta (no formato HH:MM): ')  # Variável recebe a hora que o paciente saiu do atendimento
                consultorio[fila_comum[0]].append(hora_saida_consul) # Insere a hora que o paciente saiu do consultório as suas informações
                consultorio[fila_comum[0]].append(med) # Insere o nome do médico que atendeu o paciente
                mover1 = fila_comum.pop(0) # Retira o paciente da fila de espera
                atendidos_comum.append(consultorio.pop(mover1)) # Adiciona o paciente a fila de atendidos do consultório em questão
                indicador_paciente = 1
    if len(fila_preferencial) > 0:
        if consultorio[fila_preferencial[0]][3] == 'Em atendimento': # Verifica se o paciente em atendimento é preferencial
            print('Paciente em atendimento\n')
            print('Senha: {} - {} - {}'.format(fila_preferencial[0], consultorio[fila_preferencial[0]][0], consultorio[fila_preferencial[0]][1]))
            print('\nDeseja finalizar a consulta deste paciente?\n1 - Sim      2 - Não/Sair')
            confirmacao = int(input('Insira a opção desejada: '))
            if confirmacao == 1:
                consultorio[fila_preferencial[0]][3] = 'Atendido'
                hora_saida_consul = input('\nInsira a hora de término da consulta (no formato HH:MM): ')  # Variável recebe a hora que o paciente saiu do atendimento
                consultorio[fila_preferencial[0]].append(hora_saida_consul) # Insere a hora que o paciente saiu do consultório as suas informações
                consultorio[fila_preferencial[0]].append(med) # Insere o nome do médico que atendeu o paciente
                mover1 = fila_preferencial.pop(0) # Retira o paciente da fila de espera
                atendidos_preferencial.append(consultorio.pop(mover1)) # Adiciona o paciente a fila de atendidos do consultório em questão
                indicador_paciente = 1
    if indicador_paciente == 0: 
        print('Não há paciente em atendimento.')
    input('\nTecle enter para voltar ao menu principal.')

def lista_espera(consultorio, fila_comum, fila_preferencial):  # Exibe lista de espera
    ''' Imprime a lista de espera do consultório desejado.
    Recebe como parâmetros: o dicionário com as informações dos pacientes; a lista da fila comum e a lista da fila
    preferencial do consultório determinado. '''

    hora_fila_espera = input('\nInsira a hora atual (no formato HH:MM): ')  
    clear()
    if len(fila_comum) > 0: # Verifica se há pacintes na fila de espera comum do consultório
        print('Fila comum\n')
        print('| {} | {} | {} | {} '.format('Senha', 'Nome'.center(30), 'Status'.center(16), 'Tempo de espera na fila' ))
        for item in fila_comum: # Percorre a lista de espera comum 
            if consultorio[item][3] == 'Em atendimento': # Verifica se o paciente se encontra em atendimento
                variacao_temp = str(datetime.datetime.strptime(consultorio[item][5], '%H:%M') - datetime.datetime.strptime(consultorio[item][4], '%H:%M')).zfill(8)  # Cálculo do tempo de espera na fila do paciente em atendimento na fila comum
                print('| {} | {} | {} | {} '.format(item, consultorio[item][0].center(30), consultorio[item][3].center(16), variacao_temp[0:5].center(23)))
            else: # Se o paciente não estiver em atendimento (ou seja, ele esta em espera)
                variacao_temp = str(datetime.datetime.strptime(hora_fila_espera, '%H:%M') - datetime.datetime.strptime(consultorio[item][4], '%H:%M')).zfill(8)  # Cálculo do tempo de espera na fila do paciente em espera
                print('| {} | {} | {} | {} '.format(item, consultorio[item][0].center(30), consultorio[item][3].center(16), variacao_temp[0:5].center(23)))                       
    if len(fila_preferencial) > 0: # Verificador se há alguém na fila preferencial do consultório
        print('\nFila preferencial\n')
        print('| {} | {} | {} | {} '.format('Senha', 'Nome'.center(30), 'Status'.center(16), 'Tempo de espera na fila'))
        for item in fila_preferencial: # Percorre a lista de espera preferencial
            if consultorio[item][3] == 'Em atendimento': # Verifica se o paciente se encontra em atendimento
                variacao_temp = str(datetime.datetime.strptime(consultorio[item][5], '%H:%M') - datetime.datetime.strptime(consultorio[item][4], '%H:%M')).zfill(8)  # Cálculo do tempo de espera na fila do paciente em atendimento na fila preferencial
                print('| {} | {} | {} | {} '.format(item, consultorio[item][0].center(30), consultorio[item][3].center(16), variacao_temp[0:5].center(23)))
            else: # Se o paciente estiver em espera
                variacao_temp = str(datetime.datetime.strptime(hora_fila_espera, '%H:%M') - datetime.datetime.strptime(consultorio[item][4], '%H:%M')).zfill(8)  # Cálculo do tempo de espera na fila do paciente em espera
                print('| {} | {} | {} | {} '.format(item, consultorio[item][0].center(30), consultorio[item][3].center(16), variacao_temp[0:5].center(23)))
    if len(fila_comum) < 1 and len(fila_preferencial) < 1: # Verifica se não há ninguém nas duas filas
        print('Não há pacientes na fila de espera deste consultório.') # Mensagem de aviso da falta de paciente na fila
    input('\nTecle enter para voltar ao menu principal.')
    clear()

def lista_atendidos(atendidos_comum, atendidos_preferencial):  # Exibe lista de pacientes atendidos
    ''' Imprime a lista de pacientes atendidos no determinado.
    Recebe como parâmetros: a lista de pacientes comuns atendidos do consultório e lista de pacientes atendidos
    preferenciais do consultório. '''

    if len(atendidos_comum) > 0:
        print('Fila comum\n')
        print('| {} | {} | {} | {} | {} '.format('Nome do paciente'.center(40), 'Tempo de espera (HH:MM)', 'Tempo de atendimento (HH:MM)', 'Consultório'.center(17), 'Médico do consultório' ))
        for item in atendidos_comum:
            tempo_fila1 = str(datetime.datetime.strptime(item[5], '%H:%M') - datetime.datetime.strptime(item[4], '%H:%M')).zfill(8)
            tempo_atendimento1 = str(datetime.datetime.strptime(item[6], '%H:%M') - datetime.datetime.strptime(item[5], '%H:%M')).zfill(8)
            print('| {} | {} | {} | {} | {} '.format(item[0].center(40), tempo_fila1[0:5].center(23), tempo_atendimento1[0:5].center(28), item[1].center(17), item[7].center(21)))
    if len(atendidos_preferencial) > 0:
        print('\nFila preferencial\n'.center(100))
        print('| {} | {} | {} | {} | {} '.format('Nome do paciente'.center(40), 'Tempo de espera (HH:MM)', 'Tempo de atendimento (HH:MM)', 'Consultório'.center(17), 'Médico do consultório' ))
        for item in atendidos_preferencial:
            tempo_fila2 = str(datetime.datetime.strptime(item[5], '%H:%M') - datetime.datetime.strptime(item[4], '%H:%M')).zfill(8)
            tempo_atendimento2 = str(datetime.datetime.strptime(item[6], '%H:%M') - datetime.datetime.strptime(item[5], '%H:%M')).zfill(8)
            print('| {} | {} | {} | {} | {} '.format(item[0].center(40), tempo_fila2[0:5].center(23), tempo_atendimento2[0:5].center(28), item[1].center(17), item[7].center(21)))
    if len(atendidos_comum) < 1 and len(atendidos_preferencial) < 1:
        print('\nAinda não há pacientes atendidos neste consultório.')
    input('\nTecle enter para voltar ao menu principal')
    clear()

def tempo_espera(consultorio, fila_comum, fila_preferencial, atentidos_comuns, atentidos_preferenciais):  # Tempos médio de espera
    ''' Função utilizada para contar o tempo de atendimento das filas no consultório.
    Recebe como parâmetros: os dados dos pacientes do consultório desejado; a fila comum e preferencial do consultório e;
    a lista de pacientes comuns e preferenciais atendidos no consultório. '''

    contador_comum = 0; contador_pref = 0  # Variáveis contadoras de pacientes do tipo comum e preferenciais para a retirada da média
    tempo_comum = datetime.timedelta(); tempo_pref = datetime.timedelta()  # Variáveis acumuladoras de tempo para o cálculo da média
    if len(atentidos_comuns) > 0:
        for item in atentidos_comuns:
            tempo_comum += (datetime.datetime.strptime(item[5], '%H:%M') - datetime.datetime.strptime(item[4], '%H:%M'))
            contador_comum += 1
    if len(fila_comum) > 0:
        if consultorio[fila_comum[0]][3] == 'Em atendimento':
            tempo_comum += (datetime.datetime.strptime(consultorio[fila_comum[0]][5], '%H:%M') - datetime.datetime.strptime(consultorio[fila_comum[0]][4], '%H:%M'))
            contador_comum += 1
    if len (atentidos_preferenciais) > 0:
        for item in atentidos_preferenciais:
            tempo_pref += (datetime.datetime.strptime(item[5], '%H:%M') - datetime.datetime.strptime(item[4], '%H:%M'))
            contador_pref += 1
    if len(fila_preferencial) > 0:
        if consultorio[fila_preferencial[0]][3] == 'Em atendimento':
            tempo_pref += (datetime.datetime.strptime(consultorio[fila_preferencial[0]][5], '%H:%M') - datetime.datetime.strptime(consultorio[fila_preferencial[0]][4], '%H:%M'))
            contador_pref += 1
    return tempo_comum, contador_comum, tempo_pref, contador_pref

def tempo_atendimento(atendidos_comum, atendidos_pref):  #  Tempos médios de atendimento
    ''' Função utilizada para contabilizar o tempo de atendimento das filas do consultório em questão.
    Recebe como parâmetros: a lista de pacientes comuns e a lista de pacientes preferenciais do consultório. '''

    contador_atendimento_c = 0; contador_atendimento_p = 0  # Guarda a quantidade de pessoas da fila atendidas no consultório
    tempo_atendimento_c = datetime.timedelta(); tempo_atendimento_p = datetime.timedelta()  # Acumuladoras do tempo de atendimento de cada paciente
    if len(atendidos_comum) > 0:  # Pacientes atendidos comuns
        for item in atendidos_comum:  # Percorre toda a lista de pacientes comuns atendidos
            tempo_atendimento_c += (datetime.datetime.strptime(item[6], '%H:%M') - datetime.datetime.strptime(item[5],'%H:%M'))  # Acrescenta o tempo de atendimento do paciente a variável acumuladora
            contador_atendimento_c += 1 
    if len(atendidos_pref) > 0:  # Pacientes atedidos preferenciais
        for item in atendidos_pref:  # Percorre toda lista de pacientes preferenciais atendids
            tempo_atendimento_p += (datetime.datetime.strptime(item[6],'%H:%M') - datetime.datetime.strptime(item[5],'%H:%M'))
            contador_atendimento_p += 1
    return tempo_atendimento_c, contador_atendimento_c, tempo_atendimento_p, contador_atendimento_p
    # Retorna em ordem: tempo atendimento pacientes comuns, quantidade de pacientes comuns, tempo de atendimento pacientes preferenciais e quantidade de pacientes preferenciais


''' Módulos principais (opções do menu) '''

def op_cadastro(opcao):
    ''' Módulo para o cadastro de pacientes.
    Recebe como parâmetro o opção de escolha do menu principal. '''
    
    clear()
    while opcao == '1':
        especialidade, tipo, nome = menu_senha() # Recebe as opções do menu
        senha, hora_entrada_fila, especialidade, tipo = gerar_senha(especialidade, tipo, contador)  # Recebe as informações do método senha
        if especialidade == 'Dermatologia': # Adicionar paciente a fila de dermatologia
            consultorio_dermato[senha] = [nome, especialidade, tipo, 'Em espera', hora_entrada_fila]
            if tipo == 'Preferencial': # Fila preferencial
                dermatologia_pref.append(senha)
            else: # Fila comum
                dermatologia_comum.append(senha)
        elif especialidade == 'Endocrinologia': # Adicionar pacientes a fila de endocrinologia
            consultorio_endo[senha] = [nome, especialidade, tipo, 'Em espera', hora_entrada_fila]
            if tipo == 'Preferencial': # Fila preferencial
                endocrinologia_pref.append(senha)
            else: # Fila comum
                endocrinologia_comum.append(senha)
        elif especialidade == 'Ortopedia': # Adicionar pacientes a fila de ortopedia
            consultorio_orto[senha] = [nome, especialidade, tipo, 'Em espera', hora_entrada_fila]
            if tipo == 'Preferencial': # Fila preferencial
                ortopedista_pref.append(senha)
            else: # Fila comum
                ortopedista_comum.append(senha)
        opcao = input('\n1 - Cadastrar outro paciente\n2 - Voltar ao menu principal\n\nInsira a opção desejada: ')
        clear()  # Limpar a tela

def op_chamar_paciente(verificador_dermato, verificador_endo, verificador_orto):
    ''' Módulo da opção de chamar paciente para o atendimento.
    Recebe como parâmetros: as variáveis dos verificadores de atendiemnto dos consultório de
    dermatologia, endrocrinologia e ortopedia, nesta ordem. '''
    
    clear()
    opcao_fila = 0 # Inicialização da variável para entrar no laço para controle de erro
    while opcao_fila < 1 or opcao_fila > 4: # Laço para controle de erro
        print('{:=^30}'.format(' Chamar paciente ')) # Imprime um título para a página
        print('\n1 - Consultório de Determatologia\n2 - Consultório de Endocrinologia\n3 - Consultório de Ortopedia\n4 - Voltar ao menu principal')
        opcao_fila = int(input('\nOpção desejada: ')) # Recebe a opção de entrar
        clear()
        if opcao_fila == 1:
            verificador_dermato = chamar_paciente(dermatologia_comum, dermatologia_pref, consultorio_dermato, verificador_dermato)
        elif opcao_fila == 2:
            verificador_endo = chamar_paciente(endocrinologia_comum, endocrinologia_pref, consultorio_endo, verificador_endo)
        elif  opcao_fila == 3:
            verificador_orto = chamar_paciente(ortopedista_comum, ortopedista_pref, consultorio_orto, verificador_orto)
    return verificador_dermato, verificador_endo, verificador_orto

def op_pular_paciente():
    ''' Módulo da opção de pular paciente. '''

    clear()
    opcao_pular_1 = 0 # Usada para entrar no loop e evitar valores fora da faixa
    while opcao_pular_1 < 1 or opcao_pular_1 > 4:
        opcao_pular_2 = 0 # Atribuição para evitar valores fora da faixa
        print('{:=^30}'. format(' Pular paciente '))
        print('\n1 - Pular paciente de Dermatologia\n2 - Pular paciente Endocrinologia\n3 - Pular paciente Ortopedia\n4 - Voltar ao menu principal')
        opcao_pular_1 = int(input('\nInsira a opção desejada: ')) 
        clear() 
        if opcao_pular_1 > 0 and opcao_pular_1 < 4: # Verifica se o número está dentro da faixa para prosseguir
            while opcao_pular_2 < 1 or opcao_pular_2 > 3: # Evitar númerp fora da faixa
                print('{:=^30}'. format(' Pular paciente '))
                print('\n1 - Fila preferencial\n2 - Fila comum\n3 - Voltar')
                opcao_pular_2 = int(input('\nInsira a opção deseja: ')) 
                if opcao_pular_2 == 3:
                    opcao_pular_1 = 0 # Atribuição para voltar a página de pular paciente
        if opcao_pular_1 == 1: # Pula paciente de dermatologia
            if opcao_pular_2 == 1: # Pula pacientes preferenciais
                pular_paciente(dermatologia_pref, consultorio_dermato)
            elif opcao_pular_2 == 2: # Pular pacientes comuns
                pular_paciente(dermatologia_comum, consultorio_dermato)
        elif opcao_pular_1 == 2: # Pular pacientes de endocrinologia
            if opcao_pular_2 == 1: # Pacientes preferenciais
                pular_paciente(endocrinologia_pref, consultorio_endo)
            elif opcao_pular_2 == 2: # Pacientes comuns
                pular_paciente(endocrinologia_comum, consultorio_endo)
        elif opcao_pular_1 == 3: # Pular pacientes de ortopedia
            if opcao_pular_2 == 1: # Pacientes preferencias
                pular_paciente(ortopedista_pref, consultorio_orto)
            elif opcao_pular_2 == 2: # Pacientes comuns
                pular_paciente(ortopedista_comum, consultorio_orto)

def op_encerrar_consulta():
    ''' Módulo da opção de encerrar consulta. '''
    
    clear()
    opcao_encerrar = 0 # Valor usado para entrar no loop
    while opcao_encerrar < 1 or opcao_encerrar > 4:  # Loop para evitar a introdução de valores fora da faixa
        clear() # Limpar a tela
        print('{:=^30}'.format(' Encerrar consulta '))
        print('\n1 - Consultório de Dermatologia\n2 - Consultório de Endocrinologia\n3 - Consultório de Ortopedia\n4 - Voltar ao menu')
        opcao_encerrar = int(input('Insira a opcção desejada: '))
        clear()
        if opcao_encerrar == 1: # Consultório de dermatologia   
            encerrar_con(consultorio_dermato, dermatologia_comum, dermatologia_pref, dermatologia_atendidos_comum, dermatologia_atendidos_pref, med_dermato)
        elif opcao_encerrar == 2: # Consultório de endocrinologia
            encerrar_con(consultorio_endo, endocrinologia_comum, endocrinologia_pref, endocrinologia_atendidos_comum, endocrinologia_atendidos_pref, med_endo)
        elif opcao_encerrar == 3: # Consultório de ortopedia
            encerrar_con(consultorio_orto, ortopedista_comum, ortopedista_pref, ortopedista_atendidos_comum, ortopedista_atendidos_pref, med_orto)
    clear()

def op_fila_espera():
    ''' Módulo da opção de exibir fila de espera. '''
    
    clear()
    opcao_fila_espera = 0
    while opcao_fila_espera < 1 or opcao_fila_espera > 4:    
        print('{:=^30}'.format(' Fila de espera '))
        print('\n1 - Fila Dermatologia\n2 - Fila Endocrinologia\n3 - Fila Ortopedia\n4 - Sair\n')
        opcao_fila_espera = int(input('Insira a opção desejada: '))
        clear()
    if opcao_fila_espera == 1:
        lista_espera(consultorio_dermato, dermatologia_comum, dermatologia_pref)
    elif opcao_fila_espera == 2:
        lista_espera(consultorio_endo, endocrinologia_comum, endocrinologia_pref)
    elif opcao_fila_espera == 3:
        lista_espera(consultorio_orto, ortopedista_comum, ortopedista_pref)

def op_fila_atendidos():
    ''' Módulo da opção de exibir pacientes atendidos. '''

    clear()
    opcao_atendidos = 0
    while opcao_atendidos < 1 or opcao_atendidos > 4: 
        print('{:=^30}'.format(' Pacientes atendidos no dia '))
        print('1 - Consultório de Dermatologia\n2 - Consultório de Endocrinologia\n3 - Consultório de Ortopedia\n4 - Sair\n')
        opcao_atendidos = int(input('Insira a opção desejada: '))
        if opcao_atendidos == 1:
            lista_atendidos(dermatologia_atendidos_comum, dermatologia_atendidos_pref)
        elif opcao_atendidos == 2:
            lista_atendidos(endocrinologia_atendidos_comum, endocrinologia_atendidos_pref)
        elif opcao_atendidos == 3:
            lista_atendidos(ortopedista_atendidos_comum, ortopedista_atendidos_pref)
    clear()

def op_tempo_medio_espera():
    ''' Módulo da opção de exibir os tempos médios de espera. '''
    
    clear()
    tempo_derma_c, cont_derma_c, tempo_derma_p, cont_derma_p = tempo_espera(consultorio_dermato, dermatologia_comum, dermatologia_pref, dermatologia_atendidos_comum, dermatologia_atendidos_pref)
    tempo_endo_c, cont_endo_c, tempo_endo_p, cont_endo_p = tempo_espera(consultorio_endo, endocrinologia_comum, endocrinologia_pref, endocrinologia_atendidos_comum, endocrinologia_atendidos_pref)
    tempo_orto_c, cont_orto_c, tempo_orto_p, cont_orto_p = tempo_espera(consultorio_orto, ortopedista_comum, ortopedista_pref, ortopedista_atendidos_comum, ortopedista_atendidos_pref)        
        
    tempo_total_comum = tempo_derma_c + tempo_endo_c + tempo_orto_c  # Somatório do tempo total de espera dos pacientes comuns
    contador_total_comum = cont_derma_c + cont_endo_c + cont_orto_c  # Somatório dos contadores de pacientes comuns (atendidos/em atendimento)

    tempo_total_preferencial = tempo_derma_p + tempo_endo_p + tempo_orto_p  # Somatório do tempo total de espera dos pacientes preferenciais
    contador_total_preferencial = cont_derma_p + cont_endo_p + cont_orto_p  # Somatório dos contadores de pacientes preferencais (atendidos/em atendimento)

    tempo_total_todas_filas = tempo_total_comum + tempo_total_preferencial  # Somatório do tempo total de espera de todas as filas
    contador_total_todas_filas = contador_total_comum + contador_total_preferencial  # Somatório dos contatores de pacientes de todas as filas

    if contador_total_todas_filas > 0:  # Verifica se há pacientes para o cálculo do tempo médio de espera
        tempo_medio_total = str(tempo_total_todas_filas/contador_total_todas_filas).zfill(8)  # Tempo médio de espera do ambulatório total (com as duas filas juntas)
        print('Tempo médio de espera do ambulatório (HH:MM): {}'.format(tempo_medio_total[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de espera do ambulatório: ainda não disponível')
        
    if contador_total_comum > 0:  # Verifica se há pacientes comuns para o cálculo do tempo médio de espera
        tempo_medio_comum = str(tempo_total_comum/contador_total_comum).zfill(8)  # Tempo médio de espera da fila comum no ambulatório (fila comum de todos os consultórios)
        print('\nTempo médio de espera da fila comum no ambulatório (HH:MM): {}'.format(tempo_medio_comum[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de espera da fila comum no ambulatório: ainda não disponível')
        
    if contador_total_preferencial > 0:  # Verifica se há pacientes preferenciais para o cálculo do tempo médio de espera
        tempo_medio_preferencial = str(tempo_total_preferencial/contador_total_preferencial).zfill(8)  # Tempo médio de espera da fila preferencial no ambulatório (fila preferencial de todos os consultórios)
        print('Tempo médio de espera na fila preferencial no ambulatório (HH:MM): {}'.format(tempo_medio_preferencial[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de espera na fila preferencial no ambulatório: ainda não disponível')
        
    if cont_derma_c > 0:  # Verifica se há pacientes comuns de dermatologia para o cálculo do tempo médio de espera
        medio_dermato_c = str(tempo_derma_c/cont_derma_c).zfill(8)  # Tempo médio de espera da fila comum do consultório de dermatologia
        print('\nTempo médio de espera consultório Dermatologia - fila comum (HH:MM): {}'.format(medio_dermato_c[0:5]))      
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de espera consultório Dermatologia - fila comum: ainda não disponível')
    if cont_derma_p > 0:   # Verifica se há pacientes preferenciais de dermatologia para o cálculo do tempo médio de espera
        medio_dermato_p = str(tempo_derma_p/cont_derma_p).zfill(8)  # Tempo médio de espera da fila preferencial do consultório de dermatologia
        print('Tempo médio de espera consultório Dermatologia - fila preferencial (HH:MM): {}'.format(medio_dermato_p[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de espera consultório Dermatologia - fila preferencial: ainda não disponível')
        
    if cont_endo_c > 0:  # Verifica se há pacientes comuns de endocrinologia para o cálculo do tempo médio de espera
        medio_endo_c = str(tempo_endo_c/cont_endo_c).zfill(8)  # Tempo médio de espera da fila comum do consultório de endocrinologia
        print('\nTempo médio de espera consultório de Endocrinologia - fila comum (HH:MM): {}'.format(medio_endo_c[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de espera consultório de Endocrinologia - fila comum: ainda não disponível')
    if cont_endo_p > 0: 
        medio_endo_p = str(tempo_endo_p/cont_endo_p).zfill(8)
        print('Tempo médio de espera consultório de Endocrinologia - fila preferencial (HH:MM): {}'.format(medio_endo_p[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de espera consultório de Endocrinologia - fila preferencial: ainda não disponível')
        
    if cont_orto_c > 0:  # Verifica se há pacientes comuns de ortopedia para o cálculo do tempo médio de espera
        medio_orto_c = str(tempo_orto_c/cont_orto_c).zfill(8)  # Tempo médio de espera da fila comum do consultório de ortopedia
        print('\nTempo médio de espera consultório de Ortopedia - fila comum (HH:MM): {}'.format(medio_orto_c[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de espera consultório de Ortopedia - fila comum: ainda não disponível')
    if cont_orto_p > 0:  # Verifica se há pacientes preferenciais de ortopedia para o cálculo do tempo médio de espera
        medio_orto_p = str(tempo_orto_p/cont_orto_p).zfill(8)  # Tempo médio de espera da fila comum do consultório de ortopedia
        print('Tempo médio de espera consultório de Ortopedia - fila preferencial (HH:MM): {}'.format(medio_orto_p[0:5]))
    else:
        print('Tempo médio de espera consultório de Ortopedia - fila preferencial: ainda não disponível')
    input('\nTecle enter para voltar ao menu principal')
    clear()

def op_tempo_medio_atendimento():
    ''' Módulo da opção de exibir os tempos médios de atendimento. '''
    
    clear()
    temp_atend_derm_c, qtd_atend_derm_c, temp_atend_derm_p, qtd_atend_derm_p = tempo_atendimento(dermatologia_atendidos_comum, dermatologia_atendidos_pref)
    temp_atend_endo_c, qtd_atend_endo_c, temp_atend_endo_p, qtd_atend_endo_p = tempo_atendimento(endocrinologia_atendidos_comum, endocrinologia_atendidos_pref)
    temp_atend_orto_c, qtd_atend_orto_c, temp_atend_orto_p, qtd_atend_orto_p = tempo_atendimento(ortopedista_atendidos_comum, ortopedista_atendidos_pref)
    ''' Funções das variáveis que recebem os dados do método tempo_atendimento():
    temp_atend_derm_c: recebe a soma dos tempo de atendimentos dos pacientes comuns de dermatologia
    qtd_atend_derm_c: recebe a quantidade de pacientes comuns atendidos em dermatologia
    temp_atend_derm_p: recebe a soma dos tempo de atendimentos dos pacientes comuns de dermatologia
    qtd_atend_derm_p: recebe a quantidade de pacientes comuns atendidos em dermatologia
    As outras variáveis que recebem dados deste mesmo método com outros parâmetros segue a mesma logica só que para cada consultório especifico.'''
    
    tempo_atendimento_total_comum = temp_atend_derm_c + temp_atend_endo_c + temp_atend_orto_c
    quantidade_atendimento_total_comum = qtd_atend_derm_c + qtd_atend_endo_c + qtd_atend_orto_c

    tempo_atendimento_total_preferencial = temp_atend_derm_p + temp_atend_endo_p + temp_atend_orto_p
    quantidade_atendimento_total_preferencial = qtd_atend_derm_p + qtd_atend_endo_p + qtd_atend_orto_p

    tempo_atendimento_total_todas_filas = tempo_atendimento_total_comum + tempo_atendimento_total_preferencial
    quantidade_atendimento_total_todas_filas = quantidade_atendimento_total_comum + quantidade_atendimento_total_preferencial

    if quantidade_atendimento_total_todas_filas > 0:  # Verifica se há pacientes para o cálculo do tempo médio de atendimento de todo ambulatório
        tempo_medio_total_atendimento = str(tempo_atendimento_total_todas_filas/quantidade_atendimento_total_todas_filas).zfill(8)  # Tempo médio de atendimento do ambulatório total (com as duas filas juntas)
        print('Tempo médio de atendimento do ambulatório (HH:MM): {}'.format(tempo_medio_total_atendimento[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de atendimento do ambulatório: ainda não disponível')
        
    if quantidade_atendimento_total_comum > 0:  # Verifica se há pacientes comuns para o cálculo do tempo médio de atendimento
        tempo_medio_comum_atendimento = str(tempo_atendimento_total_comum/quantidade_atendimento_total_comum).zfill(8)  # Tempo médio de atendimento da fila comum no ambulatório (fila comum de todos os consultórios)
        print('\nTempo médio de atendimento da fila comum no ambulatório (HH:MM): {}'.format(tempo_medio_comum_atendimento[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de atendimento da fila comum no ambulatório: ainda não disponível')

    if quantidade_atendimento_total_preferencial > 0:  # Verifica se há pacientes preferenciais para o cálculo do tempo médio de atendimento
        tempo_medio_preferencial_atendimento = str(tempo_atendimento_total_preferencial/quantidade_atendimento_total_preferencial).zfill(8)  # Tempo médio de atendimento da fila preferencial no ambulatório (fila preferencial de todos os consultórios)
        print('Tempo médio de atendimento na fila preferencial no ambulatório (HH:MM): {}'.format(tempo_medio_preferencial_atendimento[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de atendimento na fila preferencial no ambulatório: ainda não disponível')
        
    if qtd_atend_derm_c > 0:  # Verifica se há pacientes comuns de dermatologia para o cálculo do tempo médio de atendimento
        medio_atendimento_dermato_c = str(temp_atend_derm_c/qtd_atend_derm_c).zfill(8)  # Tempo médio de atendimento da fila comum do consultório de dermatologia
        print('\nTempo médio de atendimento consultório Dermatologia - fila comum (HH:MM): {}'.format(medio_atendimento_dermato_c[0:5]))      
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de atendimento consultório Dermatologia - fila comum: ainda não disponível')
    if qtd_atend_derm_p > 0:   # Verifica se há pacientes preferenciais de dermatologia para o cálculo do tempo médio de atendimento
        medio_atendimento_dermato_p = str(temp_atend_derm_p/qtd_atend_derm_p).zfill(8)  # Tempo médio de atendimento da fila preferencial do consultório de dermatologia
        print('Tempo médio de atendimento consultório Dermatologia - fila preferencial (HH:MM): {}'.format(medio_atendimento_dermato_p[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de atendimento consultório Dermatologia - fila preferencial: ainda não disponível')
        
    if qtd_atend_endo_c > 0:  # Verifica se há pacientes comuns de endocrinologia para o cálculo do tempo médio de atendimento
        medio_atendimento_endo_c = str(temp_atend_endo_c/qtd_atend_endo_c).zfill(8)  # Tempo médio de atendimento da fila comum do consultório de endocrinologia
        print('\nTempo médio de atendimento consultório de Endocrinologia - fila comum (HH:MM): {}'.format(medio_atendimento_endo_c[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de atendimento consultório de Endocrinologia - fila comum: ainda não disponível')
    if qtd_atend_endo_p > 0:  # Verifica se há pacientes prefenciais de endocrinologia para o cálculo do tempo médio de atendimento
        medio_atendimento_endo_p = str(temp_atend_endo_p/qtd_atend_endo_p).zfill(8)  # Tempo médio de atendimento da preferencial comum do consultório de endocrinologia
        print('Tempo médio de atendimento consultório de Endocrinologia - fila preferencial (HH:MM): {}'.format(medio_atendimento_endo_p[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('Tempo médio de atendimento consultório de Endocrinologia - fila preferencial: ainda não disponível')

    if qtd_atend_orto_c > 0:  # Verifica se há pacientes comuns de ortopedia para o cálculo do tempo médio de atendimento
        medio_atendimento_orto_c = str(temp_atend_orto_c/qtd_atend_orto_c).zfill(8)  # Tempo médio de atendimento da fila comum do consultório de ortopedia
        print('\nTempo médio de atendimento consultório de Ortopedia - fila comum (HH:MM): {}'.format(medio_atendimento_orto_c[0:5]))
    else:  # Caso não haja pacietes na fila para o cálculo do tempo médio
        print('\nTempo médio de atendimento consultório de Ortopedia - fila comum: ainda não disponível')
    if qtd_atend_orto_p > 0:  # Verifica se há pacientes preferenciais de ortopedia para o cálculo do tempo médio de atendimento
        medio_atendimento_orto_p = str(temp_atend_orto_p/qtd_atend_orto_p).zfill(8)  # Tempo médio de atendimento da fila comum do consultório de ortopedia
        print('Tempo médio de atendimento consultório de Ortopedia - fila preferencial (HH:MM): {}'.format(medio_atendimento_orto_p[0:5]))
    else:
        print('Tempo médio de atendimento consultório de Ortopedia - fila preferencial: ainda não disponível')
    input('\nTecle enter para voltar ao menu principal')
    clear()


''' Início do programa principal '''

while opcao_menu != '9':  # Menu principal
    
    opcao_menu = menu()   # Opções do menu principal
    clear()  # Limpar a tela
    
    if opcao_menu == '1':  # Gerar senha/cadastro 
        op_cadastro(opcao_menu)

    elif opcao_menu == '2': # Chamar paciente para atendimento  #TRANSFORMAR EM MÉTODO
        verificador_atendimento_dermato, verificador_atendimento_endo, verificador_atendimento_orto = op_chamar_paciente(verificador_atendimento_dermato, verificador_atendimento_endo, verificador_atendimento_orto)
    
    elif opcao_menu == '3': # Pular paciente
        op_pular_paciente()
        
    elif opcao_menu == '4': # Encerrar uma consulta
        op_encerrar_consulta()
        
    elif opcao_menu == '5': # Exibir fila de espera
        op_fila_espera()
        
    elif opcao_menu == '6': # Exibir pacientes atendidos no dia
        op_fila_atendidos()
        
    elif opcao_menu == '7': # Exibir tempo médio de espera dos pacientes
        op_tempo_medio_espera()
        
    elif opcao_menu == '8': # Exibir tempo médio de atendimento dos pacientes.
        op_tempo_medio_atendimento()