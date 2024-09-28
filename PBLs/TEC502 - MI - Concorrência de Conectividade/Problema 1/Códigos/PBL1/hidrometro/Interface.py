# -*- coding: utf-8 -*-
from tkinter import *
from tkinter import messagebox
from Hidrometro import Hidrometro

class Interface():

    #CONSTANTES
    FONT = ("Helvetica", 16)
    BG = "#44475a"
    FG ="white"

    #Indicador de fornecimento desligado
    __desligado = False  # ignifica que está ligado (False = cliente ligado; True = cliente desligado)

    #Elementos da interface
    janela = Tk()
    consumo_atual = Label(janela)
    valor_consumo = Label(janela)
    text_alterar_vazao = Label(janela)
    alterar_vazao = Entry(janela)
    button_alterar_vazao = Button(janela)
    text_alterar_pressao = Label(janela)
    button_alterar_pressao = Button(janela)
    alterar_pressao = Entry(janela)
    
    def __init__(self, hidrometro: Hidrometro, resolucao:str, titulo:str): 
        self.__resolucao = resolucao
        self.__titulo = titulo
        self.__hidrometro = hidrometro

        self.alterar_vazao.insert(0, '0')        # Exibe o valor de vazão ao iniciar o hidrômetro
        self.alterar_pressao.insert(0, '1')    # Exibe o valor de pressão ao iniciar o hidrômetro
    #----------- Getters --------------
    def get_resolucao(self) -> str:
        return self.__resolucao

    def get_titulo(self) -> str:
        return self.__titulo
    
    def get_redimensionar_altura(self) -> bool:
        return self.__redimensionarAltura

    def get_redimensionar_largura(self) -> bool:
        return self.__redimensionarLargura
    
    def get_hidrometro(self) -> Hidrometro:
        return self.__hidrometro

    def get_desligado(self) -> bool:
        return self.__desligado

    # ------------- Setters ----------------------
    def set_resolucao(self, newResolucao: str):
        self.__resolucao = newResolucao
        self.__atualizar_resolucao()

    def set_titulo(self, newTitulo: str):
        self.__titulo = newTitulo
        self.__atualizar_titulo()

    def set_desligado(self, desligado: bool):
        self.__desligado = desligado
    

    #Criar tela
    def iniciar_interface(self):
        """ Inicia a interface do hidrômetro """
        def atualizar_vazao():
            """ Função para mudar a vazão do hidrômetro """
            try:
                new_vazao = float(self.alterar_vazao.get())
                if (new_vazao >= 0):
                    self.__hidrometro.set_vazao(new_vazao)
                else:
                    messagebox.showerror("Vazão inserida inválida", "A vazão selecionada não é menor que 0.")
            except ValueError:
                messagebox.showerror("Vazão inserida inválida", "A vazão selecionada não é um número.")

        def atualizar_pressao():
            """ Função para atualizar a pressão do hidrômetro """
            try:
                nova_pressao = float(self.alterar_pressao.get())
                if (nova_pressao >= 0):
                    self.__hidrometro.set_pressao(nova_pressao)
                else:
                    messagebox.showerror("Pressão inserida inválida", "A pressão selecionada não é menor que 0.")
            except ValueError:
                messagebox.showerror("Pressão inserida inválida", "A pressão selecionada não é um número.")

        self.janela.geometry(self.__resolucao)
        self.janela.title(self.__titulo)
        self.janela.resizable(True, True)
        self.janela.configure(bg="#44475a")


        #Configurações elementos visuais de informações
        self.text_alterar_vazao.config(text=f"Insira o valor vazão (m³/s) desejada",  font=self.FONT, bg=self.BG, fg=self.FG)
        self.alterar_vazao.config(text=f"Insira a vazão", width=30, bg=self.BG, highlightbackground="#ff79c6", fg=self.FG, relief=SUNKEN)
        self.button_alterar_vazao.config(text=f"Alterar vazão", command=atualizar_vazao, bg="#ff79c6", highlightbackground="#ff79c6", relief=FLAT, activebackground="#fa5fb8")

        self.text_alterar_pressao.config(text=f"Insira o valor da pressão (bar) desejada",  font=self.FONT, bg=self.BG, fg=self.FG)
        self.alterar_pressao.config(text=f"Insira a pressão", width=30, bg=self.BG, highlightbackground="#ff79c6", fg=self.FG, relief=SUNKEN)
        self.button_alterar_pressao.config(text=f"Alterar pressão", command=atualizar_pressao, bg="#ff79c6", highlightbackground="#ff79c6", relief=FLAT, activebackground="#fa5fb8")
        
        #Localização dos itens de alterar vazão
        self.text_alterar_vazao.grid(column=0, row=2, padx=35, pady=20)
        self.alterar_vazao.grid(column=1, row=2, padx=35, pady=20)
        self.button_alterar_vazao.grid(column=2, row=2, padx=35, pady=20)

        #Localização dos itens de alterar pressão
        self.text_alterar_pressao.grid(column=0, row=3, padx=35, pady=20)
        self.alterar_pressao.grid(column=1, row=3, padx=35, pady=20)
        self.button_alterar_pressao.grid(column=2, row=3, padx=35, pady=20)
        
        self.janela.mainloop()
   

    def desabilitar_vazao(self):
        """ Desativa a opção do usuário alterar a vazão"""
        self.button_alterar_vazao.config(state=DISABLED)

    def habilitar_vazao(self):
        """ Habilita o botão de alterar vazão """
        self.button_alterar_vazao.config(state=NORMAL)

    # ----------- Métodos privados ---------
    #Atualizar elementos
    def __atualizar_titulo(self):
        self.janela.config(title=self.__titulo)

    def __atualizar_resolucao(self):
        self.janela.config(geometry=self.__resolucao)
    

        



    
    