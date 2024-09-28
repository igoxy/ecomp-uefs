extends CanvasLayer

@onready var continuar = $VBoxContainer/Continuar

# Called when the node enters the scene tree for the first time.
func _ready():
	visible = false

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func _unhandled_input(event):
	if event.is_action_pressed("ui_cancel"):
		visible = true
		get_tree().paused = true
		continuar.grab_focus()

func _on_continuar_pressed():
	get_tree().paused = false
	visible = false

func _on_menu_principal_pressed():
	Global.reset_infos()
	get_tree().paused = false
	get_tree().change_scene_to_file("res://Telas/menu_principal.tscn")
	
