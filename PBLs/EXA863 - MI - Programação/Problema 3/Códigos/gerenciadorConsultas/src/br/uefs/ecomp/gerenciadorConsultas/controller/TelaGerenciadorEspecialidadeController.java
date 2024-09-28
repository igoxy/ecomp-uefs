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
 * Controlador para a tela de gerenciamento de especialidades. 
 * Permite efetuar a comunicação entre a view da tela de gerenciamento de especialidades e o model.
 *
 * @author Igor
 */
public class TelaGerenciadorEspecialidadeController implements Initializable {

    @FXML
    private ListView<Especialidade> listViewEspecialidades;
    
    private ObservableList<Especialidade> observListaEspecialidades;
    
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
        carregarEspecialidades();
    }    
    
    /**
     * Controla  o evento de click no botão adicionar. Ao clicar no botão um nova janela
     * é aberta para o cadastro de um nova especialidade.
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickAdicionar(ActionEvent event) {
        String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaCadastroEspecialidade.fxml";
        String titulo = "Adicionar Especialidade";
        ResourceBundle rb = getRecursoRecepcionista();
        try {
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciadorEspecialidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        carregarEspecialidades();  //Recarrega a lista de especialidades
    }

    /**
     * Controla o evento de click no botão editar. Ao clicar no botão, se alguma
     * especialidade da lista estiver selecionada, abre uma nova janela para a edição
     * da especialidade selecionada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickEditar(ActionEvent event) {
        if (!listViewEspecialidades.getSelectionModel().isEmpty()){
            Especialidade especialidade = listViewEspecialidades.getSelectionModel().getSelectedItem();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("especialidade")){
                        return especialidade;
                    } else if (key.contains("recepcionista")){
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
                    return new HashSet<>(Arrays.asList("especialidade", "recepcionista"));
                }
            };
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaEditarEspecialidade.fxml";
            String titulo = "Editar Especialidade";
            try {
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
                carregarEspecialidades();
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorEspecialidadeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Controla o evento de click no botão de remover. Ao clicar no botão, se houver alguma
     * especialidade selecionada e a mesma não apresentar médicos associados, a especialidade
     * é removida do sistema.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickRemover(ActionEvent event) {
        if (!listViewEspecialidades.getSelectionModel().isEmpty()){
            Especialidade especialidade = listViewEspecialidades.getSelectionModel().getSelectedItem();
            if (recepcionista.removerEspecialidade(especialidade)){
                carregarEspecialidades();
            }else{
                String titulo = "A especialidade não pode ser excluída";
                String mensagem = "A especialidade não pode ser excluída pois existe(m) médico(s) relacionado a ela.";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    /**
     * Controla o evento de click no botão de voltar. Ao clicar no botão a tela de gerenciamento
     * de especialidade é fechada e volta para a tela anterior (a tela de opções do recepcionista).
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
            Logger.getLogger(TelaGerenciadorEspecialidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //******* Métodos complementares ********
    /**
     * O método é responsável por carregar a lista de especialidades cadastradas no sistema.
     */
    private void carregarEspecialidades(){
        observListaEspecialidades = FXCollections.observableList(Sistema.getEspecialidades());
        listViewEspecialidades.setItems(observListaEspecialidades);
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
