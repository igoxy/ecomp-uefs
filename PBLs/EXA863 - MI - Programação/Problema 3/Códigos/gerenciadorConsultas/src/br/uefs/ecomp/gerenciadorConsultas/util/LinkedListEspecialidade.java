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

import br.uefs.ecomp.gerenciadorConsultas.model.Especialidade;
import java.util.LinkedList;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Especialidade e
 * implementa o método, específico da lista de especialidades, para a remoção de
 * especialidades do sistema.
 *
 * @author Igor
 * @see java.util.LinkedList
 */
public class LinkedListEspecialidade extends LinkedList<Especialidade> {
    /**
     * Remove a especialidade do sistema se não apresentar algum médico associado.
     * 
     * @param especialidade a especialidade a ser removida.
     */
    public void removerEspecialidade(Especialidade especialidade) {
        if (especialidade.getMedicos().isEmpty()) {  //Verifica se a especialidade selecionada não apresentar médico associado
            remove(especialidade);  //Remove a especialidade da lista de especialidades
        }

    }
}
