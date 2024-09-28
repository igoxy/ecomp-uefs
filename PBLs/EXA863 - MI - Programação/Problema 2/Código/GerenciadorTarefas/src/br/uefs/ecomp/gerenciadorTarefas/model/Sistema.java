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
package br.uefs.ecomp.gerenciadorTarefas.model;  //Pacote da classe

import br.uefs.ecomp.gerenciadorTarefas.util.LinkedListSistema;

/**
 * A classe implementa os comportamentos do <b>sistema</b>. Deste modo, apresenta
 * os métedos que compõem o sistema. Além disso, detém o atributo que forma o 
 * sistema, sendo ele:
 * <ul>
 *  <li>Lista de projetos: armazena os projetos cadastrados no gerenciador de tarefas.</li>
 * </ul>
 * 
 * @see br.uefs.ecomp.gerenciadorTarefas.model.Projeto
 * @see br.uefs.ecomp.gerenciadorTarefas.util.LinkedListSistema
 * @author Igor
 */
public class Sistema {
    
    private static LinkedListSistema projetos = new LinkedListSistema();  //Lista de projetos do sistema. O sistema deve apresentar somente uma lista de projetos, portanto, deve ser static.
    
    /**
     * Construtor da classe Sistema. Construtor vazio. 
     */
    public Sistema(){}
    
    //Getters e Setters
    /**
     * Obtém a lista de projetos do sistema.
     * @return uma LinkedListSistema do tipo Projeto com todos os
     * projetos cadastrados no sistema.
     */
    public static LinkedListSistema getProjetos() {
        return projetos;
    }

    /**
     * Altera a lista de projetos do sistema. Para isso, deve ser fornecida uma lista
     * de projetos.
     * @param Projetos uma nova lista de projetos do tipo LinkedListSistema.
     */
    public static void setProjetos(LinkedListSistema Projetos) {
        Sistema.projetos = Projetos;
    }
    //Fim Getters e Setters
}
