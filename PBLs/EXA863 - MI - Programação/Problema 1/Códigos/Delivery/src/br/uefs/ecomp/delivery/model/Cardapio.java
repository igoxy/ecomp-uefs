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
 * Esta classe implementa um objeto do tipo <b>Cardapio</b>. A classe contém
 * os atributos de um cardápio (prato) vendido pelo estabelecimento. Esses
 * atributos são: valor e descrição.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Cadardapio cardapio = new Cardapio("descrição", 0.0); </code></p>
 * 
 * @author Igor
 * 
 */
public class Cardapio {
    private String descricao; //Descrição do cardápio (prato).
    private double valor;  //Valor do cardápio.
    
    /**
     * Construtor Cardapio da classe <b>Cardapio</b>. Inicializa os atributos
     * com as informações passadas por parâmetro.
     * 
     * @param descricao a descrição do cardápio.
     * @param valor o valor do cardápio.
     */
    public Cardapio(String descricao, double valor){
        this.descricao = descricao;
        this.valor = valor;
    }

    /**
     * Obtém a descrição.
     * @return uma String representando a descricao.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém o valor.
     * @return um double represetando o valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * Altera a <i>descrição</i>.
     * @param descricao a nova descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Altera o <i>valor</i>. 
     * @param valor o novo valor.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Compara se outro objeto do tipo <i>Cardapio</i> é igual a esse.
     * 
     * O método <b>equals</b> verifica se a descrição e o valor de ambos os
     * objetos são idênticos.
     * 
     * @param c o objeto do tipo <i>Cardapio</i> a ser comparado.
     * @return <i>true</i> se o os objetos são iguais, senão retona <i>false</i>.
     */
    public boolean equals(Cardapio c){
        return descricao.equals(c.getDescricao()) && c.getValor() == valor;
    }
}
