function w = myHann(N)
    % Inicializa o vetor da janela de Hanning
    w = zeros(N, 1);
    
    % Calcula a janela de Hanning
    for n = 0:N-1
        w(n+1) = 0.5 * (1 - cos(2*pi*n / (N-1)));
    end
end
