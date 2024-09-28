extends Node2D


func _ready():
	pass 


func _process(delta):
	pass


func _on_powerups_body_entered(body):
	if body.is_in_group("jogador"): #Verifica se foi realmente o personagem quem entrou na área
		queue_free()
		body.set_powerup(true)
		
		#Fazer a parte que o personagem volta ao estado padrão
