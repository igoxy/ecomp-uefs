# -*- coding: utf-8 -*-

# Disciplina: EXA869 - MI Processadores de Linguagens de Programação
# Aluno: Igor Figueredo Soares
# Matrícula: 19211242

class Token:
    def __init__(self, token: str, lexeme: str, position: int, line: int, ref: int = None) -> None:

        self.token = token
        self.lexeme = lexeme
        self.position = position
        self.line = line
        self.ref = ref      # Parâmetro exclusivo para tokens do tipo identificador -> referência para posição na tabela de simbolos

    