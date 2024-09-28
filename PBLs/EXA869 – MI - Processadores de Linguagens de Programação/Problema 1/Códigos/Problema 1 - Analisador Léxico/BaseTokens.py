# -*- coding: utf-8 -*-

# Disciplina: EXA869 - MI Processadores de Linguagens de Programação
# Aluno: Igor Figueredo Soares
# Matrícula: 19211242

class BaseTokens:
    ''' Classe para os Tokens base possíveis '''

    base_tokens = {}

    def __init__(self) -> None:
        self.__load_all()
        
    
    # Método exclusivo para obter token de palavras chaves
    def get_token_keyword(self, lexeme):
        if (self.is_keyword(lexeme)):
            return self.base_tokens[lexeme]
        return None
    
    # Verifica se um lexema é associado a uma palavra-chave
    def is_keyword(self, lexeme) -> bool:
        if lexeme in self.base_tokens:
            return True
        return False
    
    # Método para obter token de operadores ou delimitadores
    def get_token(self, lexeme):
        return self.base_tokens[lexeme]

    # == Métodos internos a classe (privados) ==
    
    # Palavras reservadas --> variables, methods, constants, class, return, empty, main, if, then, else, while, for, read, write, integer, float, boolean, string, true, false, extends
    def __load_keywords(self):
        keys = ['variables', 'methods', 'constants', 'class', 'return', 'empty', 'main', 'if', 'then', 'else', 
                'while', 'for', 'read', 'write', 'integer', 'float', 'boolean', 'string', 'true', 'false', 'extends']
        for key in keys:
            self.base_tokens[key] = f'TOKEN.{key.upper()}'
    
    # Delimitadores --> ; , ( ) { } [ ]
    def __load_delimiters(self):
        self.base_tokens[';'] = 'TOKEN.PONTO_VIRGULA'
        self.base_tokens[','] = 'TOKEN.VIRGULA'
        self.base_tokens['('] = 'TOKEN.PARENTESE_ESQUERDO'
        self.base_tokens[')'] = 'TOKEN.PARENTESE_DIREITO'
        self.base_tokens['{'] = 'TOKEN.CHAVE_ESQUERDA'
        self.base_tokens['}'] = 'TOKEN.CHAVE_DIREITA'
        self.base_tokens['['] = 'TOKEN.COLCHETE_ESQUERDO'
        self.base_tokens[']'] = 'TOKEN.COLCHETE_DIREITO'
    
    # Operadores --> + - * / == != > >= < <= && || = ++ -- .
    def __load_operators(self):
        self.base_tokens['+'] = 'TOKEN.MAIS'
        self.base_tokens['-'] = 'TOKEN.MENOS'
        self.base_tokens['*'] = 'TOKEN.ASTERISCO'
        self.base_tokens['/'] = 'TOKEN.BARRA'
        self.base_tokens['=='] = 'TOKEN.IGUAL_IGUAL'
        self.base_tokens['!='] = 'TOKEN.DIFERENTE'
        self.base_tokens['>'] = 'TOKEN.MAIOR'
        self.base_tokens['>='] = 'TOKEN.MAIOR_IGUAL'
        self.base_tokens['<'] = 'TOKEN.MENOR'
        self.base_tokens['<='] = 'TOKEN.MENOR_IGUAL'
        self.base_tokens['&&'] = 'TOKEN.E_COMERCIAL_DUPLO'
        self.base_tokens['||'] = 'TOKEN.PIPE_DUPLO'
        self.base_tokens['='] = 'TOKEN.IGUAL'
        self.base_tokens['++'] = 'TOKEN.MAIS_MAIS'
        self.base_tokens['--'] = 'TOKEN.MENOS_MENOS'
        self.base_tokens['.'] = 'TOKEN.PONTO'

    # Tipos --> String Character Number 
    def __load_types(self):
        self.base_tokens['string'] = 'TOKEN.STRING'
        self.base_tokens['char'] = 'TOKEN.CARACTERE'
        self.base_tokens['num'] = 'TOKEN.NUMERO'

    # Comentários --> Comment_Line Comment_Block
    def __load_comments(self):
        self.base_tokens['//'] = 'TOKEN.COMENTARIO_LINHA'
        self.base_tokens['/*'] = 'TOKEN.COMENTARIO_BLOCO_ESQUERDO'
        self.base_tokens['*/'] = 'TOKEN.COMENTARIO_BLOCO_DIREITO'
    
    # Insere todos os tokens na lista de tokens possíveis
    def __load_all(self):
        self.__load_keywords()      # Insere os Tokens de palavras reservadas na lista
        self.__load_delimiters()    # Insere os Tokens de delimitadores na lista
        self.__load_operators()     # Insere os Tokens de operadores na lista
        self.__load_types()         # Insere os Tokens de tipos na lista
        self.__load_comments()      # Insere os Tokens de comentários na lista