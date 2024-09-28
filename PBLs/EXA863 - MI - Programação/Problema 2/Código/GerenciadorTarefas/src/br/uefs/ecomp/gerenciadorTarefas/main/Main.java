/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 19/10/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.gerenciadorTarefas.main;  //Pacote da classe

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal do sistema, responsável por inicializar a aplicação.
 * 
 * @author ifs54
 */
public class Main extends Application {
    
    private static Scene sceneProjetos;  //A tela inicial do sistema (default) deve ser a tela de exibe os projetos.
    
    private static Stage stage;
    
    
    /**
     * Método de inicialização do gerenciador de tarefas.
     * @param primaryStage o Stage principal.
     * @throws IOException exceção lançada caso ocorra algum erro ao carregar os arquivos 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
       stage = primaryStage;
       primaryStage.setTitle("Gerenciador de tarefas - Projetos");
       
       Parent rootProjetos = FXMLLoader.load(getClass().getResource("/br/uefs/ecomp/gerenciadorTarefas/view/PaginaInicial.fxml"));  //Carrega o fxml da tela de projetos
       sceneProjetos = new Scene(rootProjetos);  //Cria a Scene da tela de projetos (tela default).
       
       primaryStage.setScene(sceneProjetos);  //Seta a scene do stage inicial como a tela de projetos
       primaryStage.show();  //Exibe a janela.
       
    }
    
    /**
     * O método altera a tela principal de exibição de acordo com a scene passada por parâmetro.
     * 
     * @param titulo uma String para o novo título da janela principal 
     * @param scene um objeto do tipo Scene que contém todo o conteúdo a ser exibido na janela.
     */
    public static void setTelaPrincipal(String titulo, Scene scene){
        stage.setScene(scene);
        stage.setTitle(titulo);
    }
    
    /**
     * Retorno a tela principal do programa para a tela de projetos a qual é a 
     * tela predefinida para o início da aplicação (tela default). 
     */
    public static void defaultTelaPrincipal(){
        stage.setScene(sceneProjetos);
        stage.setTitle("Gerenciador de taferas - Projetos");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //Sistema sistema = new Sistema();
    }
    
}
