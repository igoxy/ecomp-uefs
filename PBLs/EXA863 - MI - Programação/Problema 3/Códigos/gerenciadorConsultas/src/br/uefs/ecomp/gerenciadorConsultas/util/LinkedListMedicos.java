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
package br.uefs.ecomp.gerenciadorConsultas.util;  //Pacote da classe

import br.uefs.ecomp.gerenciadorConsultas.model.Consulta;
import br.uefs.ecomp.gerenciadorConsultas.model.Horario;
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
import java.util.LinkedList;
import java.util.Map;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Medico e
 * implementa os métodos, específicos da lista de médicos, para a validação das credenciais
 * do médico e remoção de médico do sistema.
 * 
 * @author Igor
 * @see java.util.LinkedList
 */
public class LinkedListMedicos extends LinkedList<Medico>{
    /**
     * Verifica se as informações de cpf e senha fornecidas pelo médico ao tentar 
     * acessar o sistema correspondem a algum dos médicos cadastrados no sistema.
     * @param cpf o CPF fornecido.
     * @param senha a senha fornecida.
     * @return true se os dados corresponderem a algum médico, false caso contrário.
     */
    public Medico validarDadosMedico(String cpf, String senha){
        for (Medico medico: this){
            if (medico.getCpf().equals(cpf) && medico.getSenha().equals(senha)){
                return medico;
            }
        }
        return null;
    }
    
    /**
     * O método verifica se o cpf não está cadastrado no sistema.
     *
     * @param cpf o cpf.
     * @return true se o cpf não estiver cadastrado no sistema, false caso
     * contrário.
     */
    public boolean cpfNaoCadastrado(String cpf) {
        return this.stream().noneMatch(medico -> (medico.getCpf().equals(cpf)));
    }
    
    /**
     * Remove o médico do sistema se o mesmo não apresentar nenhuma consulta realizada.
     * @param medico o médico a ser removido.
     * @return true se o médico foi removido, false caso contrário.
     */
    public boolean removerMedico(Medico medico){
        //int indiceMedico = this.indexOf(medico);  //Obtém o indice do medico na lista
        if (medico.getAgenda().getConsultasRealizadas().isEmpty()){
            //medico.getEspecialidade().getMedicos().remove(medico);
            Map<String, LinkedListHorarios> consultasMarcadas = medico.getAgenda().getConsultasMarcadas();
            for (LinkedListHorarios horarios: consultasMarcadas.values()){
                for (Horario horario: horarios){
                    Consulta consulta = horario.getConsulta();  //Obtém a consulta do horário
                    Paciente paciente = consulta.getPaciente();  //Obtém o paciente da consulta do horário
                    paciente.getConsultas().remove(consulta);  //Remove a consulta do médico da lista de consultas do paciente
                }     
            }
            return this.remove(medico);
        }else{
            return false;
        }
    }
}
