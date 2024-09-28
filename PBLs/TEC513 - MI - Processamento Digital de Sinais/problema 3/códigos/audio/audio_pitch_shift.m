% Código implementado no Octave
% Técnica 2 - Pitch shift

close all; clc; clear;

[audio,fs] = audioread("audio.wav");

function [output] = pitchshift(audio, pitchShift, Ts)

% Tamanho da janela da FFT
nWindow = 2048;
N = Ts / nWindow;

[samp, nChannels] = size(audio);
tSamp = samp;
padding = 0;

if mod(samp, nWindow) > 0
  padding = nWindow - mod(samp, nWindow);
  tSamp = samp + padding;
  audio = [audio; zeros(padding, nChannels)];
end

iMatrix = repmat([0:(nWindow-1)]', 1, nChannels);
halfMatrix = iMatrix(1:nWindow/2, :);

% Define o tamanho do passo de cada janela
oSamp = 4;
stepSize = nWindow / oSamp;
expected = 2 * pi * stepSize / nWindow;
expectedPhaseShift = halfMatrix * expected;

% Mantém as fases para o cálculo da menor variação
lastPhase = zeros(nWindow/2, nChannels);
sumPhase = zeros(nWindow/2, nChannels);
outputSum = zeros(nWindow * 2, nChannels);

for i = 0:stepSize:(tSamp - nWindow - 1)
  startx = i + 1;
  endx = (startx - 1) + nWindow;

  % Define o início de uma janela para o processamento
  data = audio(startx:endx,:);
  ft = fft(data)(1:nWindow/2, :);
  phase = angle(ft);
  phaseShift = phase - lastPhase;
  lastPhase = phase;
  phaseShift -= expectedPhaseShift;
  phaseShift -= pi * (fix(phaseShift / pi));
  phaseShift = (oSamp * phaseShift) ./ (2 * pi);
  trueFreq = (N .* halfMatrix) + (phaseShift .* N);
  trueMag = abs(ft);

  shiftFreq = zeros(nWindow/2, nChannels);
  shiftMag = zeros(nWindow/2, nChannels);

  for chan = 1:nChannels
    for i = 0 : ((nWindow / 2) - 1)
      index = round(i * pitchShift) + 1;
      if (index <= nWindow / 2)
        shiftMag(index, chan) += trueMag(i+1, chan);
        shiftFreq(index, chan) = trueFreq(i+1, chan) * pitchShift;
      end
    end
  end

  shiftFreq -= (halfMatrix .* N);
  shiftFreq ./= N;
  shiftFreq = 2 * pi * (shiftFreq ./ oSamp);
  shiftFreq += expectedPhaseShift;
  sumPhase += shiftFreq;
  outputData = shiftMag .* cos(sumPhase);
  outputData += j * (shiftMag .* sin(sumPhase));
  data = real(ifft(outputData, nWindow));
  outputSum(1:nWindow,:) = data;

  output(startx:startx+stepSize - 1,:) = outputSum(1:stepSize,:);
end

% Plotando os espectrogramas
% Caso a função specgram não funcione importar o pacote: pkg load signal
figure(1);
specgram(audio);
figure(2);
specgram(output);
end

output = pitchshift(audio, 2, 44100);
audiowrite("shifted.wav", output, fs);

