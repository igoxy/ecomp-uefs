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

import br.uefs.ecomp.gerenciadorConsultas.exceptions.CpfCadastradoException;
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
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
 * Controlador para a tela de editar peciente. 
 * Permite efetuar a comunicação entre a view da tela de editar paciente e o model.
 *
 * @author Igor
 */
public class TelaEditarPacienteController implements Initializable {

    @FXML
    private TextField nomePaciente;
    @FXML
    private TextField cpfPaciente;
    @FXML
    private Button btnConcluido;

    private Paciente paciente;
    private Recepcionista recepcionista;
    
    private CriadorJanelas criadorJanelas = new CriadorJanelas();  //Cria um objeto para abrir novas janelas.
    
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
        paciente = (Paciente) rb.getObject("paciente");
        nomePaciente.setText(paciente.getNome());
        cpfPaciente.setText(paciente.getCpf());
    }    

    /**
     * Controla o evento de click no botão de cancelar. Ao clicar no botão
     * todas as operações são canceladas e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão concluído. Ao clicar no botão, se todos os campos
     * estiverem preenchidos corretamente, as alterações são gravadas e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if(!(nomePaciente.getText().isBlank() || cpfPaciente.getText().isBlank())){
            String nome = nomePaciente.getText().toUpperCase();
            String cpf = cpfPaciente.getText();
            try {
                recepcionista.editarPaciente(paciente, nome, cpf);
                fecharJanela();
            } catch (CpfCadastradoException ex) {
                String titulo = "Erro ao editar as informações do paciente";
                String mensagem = ex.getMessage();
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    //******* Métodos complementares *******
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnConcluido.getScene().getWindow();
        stage.close();
    }
}
