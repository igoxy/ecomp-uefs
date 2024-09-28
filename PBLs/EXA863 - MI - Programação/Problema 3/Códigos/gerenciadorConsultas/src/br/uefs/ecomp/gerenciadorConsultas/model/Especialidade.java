/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI Programação
Concluido em: 08/12/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.gerenciadorConsultas.model;  //Pacote da classe

import java.util.LinkedList;

/**
 * A classe implementa o comportamento de uma <b>Especialidade</b>. A classe
 * contém os atributos de uma especialidade. Esses atributos são: o nome da especialidade
 * e uma lista com os médicos pertencentes a especialidade.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Especialidade especialidade = new Especialidade("nome");</code></p>
 * 
 * @author Igor
 * @see java.util.LinkedList
 */
public class Especialidade {
    private LinkedList<Medico> medicos;
    private String nome;
    
    /**
     * Construtor da classe Especialidade. Inicializa o atributo nome passados por
     * parâmetro no momento da instância do objeto do tipo Especialidade. Além disso,
     * inicializa a lista de médicos associados a especialidade.
     * 
     * @param nome o nome da especialidade.
     */
    public Especialidade(String nome){
        this.nome = nome;
        medicos = new LinkedList();
    }

    /**
     * Obtém a lista de médicos associados a especialidade.
     * @return um objeto do tipo <code>LinkedList&lt;Medico&gt;</code> representando a lista de médicos.
     */
    public LinkedList<Medico> getMedicos() {
        return medicos;
    }

    /**
     * Altera a lista de médicos associados a especialidade. Para isso, uma nova lista de médicos deve ser fornecida.
     * @param medicos a nova lista de médicos.
     */
    public void setMedicos(LinkedList<Medico> medicos) {
        this.medicos = medicos;
    }

    /**
     * Obtém o nome da especialidade.
     * @return uma String representado o nome da especialidade.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome da especialidade. Para isso, um novo nome deve ser fornecido.
     * @param nome o novo nome para a especialidade.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Obtém o nome que representa o objeto quando instanciado.
     * @return uma String com um nome que representa o objeto.
     */
    public String toString(){
        return nome;
    }
}
