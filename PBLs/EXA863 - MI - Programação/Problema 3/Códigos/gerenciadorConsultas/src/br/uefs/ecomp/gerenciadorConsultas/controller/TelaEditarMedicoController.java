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
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
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
 * Controlador para a tela de editar médico. 
 * Permite efetuar a comunicação entre a view da tela de editar médico e o model.
 *
 * @author Igor
 */
public class TelaEditarMedicoController implements Initializable {

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
    private Button btnConcuido;
    @FXML
    private TextField crmMedico;
    
    private ObservableList<Especialidade> opcoesEspecialidades;
    private ObservableList<Especialidade> opcoesSubespecialidades;
    
    private Medico medico;
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
        medico = (Medico) rb.getObject("medico");
        nomeMedico.setText(medico.getNome());
        crmMedico.setText(medico.getCrm());
        cpfMedico.setText(medico.getCpf());
        senhaMedico.setText(medico.getSenha());
        carregarOpcoesEspecialidades();
        especialidades.getSelectionModel().select(medico.getEspecialidade());
        subespecialidades.getSelectionModel().select(medico.getSubespecialidade());
        carregarOpcoesSubespecialidades();
    }    

    /**
     * Controla o evento de seleção de especialiadade. Se houver especialidade
     * selecionada, é liberada a opção de seleção de subespecialidade e a especialidade
     * selecionada não é exibida como opção na subespecialidade.
     * 
     * @param event o evento.
     */
    @FXML
    private void especialidadeSelecionada(ActionEvent event) {
        if (!especialidades.getSelectionModel().isEmpty()) {
            carregarOpcoesSubespecialidades();
        }
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
     * estiverem preenchidos corretamente, o médico é editado e a janela é fechada. Caso contrário
     * é exibida uma mensagem de erro.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if (!(nomeMedico.getText().isBlank() || cpfMedico.getText().isBlank() || senhaMedico.getText().isBlank() || confirmacaoSenhaMed.getText().isBlank() || especialidades.getSelectionModel().isEmpty() || crmMedico.getText().isBlank())) {
            String nome = nomeMedico.getText().toUpperCase();
            String crm = crmMedico.getText();
            String cpf = cpfMedico.getText();
            String senha = senhaMedico.getText();
            String confirmacaoSenha = confirmacaoSenhaMed.getText();
            Especialidade especialidade = especialidades.getSelectionModel().getSelectedItem();
            Especialidade subespecialidade = subespecialidades.getSelectionModel().getSelectedItem();
            if (!senha.equals(confirmacaoSenha)) {
                String titulo = "As senha informada não são iguais.";
                String mensagem = "A senha informada para confirmação não é igual a senha escolhida.";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            } else {
                try {
                    recepcionista.editarMedico(medico, nome, crm, cpf, senha, especialidade, subespecialidade);
                    fecharJanela();
                } catch (CpfCadastradoException ex) {
                    String titulo = "Erro ao editar o médico";
                    criadorJanelas.exibirMensagemErro(titulo, ex.getMessage());
                }
            }
        } else {
            String titulo = "Não são permitidos campos vazios";
            String mensagem = "Preencha todos os campos.";
            criadorJanelas.exibirMensagemErro(titulo, mensagem);
        }
    }
    
    //******** Métodos complementares ********
    /**
     * O método é responsável por carregar as opções de especialidades no ComboBox.
     */
    private void carregarOpcoesEspecialidades(){
        opcoesEspecialidades = FXCollections.observableList(Sistema.getEspecialidades()); 
        especialidades.setItems(opcoesEspecialidades);  //Coloca as opções no ComboBox de especialidades 
    }
    
    /**
     * O método é responsável por carregar as opções de subespecialidade no ComboBox
     */
    private void carregarOpcoesSubespecialidades() {
        Especialidade especialidadeSelecionada = especialidades.getSelectionModel().getSelectedItem();
        LinkedListEspecialidade subespecialidadesList = new LinkedListEspecialidade();
        subespecialidadesList.addAll(Sistema.getEspecialidades());
        subespecialidadesList.remove(especialidadeSelecionada);
        opcoesSubespecialidades = FXCollections.observableList(subespecialidadesList);
        subespecialidades.setItems(opcoesSubespecialidades);
        subespecialidades.setDisable(false);
        subespecialidades.setVisible(true);
    }
    
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnConcuido.getScene().getWindow();
        stage.close();
    }
}
