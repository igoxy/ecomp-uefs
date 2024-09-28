function w = myHamming(N)
    % Inicializa o vetor da janela de Hamming
    w = zeros(N, 1);
    
    % Calcula a janela de Hamming
    for n = 0:N-1
        w(n+1) = 0.54 - 0.46 * cos(2*pi*n / (N-1));
    end
end