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
 * FXML Controller class.
 * Controlador para a interface de editar informações do projeto. 
 * Permite efetuar a comunicação entre a view de editar projeto e o model.
 *
 * @author ifs54
 */
public class EditarProjetoController implements Initializable {

    @FXML
    private TextField nomeProjeto;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    
    private Projeto projeto;
    
    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.projeto = (Projeto) rb.getObject("projeto");
        nomeProjeto.setText(projeto.getNome());
    }

    /**
     * Controla o evento do botão cancelar. Ao clicar no botão cancelar da interface,
     * a janela é fechada e nenhuma outra ação é executada.
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Controla o evento de click no botão concluído. Ao clicar no botão concluído,
     * verifica se o campo presente na interface está vazio, caso não, comunica com o
     * model a alteração das informações e a janela é fechada. Já caso o campo
     * apresente-se vazio, não há ação.
     * @param event 
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!nomeProjeto.getText().isBlank()){
            int indexProjeto = Sistema.getProjetos().indexOf(projeto);  //Encontra o índice, na lista de projetos, do projeto que está sendo editado 
            Sistema.getProjetos().get(indexProjeto).setNome(nomeProjeto.getText());  //Chama o método de alterar o nome do projeto que foi editado e fornce o nome

            Stage stage = (Stage) btnConcluido.getScene().getWindow();  //Obtém a janela que está sendo exibida
            stage.close();  //Fecha a janela exibida 
        }
    }
    
}
