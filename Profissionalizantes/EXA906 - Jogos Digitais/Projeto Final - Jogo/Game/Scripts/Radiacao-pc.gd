extends Area2D 


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _on_body_entered(body):
	if body.is_in_group("jogador"): #Verifica se foi realmente o personagem quem entrou na área
		body.die(body.KILL_BY_FALL)
		
