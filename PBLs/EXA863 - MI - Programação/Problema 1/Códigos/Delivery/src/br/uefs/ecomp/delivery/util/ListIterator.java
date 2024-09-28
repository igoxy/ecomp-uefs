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

import java.util.Iterator;

/**
 * Implementa a interface <i>Iterator</i>.Deste modo, a classe implementa um 
 objeto que permite percorrer a estrutura de dados lista encadeada. Em outras palavras, 
 a classe implementa os métodos necessário para iterar uma coleção.
 * 
 * @author Igor
 * @param <T> o tipo de objeto do iterator. 
 * @see br.uefs.ecomp.delivery.util.No
 */
public class ListIterator<T> implements Iterator{
    private No<T> proximo;
    
    public ListIterator(No<T> primeiro){
       this.proximo = primeiro;
    }
    
    /**
     * Verifica ainda existem elementos para serem iterados.
     * @return true se a iteração apresentar mais elementos, false caso contrário.
     */
    @Override
    public boolean hasNext() {
        return proximo != null;
    }

    /**
     * Obtém o elemento seguinte da iteração.
     * @return o próximo elemento da iteração.
     */
    @Override
    public T next() {
        No<T> temp = proximo;
        if(hasNext()){
            proximo = proximo.getProximo();
            return temp.getData();
        }
        return null;
    }
}
