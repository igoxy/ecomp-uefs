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

package br.uefs.ecomp.gerenciadorConsultas.main;  //Pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import br.uefs.ecomp.gerenciadorConsultas.model.Sistema;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal do sistema, responsável por inicializar a aplicação.
 * 
 * @author Igor
 */
public class Main extends Application {

    private static Scene scenePrimaria;  //A tela inicial do sistema (default) deve ser a tela de autoatendimento.

    private static Stage stage;

    /**
     * Método de inicialização do sistema de gerenciamento de consultas.
     * @param primaryStage o Stage principal.
     * @throws IOException exceção lançada caso ocorra algum erro ao carregar os arquivos 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        primaryStage.setTitle("Gerenciador de atendimento");

        Parent rootTelaInicial = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorConsultas/view/telaInicial.fxml"));  //Carrega o fxml da tela inicial
        scenePrimaria = new Scene(rootTelaInicial);

        primaryStage.setScene(scenePrimaria);
        primaryStage.show();  //Exibir a janela
    }

    /**
     * O método altera a tela principal de exibição de acordo com a scene
     * passada por parâmetro.
     *
     * @param titulo uma String para o novo título da janela principal
     * @param scene um objeto do tipo Scene que contém todo o conteúdo a ser
     * exibido na janela.
     */
    public static void setTelaPrincipal(String titulo, Scene scene) {
        stage.setScene(scene);
        stage.setTitle(titulo);
    }

    /**
     * Retorno a tela principal do programa para a tela de projetos a qual é a
     * tela predefinida para o início da aplicação (tela default).
     */
    public static void defaultTelaPrincipal() {
        stage.setScene(scenePrimaria);
        stage.setTitle("Gerenciador de atendimento");
    }

    /**
     * Método main.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Cadastra um(a) recepcionista padrão
        Recepcionista recep = new Recepcionista("ADMIN", "123", "admin");
        Sistema.getRecepcionistas().add(recep);
        //Cadastro recepcionista - Fim
        
        launch(args);
    }
}
