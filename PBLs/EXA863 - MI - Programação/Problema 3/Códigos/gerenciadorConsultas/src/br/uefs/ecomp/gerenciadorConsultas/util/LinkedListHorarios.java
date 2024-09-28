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

import br.uefs.ecomp.gerenciadorConsultas.model.Horario;
import java.util.LinkedList;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Horario e
 * implementa o método, específico da lista de horários, para a verificar se
 * um determinado horário já está cadastrado.
 * 
 * @author Igor
 * @see java.util.LinkedList
 */
public class LinkedListHorarios extends LinkedList<Horario>{
    /**
     * Verifica se o horário está cadastrado na lista de horários.
     * 
     * @param horarioVerificar o horário a ser verificado.
     * @return true se o horário estiver cadastrado, false caso contrário.
     */
    public boolean isCadastrado(String horarioVerificar){
        return this.stream().anyMatch(horario -> (horario.getIdentificacao().contains(horarioVerificar)));
    }
}
