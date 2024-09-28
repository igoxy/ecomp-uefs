extends Node2D

#Elementos do nó
@onready var score = $Pontos as Label
@onready var button_next_level = $ProximaFase as Button

# Called when the node enters the scene tree for the first time.
func _ready():
	score.text = str(Global.current_score)
	if Global.current_level == 2:
		button_next_level.disabled = true
	Global.reset_infos()
	
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _on_proximo_nivel_pressed():
	get_tree().change_scene_to_file("res://Niveis/nivel_02.tscn")

func _on_menu_principal_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_principal.tscn")
