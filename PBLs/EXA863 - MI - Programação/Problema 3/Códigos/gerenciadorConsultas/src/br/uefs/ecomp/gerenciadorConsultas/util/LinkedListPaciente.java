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
import br.uefs.ecomp.gerenciadorConsultas.model.Medico;
import br.uefs.ecomp.gerenciadorConsultas.model.Paciente;
import java.util.LinkedList;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Paciente e
 * implementa os métodos, específicos da lista de pacientes, para a validação das credenciais
 * do paciente, busca de paciente e remoção de paciente do sistema.
 * @author Igor
 * @see java.util.LinkedList
 */
public class LinkedListPaciente extends LinkedList<Paciente>{
    /**
     * Verifica se as informações de cpf e senha fornecidas correspondem a algum 
     * dos pacientes cadastrados no sistema.
     * @param nome o nome fornecido.
     * @param cpf o CPF fornecido.
     * @return true se os dados corresponderem a algum paciente, false caso contrário
     */
    public boolean validarDadosPaciente(String nome, String cpf){
        return this.stream().anyMatch(paciente -> (paciente.getCpf().equals(cpf) && paciente.getNome().equals(nome)));
    }
    
    /**
     * O método verifica se o cpf não está cadastrado no sistema.
     * 
     * @param cpf o cpf.
     * @return true se o cpf não estiver cadastrado no sistema, false caso contrário.
     */
    public boolean cpfNaoCadastrado(String cpf){
        return this.stream().noneMatch(paciente -> (paciente.getCpf().equals(cpf)));
    }
    
    /**
     * Busca um paciente na lista por meio do seu nome e CPF.
     * 
     * @param nome o nome do paciente.
     * @param cpf o CPF do paciente.
     * @return o objeto do tipo <b>Paciente</b> se o paciente for encontrado, <b>null</b> caso contrário.
     */
    public Paciente buscarPaciente(String nome, String cpf){
        for (Paciente paciente: this){
            if (paciente.getNome().equals(nome) && paciente.getCpf().equals(cpf)){
                return paciente;  //Se encontrar retorna o paciente
            }
        }
        return null;  //Se não encontrar, retorna null
    }
    
    /**
     * Remove o paciente do sistema se o mesmo não apresentar nenhuma consulta realizada.
     * @param paciente o paciente a ser removido do sistema.
     * @return true caso o paciente seja removido, false caso contrário.
     */
    public boolean removerPaciente(Paciente paciente){
        LinkedList<Consulta> consultas = paciente.getConsultas();  //Obtém a lista de consultas do paciente
        if (!consultas.stream().noneMatch(consulta -> (consulta.isEfetuada()))) {  //Verifica se o paciente tem consultas efetuadas
            return false;  //Não remove o paciente se já houver consultas efetuadas
        }else{
            LinkedListHorarios remover = new LinkedListHorarios();
            for (Consulta consulta: consultas){ //Percorre as consultas marcadas do paciente
                Medico med = consulta.getMedico();  //Obtém o médico da consulta
                med.getAgenda().getConsultasMarcadas().values().stream().map(horarios -> { //Percorre a agenda de consultas marcadas do médico
                    horarios.forEach(horario -> { //Percorre os horários de consultas marcadas do médico 
                        if (horario.getConsulta().getPaciente() == paciente) { // verifica se o paciente da consulta é o mesmo a ser excluído
                            remover.add(horario); //Adiciona o horário na lista de horários a ser removido - O horário é removido pois pertece ao paciente que está sendo excluído do sistema
                        }
                    });
                    return horarios; 
                }).forEachOrdered(horarios -> {
                    int tamanhoRemover = remover.size();  //Obtém o tamanho da lista de horários a ser removido
                    for (int i = 0; i < tamanhoRemover; i++){  //Itera toda lista de horários a ser removidos, removendo-os.
                        horarios.remove(remover.remove());  //Remove o horário da lista de horários a ser removidos e da agenda no médico
                    }
                });
            }
            return this.remove(paciente);  //Remove o paciente se não houver consultas efetuadas
            
        }   
    }     
}
