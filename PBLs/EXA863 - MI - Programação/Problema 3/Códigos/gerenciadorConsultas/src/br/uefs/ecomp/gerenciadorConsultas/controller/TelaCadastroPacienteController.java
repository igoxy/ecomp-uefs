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
package br.uefs.ecomp.gerenciadorConsultas.controller;

import br.uefs.ecomp.gerenciadorConsultas.exceptions.CpfCadastradoException;
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
 * Controlador para a tela de cadastro de paciente. 
 * Permite efetuar a comunicação entre a view da tela de cadastro de paciente e o model.
 *
 * @author Igor
 */
public class TelaCadastroPacienteController implements Initializable {

    @FXML
    private TextField nomePaciente;
    @FXML
    private TextField cpfPaciente;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    
    private CriadorJanelas criadorJanelas = new CriadorJanelas();  //Cria um objeto para abrir novas janelas.
    
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
        
        recepcionista = (Recepcionista) rb.getObject("recepcionista"); //Recepcionista que abriu a tela de cadastro.
    }    

    /**
     * Controla o evento de click no botão de cancelar. Ao clicar no botão a operação
     * é cancelada e janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão de concluído. Ao clicar no botão, se todos
     * os campos estiverem preenchidos corretamente, o novo paciente é cadastrado no sistema
     * e a janela fecha.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if(!(nomePaciente.getText().isBlank() || cpfPaciente.getText().isBlank())){
            String nome = nomePaciente.getText().toUpperCase();
            String cpf = cpfPaciente.getText();
            if(cpf.matches("\\d+")){  //Verifica se o cpf tem somente números
                try {
                    recepcionista.cadastrarPaciente(nome, cpf);
                    fecharJanela();
                } catch (CpfCadastradoException ex) {
                    String titulo = "Erro ao cadastrar o paciente";
                    String mensagem = ex.getMessage();
                    criadorJanelas.exibirMensagemErro(titulo, mensagem);
                }
            }else{
                String titulo = "O CPF deve ter somente números";
                String mensagem = "O CPF informado não é válido. O CPF informado deve conter somente números";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }  
    }

    //******* Métodos complementares *******
    /**
     * O método é responsável por fechar a janela de cadastro de paciente.
     */
    private void fecharJanela() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow(); //Obtém a janela
        stage.close();  //Fecha a janela
    }
}
