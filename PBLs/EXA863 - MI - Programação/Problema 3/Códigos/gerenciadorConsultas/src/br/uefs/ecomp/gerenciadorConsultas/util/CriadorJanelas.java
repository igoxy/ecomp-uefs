/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI Programação
Concluido em: 08/12/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.gerenciadorConsultas.util;  //Pacote da classe

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A classe implementa o comportamento de um criar de janelas. Dispõe de métodos
 * para a criação de um nova janela a partir de um FXML e janelas de alertas.
 * 
 * @author Igor
 */
public class CriadorJanelas {
    
    /**
     * O método é responsável por abrir uma nova janela. Cria um nova janela e
     * mostra na tela.
     *
     * @param enderecoFXML o endereço do arquivo FXML da tela a ser aberta.
     * @param titulo o título da tela.
     * @param rb os recursos externos necessários na tela. Se for null nenhum
     * recuros é passado para o controlador da tela.
     * @param tipo o tipo de tela. Se for '1': indica que a tela deve ser
     * exibida e o restante do código é executado; se for qualquer outro valor
     * no char, a tela é exibida e o restante do código no controlador que abriu
     * a tela só é executado após a tela ser fechada.
     *
     * @throws IOException lançada se ocorrer um erro ao abrir o arquivo FXML da
     * tela.
     */
    //Talvez mudar para um método estático global
    public void abrirNovaJanela(String enderecoFXML, String titulo, ResourceBundle rb, char tipo) throws IOException {
        final Stage stage = new Stage();  //Cria o stage
        stage.setTitle(titulo); //Adiciona o título da janela
        stage.initModality(Modality.APPLICATION_MODAL);  //Define a janela como modal
        Parent root;  //Cria o elemento que recebe os dados do fxml
        if (rb != null) {  //Verifica se foi passado algum objeto para injetar no controller da janela criada
            root = FXMLLoader.load(getClass().getResource(enderecoFXML), rb);  //Se sim, fornce o fxml e as informações injetadas
        } else {
            root = FXMLLoader.load(getClass().getResource(enderecoFXML));  //Senão, fornece somente o fxml
        }
        Scene scene = new Scene(root);  //Carrega a cena com o root
        stage.setScene(scene);  //Seta o stage
        if (tipo == '1') {  //Verifica o tipo de visualização. 1 = somente mostrar a janela
            stage.show();  //Mostra a janela
        } else {   //Outro tipo qualquer = janela é exibida e a execução do código fica aguardando a janela ser fechada.
            stage.showAndWait(); //Mostra a janela e aguarda
        }
    }
    
    /**
     * O método é responsável por exibir mensagens de erro. O método cria um
     * novo alerta e exibe para o usuário informando a mensagem de erro.
     *
     * @param titulo o título da janela da mensagem de erro.
     * @param mensagem a mensagem de erro.
     */
    public void exibirMensagemErro(String titulo, String mensagem) {
        Alert novoAlerta = new Alert(Alert.AlertType.ERROR);
        novoAlerta.setTitle(titulo);
        novoAlerta.setContentText(mensagem);
        novoAlerta.show();
    }
}
