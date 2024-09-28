extends Node2D

@onready var path_follow: PathFollow2D = $Path2D/PathFollow2D
@export var speed: int = 64 
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	path_follow.progress += speed * delta


func _on_inimigo_body_entered(body):
	if body.is_in_group("jogador"): #Verifica se foi realmente o personagem quem entrou na área
		body.die(body.KILL_BY_ENEMY)
