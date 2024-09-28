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
 * FXML Controller class
 * Controlador para a interface de editar informações de uma determinada tarefa. 
 * Permite efetuar a comunicação entre a view de editar tarefa e o model.
 *
 * @author ifs54
 */
public class EditarTarefaController implements Initializable {

    @FXML
    private TextField nomeTarefa;
    @FXML
    private TextField descricaoTarefa;
    @FXML
    private DatePicker dataConclusao;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    
    private Tarefa tarefa;

    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tarefa = (Tarefa) rb.getObject("tarefa"); //Obtém o objeto Tarefa
        nomeTarefa.setText(tarefa.getNome());  //Obtém o nome atual da tarefa
        descricaoTarefa.setText(tarefa.getDescricao()); //Obtém a descrição atual da tarefa 
        dataConclusao.setValue(tarefa.getDataConclusao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); //Obtém a data atual prevista de conclusão da tarefa
        
    }    
    
    /**
     * Controla o evento de click no botão cancelar da interface. 
     * Ao clicar no botão cancelar da interface, a janela é fechada e nenhuma 
     * outra ação é executada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Controla o evento de click no botão concluído da interface. Ao clicar no
     * botão, é verificado se algum dos campos da janela está vazio, caso não 
     * apresente algum campo vazio, as novas informações são atribuídas a tarefa.
     * Já caso apresente algum campo vazio, nenhuma ação é efetuada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!(nomeTarefa.getText().isBlank() || descricaoTarefa.getText().isBlank() || dataConclusao.getValue().toString().isBlank())){
            tarefa.setNome(nomeTarefa.getText());
            tarefa.setDescricao(descricaoTarefa.getText());
            Date data = Date.from(dataConclusao.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()); 
            tarefa.setDataConclusao(data);
            Stage stage = (Stage) btnConcluido.getScene().getWindow();
            stage.close();
        }
    } 
}
