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
package br.uefs.ecomp.delivery.model;  //Pacote da classe Cardapio

/**
 * Esta classe implementa a conduta de um <b>item de um pedido</b>. Essa classe contém
 * os atributos do item, sendo eles, opcaoMenu que armazena a referência para o 
 * cardápio (prato) selecionado pelo cliente; a quantidade que indica a quantidade
 * de pratos; a observação que dispõe de qualquer informação extra fornecida
 * pelo cliente para o item do pedido; e o pedido que armazena a referência para
 * o pedido que o este item está associado.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Cardapio c1 = new Cardapio("descrção", 1.0);<br>
 * Cliente cliente = new Cliente("nome", "telefone", "email");<br>
 * Pedido p1 = new Pedido(cliente, 1553303045, "endereço", true);<br>
 * ItemPedido item = new ItemPedido(c1, 1, "observação");<br>
 * item.setPedido(p1);</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.model.Cardapio
 * @see br.uefs.ecomp.delivery.model.Pedido
 * @see br.uefs.ecomp.delivery.model.Cliente
 */
public class ItemPedido {
    private Cardapio opcaoMenu;
    private int quantidade;
    private String observacao;
    private Pedido pedido;
    
    /**
     * Construtor da classe <b>ItemPedido</b>. Inicializa os atributos com as
     * informações passadas por parâmetros.
     * 
     * @param opcaoMenu o prato escolhido pelo usuário.
     * @param quantidade a quantidade desde item.
     * @param observacao a observação sobre o prato escolhido.
     */
    public ItemPedido(Cardapio opcaoMenu, int quantidade, String observacao){
        this.opcaoMenu = opcaoMenu;
        this.quantidade = quantidade;
        this.observacao = observacao;
    }

    /**
     * Obtém o cardápio (o prato) deste item do pedido.
     * @return um objeto do tipo Cardapio representando a opcaoMenu.
     */
    public Cardapio getOpcaoMenu() {
        return opcaoMenu;
    }

    /**
     * Obtém a quantidade do prato escolhido. 
     * @return um inteiro com valor da quantidade.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Obtém a observação sobre este item do pedido.
     * @return uma String represetando a observacao.
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Obtém o pedido que este item está associado.
     * @return um objeto do tipo Pedido representando o pedido.
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Altera o cardápio (prato) deste item do pedido.
     * @param cardapio o novo cardápio (prato).
     */
    public void setOpcaoMenu(Cardapio cardapio) {
        this.opcaoMenu = cardapio;
    }

    /**
     * Altera a quantidade do item pedido.
     * @param quantidade a nova quantidade.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Altera a observação.
     * @param observacao a nova observação.
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * Altera o pedido associado ao item.
     * @param pedido o pedido a ser associado ao item.
     */
    public void setPedido(Pedido pedido){ 
        this.pedido = pedido;  //Associa o pedido ao item.
        setPedidoItens(pedido);  //Associa o item a lista de itens do pedido.
    }
    
    /**
     * O método associa o item a lista de itens do pedido referenciado no parâmetro.
     * @param pedido o pedido que o item deve ser associado.
     */
    private void setPedidoItens(Pedido pedido){
        pedido.getItens().add(this); //Associa o item a lista de itens do pedido.
    }
}
