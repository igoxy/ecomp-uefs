extends Node2D

var current_level: int = 0 # Armazena a informação de qual nível levou o jogador ao gamer over (padrão = nenhum)

func _ready():
	current_level = Global.current_level # Indica o nível que o jogador estava jogando antes de resetar as infos
	Global.reset_infos()
	
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _on_tentar_novamente_pressed():
	if current_level == 1:
		get_tree().change_scene_to_file("res://Niveis/nivel_01.tscn")
	elif current_level == 2:
		get_tree().change_scene_to_file("res://Niveis/nivel_02.tscn")
	else:
		get_tree().change_scene_to_file("res://Telas/menu_principal.tscn") # Caso aconteça algum erro de não registrar o nível atual

func _on_menu_principal_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_principal.tscn")
