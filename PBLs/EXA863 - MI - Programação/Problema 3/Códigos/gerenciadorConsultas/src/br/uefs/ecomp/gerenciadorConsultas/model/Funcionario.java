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

/**
 * Classe abstrata que implementa o comportamento e o atributos em comum dos funcionários 
 * (médicos e recepcionistas) do sistema. A classe contém os atributos: CPF, nome e senha.
 * 
 * @author Igor
 */
public abstract class Funcionario {
    private String cpf;
    private String nome;
    private String senha;
    
    /**
     * Construtor da classe abstrata. Inicializa os atributos nome, CPF e a senha
     * passados por parâmetro.
     * 
     * @param nome o nome do funcionário.
     * @param cpf o CPF do funcionário.
     * @param senha a senha do funcionário.
     */
    public Funcionario(String nome, String cpf, String senha){
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
    }

    /**
     * Obtém o CPF.
     * @return uma String representando o CPF.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Altera o CPF. Para isso, um novo CPF deve ser fornecido.
     * @param cpf o novo CPF.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o nome.
     * @return uma String representando o nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome. Para isso, um novo nome deve ser fornecido.
     * @param nome o novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a senha. 
     * @return uma String representando a senha.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Altera a senha. Para isso, uma nova senha deve ser fornecida.
     * @param senha a nova senha.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
}
