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
package br.uefs.ecomp.gerenciadorConsultas.model; //Pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListEspecialidade;
import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListMedicos;
import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListPaciente;
import br.uefs.ecomp.gerenciadorConsultas.util.LinkedListRecepcionista;

/**
 * A classe implementa os comportamentos do <b>Sistema</b>. Deste modo, apresenta os atributos
 * que compõe o sistema, sendo eles:
 * <ul>
 *  <li>Lista de médicos: armazena os médicos cadastrados no sistema.</li>
 *  <li>Lista de especialidades: armazena as especialidades cadastradas no sistema.</li>
 *  <li>Lista de pacinetes: armazena todos os pacientes cadastrados no sistema.</li>
 *  <li>Lista de recepcionistas: armazena todos os recepcionistas cadastrados no sistema.</li>
 * </ul>
 * 
 * <p><b> Exemplo de uso</b>:</p>
 * <p><code>Sistema sistema = new Sistema();</code></p>
 * 
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.util.LinkedListEspecialidade
 * @see br.uefs.ecomp.gerenciadorConsultas.util.LinkedListMedicos
 * @see br.uefs.ecomp.gerenciadorConsultas.util.LinkedListPaciente
 * @see br.uefs.ecomp.gerenciadorConsultas.util.LinkedListRecepcionista
 */
public class Sistema {
    private static LinkedListMedicos medicos = new LinkedListMedicos();
    private static LinkedListEspecialidade especialidades = new LinkedListEspecialidade();
    private static LinkedListPaciente pacientes = new LinkedListPaciente();
    private static LinkedListRecepcionista recepcionistas = new LinkedListRecepcionista();
    
    /**
     * Construtor da classe Sistema. Construtor vazio.
     */
    public Sistema(){
        //Vazio
    }
    
    /**
     * Obtém os médicos cadastrados no sistema.
     * @return um objeto do tipo <code>LinkedListMedicos</code> representando a lista de médicos.
     */
    public static LinkedListMedicos getMedicos() {
        return medicos;
    }

    /**
     * Altera a lista de médicos  cadastrados no sistema. Para isso, uma nova lista deve ser fornecida.
     * @param medicos a nova lista de médicos cadastrados no sistema.
     */
    public static void setMedicos(LinkedListMedicos medicos) {
        Sistema.medicos = medicos;
    }

    /**
     * Obtém a lista de especialidades cadastradas no sistema.
     * @return um objeto do tipo <code>LinkedListEspecialidade</code> representado a lista de especialidades cadastradas no sistema.
     */
    public static LinkedListEspecialidade getEspecialidades() {
        return especialidades;
    }

    /**
     * Altera a lista de especialidades cadastradas no sistema. Para isso, uma nova lista de especialidades deve ser fornecida.
     * @param especialidades a nova lista de especialidades.
     */
    public static void setEspecialidades(LinkedListEspecialidade especialidades) {
        Sistema.especialidades = especialidades;
    }

    /**
     * obtém a lista de pacientes cadastrados no sistema.
     * @return um objeto do tipo <code>LinkedListPaciente</code> representando a lista de pacientes cadastrados no sistema.
     */
    public static LinkedListPaciente getPacientes() {
        return pacientes;
    }

    /**
     * Altera a lista de pacientes cadastrados no sistema. Para isso, uma nova lista deve ser fornecida.
     * @param pacientes a nova lista de pacientes cadastrados no sistema.
     */
    public static void setPacientes(LinkedListPaciente pacientes) {
        Sistema.pacientes = pacientes;
    }

    /**
     * Obtém a lista de recepcionistas cadastrados no sistema.
     * @return um objeto do tipo <code>LinkedListRecepcionista</code> representando a lista de recepcionistas cadastrados no sistema.
     */
    public static LinkedListRecepcionista getRecepcionistas() {
        return recepcionistas;
    }

    /**
     * Altera a lista de recepcionistas cadastrados no sistema. Para isso, uma nova lista deve ser fornecida.
     * @param recepcionistas a nova lista de recepcionistas cadastrados no sistema.
     */
    public static void setRecepcionistas(LinkedListRecepcionista recepcionistas) {
        Sistema.recepcionistas = recepcionistas;
    }
}
