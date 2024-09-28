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

/**
 * FXML Controller class
 * Controlador para a tela do médico. 
 * Permite efetuar a comunicação entre a view da tela do médico e o model.
 *
 * @author Igor
 */
public class TelaMedicoController implements Initializable {

    
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
    }    

    /**
     * Controla o evento de click no botão de consultas pendentes. Ao clicar no botão
     * uma nova janela é aberta. Essa nova janela é reponsável por exibir os horários e 
     * as consultas não atendidas de uma data.
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickConsultasMarcadas(ActionEvent event) {
        String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaConsultasMedico.fxml";
        String titulo = "Consultas Pendentes";
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("medico")) {
                    return medico;
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
        try {
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '1');
        } catch (IOException ex) {
            Logger.getLogger(TelaMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Controla o evento de click no botão sair. Ao clicar no botão é feito o logout 
     * do médico. A tela do médico é fechada e retorna para a tela principal do sistema.
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickSair(ActionEvent event) {
        Main.defaultTelaPrincipal();
    }

    /**
     * Controla o evento de click no botão consultas atendidas. Ao clicar no botão 
     * uma nova janela é aberta. Essa nova janela é reponsável por exibir os horários e 
     * as consultas atendidas pelo médico de uma data selecionada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConsultasAtendidas(ActionEvent event) {
        String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaConsultasAtendidasMedico.fxml";
        String titulo = "Consultas Atendidas";

        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("medico")) {
                    return medico;
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
        try {
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '1');
        } catch (IOException ex) {
            Logger.getLogger(TelaMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Controla o evento de click no botão de histórico pacientes. Ao clicar nesse botão
     * uma nova tela é aberta. Essa nova tela exibe todos os pacientes do sistema e o médico
     * pode selecionar o desejado para visualizar as consultas.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickHistoricoPacientes(ActionEvent event) {
        String fxml = "/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorPaciente.fxml";  //Abre a tela que lista os pacientes
        String titulo = "Pacientes";
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("origem")) {
                    return "medico";  //Indica que o médico quem abriu a janela de pacientes
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
                return new HashSet<>(Arrays.asList("origem"));
            }
        };
        try {
            criadorJanelas.abrirNovaJanela(fxml, titulo, rb, '2');
        } catch (java.io.IOException ex) {
            //Erro ao abrir a janela
        }
    }
}
