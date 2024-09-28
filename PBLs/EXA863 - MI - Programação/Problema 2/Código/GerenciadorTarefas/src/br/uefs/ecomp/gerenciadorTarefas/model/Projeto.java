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

import java.util.LinkedList;

/**
 * A classe implementa os comportamentos de um <b>Projeto</b>. A classe contém os atributos de
 * um projeto. Esses atributos são: o nome do projeto, uma lista para armazenar as tarefas
 * pendentes, uma lista para armazenar as tarefas em execução e uma lista para armazenar
 * as tarefas concluídas.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Projeto projeto = new Projeto("nome");</code></p>
 * 
 * @author Igor
 * @see java.util.LinkedList
 * 
 */
public class Projeto {
    private String nome;  //Nome do projeto
    private LinkedList<Tarefa> tarefasPendentes;  //Lista de tarefasPendentes pendentes
    private LinkedList<Tarefa> tarefasExecucao;  //Lista de tarefasPendentes em execução
    private LinkedList<Tarefa> tarefasConcluidas; //Lista de tarefasPendentes concluídas
    
    /**
     * Construtor da classe Projeto. Inicaliza o atributo nome com a informação
     * passada por parâmetro no momento da instância do objeto do tipo projeto.
     * Além disso, inicializa as listas de tarefas do projeto.
     * 
     * @param nome o nome para o Projeto.
     */
    public Projeto(String nome){
        this.nome = nome;
        this.tarefasPendentes = new LinkedList<>();
        this.tarefasExecucao = new LinkedList<>();
        this.tarefasConcluidas = new LinkedList<>();
    }
    
    //Getters e Setters
    
    /**
     * Obtém o nome do projeto.
     * @return uma String com o nome do projeto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome do projeto. Para isso, um novo nome deve ser fornecido.
     * @param nome um novo nome para o projeto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a lista de tarefas pendentes do projeto.
     * @return uma <code>LinkedList&lt;Tarefa&gt;</code> com todas as tarefas pendentes.
     */
    public LinkedList<Tarefa> getTarefasPendentes() {
        return tarefasPendentes;
    }

    /**
     * Altera a lista de tarefas pendentes do projeto. Para isso, uma nova lista de tarefas deve ser
     * fornecida.
     * @param tarefasPendentes uma nova lista de tarefas pendentes do tipo <code>LinkedList&lt;Tarefa&gt;</code>.
     */
    public void setTarefasPendentes(LinkedList<Tarefa> tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    /**
     * Obtém a lista de tarefas em execução do projeto.
     * @return uma <code>LinkedList&lt;Tarefa&gt;</code> com todas as tarefas em execução.
     */
    public LinkedList<Tarefa> getTarefasExecucao() {
        return tarefasExecucao;
    }

    /**
     * Altera a lista de tarefas em execução do projeto. Para isso, uma nova lista de tarefas deve ser
     * fornecida.
     * @param tarefasExecucao uma nova lista de tarefas em execução do tipo <code>LinkedList&lt;Tarefa&gt;</code>.
     */
    public void setTarefasExecucao(LinkedList<Tarefa> tarefasExecucao) {
        this.tarefasExecucao = tarefasExecucao;
    }

    /**
     * Obtém a lista de tarefas concluídas do projeto.
     * @return uma <code>LinkedList&lt;Tarefa&gt;</code> com todas as tarefas concluídas.
     */
    public LinkedList<Tarefa> getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    /**
     * Altera a lista de tarefas concluídas do projeto. Para isso, uma nova lista de tarefas deve ser
     * fornecida.
     * @param tarefasConcluidas uma nova lista de tarefas concluídas do tipo <code>LinkedList&lt;Tarefa&gt;</code>.
     */
    public void setTarefasConcluidas(LinkedList<Tarefa> tarefasConcluidas) {
        this.tarefasConcluidas = tarefasConcluidas;
    }
    //Fim Getters e Setters
    
    //Funcionalidades
    /**
     * O método é responsável por altarar o status de uma tarefa passada por parâmetro,
     * para um novo status, também fornecido por parâmetro. Além disso, o método é responsável
     * por mover a tarefa para a lista correspondente ao seu novo status.
     * 
     * @param tarefa a tarefa a ser movida de status (quadro).
     * @param novoStatus um <code>Enum</code> representando o novo status da tarefa.
     */
    public void trocarTarefaQuadro(Tarefa tarefa, StatusTarefa novoStatus){
        switch(novoStatus){
            case PENDENTE -> {
                tarefa.setStatus(StatusTarefa.PENDENTE);  //Muda a indicação de Status da tarefa para PENDENTE
                if(tarefasExecucao.contains(tarefa)){
                    tarefasExecucao.remove(tarefa); //Remove a tarefa da lista atual
                    tarefasPendentes.add(tarefa); //Insere a tarefa na nova lista  
                }else if(tarefasConcluidas.contains(tarefa)){
                    tarefasConcluidas.remove(tarefa); //Remove a tarefa da lista atual
                    tarefasPendentes.add(tarefa); //Insere a tarefa na nova lista 
                }
            }
            case EM_EXECUCAO -> {
                tarefa.setStatus(StatusTarefa.EM_EXECUCAO); //Muda a indicação de Status da tarefa para EM_EXECUCAO
                if(tarefasPendentes.contains(tarefa)){
                    tarefasPendentes.remove(tarefa);  //Remove a tarefa da lista atual
                    tarefasExecucao.add(tarefa);  //Insere a tarefa na nova lista   
                }else if(tarefasConcluidas.contains(tarefa)){
                    tarefasConcluidas.remove(tarefa);  //Remova a tarefa da lista atual
                    tarefasExecucao.add(tarefa); //Insere a tarefa na nova lista 
                }
            }
            case CONCLUIDA -> {
                tarefa.setStatus(StatusTarefa.CONCLUIDA);  //Muda a indicação de Status da tarefa para CONCLUIDA
                if(tarefasPendentes.contains(tarefa)){
                    tarefasPendentes.remove(tarefa);  //Remove a tarefa da lista atual
                    tarefasConcluidas.add(tarefa);  //Insere a tarefa na nova lista   
                }else if(tarefasExecucao.contains(tarefa)){
                    tarefasExecucao.remove(tarefa);  //Remova a tarefa da lista atual
                    tarefasConcluidas.add(tarefa); //Insere a tarefa na nova lista 
                }
            }
            default -> {
            }
        }
    }
    
    /**
     * Obtém o nome que representa o objeto, quando instanciado.
     * @return uma String contendo o nome que representa o objeto.
     */
    @Override
    public String toString(){
        return nome;
    }
}
