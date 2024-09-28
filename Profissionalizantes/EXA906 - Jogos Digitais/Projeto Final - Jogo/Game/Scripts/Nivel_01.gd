extends Node2D

@export var text_description: String = ''

@onready var timer = get_node("./HUD/Timer") as Timer


#Elementos químicos para o primeiro nível - Massa atômica
var elements = [  # Símbolo: [Nome, Massa atômica]
	{"H":  1},
	{"He": 2},
	{"Li": 3},
	{"B":  5},
	{"C":  6},
	{"N":  7},
	{"O":  8},
	{"F":  9},
	{"Na": 11},
	{"Mg": 12},
	{"Al": 13},
	{"Si": 14},
	{"P":  15},
	{"S":  16},
	{"Cl": 17},
	{"K":  19},
	{"Ca": 20},
	{"Cr": 24},
	{"Fe": 26},
	{"Co": 27},
	{"Ni": 28},
	{"I":  53},
	{"Xe": 54},
	{"Hg": 80},
]

# Called when the node enters the scene tree for the first time.
func _ready():
	GerenciadorMenus.set_on_menu(false) #Indica que o jogo não está em nenhuma tela de menu
	_load_elements()
	
	#DEBUG
	print(Global.queue_elements)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _load_elements():
	var temp_elements: Array = elements.duplicate(true) #Duplica o array original com os elementos para a fase
	temp_elements.shuffle() #Mistura os elementos dentro do array temporário
	
	var pos_elements = get_tree().get_nodes_in_group("posicao_elementos") #Obtém os marcadores de posição dos elementos na fase
	for pos in pos_elements: #Obter elementos do array
		var current_element: Dictionary = temp_elements.pop_front()
		
		var myElement = load("res://Cenas/elemento.tscn").instantiate()
		myElement.init(current_element) #Passa o elmento para o objeto a ser criado
		myElement.global_position = pos.global_position
		Global.queue_elements.append(current_element) #Adiciona o elemento a fila de elementos que será exibido para o player coletar
		add_child(myElement) #Adiciona o elemento ao Nó principal do nível
	
	Global.queue_elements.shuffle() #Embaralha os elementos da fila para mudar a ordem de coleta
	Global.text_description = text_description #Texto a ser mostrado no painel junto a informação do elemento
	Global.current_level = 1 #Indica qual é o nível que o jogador está jogando no momento
	Global.start_queue() #Inicia a verificação da ordem de coleta dos elementos
	
	
	
	
