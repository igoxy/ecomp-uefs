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

import br.uefs.ecomp.gerenciadorConsultas.model.Consulta;
import br.uefs.ecomp.gerenciadorConsultas.model.Horario;
import br.uefs.ecomp.gerenciadorConsultas.model.Prontuario;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de prontuário. 
 * Permite efetuar a comunicação entre a view da tela de prontuário e o model.
 *
 * @author Igor
 */
public class TelaProntuarioController implements Initializable {

    @FXML
    private Button btnSairConsulta;
    @FXML
    private TextField textIdentificaoPaciente;
    @FXML
    private TextArea textAnamnese;
    @FXML
    private TextArea textExameFisico;
    @FXML
    private TextArea textHipoteseDiagnostica;
    @FXML
    private TextArea textDiagnosticosDefinitivos;
    @FXML
    private TextArea textTratamentosEfetuados;
    @FXML
    private Button btnFinalizarConsulta;
    @FXML
    private Button btnHistorico;
    
    private Consulta consulta;  //A consulta
    private Horario horario;  //O horário que a consulta está contida
    private String editavel = "sim";  //Indica se os campos podem ser editados - inicializa com sim
    
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
        consulta = (Consulta) rb.getObject("consulta");  //Obtém a consulta
        editavel = (String) rb.getObject("editavel"); 
        if (editavel.contains("nao")){  //Verifica os campos do prontuário podem ser editaveis.
            //No caso de visualização por meio do histórico de consultas, os prontuários não devem ser editados.
            setNaoEditavel();  //Desabilita a edição dos campos e da opção de finalizar consulta
            carregarProntuario(); //Carrega as informações do prontuário
            horario = null;  //Não é necessário o horário se o prontuário não é editável
        }else{
            horario = (Horario) rb.getObject("horario"); //Obtém o horário da consulta
        }   
    }    

    /**
     * Controla o evento de click no botão de sair consulta. Ao clicar no botão
     * a tela de prontuário é fechada e nenhuma alteração é salva.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickSairConsulta(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão receita. Ao clicar no botão é aberta uma nova
     * janela com a receita do paciente para aquela consulta.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickReceita(ActionEvent event) {
        String telaFXML = "/br/uefs/ecomp/gerenciadorConsultas/view/telaReceita.fxml";
        String titulo = "Receita";
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("consulta")) {
                    return consulta;
                }else if (key.contains("editavel")){
                    return editavel;
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
                return new HashSet<>(Arrays.asList("consulta", "editavel"));
            }
        };
        try {
            criadorJanelas.abrirNovaJanela(telaFXML, titulo, rb, '1');
        } catch (IOException ex) {
            Logger.getLogger(TelaProntuarioController.class.getName()).log(Level.SEVERE, null, ex); //Erro ao abrir a tela
        }
    }

    /**
     * Controla o evento de click no botão finalizar consulta. Ao clicar no botão
     * o prontuário é salvo, a consulta é transferida para as consultas atendidas pelo
     * médico e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickFinalizarConsulta(ActionEvent event) {
        String data = consulta.getData(); //Inicializa a variável
        consulta.setEfetuada(true);
        consulta.getMedico().getAgenda().setHorarioConsultasRealizadas(data, horario); //Move o horário da consulta para horários de consultas atendidas
        String identificao = textIdentificaoPaciente.getText();  //Obtém a identificação do paciente
        String anamnese = textAnamnese.getText();  //Obtém a anamnese
        String exameFisico = textExameFisico.getText(); //Obtém o exame físico
        String hipoteseDiagnostica = textHipoteseDiagnostica.getText(); //Obtém a hipotése diagnostica
        String diagnosticosDefinitivos = textDiagnosticosDefinitivos.getText(); //Obtém o diagnostico definitivo
        String tratamentosEfetuados = textTratamentosEfetuados.getText(); //Obtém os tratamentos efetuados
        Prontuario prontuario = new Prontuario(identificao, anamnese, exameFisico, hipoteseDiagnostica, diagnosticosDefinitivos, tratamentosEfetuados);
        prontuario.setReceita(consulta.getProntuario().getReceita());  //Obtém a receita presente no prontuário do paciente e adicona ao novo prontuário com as informações
        consulta.setProntuario(prontuario);  //Seta o prontuário do paciente - fornece um prontuário com todas as informações
        fecharJanela(); //Fecha a janela
    }
    
    /**
     * Controla o evento de click no botão histórico paciente. Ao clicar no botão
     * uma nova janela é aberta exibindo todas as consultas já realizadas do paciente.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickHistoricoPaciente(ActionEvent event) {
        String telaFXML = "/br/uefs/ecomp/gerenciadorConsultas/view/telaHistoricoConsultasPaciente.fxml";
        String titulo = "Histórico de consultas - ".concat(consulta.getPaciente().getNome());
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("paciente")) {
                    return consulta.getPaciente();
                } else if (key.contains("origem")) {
                    return "medico";
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
                return new HashSet<>(Arrays.asList("paciente", "origem"));
            }
        };
        try {
            criadorJanelas.abrirNovaJanela(telaFXML, titulo, rb, '1');
        } catch (IOException ex) {
            Logger.getLogger(TelaProntuarioController.class.getName()).log(Level.SEVERE, null, ex); //Erro ao abrir a tela
        }
    }
    
    //******* Métodos complementares *******
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnSairConsulta.getScene().getWindow();  //Obtém a janela
        stage.close();  //Fecha a janela
    }
    
    /**
     * O método é responsável por definir as opções do prontuário como não editável.
     * Deste modo, nenhuma informação do prontuário pode ser modificada. Isso deve 
     * ocorrer quando o prontuário for visualizado pelo médico por meio do histórico de
     * consultas do paciente.
     * 
     */
    private void setNaoEditavel() {
        //Suspende a edição dos campos do prontuário
        textIdentificaoPaciente.setEditable(false);
        textAnamnese.setEditable(false);
        textExameFisico.setEditable(false);
        textHipoteseDiagnostica.setEditable(false);
        textDiagnosticosDefinitivos.setEditable(false);
        textTratamentosEfetuados.setEditable(false);
        
        //Desativa o botão de finalizar consulta
        btnFinalizarConsulta.setDisable(true);
        btnFinalizarConsulta.setVisible(false);
        
        //Desativa o botão de visualizar histórico de consultas
        btnHistorico.setDisable(true);  //Desabilita
    }

    /**
     * O método é reponsável por carregar as informações do prontuário.
     */
    private void carregarProntuario() {
        textIdentificaoPaciente.setText(consulta.getProntuario().getIdenficicacoPaciente());
        textAnamnese.setText(consulta.getProntuario().getAnamnese());
        textExameFisico.setText(consulta.getProntuario().getExameFisico());
        textHipoteseDiagnostica.setText(consulta.getProntuario().getHipoteseDiagnostica());
        textDiagnosticosDefinitivos.setText(consulta.getProntuario().getDiagnosticoDefinitivo());
        textTratamentosEfetuados.setText(consulta.getProntuario().getTratamentosEfetuados());
    }
}
