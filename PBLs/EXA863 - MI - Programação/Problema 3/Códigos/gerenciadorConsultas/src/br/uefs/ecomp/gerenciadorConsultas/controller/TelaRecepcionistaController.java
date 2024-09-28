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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class
 * Controlador para a tela do recepcionista. 
 * Permite efetuar a comunicação entre a view da tela recepcionista e o model.
 *
 * @author Igor
 */
public class TelaRecepcionistaController implements Initializable {
    
    private Recepcionista recepcionista;

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
    }    

    /**
     * Controla o evento de click no botão pacientes. Ao clicar no botão a tela exibida
     * passa a ser a tela de gerenciamento de pacientes.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickBtnPacientes(ActionEvent event) {
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("origem")) {
                    return "recepcionista";
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
                return new HashSet<>(Arrays.asList("origem", "recepcionista"));
            }
        };
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorPaciente.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal("Pacientes", scene);
        } catch (IOException ex) {
            Logger.getLogger(TelaRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Controla o evento de click no botão medicos. Ao clicar no botão a tela exibida
     * passa a ser a tela de gerenciamento de médicos. 
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickBtnMedicos(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorMedico.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal("Médicos", scene);
        } catch (IOException ex) {
            Logger.getLogger(TelaRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    /**
     * Controla o evento de click no botão especialidades. Ao clicar no botão a tela exibida
     * passa a ser a tela de gerenciamento de especialidades. 
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickBtnEspecialidades(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorEspecialidade.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal("Especialidades", scene);
        } catch (IOException ex) {
            Logger.getLogger(TelaRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Controla o evento de click no botão recepcionista. Ao clicar no botão a tela exibida
     * passa a ser a tela de gerenciamento de recepcionista. 
     * 
     * @param event o evento. 
     */
    @FXML
    private void clickBtnRecepcionistas(ActionEvent event) {
        try {
            ResourceBundle rb = getRecursoRecepcionista();
            Parent root = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaGerenciadorRecepcionista.fxml"), rb);
            Scene scene = new Scene(root);
            Main.setTelaPrincipal("Recepcionistas", scene);
        } catch (IOException ex) {
            Logger.getLogger(TelaRecepcionistaController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    /**
     * Controla o evento de click no botão sair. Ao clicar no botão é feito o logout
     * do recepcionista e a tela exibida passada a ser a tela principal do sistema.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickSair(ActionEvent event) {
        Main.defaultTelaPrincipal();

    }
    
    //****** Métodos complementares *******
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
