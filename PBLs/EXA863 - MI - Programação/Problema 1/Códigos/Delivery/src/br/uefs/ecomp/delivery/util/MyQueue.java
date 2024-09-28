/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 14/09/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.delivery.util;

/**
 * A classe implementa a interface {@link br.uefs.ecomp.delivery.util.IQueue}.
 * Portanto, implementa o comportamento da estrutura de dados <b>Fila</b>.
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.No
 */
public class MyQueue<T> implements IQueue {
    private No<T> primeiro;
    private No<T> ultimo;
    private int tamanho;
    
    /**
     * O construtor da classe <b>MyQueue</b>. Inicializa os atributos com os
     * valores padrões. Os atributos primeiro e ultimo recebem os valores 
     * <code>null</code> e o atributo tamanho recebe o valor 0.
     */
    public MyQueue(){
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
        //Construtor vazio
    }
    
    /**
     * Adiciona um novo elemento ao final da fila, ou seja, enfileirando. Se a 
     * fila estiver vazia, o elemento é inserido no início, sendo refênciado como
     * primeiro e também como o último elemento da fila.
     * 
     * @param data o elemento a ser adicionado.
     */
    @Override
    public void enqueue(Object data) {
        if (isEmpty()){
            No<T> novo = new No(data);
            primeiro = ultimo = novo; 
        }else{
            No<T> auxiliar = ultimo;
            No<T> novo = new No(data);
            ultimo = novo;  
            auxiliar.setProximo(ultimo);
        }
        tamanho++;
    }
    
    /**
     * Recupera e remove o elemnto que está no início da fila.
     * 
     * @return o primeiro elemento da fila ou <code>null</code> caso a fila não tenha elementos. 
     */
    @Override
    public Object dequeue() {
        if (!isEmpty()){
            T informacao = primeiro.getData();
            if (tamanho == 1){
                primeiro = ultimo = null;
            }else{
                primeiro = primeiro.getProximo();
            }
            tamanho--;
            return informacao;
        }
        return null;
    }
    /**
     * Recupera o primeiro elemento da fila sem removê-lo.
     * 
     * @return o primeiro elemento da fila ou <code>null</code> caso a fila não tenha elementos.
     */
    @Override
    public Object peek() {
        if(!isEmpty()){
            return primeiro.getData();
        }else{
            return null;
        }
    }
    
    /**
     * Recupera o último elemento da fila sem removê-lo.
     * 
     * @return o último elemento da fila ou <code>null</code> caso a fila não tenha elementos.
     */
    @Override
    public Object last() {
        if(!isEmpty()){
            return ultimo.getData();
        }else{
            return null;
        }
    }
    /**
     * Obtém o número de elementos na fila.
     * 
     * @return um inteiro referente ao número de elementos na fila.
     */
    @Override
    public int size() {
        return tamanho;
    }
    /**
     * Verifica se a fila não contém elementos.
     * 
     * @return <code>true</code>, caso a lista não tenha elementos e <code>false</code> caso tenha algum elemento.
     */
    @Override
    public boolean isEmpty() {
        return primeiro == null;
    }
    
    /**
     * Retorna o primeiro nó da fila.
     * 
     * @return o primeiro nó da fila.
     */
    public No<T> getPrimeiro(){
        return primeiro;
    }
    
    /**
     * Retorna o último nó da fila.
     * 
     * @return o último nó da fila.
     */
    protected No<T> getUltimo(){
        return ultimo;
    }
}
