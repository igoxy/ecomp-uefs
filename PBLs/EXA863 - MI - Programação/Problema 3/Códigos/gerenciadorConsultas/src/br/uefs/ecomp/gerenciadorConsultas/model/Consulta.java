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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A classe implementa o comportamento de uma <b>Consulta</b>. A classe contém os atributos
 * de uma consulta. Esses atributos são: o médico da consulta, a data da consulta, o horário
 * da consulta, o prontuário da consulta, o paciente da consulta e se a consulta já foi efetuada.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Medico medico = new Medico("nome","crm","123","senha", new Especialidade("ex1"), new Especialidade("ex2"));</code></p>
 * <p><code>Consulta consulta = new Consulta(medico, "01/01/01", "00:00");</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Medico
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Prontuario
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Paciente
 */
public class Consulta {
    private Medico medico;
    private Prontuario prontuario;
    private String data;
    private String horario;
    private boolean efetuada;  //Identifica se a consulta já foi efetuada.
    private String nomePaciente;  //Identifica o paciente da consulta
    private Paciente paciente;
    
    /**
     * Construtor da classe Consulta. Inicializa os atributos medico, data e horário passados
     * por parâmetro no momento da instância do objeto do tipo Consulta. Além disso, inicializa
     * o atributo efetuada, do tipo <code>boolean</code> que indica se a consulta já foi efetuada,
     * com false e cria um prontuário para a consulta.
     * 
     * @param medico o médico da consulta.
     * @param data a data da consulta.
     * @param horario o horário da consulta.
     */
    public Consulta(Medico medico, String data, String horario){
        this.medico = medico;
        this.data = data;
        this.horario = horario;
        this.efetuada = false;
        this.prontuario = new Prontuario();
    }

    /**
     * Obtém o prontuário da consulta.
     * @return um objeto do tipo <code>Prontuario</code> representando o prontuário da consulta.
     */
    public Prontuario getProntuario() {
        return prontuario;
    }

    /**
     * Altera o prontuário da consulta. Para isso, um novo prontuário deve ser fornecido.
     * @param prontuario o novo prontuário para a consulta.
     */
    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    /**
     * Obtém o médico da consulta.
     * @return o objeto do tipo <code>Medico</code> representando o médico da consulta.
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Altera o médico da consulta. Para isso, um novo médico deve ser fornecido.
     * @param medico o novo médico para a consulta.
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    /**
     * Indica se a consulta ja foi efetuada, ou seja, indica se a consulta
     * já foi atendida ou não pelo médico.
     * @return true se a consulta já foi efetuada, false caso contrário.
     */
    public boolean isEfetuada() {
        return efetuada;
    }

    /**
     * Altera a indicação se a consulta já foi efetuada ou não. Para isso, um boolean deve ser fornecido. 
     * @param Efetuada o <code>boolean</code> indicando se a consulta já foi efetuada ou não.
     */
    public void setEfetuada(boolean Efetuada) {
        this.efetuada = Efetuada;
    }

    /**
     * Obtém o nome do paciente da consulta.
     * @return uma String representando o nome do paciente relacionado a consulta.
     */
    public String getNomePaciente() {
        return nomePaciente;
    }

    /**
     * Altera o nome do paciente da consulta. Para isso, um novo nome deve ser fornecido.
     * @param nomePaciente o novo nome do paciente da consulta.
     */
    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    /**
     * Obtém o paciente relacionado a consulta.
     * @return um objeto do tipo <code>Paciente</code> representando o paciente.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Altera o paciente relacionado a consulta. Para isso, um novo paciente deve ser fornecido.
     * @param paciente o novo paciente da consulta.
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * Obtém a data da consulta.
     * @return uma String representando a data da consulta.
     */
    public String getData() {
        return data;
    }

    /**
     * Altera a data da consulta. Para isso, uma nova data deve ser fornecida.
     * @param data a nova data da consulta.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obtém o horário da consulta.
     * @return uma String representando o horário da consulta. 
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Altera o horário da consulta. Para isso, um novo horário deve ser fornecido.
     * @param horario o novo horário da consulta.
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    /**
     * Obtém o nome que representa o objeto quando instanciado. O nome que representa
     * um objeto do tipo Consulta tem: a data da consulta, o horário, o médico e 
     * se a consulta foi efetuada.
     * @return uma String com um nome que representa o objeto.
     */
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String s = "";
        try {
            s = formatter.format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(getData()));
            
        } catch (ParseException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        String toString = "Data: " + s + "| Horário: " + getHorario() + "\nMédico: " + medico.toString();
        if (isEfetuada()) {
            toString = toString.concat("\nConsulta Efetuada.");
        } else {
            toString = toString.concat("\nConsulta Pendente.");
        }
        return toString;
    }
}
