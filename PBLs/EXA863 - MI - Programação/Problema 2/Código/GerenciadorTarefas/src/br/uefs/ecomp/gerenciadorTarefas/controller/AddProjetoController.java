/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 19/10/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/

package br.uefs.ecomp.gerenciadorTarefas.controller;  //Pacote da classe

import br.uefs.ecomp.gerenciadorTarefas.model.Projeto;
import br.uefs.ecomp.gerenciadorTarefas.model.Sistema;
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
 * Controlador para a interface de adicionar projetos ao sistema. Permite
 * efetuar a comunicação entre a view de adicionar a projetos e o model.
 *
 * @author ifs54
 */
public class AddProjetoController implements Initializable {

    @FXML
    private TextField nomeProjeto;
    @FXML
    private Button concluido;
    @FXML
    private Button cancelar;

    /**
     * Initializes the controller class.É chamado ao inicializar a classe controladora.
     * @param url a URL.
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //vazio
    }    
    
    /**
     * Controla o evento de click no botão concluído da interface para o cadastro
     * de novo projeto. Verifica se o campo presente na interface está vazio,
     * senão tiverem, comunica com o model para cadastrar o novo projeto e a
     * janela é fechada. Já caso o campo apresente-se vazio, não há ação.
     * @param event o evento.
     */
    @FXML
    private void clickBotaoConcluido(ActionEvent event) {
        if (!nomeProjeto.getText().isBlank()){
            Projeto p = new Projeto(nomeProjeto.getText());
            //PaginaInicialController.getObservableListTarefas().add(p);  //Inserindo pela lista observable, inserindo posteriormente na lista do sistema
            Sistema.getProjetos().add(p); //Insereindo diretamente na lista do sistema
            Stage stage = (Stage) concluido.getScene().getWindow();
            stage.close();
        }
    }
    
    /**
     * Controla o evento do click do botão de cancelar o cadastro de novo projeto.
     * Fecha a tela ao clicar no botão de cancelar.
     * @param event o evento.
     */
    @FXML
    private void clickBotaoCancelar(ActionEvent event) {
       Stage stage = (Stage) cancelar.getScene().getWindow();
       stage.close();
    }
}
