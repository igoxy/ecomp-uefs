clc;
clear all;
close all;

% Leitura da Imagem
Read = imread("lena 256x256.tif");

 %f = double(Read); % Conversão da variável Read

 [N,N] = size(Read); % [N,N] = size(f)
 Maximo = max(max(Read)); %Maximo = max(max(f))


% Cálculo da Transformada de Fourier da Imagem

% Adição de Ruído à Imagem Original


  fre = 100/N;

for I = 1 : N
    for J = 1 : N                    % Ruído Gaussiano de desvio padrão sigma que será somado à imagem
       R(J,I)= 25*sin((2*pi*fre)*I); % Assume um valor aleatório de acordo com a função densidade
    end                              % de probabilidade (distribuição) normal ou gaussiana.
end


  fn = Read + R; %fn = f + R

figure;
colormap(gray(Maximo));
subplot(2,2,1);
image(Read); %image(f);
title('Imagem original');


subplot(2,2,2);
image(fn);
title('Imagem ruidosa');

F = fft2(Read); %F = fft2(f);
FN = fft2(fn);

subplot(2,2,3);
c = 15/log10(max(max(abs(F))));                 % Constante de escala
D = c * log(1 + abs(F));
z = abs(D);
mesh(z);
title('\fontsize{8}Espectro da DFT de f em Escala Logarítimica');

subplot(2,2,4);
cn = 15/log10(max(max(abs(FN))));                 % Constante de escala
DN = cn * log(1 + abs(FN));
zn = abs(DN);
mesh(zn);
title('\fontsize{8}Espectro da DFT de fn em Escala Logarítimica');

Soma1 = 0;

for I = 1 : N

    for J = 1 : N

        Soma1 = Soma1 + (Read(I,J) - fn(I,J))^2; %Soma1 = Soma1 + (f(I,J) - fn(I,J))^2;

    end

end


Soma2 = 0;

for I = 1 : N

    for J = 1 : N

        Soma2 = Soma2 + (Read(I,J))^2; %Soma2 = Soma2 + (f(I,J))^2;

    end

end

SNR = abs(10*log10(Soma1/Soma2));

imwrite(fn, 'lena-ruidosa.tiff');


