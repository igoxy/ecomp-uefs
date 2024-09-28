function w = myBlackman(N)
    % Inicializa o vetor da janela de Blackman
    w = zeros(N, 1);
    
    % Calcula a janela de Blackman
    for n = 0:N-1
        w(n+1) = 0.42 - 0.5 * cos(2*pi*n / (N-1)) + 0.08 * cos(4*pi*n / (N-1));
    end
    if ~isempty(w)
        w(1) = 0;
        w(N) = 0;
    end
end
