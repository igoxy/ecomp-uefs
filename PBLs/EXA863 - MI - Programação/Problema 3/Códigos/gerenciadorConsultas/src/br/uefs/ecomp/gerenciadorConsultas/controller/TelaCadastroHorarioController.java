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
package br.uefs.ecomp.gerenciadorConsultas.controller;  //O pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.exceptions.ConsultaMarcadaHorarioException;
import br.uefs.ecomp.gerenciadorConsultas.exceptions.HorarioCadastradoException;
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.util.CriadorJanelas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controlador para a tela de cadastro de horários. 
 * Permite efetuar a comunicação entre a view da tela de cadastro de horários e o model.
 *
 * @author Igor
 */
public class TelaCadastroHorarioController implements Initializable {

    @FXML
    private TextField textHoras;
    @FXML
    private TextField textMinutos;
    @FXML
    private Button btnConcluido;
    
    private Medico medico;
    private String data;
    private String horarioNovo;
    
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
        data = (String) rb.getObject("data");
    } 
    
    /**
     * Controla o evento de click no botão de cancelar. Ao clicar no botão, todas
     * operações são canceladas e a janela é fechada.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickCancelar(ActionEvent event) {
        fecharJanela();
    }
    
    /**
     * Controla o evento de click no botão concluído. Ao clicar no botão, verifica se todos campos
     * foram preenchidos corretamente e se não há um horário igual cadastrado para a data. Se todos
     * os dados estiverem preenchidos corretamente e o horário já não estiver cadastrado, um novo 
     * horário é criado, caso contrário é exibida uma mensagem de erro.
     * 
     * @param event o evento.
     */
    @FXML
    private void clickConcluido(ActionEvent event) {
        try {
            if (textHoras.getText().length() == 2 && textMinutos.getText().length() == 2) { //Verifica se o números colocados são de dois dígitos
                int horas = Integer.parseInt(textHoras.getText());  //Pega os dois primeiros valores da caixa de texto
                int minutos = Integer.parseInt(textMinutos.getText());  //Pega os dois primeiros valores da caixa de texto
                if (23 >= horas && 59 >= minutos) {  //Verifica se a hora está dentro do intervalo de tempo de um dia 
                    try {
                        horarioNovo = String.format("%02d", horas) + ":" + String.format("%02d", minutos);
                        medico.getAgenda().cadastrarHorario(data, horarioNovo);
                    } catch (HorarioCadastradoException ex) {
                        criadorJanelas.exibirMensagemErro("Erro ao cadastrar o horário", ex.getMessage());
                    } catch (ConsultaMarcadaHorarioException ex) {
                        criadorJanelas.exibirMensagemErro("Erro ao cadastrar o horário", ex.getMessage());
                    }
                } else {
                    criadorJanelas.exibirMensagemErro("Erro ao cadastrar o horário", "Insira somente números de dois digítos para cada campo.\n"
                            + "Nas horas coloque números de 00 até 23 e\nnos minutos de 00 até 59.");
                }
            } else {
                criadorJanelas.exibirMensagemErro("Erro ao cadastrar o horário", "Insira somente números de dois digítos para cada campo.\n"
                        + "Nas horas coloque números de 00 até 23 e\nnos minutos de 00 até 59.");
            } 
        } catch (NumberFormatException ex) {
            //Erro caso não seja números os primeiros digitos dos campos de horas e minutos
            criadorJanelas.exibirMensagemErro("Erro ao cadastrar o horário", "O horário inserido não segue o formato esperado. Insira somente números.\n"
                    + "Nas horas coloque um números de 00 até 23.\n Nos minutos coloque um número de 00 até 59.");
        }
        fecharJanela();
    }
     
    //****** Métodos complementares *******
    /**
     * O método é responsável por fechar a janela de cadastro de horários.
     */
    private void fecharJanela(){
        Stage stage = (Stage) btnConcluido.getScene().getWindow();  //Obtém a janela
        stage.close();  //Fecha a janelas
    }
}
