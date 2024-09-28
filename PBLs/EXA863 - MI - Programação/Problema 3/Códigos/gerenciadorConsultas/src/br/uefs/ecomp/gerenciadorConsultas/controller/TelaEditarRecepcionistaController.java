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
 * Controlador para a tela de editar recepcionista. 
 * Permite efetuar a comunicação entre a view da tela de editar recepcionista e o model.
 *
 * @author Igor
 */
public class TelaEditarRecepcionistaController implements Initializable {

    @FXML
    private TextField nomeRecepcionista;
    @FXML
    private TextField cpfRecepcionista;
    @FXML
    private PasswordField senhaRecepcionista;
    @FXML
    private PasswordField confirmacaoSenhaRecep;
    @FXML
    private Button btnConcluido;
    
    private Recepcionista recepcionista;  //Recepcionista que chamou a opção
    private Recepcionista recepcionistaEditar;  //Recepcionista a ser editado
    
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
        recepcionistaEditar = (Recepcionista) rb.getObject("recepcionistaEditar");
        nomeRecepcionista.setText(recepcionistaEditar.getNome());
        cpfRecepcionista.setText(recepcionistaEditar.getCpf());
        senhaRecepcionista.setText(recepcionistaEditar.getSenha());
    }    

    /**
     * Controla o evento de click no botão de cancelar. Ao clicar no botão
     * todas as operações são canceladas e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }

    /**
     * Controla o evento de click no botão concluído. Ao clicar no botão, se todos os campos
     * estiverem preenchidos corretamente, as alterações são gravadas e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        if(!(nomeRecepcionista.getText().isBlank() || cpfRecepcionista.getText().isBlank() || senhaRecepcionista.getText().isBlank())){
            String nome = nomeRecepcionista.getText().toUpperCase();
            String cpf = cpfRecepcionista.getText();
            String senha = senhaRecepcionista.getText();
            String confirmacaoSenha = confirmacaoSenhaRecep.getText();
            if(!senha.equals(confirmacaoSenha)){
                String titulo = "As senha informada não são iguais.";
                String mensagem = "A senha informada para confirmação não é igual a senha escolhida.";
                criadorJanelas.exibirMensagemErro(titulo, mensagem);
            }else{
                try{
                    recepcionista.editarRecepcionista(recepcionistaEditar, nome, cpf, senha);
                    fecharJanela();
                }catch (CpfCadastradoException ex){
                    String titulo = "Erro ao editar o recepcionista";
                    criadorJanelas.exibirMensagemErro(titulo, ex.getMessage());
                }  
            }
            
        }
    }
    
    //****** Métodos complementares *******
    /**
     * O método é responsável por fechar a janela.
     */
    private void fecharJanela() {
        Stage stage = (Stage) btnConcluido.getScene().getWindow();
        stage.close();
    }

}
