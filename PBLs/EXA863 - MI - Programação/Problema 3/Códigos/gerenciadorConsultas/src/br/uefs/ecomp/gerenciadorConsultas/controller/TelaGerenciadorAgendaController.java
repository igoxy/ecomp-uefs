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
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de agenda do médico. 
 * Permite efetuar a comunicação entre a view da tela e o model.
 *
 * @author Igor
 */
public class TelaGerenciadorAgendaController implements Initializable {

    @FXML
    private DatePicker dataConsulta;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnConcluido;
    @FXML
    private ListView<Horario> listViewHorarios;
    @FXML
    private Label textMensagem;
    
    private Medico medico;  //Médico associado a agenda
    private ObservableList<Horario> observListaHorarios;
    
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
        String origem = (String) rb.getObject("origem");
        setOpcoes(origem);  //Desabilita algumas opções caso não seja o recepcionista quem abriu a tela de agenda
    }    

    /**
     * Controla o evento de seleção de data. Caso o usuário selecione uma data específica,
     * os horários disponíveis cadastrados para o médico são exibidos. Caso a data selecionada
     * não apresente horários disponíveis, nada é exibido.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickData(ActionEvent event) {
        try{
            Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String dataString = data.toString();
            carregarHorarios(dataString);
        }catch (java.lang.NullPointerException ex){
            //Se não tiver nada selecionado ou não tiver horários para data selecionada carrega ListView vazia
            carregarHorarios("");
        }
    }

    /**
     * Controla o evento de click no botão de adicionar horário. Ao clicar no botão
     * uma nova janela é aberta para o cadastro de um novo horário. Essa nova janela
     * aberta é controlada por outra classe controladora {@link TelaCadastroHorarioController}.
     * 
     * @param event o evento.
     * @throws IOException lançada se ocorrer um erro ao abrir o arquivo FXML da tela.
     */
    @FXML
    private void clickAdicionarHorario(ActionEvent event) throws IOException {
        try {  //Se houver data selecionada
            Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String dataString = data.toString();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("medico")){
                        return medico;
                    }else if (key.contains("data")){
                        return dataString;
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
                    return new HashSet<>(Arrays.asList("medico", "stage", "data"));
                }
            };
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaCadastroHorario.fxml";
            String titulo = "Cadastrar horário";            
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '0'); //Abre a nova janela
            carregarHorarios(dataString);  //Recarrega os horários
                  
        }catch (java.lang.NullPointerException ex){  //Se não houver data selecionada.
            //Não faz nada.
        } 
    }
    
    /**
     * Controla o evento de click no botão de remover horário. Ao clicar no botão, se houver
     * algum horário selecionado, o horário é removido da relação de horários disponíveis
     * para a marcação de consultas.
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickRemoverHorario(ActionEvent event) {
        if(!listViewHorarios.getSelectionModel().isEmpty()){
            Horario horarioRemover = listViewHorarios.getSelectionModel().getSelectedItem();
            Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String dataString = data.toString();
            medico.getAgenda().getHorariosDisponiveis().get(dataString).remove(horarioRemover);
            carregarHorarios(getDataString());
        }
    }
    
    
    /**
     * Controla o evento de click no botão de marcar consulta. Ao clicar no botão, se houver
     * algum horário selecionado, um nova tela para a marcação de consulta no horário selecionado
     * é aberta. Essa nova tela é controlada por outra classe controladora {@link TelaMarcarCOnsultaController}.
     * Ao marcar a consulta, o horário selecionado não estará mais disponível na lista.
     * 
     * @param event o evento.
     * @throws IOException lançada se ocorrer um erro ao abrir o arquivo FXML da tela.
     */
    @FXML
    private void clickMarcarConsulta(ActionEvent event) throws IOException {
        if(!listViewHorarios.getSelectionModel().isEmpty()){
            Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String dataString = data.toString();
            
            int tamanhoInicialHorarios = medico.getAgenda().getHorariosDisponiveis().get(dataString).size();
            Horario horario = listViewHorarios.getSelectionModel().getSelectedItem();
          
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("medico")){
                        return medico;
                    }else if (key.contains("data")){
                        return getDataString();
                    }else if (key.contains("horario")){
                        return horario;
                    }else if (key.contains("listaHorarios")){
                        return observListaHorarios;
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
                    return new HashSet<>(Arrays.asList("medico", "data", "horario", "stage", "listaHorario"));
                }
            };
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaMarcarConsulta.fxml";
            String titulo = "Marcar consulta com o Dr. " + medico.getNome();
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '0');
            
            //Verifica se houve cadastro de consulta
            int tamanhoFinalHorarios = medico.getAgenda().getHorariosDisponiveis().get(dataString).size();
            if (tamanhoInicialHorarios != tamanhoFinalHorarios){
                carregarHorarios(dataString);  //Recarrega a lista de horários
            }  
        }
    }
    /**
     * Controla o evento de click no botão concluído. Ao clicar no botão a janela é fechada
     * e o programa retorna a janela anterior.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        fecharJanela();
    }
    
    //******* Métodos complementares *******
    /**
     * O método é responsável por carregar os horários cadastrados da data selecionada 
     * na ListView de horários disponíveis.
     * 
     * @param data a data selecionada.
     * @throws java.lang.NullPointerException lançada se alguma data não for válida.
     */
    private void carregarHorarios(String data) throws java.lang.NullPointerException{
        if (data.equals("")){  //Senão for informada nenhuma data
            observListaHorarios = null; //Nenhuma informação é carregada na ListView
            listViewHorarios.setItems(observListaHorarios);
        }else{
            observListaHorarios = FXCollections.observableArrayList(medico.getAgenda().getHorariosDisponiveis().get(data));
            observListaHorarios.sort((Horario o1, Horario o2) -> {  //Ordena os horários.
                int horario1 = Integer.parseInt(o1.getIdentificacao().replace(":", ""));
                int horario2 = Integer.parseInt(o2.getIdentificacao().replace(":", ""));
                return horario1 - horario2;
            });
            listViewHorarios.setItems(observListaHorarios);
        }
    }
    
    /**
     * Obtém a data selecionada no formato de String.
     * @return uma String representando a data.
     */
    private String getDataString(){
        Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String dataString = data.toString();
        return dataString;
    }
    
    /**
     * O método é responsável por desabilitar algumas opções e alterar o texto de
     * exibição se a tela de agenda de horários disponíveis for acessada por um paciente 
     * por meio do terminal de autoatendimento. As opções desabilitadas são: adicionar horário, 
     * remover horário e editar horário.
     * 
     * @param origem identifica se a tela foi chamada por meio de um recepcionista ou paciente. 
     */
    private void setOpcoes(String origem){
        if (!origem.contains("recepcionista")){  //Se a tela anterior não for a de gerenciamento de agendas do médico, limita as opções          
            //Se a tela anterior for de gerenciamento de agenda do médico, significa que foi a recepcionista que abriu a tela, caso contrário foi a partir do autoatendimento
            btnAdicionar.setDisable(true);  //Desabilita o botão de adicionar horários
            btnAdicionar.setVisible(false);  //Esconde o botão de adicionar horário
            btnRemover.setDisable(true);  //Desabilita o botão de remover horário
            btnRemover.setVisible(false);  //Esconde o botão de remover horário
            
            textMensagem.setText("Selecione a data e o horário para agendar sua consulta.");  //Altera o texto de exibição para o paciente
        }
    }
    
    /**
     * Método responsável por fechar a janela (tela).
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnConcluido.getScene().getWindow();  //Obtém a janela
        stage.close();  //Fecha a janela
    }
        
}
