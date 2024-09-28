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

import br.uefs.ecomp.gerenciadorConsultas.main.Main;
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.model.Sistema;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de gerenciamento de pacientes. 
 * Permite efetuar a comunicação entre a view da tela de gerenciamento de pacientes e o model.
 *
 * @author Igor
 */
public class TelaGerenciadorPacienteController implements Initializable {

    @FXML
    private ListView<Paciente> listaPacientes;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnRemover;
    
    private String origem = "";
    private ObservableList<Paciente> observListaPacientes;
    
    private Recepcionista recepcionista = null;
    
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
        
        origem = (String) rb.getObject("origem");
        if (origem.contains("medico")){
            btnAdicionar.setDisable(true);
            btnAdicionar.setVisible(false);
            
            btnEditar.setDisable(true);
            btnEditar.setVisible(false);
            
            btnRemover.setDisable(true);
            btnRemover.setVisible(false);
        }else{
            recepcionista = (Recepcionista) rb.getObject("recepcionista");
        }
        carregarPacientes();
        
    }    
    
    /**
     * Controla o evento de click no botão de adicionar. Ao clicar no botão uma nova
     * janela é aberta para a cadastro de um novo paciente.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAdicionar(ActionEvent event) {     
        try {
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("recepcionista")) {
                        return recepcionista;
                    } else {
                        return null;
                    }
                }

                @Override
                public Enumeration<String> getKeys() {
                    return Collections.enumeration(handleKeySet());
                }

                @Override
                protected Set<String> handleKeySet() {
                    return new HashSet<>(Arrays.asList("recepcionista"));
                }
            };
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaCadastroPaciente.fxml";
            String titulo = "Cadastrar Paciente";
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
            carregarPacientes();
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciadorPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Controla o evento de click no botão editar. Ao clicar no botão, se houver
     * algum paciente selecionado, uma nova janela é aberta para edição das informações
     * do paciente selecionado.
     * 
     */
    @FXML
    private void clickEditar(ActionEvent event) {
        if (!listaPacientes.getSelectionModel().isEmpty()){
            try {
                Paciente paciente = listaPacientes.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("paciente")){
                            return paciente;
                        }else if (key.contains("recepcionista")){
                            return recepcionista;
                        }else{
                            return null;
                        }
                    }
                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(handleKeySet());
                    }

                    @Override
                    protected Set<String> handleKeySet(){
                        return new HashSet<>(Arrays.asList("paciente", "recepcionista"));
                    }
                };
                String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaEditarPaciente.fxml";
                String titulo = "Editar Paciente";
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
                carregarPacientes();
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Controla o evento de click no botão de remover. Ao clicar no botão, se houver algum
     * paciente selecionado e o mesmo não apresentar consultas efetuadas, o paciente é removido
     * do sistema. Caso contrário uma mensagem de erro é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickRemover(ActionEvent event) {
        if (!listaPacientes.getSelectionModel().isEmpty()){ 
            Paciente paciente = listaPacientes.getSelectionModel().getSelectedItem();  //Obtém o objeto selecionado da listView
            if (recepcionista.removerPaciente(paciente)){ //Tenta remover o paciente
                carregarPacientes();  //Se foi removido o paciente, atualiza a lista
            }else{  //Senão
                String titulo = "O paciente não pode ser removido";
                String mensagem = "O paciente não pode ser removido pois\n"
                        + "apresenta consulta(s) efetudas";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    /**
     * Controla o evento de click no botão voltar. Ao clicar no botão a tela de gerenciamento
     * de paciente é fechada e retorna para a tela de opções do recepcionista.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickVoltar(ActionEvent event) {
        if (!origem.contains("medico")) {
            try {
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("recepcionista")) {
                            return recepcionista;
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(handleKeySet());
                    }

                    @Override
                    protected Set<String> handleKeySet() {
                        return new HashSet<>(Arrays.asList("recepcionista"));
                    }
                };
                Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaRecepcionista.fxml"), rb);
                Scene scene = new Scene(root);
                Main.setTelaPrincipal("Recepcionista", scene);
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            fecharJanela();
        }
    }
    
    /**
     * Controla o evento de click no botão de visualizar consultas. Ao clicar no botão
     * se um paciente estiver selecionado uma nova tela de com as consultas do paciente
     * é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickHistoricoConsultas(ActionEvent event) {
        if (!listaPacientes.getSelectionModel().isEmpty()) {
            try {
                Paciente paciente = listaPacientes.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("paciente")) {
                            return paciente;
                        } else if (key.contains("origem")) {
                            return origem;
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(handleKeySet());
                    }

                    @Override
                    protected Set<String> handleKeySet() {
                        return new HashSet<>(Arrays.asList("paciente", "recepcionista"));
                    }
                };
                String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaHistoricoConsultasPaciente.fxml";
                String titulo = "Consultas - ".concat(paciente.getNome());

                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '1');
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //******* Métodos complementares *********
    /**
     * O método é responsável por carregar os pacientes na lista de pacientes.
     */
    private void carregarPacientes(){
        observListaPacientes = FXCollections.observableList(Sistema.getPacientes());
        listaPacientes.setItems(observListaPacientes);
    }
    
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnAdicionar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * O método é responsável por criar um objeto ResourceBundle com o objeto
     * Recepcionista. 
     * 
     * @return um objeto do tipo ResourceBundle.
     */
    private ResourceBundle getRecursoRecepcionista() {
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("recepcionista")) {
                    return recepcionista;
                } else {
                    return null;
                }
            }

            @Override
            public Enumeration<String> getKeys() {
                return Collections.enumeration(handleKeySet());
            }

            @Override
            protected Set<String> handleKeySet() {
                return new HashSet<>(Arrays.asList("recepcionista"));
            }
        };
        return rb;
    }
}
