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
import br.uefs.ecomp.gerenciadorTarefas.model.Tarefa;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class.
 * Controlador para a interface de tarefa de um determinado projeto. 
 * Permite efetuar a comunicação entre a view da interface e o model.
 *
 * @author ifs54
 */

public class PaginaTarefasController implements Initializable {

    @FXML
    private ListView<Tarefa> listaTarefasConcluidas;
    @FXML
    private ListView<Tarefa> listaTarefasExecucao;
    @FXML
    private ListView<Tarefa> listaTarefasPendentes;
    
    Projeto projeto;
    
    private ObservableList<Tarefa> observTarefasPendentes;
    private ObservableList<Tarefa> observTarefasExecucao;
    private ObservableList<Tarefa> observTarefasConcluidas;
    
    private int segundosFimDia;  //Tempo até o final do dia em segundos
    
    /**
     * Initializes the controller class.
     * É chamado ao inicializar a classe controladora.
     * @param url a URL
     * @param rb os recursos externos necessários na classe.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        segundosFimDia = segundosFimDia();  //Armazena os segundos até chegar ao outro dia
        this.projeto = (Projeto) rb.getObject("projeto");  //Obtém o projeto que deve ser exibidas as tarefas
        carregarTarefas();  //Carrega as tarefas
        
        //Cria uma rotina de atualização da tela caso ocorra a mudança de dia enquanto a tela é exibida - Isso permite atualizar informações caso uma tarefa venha ficar atrasada enquanto é exibida as tarefas
        Timeline oneMinuteTimeline = new Timeline(new KeyFrame(Duration.seconds(segundosFimDia), event ->{
            carregarTarefas();  //Recarrega a tarefas
            segundosFimDia = segundosFimDia();  //Defini um novo tempo para atualizar a página
        }));
        oneMinuteTimeline.setCycleCount(Timeline.INDEFINITE);
        oneMinuteTimeline.play();
        //Fim da rotina
                                                                                                  
  
    }    
    
    /**
     * Controla o evento de click no botão alterar status da tarefa. Ao clicar no botão,
     * se houver tarefa selecionada, uma nova janela é exibida permitindo escolher um novo
     * status para a tarefa. Essa janela é controlada por outra classe controladora
     * {@link AlterarStatusTarefaController}. Caso nenhuma tarefa apresente-se selecionada
     * nenhuma ação é executada.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickAlterarStatus(ActionEvent event) throws IOException {
        if (!listaTarefasPendentes.getSelectionModel().isEmpty() || !listaTarefasExecucao.getSelectionModel().isEmpty() || !listaTarefasConcluidas.getSelectionModel().isEmpty()){
            Tarefa tarefa = tarefaSelecionada();  //Retorna a tarefa que foi selecionada no Listview para alterar o Status
            
            //Cria a janela de alterar o Status da tarefa selecionada
            final Stage stage = new Stage();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("tarefa")){
                        return tarefa;
                    }else if (key.contains("projeto")){
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
                    return new HashSet<>(Arrays.asList("tarefa", "projeto", "stage"));
                }
            };
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/AlterarStatusTarefa.fxml"), rb);
            Scene scene = new Scene(root);
            stage.setTitle("Alterar Status da tarefa");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarTarefas();
        }
    }
    
    /**
     * Controla o evento de click no botão voltar para projetos. Ao clicar nesse botão,
     * a janela principal volta para a janela de projetos do sistema. Essa janela é
     * controlada por outra classe controladora {@link PaginaTarefasController}.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickVoltar(ActionEvent event) throws IOException {
        Main.defaultTelaPrincipal();
    }
    
    /**
     * Controla o evento de click no botão adicionar tarefa. Ao clicar no botão,
     * uma nova janela é mostrada. Nesta janela é possível criar uma nova tarefa
     * informando os dados da mesma. Essa janela é controlada por outra classe 
     * controladora {@link AddTarefaController}.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickAdicionarTarefa(ActionEvent event) throws IOException {
        //Cria a janela para adicionar tarefa
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
        Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/AddTarefa.fxml"), rb);
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Adicionar Tarefa");
        stage.showAndWait();
        carregarTarefas();  
    }
    
    /**
     * Controla o evento de click no botão atualizar. Ao clicar no botão,
     * as ListViews de tarefas são atualizadas.
     * @param event o evento.
     */
    private void clickAtualizar(ActionEvent event) {
        carregarTarefas();
    }
    
    /**
     * Controla o evento de click no botão de editar tarefa. Ao clicar no botão,
     * se houver tarefa selecionada, uma nova janela para editar as informações da
     * tarefa é exibida. Essa nova janela é controlada por outra classe controladora
     * {@link EditarTarefaController}.
     * 
     * @param event o evento.
     * @throws IOException 
     */
    @FXML
    private void clickEditarTarefa(ActionEvent event) throws IOException {
        if (!listaTarefasPendentes.getSelectionModel().isEmpty() || !listaTarefasExecucao.getSelectionModel().isEmpty() || !listaTarefasConcluidas.getSelectionModel().isEmpty()){
            Tarefa tarefa = tarefaSelecionada();  //Retorna a tarefa que foi selecionada no Lisview para ser editada
            
            //Cria a janela de editar as informações da tarefa selecionada
            final Stage stage = new Stage();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("tarefa")){
                        return tarefa;
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
                    return new HashSet<>(Arrays.asList("tarefa", "stage"));
                }
            };
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/EditarTarefa.fxml"), rb);
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Editar Tarefa");
            stage.showAndWait();
            carregarTarefas();
        }
    }
    
    /**
     * Controla o evento de click no botão de excluir tarefa. Ao clicar no botão,
     * se houver alguma tarefa selecionada, a tarefa é excluída da lista de tarefas do
     * projeto.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickExcluirTarefa(ActionEvent event) {
        if (!listaTarefasPendentes.getSelectionModel().isEmpty() || !listaTarefasExecucao.getSelectionModel().isEmpty() || !listaTarefasConcluidas.getSelectionModel().isEmpty()){
            Tarefa tarefa = tarefaSelecionada();
            switch (tarefa.getStatus()) {
                case PENDENTE -> projeto.getTarefasPendentes().remove(tarefa);
                case EM_EXECUCAO -> projeto.getTarefasExecucao().remove(tarefa);
                default -> projeto.getTarefasConcluidas().remove(tarefa);
            }
            carregarTarefas();
        }
    }
    
    /**
     * Ao clicar na ListView de tarefas concluídas, limpa qualquer seleção de outra
     * ListView presente na página.
     * 
     * @param event o evento.
     */
    @FXML
    @SuppressWarnings("empty-statement")
    private void clickConcluida(MouseEvent event) {
        limparSelecaoListView("c"); //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas concluídas
    }
    
    /**
     * Ao clicar na ListView de tarefas em execução, limpa qualquer seleção de outra
     * ListView presente na página.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickExecucao(MouseEvent event) {
        limparSelecaoListView("e");  //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas em execução
    }

     /**
     * Ao clicar na ListView de tarefas pendentes, limpa qualquer seleção de outra
     * ListView presente na página.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickPendentes(MouseEvent event) {
        limparSelecaoListView("p"); //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas em execução
    }
    
    /**
     * Limpa qualquer seleção das ListViews caso ocorra um click fora de qualquer
     * uma delas, com exceção de botões.
     * 
     * @param event o evento.
     */
    private void clickForaElementos(MouseEvent event) {
        limparSelecaoListView("");  //Limpar qualquer seleção das ListViews
    }
    
    /**
     * Limpa qualquer seleção nas outras ListViews caso ocorra o evento de selecionar um 
     * elemento da ListView de tarefas concluídas com o teclado, ou ainda, caso alguma tecla 
     * seja pressionada quando a ListView de tarefas concluídas estiver selecionada.
     * 
     * @param event o evento.
     */
    @FXML
    private void listViewConcluidaSelecionada(KeyEvent event) {
        limparSelecaoListView("c");  //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas concluídas
    }
    
    /**
     * Limpa qualquer seleção nas outras ListViews caso ocorra o evento de selecionar um 
     * elemento da ListView de tarefas em execução com o teclado, ou ainda, caso alguma tecla 
     * seja pressionada quando a ListView de tarefas em execução estiver selecionada.
     * 
     * @param event o evento.
     */
    @FXML
    private void listViewExecucaoSelecionada(KeyEvent event) {
        limparSelecaoListView("e"); //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas em execução
    }
    
    /**
     * Limpa qualquer seleção nas outras ListViews caso ocorra o evento de selecionar um 
     * elemento da ListView de tarefas pendentes com o teclado, ou ainda, caso alguma tecla
     * seja pressionada quando a ListView de tarefas pendentes estiver selecionado.
     * 
     * @param event o evento.
     */
    @FXML
    private void listViewPendentesSelecionada(KeyEvent event) {
        limparSelecaoListView("p"); //Limpa qualquer seleção das ListViews, com execação da ListView de tarefas pendentes
    }
    
    //******** Métodos complementares *********
    /**
     * O método é responsável por encontrar o tempo, em segundos, a partir de um momento
     * até o final do dia e acrescentar 1 segundo a esse tempo. O método permite efetuar
     * a atualização de tarefas caso um dia acabe e inicie outro durante a exibição da lista
     * de tarefas.
     * 
     * @return retorna um int com os segundos até o final do dia atual acrescido de 1 segundo.
     */
    private int segundosFimDia(){
        Calendar dt = Calendar.getInstance();  //Obtém o calendário regional
        int hora = dt.get(Calendar.HOUR_OF_DAY);  //Obtém a hora atual do sistema
        int minuto = dt.get(Calendar.MINUTE);  //Obtém o minuto atual do sistema
        int segundos = dt.get(Calendar.SECOND);  //Obtém o segundo atual
        int segFimDia = 86400 - ((hora*3600) + (minuto*60) + segundos);  //Armazena o tempo, sem segundos, até o final do dia a partir deste instante
        return segFimDia + 1; //Retorna os segundos até o fim do dia acrescido de 1 segundo - O acréscimo do segundo garante que o valor indique o novo dia
    }
    
    /**
     * O método é responsável por carregar as listViews (quadros de tarefas) com as tarefas
     * de acordo com seus status. O método também pode ser chamado para atulizar a exibição
     * de elementos das ListViews, caso exista alguma alteração ou exclusão em alguma das tarefas.
     */
    private void carregarTarefas(){
        observTarefasPendentes = FXCollections.observableList(projeto.getTarefasPendentes());
        listaTarefasPendentes.setItems(observTarefasPendentes);
        
        observTarefasExecucao = FXCollections.observableList(projeto.getTarefasExecucao());
        listaTarefasExecucao.setItems(observTarefasExecucao);
        
        observTarefasConcluidas = FXCollections.observableList(projeto.getTarefasConcluidas());
        listaTarefasConcluidas.setItems(observTarefasConcluidas);
    }
    
    /**
     * O método verifica qual foi a tarefa selecionada dos listview presentes na página
     * e retorna a tarefa.
     * 
     * @return um objeto do tipo Tarefa indicando a tarefa selecionada
     */
    private Tarefa tarefaSelecionada(){
        Tarefa tarefa;
        if (!listaTarefasPendentes.getSelectionModel().isEmpty()){
            tarefa = listaTarefasPendentes.getSelectionModel().getSelectedItem();
        }else if (!listaTarefasExecucao.getSelectionModel().isEmpty()){
           tarefa = listaTarefasExecucao.getSelectionModel().getSelectedItem();
        }else{
           tarefa = listaTarefasConcluidas.getSelectionModel().getSelectedItem();
        }
        return tarefa;
    }
    
    /**
     * O método limpa a seleção de cada um dos Listview presentes na página, caso
     * algum elemento deles apresente-se selecionado. O método recebe por parâmetro
     * uma String que indica se alguma das ListViews não deve ter sua seleção limpa.
     * 
     * a String <b>"p"</b> representa a ListView de tarefas pedentes; 
     * a String <b>"e"</b> representa a ListView de tarefas em execução; 
     * a String <b>"c"</b> representa a ListView de tarefas concluídas;
     * <b>qualquer outra</b> String representa que todas as ListViews devem ter sua seleção limpa;
     * 
     * @param exececao recebe uma char que indica a ListView que não deve ter sua seleção
     * limpa.
     */
    private void limparSelecaoListView(String excecao){
        switch (excecao){
            case "p" -> {
                if (!listaTarefasExecucao.getSelectionModel().isEmpty()){
                    listaTarefasExecucao.getSelectionModel().clearSelection();
                }
                if (!listaTarefasConcluidas.getSelectionModel().isEmpty()){
                    listaTarefasConcluidas.getSelectionModel().clearSelection();
                }
            }
            case "e" -> {
                if (!listaTarefasPendentes.getSelectionModel().isEmpty()){
                    listaTarefasPendentes.getSelectionModel().clearSelection(); ;
                }
                if (!listaTarefasConcluidas.getSelectionModel().isEmpty()){
                    listaTarefasConcluidas.getSelectionModel().clearSelection();
                }
            }
            case "c" -> {
                if (!listaTarefasPendentes.getSelectionModel().isEmpty()){
                    listaTarefasPendentes.getSelectionModel().clearSelection(); ;
                }
                if (!listaTarefasExecucao.getSelectionModel().isEmpty()){
                    listaTarefasExecucao.getSelectionModel().clearSelection();
                }
            }
            default -> {
                if (!listaTarefasPendentes.getSelectionModel().isEmpty()){
                    listaTarefasPendentes.getSelectionModel().clearSelection(); ;
                }
                if (!listaTarefasExecucao.getSelectionModel().isEmpty()){
                    listaTarefasExecucao.getSelectionModel().clearSelection();
                }
                if (!listaTarefasConcluidas.getSelectionModel().isEmpty()){
                    listaTarefasConcluidas.getSelectionModel().clearSelection();
                }
            }
        } 
    }
}
