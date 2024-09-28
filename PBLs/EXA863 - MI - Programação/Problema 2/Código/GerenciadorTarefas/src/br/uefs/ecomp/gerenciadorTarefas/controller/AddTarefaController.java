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
import br.uefs.ecomp.gerenciadorTarefas.model.Tarefa;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 * Controlador para a interface de adicionar tarefas a um projeto. Permite
 * efetuar a comunicação entre a view de adicionar tarefa e o model.
 * @author ifs54
 */
public class AddTarefaController implements Initializable {

    @FXML
    private TextField descricaoTarefa;
    @FXML
    private DatePicker dataConclusao;
    @FXML
    private TextField nomeTarefa;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    
    Projeto projeto;

    /**
     * Initializes the controller class. 
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        projeto = (Projeto) rb.getObject("projeto");
    }    
    
    /**
     * Controla o evento do click no botão de cancelar no cadastro de um novo projeto.
     * Fecha a tela ao clicar no botão cancelar.
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Controla o evento de click no botão concluído da interface. Verifica se algum dos campos
     * estão vazios, senão tiverem, comunica com o model para cadastrar o novo projeto e a
     * janela é fechada. Já caso algum campo apresente-se vazio, não há ação.
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        try{
            if (!(nomeTarefa.getText().isBlank() || descricaoTarefa.getText().isBlank())){
                Date data = Date.from(dataConclusao.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());        
                Tarefa tarefa = new Tarefa(nomeTarefa.getText(), descricaoTarefa.getText(), data);
                projeto.getTarefasPendentes().add(tarefa);
                Stage stage = (Stage) btnConcluido.getScene().getWindow();
                stage.close();
            }
        } catch(java.lang.NullPointerException ex){
            //Vazio
        }
    }
}
