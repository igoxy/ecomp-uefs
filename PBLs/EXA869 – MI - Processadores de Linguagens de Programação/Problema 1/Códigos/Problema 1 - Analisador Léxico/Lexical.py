# -*- coding: utf-8 -*-

# Disciplina: EXA869 - MI Processadores de Linguagens de Programação
# Aluno: Igor Figueredo Soares
# Matrícula: 19211242

#INFO: FOI CONSIDERADO QUE UM VALOR NEGATIVO É REPRESENTADO POR UM OPERADOR MENOS (-) SEGUIDO DO NÚMERO

# Imports
from Erros import Erro
from Token import Token
from BaseTokens import BaseTokens

class Lexical:

    # -- Variáveis --
    row = 0                 # Igual ao índice do vetor
    position = 0            # Posição na linha

    # -- Listas --
    tokens_found = []
    errors_found = []

    symbol_table = []       # Tabela de símbolos (na verdade é uma lista kkkk)

    # Carregando Tokens básicos
    base_tokens = BaseTokens()

    def __init__(self, rows: list):
        self.rows = rows

    # Método para a análise léxica
    def lex(self):
        ''' Executa a análise léxica '''

        self.reset_lists()  # Reinicia as listas para iniciar a análise

        #row = 0
        while (self.row < len(self.rows)):
            current_line = str(self.rows[self.row]).strip()     # Obtém a linha atual e remove quebras de linha e espaços em branco no inicio e no final
            self.position = 0                                   # Reinicia a posição

            if (len(current_line) < 1):                         # Se a linha tiver em branco
                self.row += 1                                   # Pula para a próxima linha
                continue

            while (self.position < len(current_line)):      # Enquanto não percorrer toda a linha
                
                # -- Verifica o caractere da posição atual --
                if self.verify_comments(current_line):                      # Verifica comentários 
                    current_line = str(self.rows[self.row]).strip()         # Se foi um comentário, atualiza a linha - isso porque para o caso do comentário em bloco, ele pode acabar em outra linha então é necessário voltar a analise a partir dela
                    continue
                elif self.verify_string_and_character(current_line):        # String e Character
                    continue
                elif self.verify_delimiters(current_line):                  # Delimitadores
                    continue
                elif self.verify_operators(current_line):                   # Operadores
                    continue
                elif self.verify_numbers(current_line):                     # Números
                    continue
                elif self.verify_keywords_and_identifiers(current_line):    # Palavras-chave e identificadores
                    continue
                elif current_line[self.position] == ' ':                    # Se for um caractere em branco
                    self.position += 1                                      # Ignora o cacractere
                else:                                                       # Se o caractere não se enquadrar em nenhum caso
                    #print(f'ERRO - ELEMENTO INVÁLIDO !!!: {current_line[self.position]}')
                    self.errors_found.append(Erro(current_line[self.position], self.position, self.row, 'Elemento desconhecido'))   # Registra o erro na lista de erros
                    self.position += 1
            self.row += 1


        # Retornar tokens, erros e a tabela de símbolos
        return self.tokens_found, self.errors_found, self.symbol_table

# ----- Métodos para as verificações -----

    # Verifica comentários
    def verify_comments(self, current_line):
        item = current_line[self.position]

        if (item == '/'):

            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                
                next_item = current_line[self.position + 1]                                             # Obtém o próximo elemento da linha

                if (next_item == '/'):                                                                  # Comentário de linha única
                    lexeme = item + next_item                                                           # Lexema - Token comentário em linha
                    token = Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row)  # Cria o token de comentário em linha
                    self.tokens_found.append(token)                                                     # Adiciona o token a lista de tokens
                    
                    self.position = len(current_line)                                                   # Seta a posição para o final da linha para pular para a próxima linha
                    return True                                                                         # Indica que encontrou um comentário de linha
                
                if (next_item == '*'):
                    lexeme = item + next_item                                                           # Lexema - Token abertura de comentário em bloco
                    token = Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row)  # Cria o token de comentário em bloco
                    self.tokens_found.append(token)                                                     # Adiciona o token a lista de tokens

                    end_comment = False
                    self.position += 2          # Indentifica que foi encontrado o lexema de comentário em bloco (dois elementos) e anda duas posições na linha
                    while (not end_comment):    # Se for um comentário em bloco vai consumindo os caracteres até achar o fechamento
                        aux_count = 0
                        
                        # Se não for último elemento da linha
                        if (self.has_next(current_line)):  # Se tiver um próximo elemento pode ser o lexema de fechar comentário em bloco (par de caracteres)
                            temp_item = current_line[self.position]     # Pega o elemento da linha
                            aux_count += 1      # Consumiu um elemento na linha 

                            if (temp_item == '*'):
                                # Se não for último elemento da linha
                                #if ((len(current_line) - 1) >= self.position + aux_count + 1):      # Verifica se depois do elemento * tem mais item
                                    
                                temp_next_item = current_line[self.position + aux_count]        # Pega o próximo elemento da linha após o *
                                    
                                if (temp_next_item == '/'):                         # Se for barra 
                                    aux_count += 1                                                                      # Consome mais um elemento na linha
                                    lexeme = temp_item + temp_next_item                                                 # Lexema - Token fechamento de comentário em bloco
                                    token = Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row)  # Cria o token de comentário em bloco
                                    self.tokens_found.append(token)                                                     # Adiciona o token a lista de tokens
                                    end_comment = True                                                                  # Comentário finalizado

                            self.position += aux_count              # Anda a quantide de elementos consumidos na linha
                        
                        # Se estiver na última posição da linha
                        elif (self.row >= (len(self.rows)-1)): # Se foi a última linha do documento
                            #print(f'ERRO - COMENATÁRIO EM BLOCO NÃO FECHADO !!!: LINHA: {self.row} - POS: {self.position}')
                            
                            self.errors_found.append(Erro('', self.position, self.row, 'Comentário em bloco não finalizado'))   # Registra o erro na lista de erros
                            self.position = len(current_line)       # Vai para a última posição para sair da linha - nesse caso encerrar a análise
                            self.row = len(self.rows) - 1           # Indica que chegou a última linha

                            return True                             # Indica que não precisa verificar mais nenhuma outra situação
                        
                        else:
                            self.row += 1                                   # Vai para a próxima linha
                            self.position = 0                               # Reinicia a posição                            
                            current_line = str(self.rows[self.row]).strip() # A linha a ser analisada é a nova linha
                    
                    return True     # Comentário finalizado - não precisa verificar mais nenhuma outra situação
        
        return False    # Se não for barra o caractere de entrada ou for o último elemento da linha


    # Verifica os delimitadores
    def verify_delimiters(self, current_line):
        item = current_line[self.position]

        if ((item == ';') or (item == ',') or (item == '(') or (item == ')') or (item == '{') or (item == '}') or (item == '[') or (item == ']')):
            #print(f'TOKEN DELIMITADOR: {item}')
            self.tokens_found.append(Token(self.base_tokens.get_token(item), item, self.position, self.row))   # Insere o Token na lista de tokens
            self.position += 1
            return True
        return False


    # Verifica os operadores
    def verify_operators(self, current_line):
        item = current_line[self.position]

        # Operadores sem combinação --> * / .
        if ((item == '*') or (item == '/') or (item == '.')):
            #print(f'TOKEN OPERATOR EXCLUSIVAMENTE UNICO: {item}')
            self.tokens_found.append(Token(self.base_tokens.get_token(item), item, self.position, self.row))    # Insere o Token na lista de tokens
            self.position += 1                                                                                  # Avança uma posição
            
            return True                                                                                         # Encontrou o elemento

        # Operadores com combinação deles mesmo que não podem vir sozinhos --> && ||
        if ((item == '&') or (item == '|')):
            
            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                next_item = current_line[self.position + 1]

                if (item == next_item):                                                                 # Se o item for igual a outro é um token operador combinado
                    lexeme = item+next_item
                    #print(f'TOKEN OPERADOR DUAS VEZES: {lexeme}')
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))   # Insere o Token na lista de tokens
                    self.position += 2
                else:
                    #print(f'ERRO - TOKEN INVÁLIDO !!!: {item}')                                         # Se o próximo item não for igual ao atual
                    self.errors_found.append(Erro(item, self.position, self.row, 'Operador inválido'))   # Registra o erro na lista de erros
                    self.position += 1

            else: # Se for o último elemento da linha
                #print(f'ERRO - TOKEN INVÁLIDO !!!: {item}')
                self.errors_found.append(Erro(item, self.position, self.row, 'Operador inválido'))       # Registra o erro na lista de erros
                self.position += 1
            
            return True                                                                                 # Indica que já achou onde o elemento se encaixa (operador)
            
        # Operadores que podem combinar entre si ou vir sozinhos --> + - ++ --
        if ((item == '-') or (item == '+')):
            
            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                
                next_item = current_line[self.position + 1]                                                                 # Obtém o próximo item da linha
                
                if (item == next_item):
                    lexeme = item+next_item
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))    # Insere o Token na lista de tokens
                    #print(f'TOKEN OPERADOR DUAS VEZES: {lexeme}')
                    self.position += 2
                else:   
                    lexeme = item
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))    # Insere o Token na lista de tokens
                    #print(f'TOKEN OPERADOR UMA VEZ: {item}')
                    self.position += 1

            else:                                                                                                           # Se for o último elemento da linha
                lexeme = item
                self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))        # Insere o Token na lista de tokens
                #print(f'TOKEN OPERADOR UMA VEZ: {item}')
                self.position += 1  
            
            return True
        
        # Operador que só pode aparecer seguido de = --> !=
        if (item == '!'):
            
            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                next_item = current_line[self.position + 1]
                if (next_item == '='):
                    lexeme = item+next_item
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))   # Insere o Token na lista de tokens
                    #print(f'TOKEN OPERADOR DUPLO !=: {lexeme}')
                    self.position += 2
                else:
                    #print(f'ERRO - TOKEN INVÁLIDO !!!: {item}')
                    self.errors_found.append(Erro(item, self.position, self.row, 'Operador inválido'))   # Registra o erro na lista de erros
                    self.position += 1
            
            else:                                                                                       # Se for o último elemento da linha
                #print(f'ERRO - TOKEN INVÁLIDO: {item}')
                self.errors_found.append(Erro(item, self.position, self.row, 'Operador inválido'))       # Registra o erro na lista de erros
                self.position += 1
            
            return True     # Indica que o caractere faz parte desse conjunto

        # Operadores com combinação com o = (igual) e que podem também aparecer sozinhos  --> = == > >= < <=
        if ((item == '=') or (item == '>') or (item == '<')):
            
            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                next_item = current_line[self.position + 1]
                if (next_item == '='):
                    lexeme = item+next_item
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))    # Insere o Token na lista de tokens
                    #print(f'TOKEN OPERADOR DUPLO: {lexeme}')
                    self.position += 2
                else:
                    lexeme = item                                                                                           # Matendo o mesmo padrão de chamar de lexeme
                    self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))    # Insere o Token na lista de tokens
                    #print(f'TOKEN OPERADOR UMA VEZ: {lexeme}')                                                              # Operador sozinho
                    self.position += 1
            
            else:
                # Se for o último elemento da linha
                lexeme = item
                self.tokens_found.append(Token(self.base_tokens.get_token(lexeme), lexeme, self.position, self.row))        # Insere o Token na lista de tokens
                #print(f'TOKEN OPERADOR UMA VEZ: {lexeme}')                                                                  # Operador sozinho
                self.position += 1
            
            return True     # Indica que o caractere faz parte desse conjunto
        
        return False    # Caso não seja nenhuma opção
    

    # Verifica os números
    def verify_numbers(self, current_line):
        item = current_line[self.position]

        if (item >= '0' and item <= '9'):

            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                aux_count = 1
                lexeme = item       # !! str(current_line[self.position])
                next_item = current_line[self.position + aux_count]
                while ((next_item >= '0') and (next_item <= '9')):

                    lexeme = lexeme + str(next_item)                                            # Concatena
                    aux_count += 1
                    if ((len(current_line) - 1) >= (self.position + aux_count)):                # Se não for o último elemento da linha
                        next_item = current_line[self.position + aux_count]
                    else:       
                        break                                                                   # Se for o último elemento da linha sai do loop
                
                # Verificação de FLOAT
                if (next_item == '.'):
                    if ((len(current_line) - 1) >= (self.position + aux_count + 1)):            # Se não for o último elemento da linha
                        if ((current_line[self.position + aux_count + 1]) >= '0' and (current_line[self.position + aux_count + 1]) <= '9'): # Verifica se o elemento após o ponto é um número
                            lexeme = lexeme + next_item                                         # Salva o ponto no lexema
                            aux_count += 1                                                      # Soma um para indicar que o ponto já foi contado
                            next_item = current_line[self.position + aux_count]                 # Obtém o número após o ponto
                            
                            while ((next_item >= '0') and (next_item <= '9')):
                                lexeme = lexeme + next_item                                     # Concatena
                                aux_count += 1
                                if ((len(current_line) - 1) >= (self.position + aux_count)):    # Se não for o último elemento da linha
                                    next_item = current_line[self.position + aux_count]         # Obtém o próximo elemento
                                else:       
                                    break                                                       # Se for o último elemento da linha sai do loop
                            
                            #print(f'TOKEN NUMERO FLOAT: {lexeme}')
                            self.tokens_found.append(Token(self.base_tokens.get_token('num'), lexeme, self.position, self.row))   # Insere o Token na lista de tokens
                            self.position += aux_count
                            return True                                                                                 # Se for número FLOAT - indica que o caractere faz parte desse conjunto
                
                #print(f'TOKEN NUMERO INT: {lexeme}')
                self.tokens_found.append(Token(self.base_tokens.get_token('num'), lexeme, self.position, self.row))     # Insere o Token na lista de tokens
                self.position += aux_count

                return True                                                                                             # Se for número INT composto - indica que o caractere faz parte desse conjunto
                            
            else:
                lexeme = item                                                                                           # Só para manter o padrão de ser lexeme
                self.tokens_found.append(Token(self.base_tokens.get_token('num'), lexeme, self.position, self.row))     # Insere o Token na lista de tokens
                #print(f'TOKEN NUMERO UNICO: {lexeme}')
                self.position += 1
            return True                                                                                                 # Se for número INT sozinho - indica que o caractere faz parte desse conjunto
        
        return False                                                                                                    # Se não for número


    # Verifica palavras-chave e Identificadores
    def verify_keywords_and_identifiers(self, current_line):
        item = current_line[self.position]

        if ((item >= 'A') and (item <= 'Z') or (item >= 'a') and (item <= 'z')):
            
            # Se não for último elemento da linha
            if (self.has_next(current_line)):
                aux_count = 1
                lexeme = str(current_line[self.position])
                next_item = current_line[self.position + aux_count]
                
                while (((next_item >= 'A') and (next_item <= 'Z')) or ((next_item >= 'a') and (next_item <= 'z')) or (next_item == '_') or ((next_item >= '0') and (next_item <= '9'))):
                    lexeme = lexeme + next_item
                    aux_count += 1
                    if ((len(current_line) - 1) >= (self.position + aux_count)):                                    # Se não for o último elemento da linha
                        next_item = current_line[self.position + aux_count]                                         # Obtém o próximo elemento
                    else:       
                        break                                                                                       # Se for o último elemento da linha sai do loop
                
                # Verifica se é uma Keyword
                result_keyword_token = self.base_tokens.get_token_keyword(lexeme)   
                if (result_keyword_token != None):    # Se for uma Keyword
                    self.tokens_found.append(Token(result_keyword_token, lexeme, self.position, self.row))          # Insere o Token na lista de tokens
                    #print(f'TOKEN KEYWORD: {lexeme}')
                else:                                                                                               # Se for um identificador
                    index_table =  self.verify_ident_table(lexeme)                                                  # Verifica se o identificador já está na tabela - retorna o index ou None
                    if (index_table != None):                                                                       # Se o identificador estiver na tabela
                        token = Token('TOKEN.ID', lexeme, self.position, self.row, index_table)                     # Cria o token indicando a pos do ID na tabela de simbolos
                        self.tokens_found.append(token)                                                             # Insere o Token na lista de tokens
                    else:
                        self.symbol_table.append(lexeme)                                                            # Adiciona o identificador na tabela de simbolos
                        token = Token('TOKEN.ID', lexeme, self.position, self.row, (len(self.symbol_table) - 1))    # Cria o token indicando a pos que o ID  foi inserido na tabela de simbolos
                        self.tokens_found.append(token)                                                             # Insere o Token na lista de tokens
                    #print(f'TOKEN IDENTIFICADOR: {lexeme}')
                
                self.position += aux_count
                return True                                                                                         # Se for um lexema com mais de um caractere
                    
            else:                                                                                                   # Se for o último elemento da linha
                lexeme = item                                                                                       # Só para manter a consistência
                result_keyword_token = self.base_tokens.get_token_keyword(lexeme)                                   # Verifica se é uma Keyword
                
                if (result_keyword_token != None):                                                                  # Se for uma Keyword
                    self.tokens_found.append(Token(result_keyword_token, lexeme, self.position, self.row))          # Insere o Token na lista de tokens
                    #print(f'TOKEN KEYWORD: {lexeme}')
                else:
                    index_table =  self.verify_ident_table(lexeme)                                                  # Verifica se o identificador já está na tabela - retorna o index ou None
                    if (index_table != None):                                                                       # Se o identificador estiver na tabela
                        token = Token('TOKEN.ID', lexeme, self.position, self.row, index_table)                     # Cria o token indicando a pos do ID na tabela de simbolos
                        self.tokens_found.append(token)                                                             # Insere o Token na lista de tokens
                    else:
                        self.symbol_table.append(lexeme)                                                            # Adiciona o identificador na tabela de simbolos
                        token = Token('TOKEN.ID', lexeme, self.position, self.row, (len(self.symbol_table) - 1))    # Cria o token indicando a pos que o ID  foi inserido na tabela de simbolos
                        self.tokens_found.append(token)                                                             # Insere o Token na lista de tokens

                    #print(f'TOKEN IDENTIFICADOR: {lexeme}')
                    
                self.position += 1  # Vai para o próximo caractere
                return True
        
        return False    # Senão for caractere

    
    # Verifica Strings e Char
    def verify_string_and_character(self, current_line):
        item = current_line[self.position]

        # Aspas duplas (String) - Considerando para o caso de dois erros - exibir os dois e a string ser desconsiderada como token válido
        if (item == '"'):   
            if (self.has_next(current_line)):  # Se não for último elemento da linha
                next_item = current_line[self.position + 1]
                initial_position = self.position    # Indica onde a string começa para salvar no token
                self.position += 1                  # Contabiliza o elemento consumido
                lexeme = item                       # Atribui o item ao lexema
                
                flag_erro = False   # Para indicar erro de caractere inválido
                while (True): # Enquanto a String não for finalizada
                    if ((ord(next_item) >= 32) and ((ord(next_item) <= 126)) and (next_item != "'") and (next_item != '"')): # Se for um elemento válido da String
                        lexeme = lexeme + next_item     # Concatena o item no lexema
  
                    elif (next_item == '"'):    # Fechamento da String
                        if (not flag_erro):     # Se não houve erro de caractere inválido salva o Token
                            lexeme = lexeme + next_item
                            self.tokens_found.append(Token(self.base_tokens.get_token('string'), lexeme, initial_position, self.row))     # Insere o Token na lista de tokens

                        # Se houve erro de caractere inválido o token não é salvo - mas a análise volta do final da string    
                        self.position += 1          # Desloca para a próxima posição para continuar a análise lexica
                        
                        return True                 # Terminou a string

                    else:   # Se for um elemento/simbolo/caractere inválido na String
                        #print(f'ERRO - SIMBOLO INVÁLIDO: {next_item}')
                        flag_erro = True                    # Indica o erro de caractere inválido - logo a string não gera um token válido, mesmo que seja fechada
                        lexeme = lexeme + next_item         # Adiciona o simbolo inválido ao lexema para exibir a "string" gerada no erro
                        self.errors_found.append(Erro(next_item, initial_position, self.row, 'Símbolo inválido para String'))   # Mesmo com o erro a string é consumida para encontrar seu fechamento

                    # Para seguir ao próximo caractere
                    if  (self.has_next(current_line)):         # Se não for último elemento da linha
                        next_item = current_line[self.position + 1]     # Obtém o próximo elemento da linha
                        self.position += 1                                      # Avança uma posição
                    else:
                        #print(f'ERRO - STRING NÃO FECHADA !!! - ITEM FINAL: {lexeme}')                       # Se o próximo item não for igual ao atual
                        self.errors_found.append(Erro(lexeme, initial_position, self.row, 'String não finalizada'))   # Registra o erro na lista de erros
                        self.position += 1
                        
                        return True
            
            else:   # Se for o último elemento da linha
                #print(f'ERRO - STRING NÃO FECHADA !!!: {item}')                                         # Se o próximo item não for igual ao atual
                self.errors_found.append(Erro(item, self.position, self.row, 'String não finalizada'))   # Registra o erro na lista de erros
                self.position += 1
                
                return True
        
        #Aspas simples (Charactere)
        elif (item == "'"):
            if (self.has_next(current_line)):  # Se não for último elemento da linha
                next_item = current_line[self.position + 1]
                initial_position = self.position    # Indica onde o Charactere começa para salvar no token
                self.position += 1                  # Contabiliza o elemento consumido
                lexeme = item                       # Atribui o item ao lexema
                
                flag_erro = False   # Para indicar erro de caractere inválido
                while (True): # Enquanto a Charactere não for finalizada
                    if ((ord(next_item) >= 32) and ((ord(next_item) <= 126)) and (next_item != "'") and (next_item != '"')): # Se for um elemento válido do Charactere
                        lexeme = lexeme + next_item     # Concatena o item no lexema

                    elif (next_item == "'"):                # Fechamento do Charactere
                        if (not flag_erro):                 # Se não houve erro de caractere inválido
                            lexeme = lexeme + next_item     # Insere o item no lexema
                
                            if (len(lexeme) == 3):          # Verifica o Charactere tem examente um símbolo (ou seja, três elementos --> 'X')
                                self.tokens_found.append(Token(self.base_tokens.get_token('char'), lexeme, initial_position, self.row))     # Insere o Token na lista de tokens 
                            else:
                                #print(f'ERRO - CHARACTERE COM QUANTIDADE DIFERENTE DE 1 DE SIMBOLOS !!! {lexeme}')                                            # Se o próximo item não for igual ao atual
                                self.errors_found.append(Erro(lexeme, initial_position, self.row, 'O Charactere deve ter um elemento'))        # Registra o erro na lista de erros
                        
                        # Se houve erro de caractere inválido o token não é salvo - mas a análise volta a partir do próximo item da linha    
                        self.position += 1          # Desloca para a próxima posição para continuar a análise lexica
                        
                        return True                 # Terminou o Charactere

                    else:               # Se for um elemento/simbolo/caractere inválido no Charactere
                        #print(f'ERRO - SIMBOLO INVÁLIDO: {next_item}')
                        flag_erro = True                            # Indica o erro de caractere inválido - logo o Charactere não gera um token válido, mesmo que seja fechado
                        lexeme = lexeme + next_item                 # Adiciona o simbolo inválido ao lexema para exibir o "Charactere" gerado no erro, caso o Char não seja fechado corretamente
                        self.errors_found.append(Erro(next_item, initial_position, self.row, 'Símbolo inválido para Charactere'))   # Mesmo com o erro o Charactere é consumido para encontrar seu fechamento

                    # Para seguir ao próximo caractere
                    if  (self.has_next(current_line)):     # Se não for último elemento da linha
                        next_item = current_line[self.position + 1]         # Obtém o próximo elemento da linha
                        self.position += 1                                  # Avança uma posição
                    else:
                        #print(f'ERRO - CHARACTERE NÃO FECHADA !!! - ITEM FINAL: {lexeme}')                          # Se o próximo item não for igual ao atual
                        self.errors_found.append(Erro(lexeme, initial_position, self.row, 'Charactere não finalizado'))     # Registra o erro na lista de erros
                        self.position += 1
                        
                        return True
            
            else:   # Se for o último elemento da linha
                #print(f'ERRO - CHARACTERE NÃO FECHADA !!!: {item}')                                         # Se o próximo item não for igual ao atual
                self.errors_found.append(Erro(item, self.position, self.row, 'Charactere não finalizado'))   # Registra o erro na lista de erros
                self.position += 1
                
                return True    


    # ------ utils ------
    def has_next(self, current_line):
        ''' Verifica se tem o próximo elemento na linha 
        Argumento:
        current_line -- Linha a ser verificada

        Retorno:
        True se tem próximo elemento - False caso contrário
        '''
        return ((len(current_line) - 1) >= self.position + 1)

    def verify_ident_table(self, lexeme):
        ''' Verifica se o símbolo está na tabela.

        Argumentos:
        lexeme -- String com o Lexema

        Retorno:
        Retorna o index do elemento na tabela ou None se o identificador não estiver na tabela.
        '''   
        if lexeme in self.symbol_table:             # Se o simbolo estiver na tabela
            return self.symbol_table.index(lexeme)  # Retorna a sua posição
        return None                                 # Se não tiver na tabela, retorna nulo

    # Reinicia as listas: tokens, erros e tabela de símbolo para um nova análise
    def reset_lists(self):
        ''' Reinicia as listas em que são salvos os tokens, erros e os símbolos '''
        self.tokens_found.clear()
        self.errors_found.clear()
        self.symbol_table.clear()