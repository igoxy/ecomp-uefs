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

import br.uefs.ecomp.delivery.model.Cliente;
import java.util.Iterator;

/**
 * É uma extensão da classe {@link br.uefs.ecomp.delivery.util.MyLinkedList} do
 * tipo Cliente. Deste modo, a classe dispõe das funcionalidades de uma lista
 * de clientes para o sistema. Além dos métodos herdados de <i>MyLinkedList</i>,
 * a classe implementa os métodos específico da lista de clientes que são responsáveis 
 * por procurar cliente por nome, procurar cliente por telefone e remover clientes
 * por meio do números de telefone.
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.MyLinkedList
 */
public class MyLinkedListClientes extends MyLinkedList<Cliente>{

    /**
     * Retorna os clientes que apresentam o nome, ou pelo menos parte dele, igual
     * a String fornecida por parâmetro.
     * 
     * @param nome o nome a ser procurado.
     * @return um objeto do tipo <code>Iterator</code> da lista de clientes encontrados.
     */

    public Iterator searchClientebyName(String nome){
        MyLinkedListClientes listaClientes = new MyLinkedListClientes();  //Cria uma lista de clientes para armazenar a referência dos clientes encontrados na busca
        //Busca os clientes que apresentam o nome informado e armazena uma referência dos mesmos em uma lista.
        for (Iterator<Cliente> it = this.iterator(); it.hasNext();) {
            Cliente item = it.next();
            if (item.getName().contains(nome)){
                listaClientes.add(item);
            }
        }
        return listaClientes.iterator();
    }
    
    /**
     * Retorna os clientes que apresentam o número, ou pelo menos parte dele, igual
     * ao número de telefone fornecido por parâmetro.
     * 
     * @param phone o telefone a ser procurado.
     * @return um objeto do tipo <code>Iterator</code> da lista de clientes encontrados.
     */
    public Iterator searchClientebyPhone(String phone){
        MyLinkedListClientes listaClientes = new MyLinkedListClientes(); //Cria uma lista de clientes para armazenar a referência dos clientes encontrados na busca
        for (Iterator<Cliente> it = this.iterator(); it.hasNext();) {
            Cliente item = it.next();
            if (item.getPhone().contains(phone)){
                listaClientes.add(item);
            }
        }
        return listaClientes.iterator();
    }
    
    /**
     * Remove os clientes que apresentam o número de telefone especificado. Caso
     * nenhum cliente apresente o número de telefone fornecido, nenhum cliente
     * é removido.
     * 
     * @param phone o número de telefone para a remoção do(s) cliente(s).
     */
    public void removeByPhone(String phone){
        int indiceCliente = 0; //Referencia o índice, da lista de clientes, do cliente que está sendo verificado no momento
        MyLinkedList indicesClientes = new MyLinkedList(); /*Cria uma lista para armazenar os índices que identificam,
        na lista de clientes do sistema, os clientes a serem removidos */
        for (Iterator<Cliente> it = this.iterator(); it.hasNext();) {  // Verifica os clientes cadastrados no sistema
            Cliente item = it.next(); 
            /*Verifica se cliente apresenta o número de telefone correspondente 
            ao fornecido para a remoção e se o cliente não tem pedidos associados*/
            if (item.getPhone().contains(phone) && item.getPedidos().isEmpty()){  //
                indicesClientes.add(0, indiceCliente);  //Armazena o índice que referencia o cliente a ser removido da lista de clientes
            }
            indiceCliente++;  //O indice que identifica o cliente é incrementado
        }
        while(!indicesClientes.isEmpty()){  //Enquanto houver índices na lista é feita a remoção dos clientes
            remove((int) indicesClientes.remove(0));  //Remove os clientes começando do último encontrado com o telefone correspondente
        }
    }   
}
