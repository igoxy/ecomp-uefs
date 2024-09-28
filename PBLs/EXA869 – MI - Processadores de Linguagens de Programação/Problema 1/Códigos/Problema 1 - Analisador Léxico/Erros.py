# -*- coding: utf-8 -*-

# Disciplina: EXA869 - MI Processadores de Linguagens de Programação
# Aluno: Igor Figueredo Soares
# Matrícula: 19211242

class Erro:
    ''' Classe para um objeto do tipo erro '''

    def __init__(self, lexeme: str, start_position: int, line: int, message: str) -> None:
        self.lexeme = lexeme                    # Lexema do erro
        self.start_position = start_position    # Posição ou posição incial do erro na linha
        self.line = line                        # Linha do erro
        self.message = message                  # Mensagem de erro
