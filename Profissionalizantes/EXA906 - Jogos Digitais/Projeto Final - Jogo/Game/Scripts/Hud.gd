extends Node2D

# Elementos do nó
@onready var timer = $Timer as Timer
@onready var time_label = $Tempo as Label
@onready var lifes = $Vidas as TextureRect
@onready var panel = $Painel as Label
@onready var score = $Pontos as Label

var queue_elements_panel: Array

# Called when the node enters the scene tree for the first time.
func _ready():
	timer.start()
	load_lives()
	Global.hud = self
	
func _process(delta):
	time_label.text = str(timer.time_left).pad_decimals(0)

func pause_timer():
	timer.paused = true

func load_lives():
	lifes.set_size(Vector2(Global.lives * 32, 32))
	
func load_panel_text(text: String):
	panel.text = str(text).to_upper()

func update_score(new_score: String):
	score.text = str(new_score) 
	
func end_level():
	timer.stop()
	Global.current_score *= int(time_label.text) #Multiplica os pontos do jogador pelo tempo restante para encontrar a pontuação total


func _on_timer_level_timeout():
	get_tree().change_scene_to_file.bind("res://Telas/game_over.tscn").call_deferred()
