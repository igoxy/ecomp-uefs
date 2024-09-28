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
import br.uefs.ecomp.gerenciadorConsultas.model.Especialidade;
import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.model.Sistema;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListEspecialidade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de cadastro de médico. 
 * Permite efetuar a comunicação entre a view da tela de cadastro de médico e o model.
 * 
 * @author Igor
 */
public class TelaCadastroMedicoController implements Initializable {

    @FXML
    private TextField nomeMedico;
    @FXML
    private TextField cpfMedico;
    @FXML
    private PasswordField senhaMedico;
    @FXML
    private PasswordField confirmacaoSenhaMed;
    @FXML
    private ComboBox<Especialidade> especialidades;
    @FXML
    private ComboBox<Especialidade> subespecialidades;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluido;
    @FXML
    private TextField crmMedico;    
    
    private ObservableList<Especialidade> opcoesEspecialidades;
    private ObservableList<Especialidade> opcoesSubespecialidades;
    
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
        carregarOpcoesEspecialidades();
        subespecialidades.setDisable(true);
        subespecialidades.setVisible(false);    
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
     * Controla o evento de click no botão concluído. Ao clicar no botão, se todos
     * os campos estiverem preenchidos corretamente, o novo médico é cadastrado no sistema
     * e a janela é fechada, caso contrário, é exibida uma mensagem de erro.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!(nomeMedico.getText().isBlank() || cpfMedico.getText().isBlank() || senhaMedico.getText().isBlank() || confirmacaoSenhaMed.getText().isBlank() || especialidades.getSelectionModel().isEmpty() || crmMedico.getText().isBlank())) {  //|| subespecialidades.getSelectionModel().isEmpty()
            String nome = nomeMedico.getText().toUpperCase();
            String crm = crmMedico.getText();
            String cpf = cpfMedico.getText();
            String senha = senhaMedico.getText();
            String confirmacaoSenha = confirmacaoSenhaMed.getText();
            Especialidade especialidade = especialidades.getSelectionModel().getSelectedItem();
            Especialidade subespecialidade = subespecialidades.getSelectionModel().getSelectedItem();
            if (cpf.matches("\\d+")) {
                if (!senha.equals(confirmacaoSenha)) {
                    String titulo = "As senha informada não são iguais.";
                    String mensagem = "A senha informada para confirmação não é igual a senha escolhida.";
                    criadorJanelas.exibirMensagemErro(titulo, mensagem);
                } else {
                    try {
                        recepcionista.cadastrarMedico(nome, crm, cpf, senha, especialidade, subespecialidade); //Cadastro
                        fecharJanela();  //Fecha a tela
                    } catch (CpfCadastradoException ex) {
                        String titulo = "Erro ao cadastrar o médico";
                        criadorJanelas.exibirMensagemErro(titulo, ex.getMessage());
                    }
                }
            } else {
                String titulo = "O CPF deve ter somente números";
                String mensagem = "O CPF informado não é válido. \nO CPF informado deve conter somente números";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    /**
     * Controla o evento de selecionar uma especialidade. Ao selecionar uma especialidade
     * a opção de subespecialidade é liberada e a especialidade selecionada não aparece
     * como uma opção de subespecialidade.
     * 
     * @param event o evento.
     */
    @FXML
    private void especialidadeSelecionada(ActionEvent event) {
        if(!especialidades.getSelectionModel().isEmpty()){
            Especialidade especialidadeSelecionada = especialidades.getSelectionModel().getSelectedItem();
            LinkedListEspecialidade subespecialidadesList = new LinkedListEspecialidade();
            subespecialidadesList.addAll(Sistema.getEspecialidades());
            subespecialidadesList.remove(especialidadeSelecionada);  //Remove a especialidade selecionada da lista de subespecialidades
            opcoesSubespecialidades = FXCollections.observableList(subespecialidadesList);
            subespecialidades.setItems(opcoesSubespecialidades);
            subespecialidades.setDisable(false);  //Habilita a opção de subespecialidade
            subespecialidades.setVisible(true);  //Deixa visível a opção de subespecialidade
        }    
    }
    
    //********* Métodos complementares ********
    /**
     * O método carrega as opções de especialidades ComboBox.
     */
    private void carregarOpcoesEspecialidades(){
        opcoesEspecialidades = FXCollections.observableList(Sistema.getEspecialidades()); 
        especialidades.setItems(opcoesEspecialidades);  //Coloca as opções no ComboBox de especialidades 
    }
    
    /**
     * O método é responsável por fechar a janela de cadastro de médicos.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();  //Obtém a janela
        stage.close();  //Fecha a janela
    }
}
