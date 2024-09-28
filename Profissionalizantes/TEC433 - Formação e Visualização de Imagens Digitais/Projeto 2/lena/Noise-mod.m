clc;
clear all;
close all;

% Leitura da Imagem
Read = imread('lena 256x256','tif');

 f = double(Read); % Convers�o da vari�vel Read

 [N,N] = size(f);
 Maximo = max(max(f));


% C�lculo da Transformada de Fourier da Imagem

% Adi��o de Ru�do � Imagem Original


  fre = 100/N;

for I = 1 : N
    for J = 1 : N                    % Ru�do Gaussiano de desvio padr�o sigma que ser� somado � imagem
       R(J,I)= 25*sin((2*pi*fre)*I); % Assume um valor aleat�rio de acordo com a fun��o densidade
    end                              % de probabilidade (distribui��o) normal ou gaussiana.
end


  fn = f + R;

figure;
colormap(gray(Maximo));
subplot(2,2,1);
image(f);
title('Imagem original');


subplot(2,2,2);
image(fn);
title('Imagem ruidosa');

F = fft2(f);
FN = fft2(fn);

subplot(2,2,3);
c = 15/log10(max(max(abs(F))));                 % Constante de escala
D = c * log(1 + abs(F));
z = abs(D);
mesh(z);
title('\fontsize{8}Espectro da DFT de f em Escala Logar�timica');

subplot(2,2,4);
cn = 15/log10(max(max(abs(FN))));                 % Constante de escala
DN = cn * log(1 + abs(FN));
zn = abs(DN);
mesh(zn);
title('\fontsize{8}Espectro da DFT de fn em Escala Logar�timica');

Soma1 = 0;

for I = 1 : N

    for J = 1 : N

        Soma1 = Soma1 + (f(I,J) - fn(I,J))^2;

    end

end


Soma2 = 0;

for I = 1 : N

    for J = 1 : N

        Soma2 = Soma2 + (f(I,J))^2;

    end

end

SNR = abs(10*log10(Soma1/Soma2));
img_uint8 = uint8(fn)
imwrite(img_uint8, 'lena-ruidosa-uint8.tiff');

