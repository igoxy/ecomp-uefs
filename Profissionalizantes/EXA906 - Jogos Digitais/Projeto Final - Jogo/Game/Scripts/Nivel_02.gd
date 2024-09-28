extends Node2D

@export var text_description: String = ''

@onready var timer = get_node("./HUD/Timer") as Timer


# Elementos químicos para o segundo nível - Distribuição Eletrônica
# Símbolo: Distribuição eletrônica
var elements = [ 
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

# Called when the node enters the scene tree for the first time.
func _ready():
	GerenciadorMenus.set_on_menu(false)
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
	Global.current_level = 2 #Indica qual é o nível que o jogador está jogando no momento
	Global.start_queue() #Inicia a verificação da ordem de coleta dos elementos
	
	
	
	
