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
import br.uefs.ecomp.gerenciadorConsultas.model.Especialidade;
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.model.Sistema;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 * Controlador para a tela de principal do sistema. 
 * Permite efetuar a comunicação entre a view da tela principal do sistema e o model.
 *
 * @author Igor
 */
public class TelaInicialController implements Initializable {

    @FXML
    private TextField cpfRecepcionista;
    @FXML
    private PasswordField senhaRecepcionista;
    @FXML
    private TextField cpfMedico;
    @FXML
    private PasswordField senhaMedico;
    @FXML
    private ComboBox<Especialidade> comboEspecialidades;
    @FXML
    private ComboBox<Medico> comboMedicos;
    
    private ObservableList<Especialidade> opcoesEspecialidades;
    private ObservableList<Medico> opcoesMedicos;
    
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
        cpfMedico.setText("");  //Limpa o campo de cpf do médico
        senhaMedico.setText("");  //Limpa o campo de senha do médicos
        comboMedicos.setDisable(true);  //Desativa a comboBox de médicos, só é ativada após selecionar uma especialidade.
        
        carregarEspecialidades();
    }    
    
    /**
     * Controla o evento de seleção da aba de autoatendimento.
     * 
     * @param event o evento.
     */
    @FXML
    private void autoatendimentoSelecionado(Event event) {
        carregarEspecialidades();
    }
    
    /**
     * Controla o evento de seleção de especialidade da janela de autoatendimento.
     * 
     * @param event o evento. 
     */
    @FXML
    private void actionEspecialidades(ActionEvent event) {
        if (!comboEspecialidades.getSelectionModel().isEmpty()){  //Se tiver alguma especialidade selecionada, libera as opções de médicos da especialidade.
            carregarMedicos(comboEspecialidades.getSelectionModel().getSelectedItem());  //Carrega os médicos da especialidade e habilita
            comboMedicos.setDisable(false);
        }else{  //Se não houver nada selecionado
            limparOpcoes();
        }
    }

    /**
     * Controla o evento de click na aba médico.
     * 
     * @param event o evento.
     */
    @FXML
    private void actionMedico(ActionEvent event) {
        cpfMedico.setText("");  //Limpa o campo de cpf do médico
        senhaMedico.setText("");  //Limpa o campo de senha do médico
    }

    /**
     * Controla o evento de click no botão limpar. Ao clicar no botão os campos
     * da aba de autoatendimento são limpos.
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickLimpar(ActionEvent event) {
        limparOpcoes();
    }

    /**
     * Controla o evento de click no botão buscar. Ao clicar no botão, se estiver selecionado
     * um médico, exibe a agenda do médico dos horários disponíveis para a marcação de consulta.
     * Permitindo o paciente encontrar um horário e marcar uma consulta.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickBuscar(ActionEvent event) {
        if(!comboMedicos.getSelectionModel().isEmpty()){
            try {
                Medico medico = comboMedicos.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("medico")){
                            return medico;
                        }else if (key.contains("origem")){ //Identifica quem chamou a tela de agenda do médico
                            return "paciente";  //Indica que foi um(a) paciente que chamou a tela de agenda do médico.
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
                        return new HashSet<>(Arrays.asList("medico","origem"));
                    }
                };
                String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorAgenda.fxml";
                String titulo = "Agenda Dr." + medico.getNome();
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
                limparOpcoes();
            } catch (IOException ex) {
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    
    //Aba recepcionista
    /**
     * Controla o evento de click no botão cancelar da aba recepcionista. Ao clicar no botão
     * todos os campos da aba são limpos.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelarRecep(ActionEvent event) {
        cpfRecepcionista.setText("");  //Limpa o campo de cpf do recepcionista
        senhaRecepcionista.setText("");  //Limpa o compo de senha do recepcionista
    }

    /**
     * Controla o evento de click no botão de entrar da aba recepcionista. Ao clicar no botão,
     * se as credenciais do recepcionista forem válidas, o recepcionista ganha acesso ao sistema.
     * Caso contrário, uma mensagem de erro é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickEntrarRecep(ActionEvent event) {
        try {
            String cpf = cpfRecepcionista.getText();  //Obtém o campo de cpf de recepcionista
            String senha = senhaRecepcionista.getText();  //Obtém o campo de senha de recepcionistat
            if (cpf.matches("\\d+")) {
                Recepcionista recepcionista = Sistema.getRecepcionistas().validarDadosRecepcionista(cpf, senha);  //Verifica se os dados são válidos (cpf e senha)
                if (recepcionista != null) {
                    cpfRecepcionista.setText("");  //Limpa o campo de cpf  - Evita que fique armazenado no textfield caso faça o logout (segurança)
                    senhaRecepcionista.setText("");  //Limpa o campo de senha - Evita que fique armazenado no textfield caso faça o logout (segurança)
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
                    Main.setTelaPrincipal("Recepcionista", scene);  //Seta a tela principal para a de recepcionista
                } else {
                    String titulo = "Usuário ou senhas incorretos.";
                    String mensagem = "Não possível efetuar o login no sistema\n"
                            + "pois as credenciais não são válidas";
                    criadorJanelas.exibirMensagemErro(titulo, mensagem);  //Mostra o alerta caso os dados de login não sejam válidos
                }
            } else {
                String titulo = "O CPF deve ter somente números";
                String mensagem = "O CPF informado não é válido.\n"
                        + "O CPF informado deve conter somente números";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
                cpfRecepcionista.setText("");  //Limpa o campo de cpf do recepcionista
                senhaRecepcionista.setText("");  //Limpa o compo de senha do recepcionista
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Aba de médico
    /**
     * Controla o evento de click no botão de cancelar da aba médico. Ao clicar no botão
     * todos os campos da aba médico são limpos.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelarMed(ActionEvent event) {
        cpfMedico.setText("");  //Limpa o campo de cpf do médico
        senhaMedico.setText("");  //Limpa o campo de senha do médico
    }

    /**
     * Controla o evento de click no botão entrar da aba médico. Ao clicar no botão,
     * se as credenciais do médico estiverem corretas, o mesmo ganha acesso ao sistema.
     * Caso contrário, uma mensagem de erro é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickEntrarMed(ActionEvent event) {
        try {
            String cpf = cpfMedico.getText();
            String senha = senhaMedico.getText();
            if (cpf.matches("\\d+")) {  //Verifica se o cpf só tem números

                Medico loginMedico = Sistema.getMedicos().validarDadosMedico(cpf, senha);
                if (loginMedico != null) {
                    cpfMedico.setText("");
                    senhaMedico.setText("");
                    ResourceBundle rb = new ResourceBundle() {
                        @Override
                        protected Object handleGetObject(String key) {
                            if (key.contains("medico")) {
                                return loginMedico;
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
                            return new HashSet<>(Arrays.asList("medico"));
                        }
                    };
                    Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaMedico.fxml"), rb);
                    Scene scene = new Scene(root);
                    Main.setTelaPrincipal("Médico", scene);  //Seta a tela principal para a de médico
                } else {
                    String titulo = "Usuário ou senhas incorretos.";
                    String mensagem = "Não possível efetuar o login no sistema\n"
                            + "pois as credenciais não são válidas";
                    criadorJanelas.exibirMensagemErro(titulo, mensagem); //Mostra o alerta caso os dados de login não sejam válidos 
                }
            } else {
                String titulo = "O CPF deve ter somente números";
                String mensagem = "O CPF informado não é válido.\n"
                        + "O CPF informado deve conter somente números";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
                cpfMedico.setText("");  //Limpa o campo de cpf do médico
                senhaMedico.setText("");  //Limpa o compo de senha do médico
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //****** Métodos complementares *******
    /**
     * O método é responsável por carregar as especialidades no ComBox na aba de autoatendimento.
     */
    private void carregarEspecialidades(){
        opcoesEspecialidades = FXCollections.observableList(Sistema.getEspecialidades());
        comboEspecialidades.setItems(opcoesEspecialidades);  //Adiciona as especilidades cadastradas no sistema no ComboBox
    }
    
    /**
     * O método é responsável por carregar os médicos da especialidade selecionada 
     * no ComBox na aba de autoatendimento.
     * 
     * @param especialidades a especialidade selecionda.
     */
    private void carregarMedicos(Especialidade especialidades){
        LinkedList<Medico> listaMedicos = especialidades.getMedicos();
        opcoesMedicos = FXCollections.observableList(listaMedicos);
        comboMedicos.setItems(opcoesMedicos);
    }
    
    /**
     * O método é responsável por limpar as opções da aba de autoatendimento e desabilitar
     * o ComboBox de médicos da mesma aba.
     */
    private void limparOpcoes(){
        comboMedicos.setDisable(true); //Desabilita as opções de médicos
        comboMedicos.setItems(null);  //Remove qualquer grupo de médicos do ComboBox
        comboEspecialidades.getSelectionModel().clearSelection();  //Limpa a seleção do comboBox de especialidades
    }
}
