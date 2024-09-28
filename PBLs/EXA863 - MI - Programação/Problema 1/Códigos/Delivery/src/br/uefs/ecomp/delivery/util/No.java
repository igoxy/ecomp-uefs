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
 * Essa classe implementa um nó que pode ser utilizado para a construção das
 * estruturas de dados: lista ligada, fila ou pilha. Os objetos desta classe
 * apresentam o atributo dado que armazena o elemento inserido em dada posição
 * da estrutura de dados que está utilizando desta classe. Além disso, apresenta
 * o atributo proximo, que contém a referência para o próximo nó na estrutura
 * de dados.<br>
 * 
 * Por fim, o nó pode receber uma inferência do tipo de dado que terá que armazenar.
 * Deste modo, é especificado que a estrutra de dados recebe objetos de um determinado
 * tipo.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * 
 * <p><code>String st = "a";<br>
 * No&lt;String&gt; no = new No&lt;String&gt;(st);<br>
 * String stri = no.getData();</code></p>
 * 
 * 
 * @author Igor
 * @param <T> o tipo do objeto que o nó deve armazenar.
 */
public class No<T> {
    private T data;  //O objeto a ser armazenado no nó
    private No<T> proximo;  //Próximo nó da lista
    
    /**
     * O construtor <b><code>No</code> sem parâmentro</b> inicializa os atributos
     * <code>data</code> e <code>proximo</code> com o valor padrão <code>null</code>.
     */
    public No(){
        this.data = null;
        this.proximo = null;
    }
    
    /**
     * O construtor <b><code>No</code> com um parâmetro</b> inicializa o atributo 
     * <code>proximo</code> com o valor padrão <code>null</code> e o atributo
     * <code>data</code> com o valor fonecido por parâmetro.
     * 
     * @param data o objeto a ser armazenado no nó. 
     */
    public No(T data){ 
        this.data = data;
        this.proximo = null;
    }
    
    /**
     * @return o objeto armazenado nó.
     */
    public T getData() {
        return data;
    }

    /**
     * @return o próximo nó ligado a este nó.
     */
    public No getProximo() {
        return proximo;
    }

    /**
     * @param data o objeto a ser armazenado no nó.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @param proximo o próximo nó ligado a este nó.
     */
    public void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }  
}
