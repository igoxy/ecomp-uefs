/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 19/10/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.gerenciadorTarefas.util;  //Pacote da classe

import br.uefs.ecomp.gerenciadorTarefas.model.Projeto;
import java.util.LinkedList;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Projeto e implementa
 * o método, específico da lista de projetos, para a remoção de projetos do sistema.
 * 
 * @see java.util.LinkedList
 * @author Igor
 */
public class LinkedListSistema extends LinkedList<Projeto>{
    
    /**
     * Remove o projeto caso não apresente tarefas pendentes e/ou em execução.
     * 
     * @param projeto o projeto a ser removido.
     */
    public void removerProjeto(Projeto projeto){
        if (projeto.getTarefasPendentes().isEmpty() && projeto.getTarefasExecucao().isEmpty()){  //Verifica se o projeto só tem tarefas concluídas ou não há tarefas
            remove(projeto);  //Remove o projeto da lista de projetos.
        }
    }
}
