extends Node2D

@onready var btn_level1 = $CanvasLayer/VBoxContainer/HBoxContainer/fase1/BtnFase1
@onready var record_level1 = $CanvasLayer/VBoxContainer/HBoxContainer/fase1/HBoxContainer/RecordePontos
@onready var record_level2 = $CanvasLayer/VBoxContainer/HBoxContainer/fase2/HBoxContainer/RecordePontos
@onready var levels = [record_level1, record_level2] # Adiciona os elementos a um vetor

# Called when the node enters the scene tree for the first time.
func _ready():
	for index in range(Global.record_score.size()):
		levels[index].text = str(Global.record_score[index]) #Adiciona o valor do recorde de acordo com o nível que já foi concluído
		
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _on_voltar_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_secundario.tscn")


func _on_btn_fase_1_pressed():
	get_tree().change_scene_to_file("res://Niveis/nivel_01.tscn")


func _on_btn_fase_2_pressed():
	get_tree().change_scene_to_file("res://Niveis/nivel_02.tscn")
