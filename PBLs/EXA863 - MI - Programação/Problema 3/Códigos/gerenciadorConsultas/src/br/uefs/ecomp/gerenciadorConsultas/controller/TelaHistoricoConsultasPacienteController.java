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
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de histórico de consultas do paciente. 
 * Permite efetuar a comunicação entre a view da tela de  histórico de consultas do paciente e o model.
 *
 * @author Igor
 */
public class TelaHistoricoConsultasPacienteController implements Initializable {

    @FXML
    private Button btnAbrirConsulta;
    @FXML
    private Button btnVoltar;
    @FXML
    private ListView<Consulta> listConsultas;

    private ObservableList<Consulta> observListaConsultas;
    
    private String origem;
    private Paciente paciente;
    
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
        origem = (String) rb.getObject("origem");  //Obtém qual tipo de usuário chamou a opção de visualizar consultas do paciente
        paciente = (Paciente) rb.getObject("paciente"); //Obtém o paciente selecionado
        if (origem.contains("recepcionista")){  //Se o recepcionista que solicitou abrir a página de histórico de consultas
            btnAbrirConsulta.setDisable(true); //Desabilita o botão de abrir consulta
            btnAbrirConsulta.setVisible(false);  //Esconde o botão de abrir consulta
            carregarConsultasRecepcionista();  //Carrega as consultas efetuadas e não efetuadas
        }else{
            carregarConsultasMedico();  //Carrega somente as consultas já efetuadas
        } 
    }    

    /**
     * Controla o evento de click no botão de abrir. Esse botão só está disponível se o médico
     * foi o responsável por aber a tela de histórico de consultas do paciente. 
     * Ao clicar no botão, se houver selecionada alguma consulta, uma nova janela é aberta 
     * exibindo o prontuário da consulta do paciente.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickAbrir(ActionEvent event) {
        if (!listConsultas.getSelectionModel().isEmpty()) {
            Consulta consulta = listConsultas.getSelectionModel().getSelectedItem();
            ResourceBundle rb = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.contains("prontuario")) {
                        return consulta.getProntuario();
                    }else if (key.contains("editavel")){  //Indica se o prontuário pode ser editado (prontuários acessados pelo histórico não devem ser editáveis)
                        return "nao";
                    }else if (key.contains("consulta")){  //Indica se o prontuário pode ser editado (prontuários acessados pelo histórico não devem ser editáveis)
                        return consulta;
                    }
                    else{
                        return null;
                    }
                }

                @Override
                public Enumeration<String> getKeys() {
                    return Collections.enumeration(handleKeySet());
                }

                @Override
                protected Set<String> handleKeySet() {
                    return new HashSet<>(Arrays.asList("prontuario", "editavel", "consulta"));
                }
            };
            String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaProntuario.fxml";
            String titulo = "Prontuário - ".concat(paciente.getNome());
            try {
                criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
            } catch (IOException ex) { //Erro ao abrir a janela
                Logger.getLogger(TelaHistoricoConsultasPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }

    /**
     * Controla o evento de click no botão voltar. Ao clicar no botão a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickVoltar(ActionEvent event) {
        fecharJanela();
    }
    
    
    //******* Métodos complementares *******
    /**
     * O método é responsável por carregar as consultas do paciente se o recepcionista
     * quem abriu a tela de histórico de consultas do paciente. São exibidas as consultas
     * tanto marcadas quanto efetuadas.
     */
    private void carregarConsultasRecepcionista(){
        observListaConsultas = FXCollections.observableList(paciente.getConsultas());
        listConsultas.setItems(observListaConsultas);
    }
    
    /**
     * O método é responsável por carregar as consultas do paciente se o médico
     * quem abriu a tela de histórico de consultas do paciente. São exibidas as consultas efetuadas.
     */
    private void carregarConsultasMedico(){
        LinkedList<Consulta> consultas = new LinkedList();
        consultas.addAll(paciente.getConsultas());
        paciente.getConsultas().stream().filter(consulta -> (!consulta.isEfetuada())).forEachOrdered(consulta -> {
            consultas.remove(consulta);
        });
        observListaConsultas = FXCollections.observableList(consultas);
        listConsultas.setItems(observListaConsultas); 
    }
    
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }
}
