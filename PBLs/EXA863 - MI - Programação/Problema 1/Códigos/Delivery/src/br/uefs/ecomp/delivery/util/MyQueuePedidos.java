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

import br.uefs.ecomp.delivery.model.ItemPedido;
import br.uefs.ecomp.delivery.model.Pedido;
import static br.uefs.ecomp.delivery.model.System.getPedidosFechados;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * É uma extensão da classe {@link br.uefs.ecomp.delivery.util.MyQueue} do tipo
 * Pedido. Desta maneira, a classe dispõe das funcionalidade de uma fila de pedidos
 * abertos para o sistema. Além dos métodos herdados de <i>MyQueue</i>, a classe 
 * apresenta o método, específico da fila de pedidos, para fechar um pedido
 * e retirá-lo da fila.
 *  
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.MyQueue
 */
public class MyQueuePedidos extends MyQueue<Pedido> {
    
    /**
     * Contrutor da classe <b>MyQueuePedidos</b> invoca o construtor da classe mãe.
     */
    public MyQueuePedidos(){
        super();
    }

    /**
     * Desinfilera o primeiro pedido da fila, ou seja, remove o primeiro pedido
     * da fila. Deste modo, o pedido removido é fechado e atribuído a lista de 
     * pedidos fechados do sistema.
     */
    public void setNexttoPedidoFechado(){
        Pedido pedido = (Pedido) dequeue();
        pedido.setFechado();
        getPedidosFechados().add(pedido);
    }
    
    /**
     * Lista os pedidos em aberto.
     * @return uma String com os pedidos em aberto.
     */
    public String listarPedidosAbertos(){
        No<Pedido> atual = getPrimeiro();  //Obtém o primeiro nó da fila de pedidos
        int contadorPedidos = 1; //Identificado do pedido.
        String pedidosAbertos = "";  //Utilizada para armazenar os pedidos em aberto.
        DateFormat formato = DateFormat.getDateTimeInstance(); //Utilizado para formatar a data para exibição
        while (atual != null){ //O loop percorre todos os elementos da fila de pedidos.
            pedidosAbertos = pedidosAbertos.concat("\nPedido " + contadorPedidos + ":\n ");  //Concatena na String de pedidos abertos o nome "Pedido" e o número do mesmo na fila.
            Date data = atual.getData().getDataHora(); //Obtem a hora do pedido que está tendo seus dados concatenado.
            pedidosAbertos = pedidosAbertos.concat(formato.format(data)+"\n " + atual.getData().getCliente().getName() + "\n ");  //Concatena a data e o nome do cliente a String que armazena os dados de cada pedido
            pedidosAbertos = pedidosAbertos.concat(atual.getData().getEndereco() + "\n Itens do pedido:\n");  //Concatena o nome "Itens do pedido"
            for (Iterator<ItemPedido> it = atual.getData().getItens().iterator(); it.hasNext();){  //Percorre a lista de itens do pedido inserindo os mesmos na listagem dos itens do pedido
                ItemPedido item = it.next();  
                pedidosAbertos = pedidosAbertos.concat("  " + item.getQuantidade() + " " + item.getOpcaoMenu().getDescricao() + "\n  ");  //Concatena a quantidade e a descrição do item do pedido
            }
            atual = atual.getProximo(); //Obtém o próximo pedido da fila
            contadorPedidos++;  //Incrementa o identificador da posição do pedido na fila
        }
        return pedidosAbertos;  //Retorna a String com todos os pedidos abertos.
    } 
}
