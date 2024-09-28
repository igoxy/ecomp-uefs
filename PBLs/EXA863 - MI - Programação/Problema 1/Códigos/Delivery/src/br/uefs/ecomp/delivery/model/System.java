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
package br.uefs.ecomp.delivery.model; //Pacote da classe Cardapio

import br.uefs.ecomp.delivery.util.MyLinkedList;
import br.uefs.ecomp.delivery.util.MyLinkedListCardapio;
import br.uefs.ecomp.delivery.util.MyLinkedListClientes;
import br.uefs.ecomp.delivery.util.MyQueuePedidos;

/**
 * A classe implementa os comportamentos do <b>sistema</b>. Deste modo, detém os atributos
 * que compõe o sistema, sendo eles:
 * <ul>
 *  <li>Lista de clientes: armazena, em ordem de cadastrado, os clientes que estão cadastrados
 *  no sistema.</li>
 *  <li>Lista de pedidos fechados: armazena todos os pedidos que já 
 *  foram concluídos.</li>
 *  <li>Lista de cardápios: guarda os pratos cadastrados no sistema.</li>
 *  <li>Fila de pedidos: detém os pedidos em aberto, ou seja, que ainda não foram
 *  finalizados.</li>
 * </ul>
 * 
 * <p><b> Exemplo de uso</b>:</p>
 * <p><code>System sistema = new System();</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.MyLinkedListClientes
 * @see br.uefs.ecomp.delivery.util.MyLinkedList
 * @see br.uefs.ecomp.delivery.util.MyLinkedListCardapio
 * @see br.uefs.ecomp.delivery.util.MyQueuePedidos
 */
public class System {
    private MyLinkedListClientes clientes;  //Lista de clientes do sistema
    private static MyLinkedList<Pedido> pedidosFechados; //O sistema deve apresentar somente uma lista de pedidos fechados, portanto, essa lista deve ser estática.
    private MyLinkedListCardapio cardapio; //Lista de cardápio
    private MyQueuePedidos pedidosAbertos; //Fila de pedidos abertos
    
    
    /**
     * Construtor System da classe <b>System</b>. Cria as listas e a fila do
     * sistema. Para isso, inicializa as listas e a fila criando as estruturas
     * de dados de acordo com auas respectivas assinaturas.  
     * 
     */
    public System(){
        this.clientes = new MyLinkedListClientes(); 
        System.pedidosFechados = new MyLinkedList();
        this.cardapio = new MyLinkedListCardapio();
        this.pedidosAbertos = new MyQueuePedidos(); 
    }

    /**
     * Obtém a lista de clientes cadastrados no sistema.
     * @return uma MyLinkedListClientes do tipo Cliente
     * com todos os clientes cadastrados no sistema.
     */
    public MyLinkedListClientes getClientes() {
        return clientes;
    }

    /**
     * Obtém a lista de pedidos fechados.
     * @return uma MyLinkedList do tipo Pedido com todos
     * os pedidos do sistema que já foram fechados.
     */
    public static MyLinkedList<Pedido> getPedidosFechados() {
        return pedidosFechados;
    }

    /**
     * Obtém a lista pratos cadastrados no sistema. 
     * @return uma MyLinkedListCardapio do tipo Cardapio com
     * todos os pratos cadastrados no sistema.
     */
    public MyLinkedListCardapio getCardapio() {
        return cardapio;
    }

    /**
     * Obtém a fila de pedidos com a situação em aberto.
     * @return uma MyQueuePedidos com os pedidos que apresentam o 
     * status de aberto no sistema.
     */
    public MyQueuePedidos getPedidosAbertos() {
        return pedidosAbertos;
    }

    /**
     * Altera a lista de clientes do sistema. Para isso, uma nova lista de clientes
     * deve ser fonecida.
     * @param listClientes uma nova lista de clientes do tipo <code>MyLinkedListClientes</code>.
     */
    public void setClientes(MyLinkedListClientes listClientes) {
        this.clientes = listClientes;
    }

    /**Altera a lista de pedidos fechados. Para isso, uma nova lista de pedidos
     * fechados deve ser fornecida.
     * @param listPedidosFechados nova lista de pedidos do tipo <code>MyLinkedList&lt;Pedidos&gt;</code>.
     */
    public static void setPedidosFechados(MyLinkedList<Pedido> listPedidosFechados) {
        System.pedidosFechados = listPedidosFechados;
    }

    /**
     * Altera a lista de pratos cadastrados no sistema. Para isso, uma nova lista
     * de pratos do tipo Cardapio deve ser fornecida.
     * @param listCardapio nova lista de Cardapio do tipo <code>MyLinkedListCardapio&lt;Cardapio&gt;</code>.
     */
    public void setCardapio(MyLinkedListCardapio listCardapio) {
        this.cardapio = listCardapio;
    }

    /**
     * Altera a fila de pedidos abertos. Para isso, uma nova fila de pedidos 
     * abertos deve ser fornecida.
     * @param queuePedidosAbertos nova fila do tipo <code>MyQueuePedidos</code> de 
     * pedidos abertos.
     */
    public void setPedidosAbertos(MyQueuePedidos queuePedidosAbertos) {
        this.pedidosAbertos = queuePedidosAbertos;
    }  
}
