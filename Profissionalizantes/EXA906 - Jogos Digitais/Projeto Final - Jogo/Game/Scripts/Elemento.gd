extends Node2D

var _identify: Dictionary #Identifica que elemento é (por ex. Al)

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func init(element: Dictionary):
	_identify = element
	_set_sprite(element.keys()[0]) #Obtém o simbolo do elemento para adicionar seu sprite
	
func _set_sprite(sprite_name: String):
	var directory = "res://Simbolos/%s.png" % sprite_name
	$Sprite2D.texture = load(directory)



func _on_elemento_body_entered(body):
	if body.is_in_group("jogador"): #Verifica se foi realmente o personagem quem entrou na área
		body.collect_element(_identify)
		queue_free()
		#self.get_name()
		#print(self.get_name())


