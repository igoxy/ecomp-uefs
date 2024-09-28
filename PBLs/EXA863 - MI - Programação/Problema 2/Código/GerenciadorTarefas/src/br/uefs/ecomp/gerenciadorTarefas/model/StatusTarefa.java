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

/**
 * Essa classe implementa um <code>enum</code> para indicar o <b>Status das tarefas</b>.
 * Deste modo, a classe apresenta as constantes responsáveis por indicar o Status
 * de uma tarefa.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>StatusTarefa status = StatusTarefa.CONCLUIDO;</code></p>
 * 
 * @author Igor
 */
public enum StatusTarefa {

    /**
     * Status pendente
     */
    PENDENTE("Pendente"),

    /**
     *  Status em execução
     */
    EM_EXECUCAO("Em execução"),

    /**
     * Status concluída
     */
    CONCLUIDA("Concluída");
    
    private String nomeStatus;
    
    /**
     * Atribui o nome ao <code>enum</code>.
     * @param nome o nome do <code>enum</code>.
     */
    private StatusTarefa(String nome){
        this.nomeStatus = nome;
    }
    /**
     * Obtém o nome que representa o <code>enum</code>.
     * @return retorna uma String com o nome do <code>enum</code>.
     */
    @Override
    public String toString(){
        return nomeStatus;
    }
}