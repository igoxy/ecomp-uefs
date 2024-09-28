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
 * A classe implementa o comportamento de um <b>Paciente</b>. A classe inclui os atributos de
 * um paciente. Esses atributos são: o nome, o CPF e uma lista de consultas.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Paciente paciente = new Paciente("nome", "123456789");</code></p>
 * 
 * @author Igor
 * @see java.util.LinkedList
 */
public class Paciente {
    private String nome;
    private String cpf;
    private LinkedList<Consulta> consultas;
    
    
    /**
     * Construtor da classe Paciente. Incializa os atributos nome e cpf passados por
     * parâmetro na instância do objeto do tipo Paciente. Além disso, inicializa a lista
     * de consultas do paciente.
     * 
     * @param nome o nome do paciente.
     * @param cpf  o CPF do paciente.
     */
    public Paciente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
        this.consultas = new LinkedList();
    }

    /**
     * Obtém o nome do paciente.
     * @return uma String representando o nome do paciente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome do paciente. Para isso, um novo nome deve ser fornecido.
     * @param nome o novo nome do paciente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o CPF do paciente.
     * @return uma String representando o CPF do paciente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Altera o CPF do paciente. Para isso, um novo CPF deve ser fornecido.
     * @param cpf o novo CPF.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém a lista de consultas do paciente.
     * @return um objeto do tipo <code>LinkedList&lt;Consulta&gt;</code> representado a lista de consultas.
     */
    public LinkedList<Consulta> getConsultas() {
        return consultas;
    }

    /**
     * Altera a lista de consultas do paciente. Para isso, uma nova lista deve ser fornecida.
     * @param consultas a nova lista de consultas.
     */
    public void setConsultas(LinkedList<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    /**
     * Obtém o nome que representa o objeto quando instanciado. O nome que representa
     * um objeto do tipo Paciente inclui: nome e CPF.
     * 
     * @return uma String com um nome que representa o objeto.
     */
    @Override
    public String toString(){
        String identificador = "Nome: ".concat(nome).concat("\nCPF: ".concat(cpf));
        return identificador; 
    }
    
    /**
     * O método é responsável por marcar uma nova consulta para o paciente.
     * 
     * @param medico o médico da consulta.
     * @param data a data da consulta.
     * @param horario o horário da consulta.
     */
    public void marcarConsulta(Medico medico, String data, Horario horario) {
        Consulta novaConsulta = new Consulta(medico, data, horario.toString());
        novaConsulta.setPaciente(this);   //Seta o paciente da consulta           
        this.getConsultas().add(novaConsulta);  //Insere a consulta na lista do paciente
        horario.setNomePaciente(this.getNome());  // Identifica o paciente ao qual o horário foi reservado 
        horario.setConsulta(novaConsulta);  //Adiciona a consulta ao horário reservado
        medico.getAgenda().setHorarioConsultasMarcadas(data, horario);  //Troca o horário de agenda. Muda da agenda de horários disponíveis para de consultas marcadas
    }
}
