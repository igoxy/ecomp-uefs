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

import br.uefs.ecomp.gerenciadorConsultas.model.Horario;
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
import br.uefs.ecomp.gerenciadorConsultas.model.Sistema;
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
 * Controlador para a tela de principal do sistema. 
 * Permite efetuar a comunicação entre a view da tela principal do sistema e o model.
 *
 * @author Igor
 */
public class TelaMarcarConsultaController implements Initializable {

    @FXML
    private TextField nomePaciente;
    @FXML
    private TextField cpfPaciente;
    @FXML
    private Button btnCancelar;
    
    private Medico medico;
    private String data;
    private Horario horario;
    
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
        medico = (Medico) rb.getObject("medico");
        data = (String) rb.getObject("data");
        horario = (Horario) rb.getObject("horario");  
    }    

    /**
     * Controla o evento de click no botão de cancelar. Ao clicar no botão a janela
     * de marcar consulta é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão Ok. Ao clicar no botão, se as informações
     * do paciente estiverem corretas, a consulta é marcada na data e horário selecionado
     * e a janela é fechada. Caso contrário, uma mensagem de erro é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickOk(ActionEvent event) {
        Paciente pacienteBuscado = buscarPaciente();  //Busca o paciente relacionado ao nome e cpf informado
        if(pacienteBuscado != null){ //Verifica se foi encontrado algum paciente com os dados fornecidos
            pacienteBuscado.marcarConsulta(medico, data, horario);
            fecharJanela();
        }else{ //Senão foi encontrado
            String titulo = "Paciente não cadastrado no sistema";
            String mensagem = "Você não está cadastrado no sistema.\nDirija-se a recepção para efetuar o seu cadastro.";
            criadorJanelas.exibirMensagemErro(titulo, mensagem);  //Exibe a mensagem de erro
        }
    }
    
    //******* Métodos complementares ******
    /**
     * O método é reponsável por buscar o paciente relacionado aos dados inseridos
     * na solicitação de marcação da consulta.
     * 
     * @return um objeto do tipo Paciente se o paciente for encontrado. Caso contrário
     * retorna null.
     */
    private Paciente buscarPaciente(){
        if (!(nomePaciente.getText().isBlank() || cpfPaciente.getText().isBlank())){
            String nome = nomePaciente.getText().toUpperCase();
            String cpf = cpfPaciente.getText();
            Paciente pacienteBuscado = Sistema.getPacientes().buscarPaciente(nome, cpf);
            return pacienteBuscado; //Retorna o paciente buscado  
        }
        return null;  //Se algum dos campos de texto estiver vazio, retorna nulo.
    }
    
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }  
}
