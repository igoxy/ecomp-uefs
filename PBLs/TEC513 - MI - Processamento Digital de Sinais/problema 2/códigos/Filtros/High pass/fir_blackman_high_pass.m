clc; clear; close;
% Projeto de filtro FIR com janela de Blackman - Resposta Finita ao Impulso

% Cálculos:
% Frequência inicial do ruído: 3750 Hz

% Frequência de passagem: fp = 3000 Hz
% Frequência de parada: fs = 3700 Hz
% Frequência de transição: ft = 3700 - 3000 = 700 Hz
% Frequência intermediária (frequência de corte - cut): fc = (3700 + 3000)/2 = 3350 Hz

filename = '../../SinalRuidoso.wav';
[sinal, fs_sinal] = audioread(filename); %lendo arquivo de áudio

fp = 3000;
fs = 3700;

% Transformando as frequências em angular e normalizando pela frequência de amostragem
wp = (fp/(fs_sinal/2))*pi; % Frequência de passagem normalizada em rad/s
ws = (fs/(fs_sinal/2))*pi; % Frequência de parada normalizada em rad/s

% Calculando as frequências de transição e de corte normalizadas
wt = ws - wp;       % Frequência de transição - normalizada
wc = (ws + wp)/2;   % Frequência intermediária (frequência de corte - cut) - normalizada


% Encontrando o comprimento da janela de Blackman
% Através da equação: N = (5.5*pi*2)/wt

N = ceil((5.5*2*pi)/wt);

% O Filtro FIR desenvolvido aqui deve ser do tipo I, ou seja, com amostras 
% simetricas e de ordem par. 
% Para isso, é necessário que o número de amostras N seja impar.
if mod(N,2) == 0 % Verifica se o número de amostras é par. Se for par acrescenta mais 1
    N = N+1;     % Transforma o número de amostras em impar. 
end              % Gera um filtro simétrico de ordem par, ou seja, um filtro FIR TIPO I

ordem = N-1;     % Ordem do filtro

% Criando a função sinc a ser janelada para o filtro passa baixas "ideal"
alpha = (N-1)/2;
n = 0:N-1;
m = n - alpha + eps; % O eps incrementa um valor muito pequeno para evitar divisões por zero
hd_sinc = sin(wc*m)./(pi*m); % Função sinc - passa baixas ideal na frequência

% Criando a função da janela
window_blackman = myBlackman(N)'; % Criando a função janela de blackman

%Janelando o sinal
hd_sinc_window = hd_sinc.*window_blackman;
hd_sinc_window = -hd_sinc_window;       % Inverte as amostras da sinc janelada
hd_sinc_window(((N-1)/2)+1) = hd_sinc_window(((N-1)/2)+1) + 1;  % Adiciona a componente DC positiva a sinc janelada

% Convoluindo áudio (sinal de entrada) com o sinal filtro janelado
sinal_filtrado = conv(hd_sinc_window, sinal);


% --- Plots ---
% FFT sinal (áudio) original
n_fft = length(sinal);
Y = fft(sinal,n_fft);
F_0 = (-n_fft/2:n_fft/2-1)*(fs_sinal/n_fft);
FFT_SINAL = fftshift(Y);

figure(1);
subplot(3,1,1); stem(F_0, abs(FFT_SINAL),'.');
xlabel('Frequência (Hz)'); ylabel('Amplitude');
title('Espectro do sinal de áudio ruidoso')

% FFT do filtro
n_fft_filtro = length(sinal);
Y_FILTRO = fft(hd_sinc_window,n_fft_filtro);
F_0_FILTRO = (-n_fft_filtro/2:n_fft_filtro/2-1)*(fs_sinal/n_fft_filtro);
FFT_FILTRO = fftshift(Y_FILTRO);

subplot(3,1,2); stem(F_0_FILTRO, abs(FFT_FILTRO),'.');
xlabel('Frequência (Hz)'); ylabel('Amplitude');
title(sprintf('Espectro do filtro - sinal sinc janelado (ordem = %d)', ordem));

% FFT do sinal filtrado
n_fft_filtrado = length(sinal_filtrado);
Y_FILTRADO = fft(sinal_filtrado, n_fft_filtrado);
F_0_FILTRADO = (-n_fft_filtrado/2:n_fft_filtrado/2-1)*(fs_sinal/n_fft_filtrado);
FFT_SINAL_FILTRADO = fftshift(Y_FILTRADO);

subplot(3,1,3); stem(F_0_FILTRADO, abs(FFT_SINAL_FILTRADO),'.');
xlabel('Frequência (Hz)'); ylabel('Amplitude');
title('Espectro do sinal filtrado')

% Diagrama de Bode do filtro aplicado
H = tf(hd_sinc_window, 1, 1/fs_sinal); % Função transferência do filtro

figure(2);
%bode(H); % Para Rad/s
subplot(2,1,1); h = bodeplot(H);
title(sprintf('Resposta em frequência do filtro (ordem = %d)', ordem));
setoptions(h,'FreqUnits','Hz','PhaseVisible','off'); % Para Hz - % A resposta em fase plotada no bode é um pouco confusa
% Resposta em fase
subplot(2,1,2); phasez(hd_sinc_window);
title(sprintf('Resposta em fase do filtro (ordem = %d)', ordem));

% --- Reconstrução do áudio ---
% Reconstruindo o áudio
inversa_audio = ifftshift(FFT_SINAL_FILTRADO); % Reverte o fftshift
sinal_filtrado_reconstruido = ifft(inversa_audio);   % Aplica a transformada inversa (ifft)

% Normalizando o audio para não ter corte - Evita Warning 
sinal_reconstruido_normalizado = sinal_filtrado_reconstruido/(max(abs(sinal_filtrado_reconstruido)));

% Salvando o áudio em um arquivo
audiowrite("./Audio_Blackman_High_Pass/SinalRuidoso_filtrado_hamming_high_pass.wav", sinal_reconstruido_normalizado, fs_sinal)