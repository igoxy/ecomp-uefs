extends Node2D

@onready var button_play = $CanvasLayer/VBoxContainer/Jogar
@onready var button_audio = $CanvasLayer/Audio as TextureButton

# Called when the node enters the scene tree for the first time.
func _ready():
	button_play.grab_focus()
	GerenciadorMenus.set_on_menu(true)
	
	# Verifica se o áudio está desligado - se tiver coloca o botão de audio no estado de pressionado
	if !GerenciadorMenus.audio_status:
		button_audio.set_pressed_no_signal(true)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _on_jogar_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_secundario.tscn")


func _on_creditos_pressed():
	get_tree().change_scene_to_file("res://Telas/creditos.tscn")


func _on_sair_pressed():
	get_tree().quit()


func _on_audio_pressed():
	GerenciadorMenus.set_audio()
