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

import br.uefs.ecomp.gerenciadorConsultas.exceptions.CpfCadastradoException;
import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de cadastro de recepcionista. 
 * Permite efetuar a comunicação entre a view da tela de cadastro de recepcionista e o model.
 * 
 * @author Igor
 */
public class TelaCadastroRecepcionistaController implements Initializable {

    @FXML
    private TextField nomeRecepcionista;
    @FXML
    private TextField cpfRecepcionista;
    @FXML
    private PasswordField senhaRecepcionista;
    @FXML
    private PasswordField confirmacaoSenhaRecep;
    @FXML
    private Button btnCancelar;
    
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
    }    
    
    /**
     * Controla o evento de click no botão cancelar. Ao clicar no botão toda operação
     * é cancelada e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão de concluído. Ao clicar no botão, se todos
     * os campos estiverem preenchidos corretamente, o novo recepcionista é cadastrado no sistema
     * e a janela é fechda. Caso contrário, uma mensagem de erro é exibida.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if(!(nomeRecepcionista.getText().isBlank() || cpfRecepcionista.getText().isBlank() || senhaRecepcionista.getText().isBlank() || confirmacaoSenhaRecep.getText().isBlank())){
            String nome = nomeRecepcionista.getText().toUpperCase();
            String cpf = cpfRecepcionista.getText();
            String senha = senhaRecepcionista.getText();
            String confirmacaoSenha = confirmacaoSenhaRecep.getText();
            if(cpf.matches("\\d+")){
                if(!senha.equals(confirmacaoSenha)){
                    String titulo = "As senha informada não são iguais.";
                    String mensagem = "A senha informada para confirmação não é igual a senha escolhida.";
                    criadorJanelas.exibirMensagemErro(titulo, mensagem);
                }else{
                    try {
                        recepcionista.cadastrarRecepcionista(nome, cpf, senha);
                        fecharJanela();
                    } catch (CpfCadastradoException ex) {
                        String titulo = "Erro ao cadastrar o recepcionista";
                        criadorJanelas.exibirMensagemErro(titulo, ex.getMessage());
                    }  
                }
            }else{
                String titulo = "O CPF deve ter somente números";
                String mensagem = "O CPF informado não é válido. O CPF informado deve conter somente números";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }
        }
    }
    
    //******* Métodos complementares ********* 
    /**
     * O método é responsável por fechar a janela de cadastro de paciente.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();  //Obtém a janela
        stage.close(); // Fecha a janela
    }

}
