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
 * A classe implementa a interface {@link br.uefs.ecomp.delivery.util.IList}. 
 * Deste modo, é reponsável por implementar a estrutura de dados <b>Lista Ligada</b>.
 * 
 * @author Igor
 * @param <T> o tipo de elemento contido na lista.
 * @see br.uefs.ecomp.delivery.util.No
 */
public class MyLinkedList<T> implements IList{
    //Atributos
    private No<T> primeiro;  //O primeiro nó da lista
    private int tamanho;  //O tamanho da lista
    
    /**
     * Construtor da classe <b>MyLinkedList</b>. <br>
     * Atribuí o valor <code>null</code> para o atributo <code>primeiro</code> 
     * e o valor 0 para o atributo <code>tamanho</code>.
     */
    public MyLinkedList(){
        this.primeiro = null;
        this.tamanho = 0;
        
    }    
     
    @Override
    public int size() {
        return tamanho;
    }

    @Override
    public void add(Object obj) {
        No<T> novoElemento = new No<T>((T)obj);
        if(primeiro == null){
            primeiro = novoElemento;
            tamanho++;
        }
        else{
            No<T> auxiliar = primeiro;
            while (auxiliar.getProximo() != null){
                auxiliar = auxiliar.getProximo();
            }
            auxiliar.setProximo(novoElemento);
            tamanho++;
        }   
    }

    @Override
    public boolean add(int pos, Object obj) {
        if (pos == 0 && primeiro !=null){
            No<T> novoElemento = new No<T>((T)obj);
            novoElemento.setProximo(primeiro);
            primeiro = novoElemento;
            tamanho++;
            return true;
        }else if(pos == size()){
            add(obj);  //Chama o metódo de inserir no final da lista
            return true;
        }
        else if (pos > 0 && pos < tamanho){
            No<T> auxiliar = primeiro;  //Guarda o nó anterior a posição que o novo nó vai entrar
            for (int i = 0; i < pos - 1; i++){
                auxiliar = auxiliar.getProximo();
            }
            No<T> novoElemento = new No<T>((T)obj);
            novoElemento.setProximo(auxiliar.getProximo());
            auxiliar.setProximo(novoElemento);
            tamanho++;
            return true;
        }else{
            return false;
        }  
    }

    @Override
    public T remove(int pos) {
        if(primeiro != null && pos == 0){
            No<T> remover = primeiro;
            primeiro = remover.getProximo();
            remover.setProximo(null);  //Desconecta o nó do elemento removido da lista
            tamanho--;
            return remover.getData();
        }else if (pos > 0 && pos < tamanho){
            No<T> anteriorRemover = primeiro;  //Nó anterior a posição a ser removida
            for (int i = 0; i < pos - 1; i++){
                anteriorRemover = anteriorRemover.getProximo();
            }
            No<T> remover = anteriorRemover.getProximo();
            anteriorRemover.setProximo(remover.getProximo());
            remover.setProximo(null);
            tamanho--;
            return remover.getData();  //Faz o cast do retorno (Type cast)
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index == 0 && primeiro != null){
            return primeiro.getData();
        }else if (index >= 0 && index < tamanho){
            No<T> elemento = primeiro;
            for (int i = 0; i < index; i++){
                elemento = elemento.getProximo();
            }
            return elemento.getData(); 
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return primeiro == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(primeiro); 
    }   
}
