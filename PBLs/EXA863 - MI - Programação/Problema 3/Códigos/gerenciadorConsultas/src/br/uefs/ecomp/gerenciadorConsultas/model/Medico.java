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
 * A classe implementa o comportamento de um <b>Medico</b>. A classe estende da
 * classe abstrata funcionário a qual fornece os atributos nome, cpf e senha. Além disso,
 * a classe contém os atributos específicos do médico. Esses atributos são: o CRM, a especialidade,
 * a subespecialidade, a agenda de horários disponíveis para marcação de consultas, a agenda de 
 * consultas marcadas e a agenda de consultas realizadas.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Medico medico = new Medico("nome","crm","123","senha", new Especialidade("ex1"), new Especialidade("ex2"));</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Especialidade
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Agenda
 */
public class Medico extends Funcionario {

    private String crm;
    private Especialidade especialidade;
    private Especialidade subespecialidade;
    private Agenda agenda;

    /**
     * Construtor da classe Medico. Inicializa os atributos nome, crm, cpf, senha, especialidade
     * e subespecialidade passados por parâmetro na instância do objeto do tipo Medico. Além disso,
     * inicializa das agendas do médico. O construtor também invoca o método privado responsável por inserir
     * o médico na lista de médicos da sua especialidade e subespecialidade.
     * 
     * @param nome o nome do médico.
     * @param crm o CRM do médico.
     * @param cpf o CPF do médico.
     * @param senha a senha do médico.
     * @param especialidade a especialidade do médico.
     * @param subespecialidade  a subespecialidade do médico.
     */
    public Medico(String nome, String crm, String cpf, String senha, Especialidade especialidade, Especialidade subespecialidade) {
        super(nome, cpf, senha);
        this.crm = crm;
        this.especialidade = especialidade;
        this.subespecialidade = subespecialidade;
        this.agenda = new Agenda();
        addMedicoEspecialidade(especialidade);
        addMedicoEspecialidade(subespecialidade);
    }
    
    //Getters e setters

    /**
     * Obtém a especialidade do médico.
     * @return um objeto do tipo Especialidade representando a especialidade do médico.
     */
    public Especialidade getEspecialidade() {
        return especialidade;
    }

    /**
     * Altera a especialidade do médico. Para isso, uma nova especialiadade deve ser fornecida.
     * @param especialidade a nova especialidade do médico.
     */
    public void setEspecialidade(Especialidade especialidade) {
        if (this.especialidade != null){
            this.especialidade.getMedicos().remove(this);
        }
        this.especialidade = especialidade;
        addMedicoEspecialidade(especialidade);
    }

    /**
     * Obtém a subespecialidade do médico.
     * @return um objeto do tipo Especialidade representando a subespecialidade do médico.
     */
    public Especialidade getSubespecialidade() {
        return subespecialidade;
    }

    /**
     * Altera a subespecialidade do médico. Para isso, uma nova subespecialidade deve ser fornecida.
     * @param subespecialidade a nova subespecialidade do médico.
     */
    public void setSubespecialidade(Especialidade subespecialidade) {
        if (this.subespecialidade != null){
            this.subespecialidade.getMedicos().remove(this);
        }
        this.subespecialidade = subespecialidade;
        addMedicoEspecialidade(subespecialidade);
    }
    
    /**
     * Obtém o CRM do médico.
     * @return uma String representando o CRM do médico.
     */
    public String getCrm() {
        return crm;
    }

    /**
     * Altera o CRM do médico. Para isso, um novo CRM deve ser fornecido.
     * @param crm o novo CRM do médico.
     */
    public void setCrm(String crm) {
        this.crm = crm;
    }
    
    /**
     * Obtém a agenda de horários do médico.
     * @return um objeto do tipo <code>Agenda</code> representando a agenda do médico.
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Altera a agenda do médico. Para isso, uma nova agenda deve ser fornecida.
     * @param agenda a nova Agenda do médico.
     */
    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
    
    //Fim getters e setters
    
    /**
     * Obtém o nome que representa o objeto quando instanciado. O nome que representa
     * um objeto do tipo Medico contém: nome, CRM, especialidade e subespecialidade (se houver).
     * 
     * @return uma String com um nome que representa o objeto.
     */
    @Override
    public String toString() {
        String identificador = getNome().concat("\nCRM: ".concat(getCrm())).concat("\nEspecialidade: ".concat(especialidade.toString()));  //"Nome: ".concat()
        if (subespecialidade != null) {
            identificador = identificador.concat("\nSubespcialidade: ".concat(subespecialidade.toString()));
        }
        return identificador;
    }
    
    //******* Métodos privados *****
    /**
     * O método é responsável por adicionar o médico na lista de médicos da sua
     * especialidade.
     * 
     * @param especialidade a especialidade.
     */
    private void addMedicoEspecialidade(Especialidade especialidade) {
        if (especialidade != null) {
            especialidade.getMedicos().add(this);
        }
    }
}
