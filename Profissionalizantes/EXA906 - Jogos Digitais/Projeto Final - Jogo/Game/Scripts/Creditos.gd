extends CanvasLayer

@onready var btn_back = $VBoxContainer/Voltar

# Called when the node enters the scene tree for the first time.
func _ready():
	btn_back.grab_focus()

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _on_voltar_pressed():
	get_tree().change_scene_to_file("res://Telas/menu_principal.tscn")
