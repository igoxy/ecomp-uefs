/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 19/10/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.gerenciadorTarefas.controller;  //Pacote da classe

import br.uefs.ecomp.gerenciadorTarefas.main.Main;
import br.uefs.ecomp.gerenciadorTarefas.model.Projeto;
import br.uefs.ecomp.gerenciadorTarefas.model.Sistema;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 * Controlador para a interface inicial do sistema, a interface de projetos. 
 * Permite efetuar a comunicação entre a view da interface e o model.
 *
 * @author ifs54
 */
public class PaginaInicialController implements Initializable{

    @FXML
    private ListView<Projeto> listProjetos;
    
    private ObservableList<Projeto> observProjetos;
    
    
    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarProjetos();
        
    }
    
    /**
     * Controla o evento de click no botão de adicionar projeto. Ao clicar 
     * no botão, exibe uma nova janela para adicionar um novo projeto. Essa
     * janela é controlada por outra classe controladora {@link AddProjetoController}.
     * 
     * @param event o evento.
     * @see AddProjetoController
     * @throws IOException 
     */
    @FXML
    private void clickAddProjeto(ActionEvent event) throws IOException {
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adicionar projeto");
        Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/AddProjeto.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        carregarProjetos();
    }
    
    /**
     * Controla o evento de click no botão de remover tarefa. Ao clicar no botão,
     * se houver algum projeto selecionado e o mesmo atender aos requisitos para
     * exclusão do sistema, o projeto é excluído. Já caso não atenda aos requisitos,
     * uma mensagem de erro é exibida. Já caso não houver algum projeto selecionado,
     * não há ação.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickRemoverProjeto(ActionEvent event) throws IOException {
        if (!listProjetos.getSelectionModel().isEmpty()){
            Projeto projeto = listProjetos.getSelectionModel().getSelectedItem();
            int tamanhoListaProjetos = Sistema.getProjetos().size();
            Sistema.getProjetos().removerProjeto(projeto);
            if (tamanhoListaProjetos == Sistema.getProjetos().size()){
                Alert alertaExcluirProjeto = new Alert(Alert.AlertType.WARNING);
                alertaExcluirProjeto.setTitle("O projeto não pode ser excluído");
                alertaExcluirProjeto.setContentText("O projeto não pode ser excluído"
                        + " pois apresenta tarefas não concluídas");
                alertaExcluirProjeto.show();
            }else{
                carregarProjetos();
            }
        }
    }
    
    /**
     * Controla o evento de click no botão de editar projeto. Ao clicar no botão,
     * se houver algum projeto selecionado, é exibida uma janela para edição do 
     * projeto. Essa janela é controlada por outra classe controladora {@link EditarProjetoController}.
     * Já caso não houver seleção de projeto, não há ação.
     * 
     * @param event o evento.
     * @see EditarProjetoController
     * @throws IOException 
     */
    @FXML
    private void clickEditarProjeto(ActionEvent event) throws IOException {
        if (!listProjetos.getSelectionModel().isEmpty()){
            Projeto projeto = listProjetos.getSelectionModel().getSelectedItem();
            final Stage stage = new Stage();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("projeto")){
                        return projeto;
                    }else if (key.contains("stage")){
                        return stage;
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
                    return new HashSet<>(Arrays.asList("projeto", "stage"));
                }
            };
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/EditarProjeto.fxml"), rb);
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarProjetos();
        }
    }
    
    /**
     * Controla o evento de click no botão atualizar. Ao clicar no botão,
     * a ListView de projetos é atualizada.
     * 
     * @param event o evento.
     */
    private void clickAtualizar(ActionEvent event) {
        carregarProjetos();
    }
    
    /**
     * Controla o evento de click no botão abrir projeto. Ao clicar no botão, se
     * houver algum projeto selecionado, a janela principal torna-se a janela das
     * tarefas do projeto selecionado. Essa janela é controlada por outra classe
     * controladora {@link PaginaTarefasController}. Já se não houver algum
     * projeto selecionado, não há ação.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickAbrirProjeto(ActionEvent event) throws IOException {
        if (!listProjetos.getSelectionModel().isEmpty()){
            Projeto projeto = (Projeto) listProjetos.getSelectionModel().getSelectedItem();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("projeto")){
                        return projeto;    
                    }
                    return null;
                }
                @Override
                public Enumeration<String> getKeys() {
                    return Collections.enumeration(handleKeySet());
                }
                
                @Override
                protected Set<String> handleKeySet(){
                    return new HashSet<>(Arrays.asList("projeto"));
                }
            };
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/PaginaTarefas.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal(projeto.getNome() + " - Tarefas", scene);
        }
    }
    
    //Método complementar
    /**
     * O método é responsável por carregar a listView com os projetos. O método 
     * também pode ser chamado para atulizar a exibição de elementos da ListView, 
     * caso exista alguma alteração ou exclusão de projetos.
     */
    private void carregarProjetos(){
        observProjetos = FXCollections.observableList(Sistema.getProjetos());
        listProjetos.setItems(observProjetos);
    }
}
