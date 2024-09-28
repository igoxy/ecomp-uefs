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
 * A classe implementa o comportamento de um <b>Horario</b>. A classe contém os atributos
 * de um horário de consulta. Esses atributos são: a consulta, a identificação do horário e 
 * o paciente do horário.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Medico medico = new Medico("nome","crm","123","senha", new Especialidade("ex1"), new Especialidade("ex2"));</code>:</p>
 * <p><code>Consulta consulta = new Consulta(medico, "01/01/01", "00:00");</code></p>
 * <p><code>Paciente paciente = new Paciente("nome", "012");</code></p>
 * <p><code>paciente.getConsultas().add(consulta);</code></p>
 * <p><code>Horario horario = new Horario(consulta, paciente);</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Consulta
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Paciente
 */
public class Horario {
    private Consulta consulta;
    private String identificacao;
    private String nomePaciente;
    
    /**
     * Construtor da classe Horario. Neste construtor, são inicializados os atributos
     * consulta e identificação passados por parâmetro no momento da instância do objeto
     * do tipo Horario.
     * @param consulta a consulta do horário.
     * @param identificacao a identificação do horário.
     */
    public Horario(Consulta consulta, String identificacao){
        this.consulta = consulta;
        this.identificacao = identificacao;
    }
    
    /**
     * Construtor da classe Horario. Neste construtor, é inicializado o atributo identificação
     * passado por parâmetro no momento da instância do objeto do tipo Horario. Além disso, atribui
     * null para o atributo consulta.
     * 
     * @param identificacao a identificação do horário.
     */
    public Horario(String identificacao){
        this.consulta = null;
        this.identificacao = identificacao;
    }

    /**
     * Obtém a consulta do horário.
     * @return um objeto do tipo <code>Consulta</code> representando a consulta do horário.
     */
    public Consulta getConsulta() {
        return consulta;
    }

    /**
     * Altera a consulta do horário. Para isso, uma nova consulta deve ser fornecida.
     * @param consulta a nova consulta.
     */
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    /**
     * Obtém a identificação do horário.
     * @return uma String com a identificação do horário.
     */
    public String getIdentificacao() {
        return identificacao;
    }

    /**
     * Altera a identificação do horário. Para isso, uma nova identificação deve ser fornecida.
     * @param identificacao a nova identificação.
     */
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    
    /**
     * Obtém o nome do paciente do horário.
     * @return uma String representando o nome do paciente do horário.
     */
    public String getNomePaciente() {
        return nomePaciente;
    }

    /**
     * Altera o nome do paciente associado ao horário. 
     * @param nomePaciente o novo nome do paciente.
     */
    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }    
    
    /**
     * Obtém o nome que representa o objeto quando instanciado.
     * @return uma String com um nome que representa o objeto.
     */
    @Override
    public String toString(){
        return identificacao;
    }
}
