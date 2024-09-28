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
 * Controlador para a tela de gerenciamento de recepcionistas. 
 * Permite efetuar a comunicação entre a view da tela de gerenciamento de recepcionistas e o model.
 *
 * @author Igor
 */
public class TelaGerenciadorRecepcionistaController implements Initializable {

    @FXML
    private ListView<Recepcionista> listaRecepcionista;
    
    private ObservableList<Recepcionista> observListaRecepcionista;
    private Recepcionista recepcionista;
    private Recepcionista recepcionistaEditar;
    
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
        carregarRecepcionistas();
    }    

    /**
     * Controla o evento de click no botão de adicionar. Ao clicar no botão uma nova janela
     * para adicionar um novo recepcionista é aberta.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAdicionar(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaCadastroRecepcionista.fxml";
            String titulo = "Cadastrar Recepcionista";
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
            carregarRecepcionistas();
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciadorRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Controla o evento de click no botão editar. Ao clicar no botão, se houver
     * algum recepcionista selecionado, uma nova janela para a edição das informações
     * do recepcionista é aberta.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickEditar(ActionEvent event) {
        if (!listaRecepcionista.getSelectionModel().isEmpty()){  
            try {
                recepcionistaEditar = listaRecepcionista.getSelectionModel().getSelectedItem();
                ResourceBundle rb = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        return switch (key) {
                            case "recepcionista" -> recepcionista;
                            case "recepcionistaEditar" -> recepcionistaEditar;
                            default -> null;
                        };
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(handleKeySet());
                    }

                    @Override
                    protected Set<String> handleKeySet() {
                        return new HashSet<>(Arrays.asList("recepcionista", "recepcionistaEditar"));
                    }
                };
                String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaEditarRecepcionista.fxml";
                String titulo = "Editar Recepcionista";
            
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
                carregarRecepcionistas();
            } catch (IOException ex) {
                Logger.getLogger(TelaGerenciadorRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    /**
     * O método é responsável por controlar o evento de click no botão voltar, Ao clicar
     * no botão a janela de gerenciamento de recepcionista é fechada e retorna para a
     * janela de opções do recepcionista.
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
            Logger.getLogger(TelaGerenciadorRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // ******* Métodos complementares *******
    /**
     * O método é responsável por carregar os recepcionistas na lista.
     */
    private void carregarRecepcionistas(){
        observListaRecepcionista = FXCollections.observableList(Sistema.getRecepcionistas());
        listaRecepcionista.setItems(observListaRecepcionista);
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
