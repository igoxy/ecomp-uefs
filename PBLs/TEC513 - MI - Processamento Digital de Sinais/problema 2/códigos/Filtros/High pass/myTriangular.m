function w = myTriangular(N)
    % Inicializa o vetor da janela triangular de Bartlett
    w = zeros(N, 1);
    
    % Calcula a janela triangular de Bartlett
    for n = 0:N-1
        w(n+1) = 1 - abs((2*n - (N-1)) / (N-1));
    end
end
