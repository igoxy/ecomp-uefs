% Código implementado no MATLAB
% Técnica 1 - Deslocamento na frequência

close all; clc; clear;

% Lendo o arquivo de áudio
[audioIn, fs] = audioread('audio.wav');

% Parâmetros do deslocamento na frequência
shiftAmount = 220; % Frequency shift in Hz

% Obtendo o espectro do sinal
N = length(audioIn);
audioInFFT = fft(audioIn);

% Calculando o número de amostras para o deslocamento
shiftSamples = round(shiftAmount / (fs / N));

% Deslocamento da frequência (não circular)
audioShiftedFFT = zeros(size(audioInFFT));
if shiftSamples > 0
    audioShiftedFFT(1:end-shiftSamples,:) = audioInFFT(shiftSamples+1:end,:);
else
    audioShiftedFFT(-shiftSamples+1:end,:) = audioInFFT(1:end+shiftSamples,:);
end

% Definindo os valores abaixo de zero como zero (somente para a parte positiva)
audioShiftedFFT(N/2+1:end,:) = 0;

% Transformada inversa
audioShifted = real(ifft(audioShiftedFFT));

% Normalizando o áudio de saída
audioShifted = audioShifted / max(abs(audioShifted(:)));

% Salvando o arquivo de áudio resultante
audiowrite('audio_shifted.wav', audioShifted, fs);

% Obtendo a parte positiva do espectro do sinal
frequencies_pos = (0:N/2-1)*(fs/N);
audioInFFT_pos = audioInFFT(1:N/2);
audioShiftedFFT_pos = audioShiftedFFT(1:N/2);

% Plot do sinal original e o sinal deslocado
figure;

subplot(2,1,1);
plot(frequencies_pos / 1000, abs(audioInFFT_pos));
title('Espectro do áudio original');
xlabel('Frequency (kHz)');
ylabel('Magnitude');
grid on;
xlim([0, 2.5]);

subplot(2,1,2);
plot(frequencies_pos / 1000, abs(audioShiftedFFT_pos));
title('Espectro do áudio deslocado');
xlabel('Frequency (kHz)');
ylabel('Magnitude');
grid on;
xlim([0, 2.5]);
