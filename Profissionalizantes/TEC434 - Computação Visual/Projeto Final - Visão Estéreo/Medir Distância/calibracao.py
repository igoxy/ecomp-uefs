import cv2

cv_file = cv2.FileStorage()
cv_file.open('stereoMap.xml', cv2.FileStorage_READ)         # Carrega os dados de calibração da câmeras

stereo_map_esq_x = cv_file.getNode('stereoMapL_x').mat()    # Carrega os parâmetros do eixo x da câmera esqueda para corrigir a imagem
stereo_map_esq_y = cv_file.getNode('stereoMapL_y').mat()    # Carrega os parâmetros do eixo y da câmera esqueda para corrigir a imagem
stereo_map_dir_x = cv_file.getNode('stereoMapR_x').mat()    # Carrega os parâmetros do eixo x da câmera direita para corrigir a imagem
stereo_map_dir_y = cv_file.getNode('stereoMapR_y').mat()    # Carrega os parâmetros do eixo y da câmera direita para corrigir a imagem

def calibrar_imagem(frame_direito, frame_esquerdo):
    # Retira as distorções das imagem causadas pela câmera
    esquerda_calibrada= cv2.remap(frame_esquerdo, stereo_map_esq_x, stereo_map_esq_y, cv2.INTER_LANCZOS4, cv2.BORDER_CONSTANT, 0)   # Remapeia o frame da câmera esqueda
    direita_calibrada= cv2.remap(frame_direito, stereo_map_dir_x, stereo_map_dir_y, cv2.INTER_LANCZOS4, cv2.BORDER_CONSTANT, 0)     # Remapeia o frame da câmera direita

    return direita_calibrada, esquerda_calibrada        # Retona os frames calibrados - sem as distorções