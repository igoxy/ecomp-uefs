#!/bin/bash

#criar a imagem a partir do Dockerfile
sudo docker build -t ifss54/servidor .

#descomente a linha abaixo e comente a linha 4 caso queira baixar a imagem direto do Docker Hub
#sudo docker pull ifss54/servidor

# com a imagem já disponível localmente no docker - executa o container
sudo docker container run --rm -it --name servidor -p 5050:5050 -p 5010:5010 --net=host ifss54/servidor