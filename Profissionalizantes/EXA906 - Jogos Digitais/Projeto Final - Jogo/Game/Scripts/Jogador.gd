extends CharacterBody2D

enum {KILL_BY_FALL, KILL_BY_ENEMY} #Identificadores para qual foi a situação que desencadeou a morte do personagem

# Propriedades do jogador
const SPEED = 200.0
const JUMP_VELOCITY = -600.0
var gravity = ProjectSettings.get_setting("physics/2d/default_gravity")
var mult_gravity = 1.6
var powerup_activated = false

#Elementos do jogador
@onready var texture_player = $textura_jogador as Sprite2D
@onready var animation_player = $animacao_jogador as AnimationPlayer
@onready var timer_powerup = $Timer as Timer

@onready var sound_jump = $PuloFx as AudioStreamPlayer2D
@onready var sound_powerup = $PowerupFx as AudioStreamPlayer2D
@onready var sound_die = $DieFx as AudioStreamPlayer2D
@onready var sound_powerup_end = $PowerupEndFx as AudioStreamPlayer2D
@onready var sound_wrong_element = $WrongElementFx as AudioStreamPlayer2D
@onready var sound_right_element = $RightElementFx as AudioStreamPlayer2D


func _ready():
	Global.player = self

func _physics_process(delta):
	#Movimentação
	velocity.x = 0
	var move_direction = int(Input.is_action_pressed("mover_direita")) - int(Input.is_action_pressed("mover_esquerda"))
	velocity.x = SPEED * move_direction
	
	if move_direction != 0:
		texture_player.scale.x = move_direction
	
	# Adicionar queda
	if not is_on_floor():
		velocity.y += (gravity * delta * mult_gravity)

	# Verificar pulo
	if Input.is_action_just_pressed("pular") and is_on_floor():
		velocity.y = JUMP_VELOCITY
		sound_jump.play()

	#Animação
	_set_animation()
	
	move_and_slide()


func _set_animation():
	var anime = "idle"
	
	if velocity.x != 0 and !powerup_activated:
		anime = "andar"
	elif velocity.x != 0 and powerup_activated:
		anime = "andar-pw"
	elif velocity.x == 0 and powerup_activated:
		anime = "idle-pw"
	if animation_player.assigned_animation != anime:
		animation_player.play(anime)

func die(type_kill):
	
	if (type_kill == KILL_BY_ENEMY and !powerup_activated): # Verifica se a morte foi engatilhada por inimigo e verifica se o powerup não está ativo
		if Global.lives > 1: #Verifica se não é a ultima vida - se for, não reproduz o som
			sound_die.play()
		Global.lose_life()
		self.global_position = get_node("../PosicaoInicial").global_position #Retorna o personagem para o começo da fase sem resetar
	elif type_kill == KILL_BY_FALL: #Se a morte foi causada por queda qualquer powerup é desativado
		if Global.lives > 1: #Verifica se não é a ultima vida - se for, não reproduz o som
			sound_die.play()
		Global.lose_life()
		set_powerup(false)
		self.global_position = get_node("../PosicaoInicial").global_position #Retorna o personagem para o começo da fase sem resetar
	
	# = get_node("Marker2D").global_position
	#self.global_position = 

func set_powerup(status: bool):
	powerup_activated = status
	if status:
		timer_powerup.start()
		sound_powerup.play()
	else:
		timer_powerup.stop()
		

func collect_element(element: Dictionary):
	
	Global.update_queue(element)

func _on_timer_powerup_timeout():
	set_powerup(false)
	sound_powerup_end.play()

