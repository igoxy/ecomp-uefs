% Código implementado no MATLAB
% Gravador de áudio a partir do microfone conectado à interface de áudio do PC

close all; clear; clc; 

% Parâmetros da gravação
fs = 44100;         % Frequência de amostragem
nBits = 16;         % Número de bits por amostra
nChannels = 1;      % Número de canais - Neste caso, mono
duration = 5;       % Duração da gravação em segundos

% Criar o objeto de gravação
recObj = audiorecorder(fs, nBits, nChannels);

% Iniciar a gravação
disp('Gravando...');
recordblocking(recObj, duration);
disp('Gravação concluída.');

% Obter os dados de áudio
audio = getaudiodata(recObj);

% Salvando o áudio
audiowrite('audio.wav', audio, fs);