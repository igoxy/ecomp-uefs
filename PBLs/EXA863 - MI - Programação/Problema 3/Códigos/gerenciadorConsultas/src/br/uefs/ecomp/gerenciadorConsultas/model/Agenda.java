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

import br.uefs.ecomp.gerenciadorConsultas.exceptions.ConsultaMarcadaHorarioException;
import br.uefs.ecomp.gerenciadorConsultas.exceptions.HorarioCadastradoException;
import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListHorarios;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe implementa os comportamento de uma <b>Agenda</b>. A classe contém o
 * atributo de uma agenda. Esse atributos é: um Map onde a chave é uma String
 * que representa a data e o valor é uma LinkedList de horários, onde armazena
 * os horários de cada dia.
 *
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Agenda agenda = new Agenda();</code></p>
 *
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.util.LinkedListHorarios
 */
public class Agenda {
    
    private Map<String, LinkedListHorarios> horariosDisponiveis;  //Armazena as datas e horários disponíveis
    private Map<String, LinkedListHorarios> consultasMarcadas;  //Armazena as datas e horários das consultas marcadas
    private Map<String, LinkedListHorarios> consultasRealizadas;  //Armazena as datas e horários da consultas realizadas
    
    /**
     * Construtor da classe Agenda. Inicializa o atributo agenda do tipo Map com
     * um HashMap para o armazenamento das datas e horários da agenda.
     */
    public Agenda() {
        
        this.horariosDisponiveis = new HashMap<>();
        this.consultasMarcadas = new HashMap<>();
        this.consultasRealizadas = new HashMap<>();
    }

    //Getters e Setters

    /**
     * Obtém os horários disponíveis para a marcação de consulta.
     * @return um objeto do tipo <code>Map&lt;String,LinkedListHorarios&gt;</code> representando os horários disponíveis
     * para a marcação de consulta.
     */
    public Map<String, LinkedListHorarios> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    /**
     * Altera os horários disponíveis para a marcação de consultas. Para isso, um novo Map
     * com as datas e horários deve ser fornecido.
     * 
     * @param horariosDisponiveis o Map com as datas e horários.
     */
    public void setHorariosDisponiveis(Map<String, LinkedListHorarios> horariosDisponiveis) {
        this.horariosDisponiveis = horariosDisponiveis;
    }

    /**
     * Obtém os horários das consultas marcadas.
     * @return um objeto do tipo <code>Map&lt;String,LinkedListHorarios&gt;</code> representando os horários das
     * consultas marcadas.
     */
    public Map<String, LinkedListHorarios> getConsultasMarcadas() {
        return consultasMarcadas;
    }

    /**
     * Altera os horários das consultas marcadas. Para isso, um novo Map de datas e horários
     * deve ser fornecido.
     * 
     * @param consultasMarcadas o Map com as novas datas e horários.
     */
    public void setConsultasMarcadas(Map<String, LinkedListHorarios> consultasMarcadas) {
        this.consultasMarcadas = consultasMarcadas;
    }

    /**
     * Obtém os horários das consultas realizadas.
     * @return um objeto do tipo <code>Map&lt;String,LinkedListHorarios&gt;</code> representando os horários das
     * consultas realizadas.
     */
    public Map<String, LinkedListHorarios> getConsultasRealizadas() {
        return consultasRealizadas;
    }

    /**
     * Altera os horários das consultas realizadas. Para isso, um novo Map com as novas datas
     * e horários deve ser fornecido.
     * @param consultasRealizadas o Map com as novas datas e horários.
     */
    public void setConsultasRealizadas(Map<String, LinkedListHorarios> consultasRealizadas) {
        this.consultasRealizadas = consultasRealizadas;
    }
    //Fim Getters e Setters

    //******* Métodos complementares ******
    /**
     * O método é responsável por mudar um determinado horário de uma data da
     * agenda de horários de consultas disponíveis para a agenda de consultas
     * marcadas. Deste modo, o horário não fica disponível para a marcação de
     * consultas e fica apto para o médico atender a consulta marcada.
     *
     * @param data a data do horário.
     * @param horario o horário.
     */
    public void setHorarioConsultasMarcadas(String data, Horario horario) {
        this.horariosDisponiveis.get(data).remove(horario);  //Remove o horário da lista de horários disponíveis
        try {
            this.consultasMarcadas.get(data).add(horario);  //Se a data já houver algum horário com consulta marcada, adiciona o novo horário a data
        } catch (java.lang.NullPointerException ex) {  //Se não houver consultas marcadas para aquela data
            LinkedListHorarios horarios = new LinkedListHorarios();  //Cria um novo conjunto de horários para a data
            horarios.add(horario);  //Adiciona o horário na lista de horários com consultas marcadas
            this.consultasMarcadas.put(data, horarios);  //Adicona a data e o horário da consulta marcada na agenda de consultas marcadas
        }
    }

    /**
     * O método é responsável por mudar um determinado horário de uma data da
     * agenda de horários de consultas marcadas para a agenda de consultas
     * realizadas. Deste modo, o horário não fica disponível para o médico
     * realizar a consulta, pois a consulta já foi realizada. O horário estará
     * disponível agora nos horários de consultas realizadas.
     *
     * @param data a data do horário.
     * @param horario o horário.
     */
    public void setHorarioConsultasRealizadas(String data, Horario horario) {
        this.consultasMarcadas.get(data).remove(horario); //Remove o horário da lista de horários de consultas marcadas
        try {
            this.getConsultasRealizadas().get(data).add(horario); //Se a data já houver algum horário com consulta marcada, adiciona o novo horário a data
        } catch (java.lang.NullPointerException ex) {  //Se não houver consultas realizadas para aquela data
            LinkedListHorarios horarios = new LinkedListHorarios();  //Cria um novo conjunto de horários para a data
            horarios.add(horario); //Adiciona o horário na lista de horários de consultas realizadas
            this.getConsultasRealizadas().put(data, horarios);  //Adicona a data e o horário da consulta realizada na agenda de consultas realizadas
        }
    }
    
    /**
     * O método é responsável por inserir um novo horário para a marcação de consulta
     * na agenda do médico. É verificado se o horário informado já não apresenta-se
     * cadastrado ou existem consultas para o horário. Se não houver, o horário é registrado,
     * caso contrário é lançado um erro.
     * 
     * @param data a data do horário
     * @param horarioNovo o horário
     * @throws HorarioCadastradoException lançada se o horário solicitado para cadastrar já estiver cadastrado no sistema
     * @throws ConsultaMarcadaHorarioException lançada se o horário solicitado para cadastrar apresentar consulta marcada
     */
    public void cadastrarHorario(String data, String horarioNovo) throws HorarioCadastradoException, ConsultaMarcadaHorarioException {
        try {
            LinkedListHorarios listaHorariosDisponiveis = getHorariosDisponiveis().get(data);
            try {
                /*
                Este try funciona especificamente para a obtenção dos horários de consultas marcadas. Pode ser lançada
                uma exceção de ponteiro nulo, igual pode ocorrer para obtenção dos horários disponíveis para a marcação de consultas.
                Porém se fosse utilizado somente o try maior, a exceção tanto de um quanto de outro entraria no mesmo bloco de tratamento de erro,
                contudo a depender de qual lançar a exceção o tratameto deve ser distinto.
                */
                LinkedListHorarios horariosMarcados = this.getConsultasMarcadas().get(data);  //Obtém os horários das consultas marcadas
                if (listaHorariosDisponiveis.isCadastrado(horarioNovo)) {  //Verifica se o horário está cadastrados nos horários dispoíveis para marcação de consulta.
                    throw new HorarioCadastradoException();
                } else if (horariosMarcados.isCadastrado(horarioNovo)) {  //Verifica se o horário está cadastrado tanto para uma consulta marcada
                    throw new ConsultaMarcadaHorarioException();
                } else {
                    Horario novoHorario = new Horario(horarioNovo);
                    listaHorariosDisponiveis.add(novoHorario);
                }
            } catch (NullPointerException ex1) {
                /*
                Trata a exceção de ponteiro nulo na obtenção dos horários de consultas marcadas. Se não
                houver o horário de consulta marcada, ou seja, lançar a exceção, indica que o novo horário
                que está tentando cadastrar, se estiver algum igual cadastrado, só poderá ser
                nos horários disponíveis para marcação de consultas.
                */
                if (listaHorariosDisponiveis.isCadastrado(horarioNovo)) {  //Verifica se o horário está cadastrado
                    throw new HorarioCadastradoException();
                } else {
                    Horario novoHorario = new Horario(horarioNovo);
                    listaHorariosDisponiveis.add(novoHorario);
                }
            }
        }catch(NullPointerException ex1){
            Horario novoHorario = new Horario(horarioNovo);  //Cria o novo horário
            LinkedListHorarios listaHorario = new LinkedListHorarios();  //Cria a lista de horários para a data
            listaHorario.add(novoHorario);  //Adiciona o horário a lista de horários da data
            this.getHorariosDisponiveis().put(data, listaHorario);  //Adiciona a data e o horário na agenda do médico
        }
    }
}
