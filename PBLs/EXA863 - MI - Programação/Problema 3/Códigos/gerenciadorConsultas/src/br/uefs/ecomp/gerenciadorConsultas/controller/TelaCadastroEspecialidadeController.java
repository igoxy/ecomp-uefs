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
package br.uefs.ecomp.gerenciadorConsultas.controller;  //O pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de cadastro de especialidades. 
 * Permite efetuar a comunicação entre a view da tela e o model.
 * 
 * @author Igor
 */
public class TelaCadastroEspecialidadeController implements Initializable {

    @FXML
    private TextField nomeEspecialidade;
    @FXML
    private Button btnConcluido;
    
    private Recepcionista recepcionista;

    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     *
     * @param url a URL.
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recepcionista = (Recepcionista) rb.getObject("recepcionista");
    }    
    
    /**
     * Controla o evento de click no botão cancelar. Ao clicar no botão qualquer
     * operação é cancelada e a janela é fechada (tela).
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }
    
    /**
     * Controla o evento de click no botão de concluído. Ao clicar no botão, se o
     * campo da página estiver preenchido corretamente, a especialidade é armazenada
     * no sistema e a janela fecha.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!nomeEspecialidade.getText().isBlank()){
            String nome = nomeEspecialidade.getText().toUpperCase();  //Obtém o nome inserido para a especialidade
            recepcionista.cadastrarEspecialidade(nome);
            fecharJanela();
        }
    }
    
    //******** Métodos complementares **********
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnConcluido.getScene().getWindow();  //Obtém a janela que está sendo exibida
        stage.close();  //Fecha a janela exibida 
    }
    
}
