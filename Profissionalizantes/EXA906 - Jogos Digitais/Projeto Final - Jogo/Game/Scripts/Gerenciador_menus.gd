extends Node

#Variáveis privadas
var _on_menu: bool = true #Indica se o jogo está em uma tela de menu ou não

#Controle de som
var music_bus = AudioServer.get_bus_index("Master") #Faixa de som do jogo
@onready var bg_song_menu = MenuBgSong as AudioStreamPlayer #Música de fundo do menu
var audio_status: bool = true # Indica se o áudio do jogo está ligado ou não (por padrão é true - ligado)


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func set_on_menu(status: bool):
	if _on_menu != status:	#Se o novo status for diferente do atual
		_on_menu = status
		_play_bg_song(status)
		
func _play_bg_song(status: bool):	#Reproduzir música de fundo
	if status:
		bg_song_menu.play()
	else:
		bg_song_menu.stop()

func set_audio():
	AudioServer.set_bus_mute(music_bus, not AudioServer.is_bus_mute(music_bus))
	audio_status = not AudioServer.is_bus_mute(music_bus)
