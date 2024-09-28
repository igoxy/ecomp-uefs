clear; clc;
%Código para a simulação da amostragem PAM natural seguindo o
%critério de Nyquist.

% -- Constantes e variáveis ---
fc_hz_para_rads = 2*pi; % Fator de conversão de hertz para rad/s

fs = 100000; % Freq para o incremento da geração do sinal
t = 0:1/fs:40;

fc = 50; % Frequência de corte
ordem = 5; % Ordem do filtro Butterworth

% ----- CRIANDO SINAIS DE ENTRADA -----
% -- Dados dos sinais --
freq1 = 20;   % 20 Hz
freq2 = 100;  % 100 Hz

% -- Gerando os sinais --
sinal1= sin(2*pi*freq1*t);       % Sinal de 20 Hz 
sinal2 = 0.5*sin(2*pi*freq2*t);  % Sinal de 100 Hz

% Somando os sinais (senoides)
sinal_entrada = sinal1 + sinal2;

% ----- FILTRANDO O SINAL -----
% aplicando o filtro Butterworth (analógico)
[z,p,k] = butter(ordem, fc*fc_hz_para_rads,'low','s'); % Filtro passa baixa analogico
[b,a] = zp2tf(z,p,k);
[b,a] = bilinear(b,a,fs);
sinal_filtrado = filter(b,a,sinal_entrada); % Sinal filtrado

% ----- Amostragem PAM (NATURAL) -----
% -- Dados amostragem --
% Teorema de Nyquist - frequência de amostragem pelo menos duas vezes maior que a maior frequência do sinal
freq_amostragem = 200; % Maior frequência após filtragem: 20 Hz | freq_amostragem > 40:  
ampl_pulso = 1;

% -- Criando trem de pulsos --
% Onda quadrada para gerar o trem de pulsos
trem_pulsos = square(2*pi*freq_amostragem*t, 25); % Criando a onda quadrada com amplitude 1 - duty cicle: 25% (ciclo de trabalho)
n_pulsos = length(trem_pulsos);

% Gerando o trem de pulsos
for i = 1:n_pulsos
    % Remove a porção negativa da onda quadrada gerada para gerar o trem de
    % pulsos
    if (trem_pulsos(i)<=0)
        trem_pulsos(i) = 0;
    else
        trem_pulsos(i) = 1;
    end
end

% -- Amostrando o sinal --
sinal_amostrado = trem_pulsos.*sinal_filtrado; % Amostragem do sinal

% -- FFT sinal amostado --
Y_AMOSTRADO = fft(sinal_amostrado);
n = length(sinal_amostrado);
fshift_amostrado = (-n/2:n/2-1)*(fs/n);
yshift_amostrado = fftshift(Y_AMOSTRADO);

% ----- Filtragem do espectro -----
% Construção do filtro 
sinal_filtro = zeros(1,length(fshift_amostrado));
sinal_filtro(fshift_amostrado>-25.0125 & fshift_amostrado<24.9875) = 1; % Filtro ideal com frequência de corte de 25 Hz
% Para evitar problemas de número imaginário: negativo: -25.0125 | positivo: 24.9875

% Filtrando o espectro do sinal amostrado
yshift_amostrado_filtrado = yshift_amostrado.*sinal_filtro;
yshift_amostrado_filtrado = yshift_amostrado_filtrado*4; % Adicionando um ganho para compensar a perda no espectro - duty cicle (1/4) - multiplica pelo inverso do duty cicle

% ----- Transformada inversa -----
inversa_sinal = ifftshift(yshift_amostrado_filtrado); % Reverte o fftshift
sinal_reconstruido = ifft(inversa_sinal);   % Aplica a transformada inversa (ifft)


% --- Gráficos ---
% - Sinais de entrada analógicos -
% Sinal 1
figure('Name', 'Sinais de entrada analógicos');
subplot(3,1,1), plot(t,sinal1);
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Sinal 1 (20 Hz)');
axis([0 0.5 -1.5 1.5]);

% Sinal 2
subplot(3,1,2), plot(t,sinal2);
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Sinal 2 (100 Hz)');
axis([0 0.5 -1.5 1.5]);

% Sinal de entrada (soma dos sinais 1 e 2)
subplot(3,1,3), plot(t,sinal_entrada);
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Soma das senoides (sinal de entrada)');
axis([0 0.5 -2 2]);

% - Filtro anti-aliasing e amostragem do sinal -
% Sinal filtrado (filtro anti-aliasing)
figure('Name', 'Sinal filtrado e amostragem');
subplot(3,1,1), plot(t, sinal_filtrado);
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Sinal filtrado - limitado em banda (após o filtro anti-aliasing)');
axis([0 0.5 -1.5 1.5]);

% Trem de pulsos
subplot(3,1,2), plot(t,trem_pulsos);
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Trem de pulsos para amostragem');
axis([0 0.5 -1.5 1.5]);

% Sinal amostrado
subplot(3,1,3), plot(t,sinal_amostrado); %plot do sinal amostrado naturalmente
xlabel('Tempo (s)');
ylabel('Amplitude');
title('Sinal amostrado - PAM natural');
axis([0 0.5 -1.5 1.5]);
%axis([0 2 -2 2]);

% - Espectro e filtragem do espectro do sinal amostrado  -
figure('Name', 'Espectro e filtragem do sinal amostrado');
% FFT sinal amostrado
subplot(3,1,1), stem(fshift_amostrado, normalize(abs(yshift_amostrado)),'.')
xlabel('Frequência (Hz)');
ylabel('Magnitude');
title('Espectro do sinal amostrado');
axis([-1000 1000 -2 750]);

% - Filtragem do espectro -
% Filtro ideal para aplicar no espectro do sinal amostrado
subplot(3,1,2), plot(fshift_amostrado,sinal_filtro);
xlabel('Frequência (Hz)');
ylabel('Magnitude');
title('Filtro ideal para filtragem do espectro do sinal amostrado');
axis([-50 50 -1 2]);

% FFT sinal amostrado após filtragem
subplot(3,1,3), stem(fshift_amostrado, normalize(abs(yshift_amostrado_filtrado)),'.')
xlabel('Frequência (Hz)');
ylabel('Magnitude');
title('Espectro do sinal amostrado após a filtragem no espectro');
axis([-1000 1000 -2 1600]);

% - Sinal reconstruído -
% Sinal reconstruído a partir do espectro filtrado do sinal amostrado
figure('Name', 'Sinal reconstruido e sinal original');
subplot(2,1,1), plot(t, sinal_reconstruido);
xlabel("Tempo (s)");
ylabel("Amplitude");
title('Sinal reconstruído a partir do espectro')
axis([0 0.5 -1.5 1.5]);

subplot(2,1,2), plot(t, sinal1);
xlabel("Tempo (s)");
ylabel("Amplitude");
title('Sinal original - senoide de 20 Hz (sinal 1)')
axis([0 0.5 -1.5 1.5]);
