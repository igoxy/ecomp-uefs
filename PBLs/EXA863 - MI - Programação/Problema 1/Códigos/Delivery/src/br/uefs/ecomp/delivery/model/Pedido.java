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
package br.uefs.ecomp.delivery.model;  //Pacote da classe Cliente

import br.uefs.ecomp.delivery.util.MyLinkedList;
import java.util.Date;
import java.util.Iterator;

/**
 * Esta classe implementa a conduta de um <b>pedido</b>. Deste modo, ela contém os
 * atributos de um pedido como, a data do pedido, o endereço e a situação (se está
 * aberto ou fechado).<br>
 * Além disso, ela guarda a referência do cliente que o pedido está associado e 
 * a lista de itens que compõe o pedido.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Cliente cliente = new Cliente("nome", "telefone", "email");<br>
 * Pedido p1 = new Pedido(cliente, 1553303045, "endereco", true);</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.MyLinkedList
 * @see br.uefs.ecomp.delivery.model.Cliente
 */
public class Pedido {
    private Cliente cliente;
    private Date dataHora;
    private String endereco;
    private boolean situacao;
    private MyLinkedList<ItemPedido> itens;
    
    
    /**
     * Construtor Cliente da classe <b>Pedido</b>. Inicializa os atributos
     * com as informações passadas por parâmetro.
     * @param cliente o cliente associado ao pedido.
     * @param dataHora a data e hora do pedido.
     * @param endereco o endereço do pedido.
     * @param situacao a situação do pedido.
     */
    public Pedido(Cliente cliente, Date dataHora, String endereco, boolean situacao){
        this.cliente = cliente; 
        this.dataHora = dataHora;
        this.endereco = endereco;
        this.situacao = situacao;
        this.itens = new MyLinkedList<>();
        setPedidotoCliente(); //Chama o método privado para adicionar o pedido a lista de pedidos do cliente.
    }

    /**
     * Obtém o cliente associado ao pedido.
     * @return um objeto do tipo Cliente representando o cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Obtém a data e a hora do pedido.
     * @return um objeto do tipo Date representando a dataHora.
     */
    public Date getDataHora() {
        return dataHora;
    }

    /**
     * Obtém o endereço associado ao pedido.
     * @return uma String representando o endereco.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Obtém a situação do pedido.
     * @return um boolean representando a situacao.
     */
    public boolean getSituacao() {
        return situacao;
    }
    
    /**
     * Obtém os itens que integram o pedido.
     * @return uma MyLinkedList com os itens do pedido.
     */
    
    public MyLinkedList<ItemPedido> getItens(){
        return itens;
    }
    
    /**
     * Obtém a soma dos valores dos itens presentes no pedido. Para isso,
     * chama o método privado <b>calculaValorTotal()</b>.
     * 
     * @see #calculaValorTotal() 
     * @return um double representando o valor total do pedido.
     */
    public double getValorTotal(){
        return calculaValorTotal();
    }
    
    /**
     * Altera o cliente que o pedido está associado.
     * @param cliente o novo cliente do pedido.
     */
    public void setCliente(Cliente cliente) {
        removePedidoCliente();  //Chamada do método para desassociar o pedido do cliente anterior.
        this.cliente = cliente;
        setPedidotoCliente();  //Chamada do método para associar o pedido ao cliente.
    }
    
    /**
     * Altera a data e hora do pedido.
     * @param dataHora a data e hora do pedido.
     */
    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Altera o endereço do pedido.
     * @param endereco o endereço do pedido.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Altera a situação do pedido. 
     * @param situacao a situação do pedido.
     */
    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
    
    /**
     * O método altera a siatuação do pedido para fechado. Para isso, o método
     * chama o <b>setSituacao</b> como parâmetro <i>false</i>.
     */
    public void setFechado(){
        setSituacao(false);
    }
    /**
     * Altera a lista dos itens pedidos fornecendo uma nova lista.
     * @param itens a nova lista de itens. 
     */
    public void setItens(MyLinkedList itens){
        this.itens = itens;
    }
    
    //--------- Métodos privados ------------
    
    /**
     * Esse método percorre cada item da lista de itens do pedido e obtem o valor
     * e a quantidde de cada um deles. A partir disso, o valor de cada item é multiplicado
     * pela sua respectiva quantidade, então, o valor obtido é incrementado a uma variável
     * que armazena o valor total do pedido. Deste modo, tem-se a soma total de cada item
     * que compõe o pedido, portanto, obtém-se o total do pedido.
     * 
     * @return um <code>double</code> indicando o valor total do pedido
     */
    private double calculaValorTotal(){
        double valorTotal = 0;  //Armazena o valor total do pedido.
        for (Iterator<ItemPedido> it = itens.iterator(); it.hasNext();) {  //Percorre a lista de itens do pedido e soma seus valores para obeter o total.
            ItemPedido item = it.next();
            valorTotal = (item.getQuantidade()*item.getOpcaoMenu().getValor()) + valorTotal;
        }
        return valorTotal;
    }
    
    /**
     * Adiciona o pedido a lista de pedidos do cliente.
     */  
    private void setPedidotoCliente(){
        this.cliente.getPedidos().add(this);
    }
    
    /**
     * Desassocia o pedido do atual cliente associado.
     */
    
    private void removePedidoCliente(){
        Iterator<Pedido> it = this.cliente.getPedidos().iterator();
        Pedido item = it.next();
        int indice = 0;
        while (item != this || it.hasNext()){  //Busca o índice do pedido a ser desassociado.
            item = it.next();
            indice++;
        }
        if (this == item){  //Se o pedido for encontrado na lista de pedidos do cliente, o mesmo é removido.
            this.cliente.getPedidos().remove(indice);
        }
    }
}
