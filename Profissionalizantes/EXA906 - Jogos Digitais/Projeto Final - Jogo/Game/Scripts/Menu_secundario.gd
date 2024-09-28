extends Node2D

@onready var continue_btn = $CanvasLayer/VBoxContainer/Continuar
@onready var new_game_btn = $CanvasLayer/VBoxContainer/NovoJogo

# Called when the node enters the scene tree for the first time.
func _ready():
	if Global.levels_played >= 1:
		continue_btn.grab_focus()
	else:
		new_game_btn.grab_focus()
		continue_btn.visible = false
		continue_btn.disabled = true

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _on_continuar_pressed():
	get_tree().change_scene_to_file("res://Telas/seletor_fase.tscn")


func _on_novo_jogo_pressed():
	Global.new_game()
	get_tree().change_scene_to_file("res://Niveis/nivel_01.tscn")


func _on_voltar_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_principal.tscn")
