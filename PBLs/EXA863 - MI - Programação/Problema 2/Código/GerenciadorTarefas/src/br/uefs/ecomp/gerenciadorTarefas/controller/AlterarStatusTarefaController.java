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
import br.uefs.ecomp.gerenciadorTarefas.model.StatusTarefa;
import br.uefs.ecomp.gerenciadorTarefas.model.Tarefa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 * Controlador para a interface de alterar o status de uma determinada tarefa de um projeto. 
 * Permite efetuar a comunicação entre a view de alterar o status da tarefa e o model.
 *
 * @author ifs54
 */
public class AlterarStatusTarefaController implements Initializable {

    @FXML
    private ComboBox<StatusTarefa> comboStatusTarefa;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    
    private Projeto projeto;
    private Tarefa tarefa;
    
    private ObservableList<StatusTarefa> opcoes;
    
    
    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Obtém o projeto e a tarefa que o status deve ser alterado 
        projeto = (Projeto) rb.getObject("projeto");
        tarefa = (Tarefa) rb.getObject("tarefa");
        
        //Inicialização do ComboBox
        opcoesComboBox(tarefa.getStatus());
        comboStatusTarefa.setItems(opcoes);
    }

    /**
     * O método verifica qual o status da tarefa que deseja-se alterar o status,
     * de acordo com isso, carrega o ComboBox com os possiveis novos status para a
     * tarefa, não apresentando o status atual da terefa na lista do ComboBox.
     * 
     * @param status o status da tarefa.
     */
    private void opcoesComboBox(StatusTarefa status){
        switch (status){
            case PENDENTE -> opcoes = FXCollections.observableArrayList(
                        StatusTarefa.EM_EXECUCAO,
                        StatusTarefa.CONCLUIDA
                );
            case EM_EXECUCAO -> opcoes = FXCollections.observableArrayList(
                        StatusTarefa.PENDENTE,
                        StatusTarefa.CONCLUIDA
                );
            case CONCLUIDA -> opcoes = FXCollections.observableArrayList(
                        StatusTarefa.PENDENTE,
                        StatusTarefa.EM_EXECUCAO
                );
            default -> {
            }
        }
    }
    
    /**
     * Controla o evento de click no botão cancelar da interface. 
     * Fecha a tela ao clicar no botão cancelar.
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Controla o evento de click no botão concluído da interface. Verifica se há alguma opção
     * selecionada no ComboBox, se houver, comunica com o model o novo status da tarefa
     * e o mesmo efetua o procedimento de troca de status. Já caso não exista alguma opção
     * selecionada, a janela é fechada.
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!comboStatusTarefa.getSelectionModel().isEmpty()){
            StatusTarefa status =  comboStatusTarefa.getSelectionModel().getSelectedItem();
            projeto.trocarTarefaQuadro(tarefa, status);
        }
        Stage stage = (Stage) btnConcluido.getScene().getWindow();
        stage.close();
    }
}
