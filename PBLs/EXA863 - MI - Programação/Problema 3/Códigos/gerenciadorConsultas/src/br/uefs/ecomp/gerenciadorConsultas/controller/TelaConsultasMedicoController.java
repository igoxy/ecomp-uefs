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

import br.uefs.ecomp.gerenciadorConsultas.model.Consulta;
import br.uefs.ecomp.gerenciadorConsultas.model.Horario;
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.net.URL;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de consultas para serem atendidas pelo médico. 
 * Permite efetuar a comunicação entre a view da tela de consultas para serem atendidas pelo médico e o model.
 *
 * @author Igor
 */
public class TelaConsultasMedicoController implements Initializable {

    @FXML
    private DatePicker dataConsulta;
    @FXML
    private TableView<Horario> tabelaConsultas;
    @FXML
    private TableColumn<Horario, String> colunaHorario;
    @FXML
    private TableColumn<Horario, String> colunaPaciente;
    @FXML
    private Button btnVoltar;
    
    private ObservableList<Horario> observHorariosConsultas;
    private Medico medico;
    
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
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("identificacao"));
        colunaPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
    }    
    
    /**
     * Controla o evento de ação no DataPicker. Ao selecionar uma data são carregados
     * os horários de consultas a serem atendidas pelo médico naquela data.
     * 
     * @param event o evento.
     */
    @FXML
    private void actionDataConsulta(ActionEvent event) {
        try{
            Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            String dataString = data.toString();
            carregarHorariosConsultas(dataString);
        }catch (java.lang.NullPointerException ex){
            carregarHorariosConsultas("");
        }
    }

    /**
     * Controla o evento de click no botão de abrir consulta. Ao clicar no botão, se algum horário
     * estiver selecionado, é aberta a consulta (prontuário) do horário.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAbrirConsulta(ActionEvent event) {
        Consulta consultaHorario = obterConsultaHorarioSelecionado(); //Obtém a consulta do horário selecionado
        Horario horario = obterHorarioSelecionado();
        if (consultaHorario != null) {
            String enderecoJanela = "/br/uefs/ecomp/gerenciadorConsultas/view/telaProntuario.fxml";
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("consulta")) {
                        return consultaHorario;
                    }else if (key.contains("horario")){
                        return horario;
                    }else if (key.contains("editavel")){
                        return "sim";
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
                    return new HashSet<>(Arrays.asList("consulta", "horario", "editavel"));
                }
            };
            try {
                criadorJanelas.abrirNovaJanela(enderecoJanela, "Prontuário ".concat(horario.getNomePaciente()), rb, '2');  //Abre uma nova janela
                Date data = Date.from(dataConsulta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                String dataString = data.toString();
                carregarHorariosConsultas(dataString);
            } catch (java.io.IOException ex) {
                Logger.getLogger(TelaHistoricoConsultasPacienteController.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }  
    }
    
    /**
     * Controla o evento de click no botão voltar. Ao clicar no botão a janela é fechada
     * e retona para a janela anterior.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickVoltar(ActionEvent event) {
        fecharJanela();  
    }
    
    
    //****** Métodos complementares ******
    /**
     * O método é responsável por carregar os horários das consultas da data selecionada.
     * 
     * @param data a data selecionada. Se for passado uma String vazia ("") não é carregado nenhum horário,
     * caso seja passada um data, carrega os horários da data, caso existam.
     */
    private void carregarHorariosConsultas(String data){
        if (data.equals("")){
            observHorariosConsultas = null;
            tabelaConsultas.setItems(observHorariosConsultas);
        }else{
            observHorariosConsultas = FXCollections.observableList(medico.getAgenda().getConsultasMarcadas().get(data));
            observHorariosConsultas.sort((Horario o1, Horario o2) -> {  //Ordena os horários
                int horario1 = Integer.parseInt(o1.getIdentificacao().replace(":", ""));
                int horario2 = Integer.parseInt(o2.getIdentificacao().replace(":", ""));
                return horario1 - horario2;
            });
            tabelaConsultas.setItems(observHorariosConsultas);
        }
    }
    
    /**
     * O método é responsável por obter o horário selecionado na tabela.
     * 
     * @return um objeto do tipo Horario com o horário selecionado. Se não houver horário
     * selecionado retorna null.
     */
    private Horario obterHorarioSelecionado(){
        if(!tabelaConsultas.getSelectionModel().isEmpty()){
            return tabelaConsultas.getSelectionModel().getSelectedItem();
        }else{
            return null;
        }
    }
    
    /**
     * O método é responável por obter a consulta do horário selecionado.
     * 
     * @return um objeto do tipo Consulta representando a consulta do horário selecionado. Se não
     * houver horário selecionado retorna null.
     */
    private Consulta obterConsultaHorarioSelecionado(){
        if(!tabelaConsultas.getSelectionModel().isEmpty()){
            return tabelaConsultas.getSelectionModel().getSelectedItem().getConsulta();
        }else{
            return null;
        }
    }
    
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }


   
}
