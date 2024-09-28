FROM python:latest

RUN mkdir /home/app
RUN apt-get install tk -y

WORKDIR /home/app
COPY . .

CMD python /home/app/main.py

# Dockerfile do hidrômetro