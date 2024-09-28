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
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 * Controlador para a tela de gerenciamento de médicos. 
 * Permite efetuar a comunicação entre a view da tela de gerenciamento de médicos e o model.
 *
 * @author Igor
 */
public class TelaGerenciadorMedicoController implements Initializable {

    @FXML
    private ListView<Medico> listaMedico;
    
    private ObservableList<Medico> observListaMedicos;
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
        carregarMedicos();
    }    

    /**
     * Controla o evento de click no botão adicionar. Ao clicar no botão adicionar
     * abre uma nova janela para o cadastro de um novo médico.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAdicionar(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaCadastroMedico.fxml";
            String titulo = "Cadastrar Médico";
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
            carregarMedicos();
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciadorMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Controla o evento de click no botão de editar. Ao clicar no botão, se houver
     * algum médico selecionado, abre uma nova janela para a edição das informações
     * do médico. 
     * 
     * @param event o evento.
     */
    @FXML
    private void clickEditar(ActionEvent event) {
        if (!listaMedico.getSelectionModel().isEmpty()) {
            try {
                Medico medico = listaMedico.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("medico")) {
                            return medico;
                        } else if (key.contains("recepcionista")){
                            return recepcionista;
                        }else {
                            return null;
                        }
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(handleKeySet());
                    }

                    @Override
                    protected Set<String> handleKeySet() {
                        return new HashSet<>(Arrays.asList("medico", "recepcionista"));
                    }
                };
                String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaEditarMedico.fxml";
                String titulo = "Editar Médico";

                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
                carregarMedicos();
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorMedicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Controla o evento de click no botão remover. Ao clicar no botão remover, se houver
     * algum médico selecionado e não houver consultas atendidas, o médico removido do sistema.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickRemover(ActionEvent event) {
        if (!listaMedico.getSelectionModel().isEmpty()){
            Medico medico = listaMedico.getSelectionModel().getSelectedItem();
            if(recepcionista.removerMedico(medico)){
                carregarMedicos();  //Se foi removido o médico, atualiza a lista
            }else{
                String titulo = "Não possível exlcuir o médico";
                String mensagem = "Não possível remover o médico pois\n"
                        + "o mesmo apresenta consultas já atendidas.";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    /**
     * Controla o evento de click no botão voltar. Ao clicar no botão a tela de gerenciamento de
     * médicos é fechada e retorna para a tela de opções do recepcionista.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickVoltar(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaRecepcionista.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal("Recepcionista", scene);
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciadorMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Controla o evento de click no botão Agenda. Ao clicar no botão abre uma nova
     * tela para o gerenciamento da agenda de horários disponíveis para a marcação de consulta
     * do médico.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAgenda(ActionEvent event) {
        if (!listaMedico.getSelectionModel().isEmpty()){
            try {
                Medico medico = listaMedico.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.contains("medico")){
                            return medico;
                        }else if (key.contains("origem")){ //Identifica quem chamou a tela de agenda do médico
                            return "recepcionista";  //Indica que foi um(a) recepcionista que chamou a tela de agenda do médico.
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
                String titulo = "Agenda Dr. " + medico.getNome();
            
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '1');
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorMedicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //******* Métodos complementares *********
    /**
    * O método é responsável por carregar a lista de médicos cadastradas no sistema.
    */
    private void carregarMedicos(){
        observListaMedicos = FXCollections.observableList(Sistema.getMedicos());
        listaMedico.setItems(observListaMedicos);
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
