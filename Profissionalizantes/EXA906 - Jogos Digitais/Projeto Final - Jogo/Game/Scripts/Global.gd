extends Node

#Variáveis gerais
var lives: int = 3
var text_description: String = ''
var current_score: int = 0
var current_level: int = 0 

#Referências a nós
var hud
var player

#Variáveis de gerenciamento
var levels_played: int = 0 #Armazena a informação de quantos níveis o jogador já concluiu. #Os níveis são progressivos, ou seja, se o jogador já concluiu 1, o segundo nível pode ser liberado 
var record_score: Array = [] #Armazena o recorde de cada nível por index de forma sequencial (p. ex. index 0 = nível 1)


# Vetores de elementos para os níveis
var massa_atomica = [  # Símbolo: [Nome, Massa atômica]
	{"H":  ["Hidrogênio", 1]},
	{"He": ["Hélio", 2]},
	{"Li": ["Lítio", 3]},
	{"B":  ["Boro", 5]},
	{"C":  ["Carbono", 6]},
	{"N":  ["Nitrogênio", 7]},
	{"O":  ["Oxigênio", 8]},
	{"F":  ["Flúor", 9]},
	{"Na": ["Sódio", 11]},
	{"Mg": ["Magnésio", 12]},
	{"Al": ["Alumínio", 13]},
	{"Si": ["Silício", 14]},
	{"P":  ["Fósforo", 15]},
	{"S":  ["Enxofre", 16]},
	{"Cl": ["Cloro", 17]},
	{"K":  ["Potássio", 19]},
	{"Ca": ["Cálcio", 20]},
	{"Cr": ["Cromo", 24]},
	{"Fe": ["Ferro", 26]},
	{"Co": ["Cobalto", 27]},
	{"Ni": ["Níquel", 28]},
	{"I":  ["Iodo", 53]},
	{"Xe": ["Xenônio", 54]},
	{"Hg": ["Mercúrio", 80]},
]
var distribuicao_eletronica = [ # Símbolo: [Distribuição eletrônica]
	{"H":  "1s1"},
	{"He": "1s2"},
	{"Li": "1s2 2s1"},
	{"B":  "1s2 2s2 2p1"},
	{"C":  "1s2 2s2 2p2"},
	{"N":  "1s2 2s2 2p3"},
	{"O":  "1s2 2s2 2p4"},
	{"F":  "1s2 2s2 2p5"},
	{"Na": "1s2 2s2 2p6 3s1"},
	{"Mg": "1s2 2s2 2p6"}
]

#Elementos a serem coletos no nível atual
var queue_elements: Array = [] #Fila dos elementos a serem coletados 
var current_element: Dictionary = {}

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func lose_life():
	if lives > 1:
		lives -= 1
		hud.load_lives()
	else:
		get_tree().change_scene_to_file.bind("res://Telas/game_over.tscn").call_deferred()

func start_queue():
	current_element = queue_elements[0] #Obtém do primeiro elemento da fila
	var key_first = queue_elements[0].keys()[0] #Obtém o simbolo do primeiro elemento da fila
	hud.load_panel_text(text_description + " " + str(queue_elements[0][key_first])) #Envia o texto para o hud mostrar no painel

func update_queue(element: Dictionary):
	var index_find = queue_elements.find(element)
	if index_find == 0:
		queue_elements.remove_at(0)
		if !queue_elements.is_empty(): #Se não for o último elemento a ser coletado
			player.sound_right_element.play() #Reproduz o efeito sonoro da captura do elemento correto
		current_score += 100
		if queue_elements.is_empty(): #Quando coleta todos os elementos - Vitória
			hud.end_level() #Chama a função para obter o tempo restante do hud e obter a pontuação total
			if current_level > record_score.size(): #Verifica se o vetor já tem o recorde do nível
				record_score.append(current_score)	#Senão tiver, insere a pontuação como recorde
				levels_played += 1 #Indica que mais um nível foi concluído
			elif current_score > record_score[current_level-1]: #Se o vetor já tem um recorde para aquele nível, verifica se a pontuação atual é maior
					record_score[current_level-1] = current_score #Se for maior, insere no lugar da pontuação antiga
			get_tree().change_scene_to_file.bind("res://Telas/win_level.tscn").call_deferred()
		else:
			current_element = queue_elements[0]
			_update_panel()
	else:
		queue_elements.remove_at(index_find)
		player.sound_wrong_element.play() #Reproduz o efeito sonoro da captura do elemento errado
		if current_score >= 100:
			current_score -= 100
		else:
			current_score = 0
	hud.update_score(str(current_score))
	
	#DEBUG
	print(queue_elements)
	
func _update_panel():
	var key_first = queue_elements[0].keys()[0] #Obtém o simbolo do primeiro elemento da fila
	hud.load_panel_text(text_description + " " + str(queue_elements[0][key_first])) #Envia o texto para o hud mostrar no painel

func reset_infos():
	lives = 3
	current_score = 0
	current_level = 0
	text_description = ''
	queue_elements.clear()

func new_game():
	reset_infos()
	record_score.clear()
	levels_played = 0
