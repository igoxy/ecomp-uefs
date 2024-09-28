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
package br.uefs.ecomp.gerenciadorConsultas.controller;  //Pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.model.Consulta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de receita. 
 * Permite efetuar a comunicação entre a view da tela de prontuário e o model.
 *
 * @author Igor
 */
public class TelaReceitaController implements Initializable {

    @FXML
    private Button btnFechar;
    @FXML
    private TextArea textReceita;
    @FXML
    private Button btnSalvar;
    
    private Consulta consulta;
    private String editavel = "sim";  //Indica se a receita é editavel - Inicializa com sim.
    

    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     *
     * @param url a URL.
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editavel = (String) rb.getObject("editavel");
        if (editavel.contains("nao")){  //Verifica se a receita é editável
            textReceita.setEditable(false);  //Desabilita a edição da receita, caso seja acessada por meio do histórico de consultas.
            btnSalvar.setDisable(true);  //Desabilita o botão de salvar, visto que não ocorrerá mudanças na receita
            btnSalvar.setVisible(false);  //Esconde o botão de salvar.
        }
        consulta = (Consulta) rb.getObject("consulta");
        carregarReceita();  
    }    

    /**
     * Controla o evento de click no botão fechar. Ao clicar no botão fechar a janela
     * de receitaé fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickFechar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão imprimir. Ao clicar no botão a receita
     * é salva no prontuário do paciente e enviada para a fila de impressão.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickImprimir(ActionEvent event) {
        consulta.getProntuario().setReceita(textReceita.getText());  //Armazena a receita no prontuário
        consulta.getProntuario().imprimirReceita("receita".concat(consulta.getPaciente().getNome().concat(".txt")));  //Envia a receita para fila de impressão
    }
    
    /**
     * Controla o evento de click no botão salvar. Ao clicar no botão a receita
     * é salva junto ao prontuário do paciente.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickSalvar(ActionEvent event) {
        consulta.getProntuario().setReceita(textReceita.getText());
    }
    
    //****** Métodos complementares *******
    /**
     * O método é responsável por fechar a janela e deletar o arquivo temporário da receita.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * O método é responsável por carrega a receita da consulta.
     */
    private void carregarReceita(){  //Carrega as informações da receita, caso o paciente já tenha uma receita anterior para essa consulta
        if (consulta.getProntuario().getReceita() != null){
            textReceita.setText(consulta.getProntuario().getReceita()); //Carrega o texto da receita no TextArea
        }
    }
}
