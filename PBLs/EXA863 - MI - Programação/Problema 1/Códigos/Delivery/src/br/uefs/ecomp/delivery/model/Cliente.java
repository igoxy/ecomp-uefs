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


/**
 * A classe implementa os objetos do tipo <b>Cliente</b>. Essa classe contém
 * os atributos de um cliente. Estes atributos são: nome, telefone, e-mail e 
 * uma lista de pedidos associados ao cliente.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Cliente cliente = new Cliente("nome", "telefone", "e-mail");</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.util.MyLinkedList
 */
public class Cliente {
    private String name; //Nome do cliente.
    private String phone;  //Telefone do cliente.
    private String email;  //Email do cliente.
    private MyLinkedList<Pedido> pedidos;  //Lista de pedidos do cliente.
    
    /**
     * Construtor Cliente da classe <b>Cliente</b>. Inicializa os atributos
     * com as informações passadas por parâmetro.
     * 
     * @param nome o nome do cliente.
     * @param telefone o telefone do cliente.
     * @param email o e-mail do cliente.
     */
    public Cliente(String nome, String telefone, String email){
        this.name = nome;
        this.phone = telefone;
        this.email = email;
        this.pedidos = new MyLinkedList<>();
    }

    /**
     * Obtém o nome do cliente.
     * @return uma String representando o nome.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém o telefone do cliente.
     * @return uma String representando o telefone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Obtém o e-mail do cliente.
     * @return uma String representando o email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtém a lista de pedidos do cliente.
     * 
     * @return uma lista do tipo MyLinkedList com os pedidos do cleinte.
     */
    public MyLinkedList<Pedido> getPedidos(){
        return pedidos;
    }
    
    
    /**
     * Altera o nome do cliente.
     * @param nome o novo nome.
     */
    public void setName(String nome) {
        this.name = nome;
    }

    /**
     * Altera o telefone do cliente.
     * @param telefone o novo telefone.
     */
    public void setPhone(String telefone) {
        this.phone = telefone;
    }

    /**
     * Altera o e-mail do cliente.
     * @param email o novo e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Altera a lista de pedidos do cliente.
     * @param pedidos a nova lista de pedidos.
     */
    public void setPedidos(MyLinkedList<Pedido> pedidos){
        this.pedidos = pedidos;
    }
    
    /**
     * Compara se outro objeto do tipo <i>Cliente</i> é igual a esse.
     * 
     * O método <b>equals</b> verifica se o nome, o telefone e o e-mail de ambos os
     * objetos são idênticos.
     * 
     * @param client o objeto do tipo <i>Cliente</i> a ser comparado.
     * @return <i>true</i> se o os objetos são iguais, senão retona <i>false</i>.
     */
    public boolean equals(Cliente client){
        return client.getName().equals(this.name) && client.getPhone().equals(this.phone) && client.getEmail().equals(this.email);
    }       
}
