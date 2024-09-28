# -*- coding: utf-8 -*-

# Disciplina: EXA869 - MI Processadores de Linguagens de Programação
# Aluno: Igor Figueredo Soares
# Matrícula: 19211242

from tkinter import *
from tkinter import messagebox
from Lexical import Lexical
import os

# --- Funções ---
def lexical_analysis():
    ''' Obtém o código-fonte e submete o mesmo ao objeto que executa a análise léxica '''
    clear_lists() # Limpa as listas da interface

    source_code = (text_code.get("1.0", END)).strip()   # Obtém o código digitado na área de texto
    source_code = source_code.split('\n')               # Separa a código em linhas

    lex = Lexical(source_code)  # Cria o objeto da análise léxica

    tokens, errors, symbols = lex.lex() # Executa a análise retornando a lista de tokens, a lista de erros e a tabela de símbolos

    # Exibição alternativa no terminal
    print(f'Tokens: {[item.token for item in tokens]}')
    print(f'Erros: {[error.message for error in errors]}')
    print(f'Tabela de símbolos: {symbols}')

    load_lists(tokens=tokens, errors=errors, symbols=symbols)

def clear_lists():
    ''' Limpa as listas da interface '''
    list_tokens.delete(0, END)
    list_erros.delete(0, END)
    table_symbols.delete(0, END)

def load_lists(tokens, errors, symbols):
    ''' Adiciona os tokens, erros e símbolos em suas respectivas listas da interface '''
    # Carrega os tokens
    for token in tokens:
        if (token.ref != None):                                             # Se for um token do tipo identificador
            res_token = f"< {token.token}, {token.lexeme}, {token.ref} >"   # Formata o token para ser exibido na lista, incluindo a posição na tabela de símbolos
        else:                                                               # Se não for um token do tipo identificador, não tem a referência para a posição na tabela de símbolos
            res_token = f"< {token.token}, {token.lexeme} >"                # Formata o token para ser exibido na lista
        list_tokens.insert(END, res_token)                                  # Adiciona na lista de exibição
        
    # Carregar erros
    if len(errors) > 0:
        for error in errors:
            if (error.lexeme == ""):    # Erro de comentário
                res_error = f"{error.message} - Em linha {error.line + 1}, coluna {error.start_position + 1}"
            else:                       # Outros erros
                res_error = f"{error.message}: {error.lexeme} - Em linha {error.line + 1}, posição {error.start_position + 1}"
            list_erros.insert(END, res_error)
            list_erros.config(fg="red")                                         # Muda a cor dos itens para indicar error
    else:
        list_erros.insert(END, "NÃO HOUVE ERROS LÉXICOS")   # Adiciona uma mensagem que não houve erros
        list_erros.config(fg="green")                       # Muda a cor da mensagem para indicar que não houve erro

        # Mensagem de sucesso
        messagebox.showinfo("Sucesso!", "Sucesso! Nenhum erro foi encontrado na análise léxica.")

    # Carregar tabela de símbolos
    for index in range(len(symbols)):
        res_symbol = f'{index} - {symbols[index]}'
        table_symbols.insert(END, res_symbol)
    
# --- Interface gráfica ---
window = Tk()
window.title("Analisador Léxico")
window.geometry("1400x900")
window.resizable(True, True)


# Para interface em tela cheia
if (os.name == "nt"):       # Sistema operacional Windows
    window.state("zoomed")
else:                       # Linux - Se tiver algum erro é só remover esse else junto a comando abaixo
    window.attributes("-zoomed", True)


window.configure()

# ---- Labels ----
# - Código-fonte -
label_code = Label(window, text="Insira o Código-fonte abaixo", fg="black", font="-weight bold -size 12").pack(side=TOP)

scroll_code=Scrollbar(window, orient="horizontal")

text_code = Text(window, height=10, xscrollcommand=scroll_code.set, wrap=NONE, font="-size 11")
text_code.pack(expand=True, fill=BOTH, padx=5, pady=5)

scroll_code.config(command=text_code.xview)
scroll_code.pack(fill=X)

# Botão para executar o código
button_run = Button(window, text="EXECUTAR ANÁLISE", bg="#28ad4b", highlightbackground="#28ad4b", relief=RAISED, activebackground="#0fd643", command=lexical_analysis).pack(expand= False)


# - Tokens -
scroll_tokens=Scrollbar(window, orient="horizontal")

label_tokens = Label(window, text="\nTokens gerados", fg="black", font="-weight bold -size 12").pack(side=TOP)
list_tokens = Listbox(window, width=35, xscrollcommand=scroll_tokens.set, bg="#dbdbdb", font="-weight bold -size 11")
list_tokens.pack(expand=True, fill=BOTH, padx=5, pady=5)

scroll_tokens.config(command=list_tokens.xview)
scroll_tokens.pack(fill=X)

# - Erros -
scroll_errors=Scrollbar(window, orient="horizontal")

label_errors = Label(window, text="Erros léxicos", fg="black", font="-weight bold -size 12").pack(side=TOP)
list_erros = Listbox(window, width=35, xscrollcommand=scroll_errors.set, bg="#dbdbdb", font="-weight bold -size 11")
list_erros.pack(expand=True, fill=BOTH, padx=5, pady=5)

scroll_errors.config(command=list_erros.xview)
scroll_errors.pack(fill=X)

# - Tabela de simbolos -
scroll_table=Scrollbar(window, orient="horizontal")

label_table = Label(window, text="Tabela de símbolos", fg="black", font="-weight bold -size 12").pack(side=TOP)
table_symbols = Listbox(window, width=35, xscrollcommand=scroll_table.set, bg="#dbdbdb", font="-weight bold -size 11")
table_symbols.pack(expand=True, fill=BOTH, padx=5, pady=5)

scroll_table.config(command=table_symbols.xview)
scroll_table.pack(fill=X)

if (__name__ == "__main__"):
    window.mainloop()           # Inicia a interface gráfica