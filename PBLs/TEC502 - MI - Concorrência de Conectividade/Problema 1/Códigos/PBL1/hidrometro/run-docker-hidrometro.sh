#!/bin/bash

#criar a imagem a partir do Dockerfile
sudo docker build -t ifss54/hidrometro .

#descomente a linha abaixo e comente a linha 4 caso queira baixar a imagem direto do Docker Hub
#sudo docker pull ifss54/hidrometro

#com a imagem já disponível localmente no docker - libera a conexão do docker com o sistema para exibir a tela e inicia o docker
sudo xhost +
sudo docker container run --rm -it --name hidrometro -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix:ro -p 5035:5035 -p 5010:5010 --net=host ifss54/hidrometro