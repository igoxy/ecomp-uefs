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
package br.uefs.ecomp.gerenciadorTarefas.model;  //Pacote da classe de teste

import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes de unidade para a classe {@link Sistema}.
 * @author Igor
 */
public class SistemaTest {
    
    private Projeto p1, p2, p3;
    private Tarefa t1, t2, t3, t4, t5, t6;
        
    /**
    * Este método é executado antes de cada teste de unidade (testes a seguir),
    * e serve para inicializar objetos que são utilizados nos testes.
    */
    @Before
    public void setUp() {

        p1 = new Projeto("P1");
        p2 = new Projeto("P2");
        p3 = new Projeto("P3");
        
        t1 = new Tarefa("Tarefa1", "Descrição1", (new GregorianCalendar(2022, 10, 10, 7, 30, 00)).getTime());
        t2 = new Tarefa("Tarefa2", "Descrição2", (new GregorianCalendar(2023, 5, 7, 20, 30, 00)).getTime());
        t3 = new Tarefa("Tarefa3", "Descrição3", (new GregorianCalendar(2020, 9, 18, 17, 30, 00)).getTime());
        
        t4 = new Tarefa("Tarefa4", "Descrição4", (new GregorianCalendar(2025, 10, 10, 7, 30, 00)).getTime());
        t5 = new Tarefa("Tarefa5", "Descrição5", (new GregorianCalendar(2024, 5, 7, 20, 30, 00)).getTime());
        t6 = new Tarefa("Tarefa6", "Descrição6", (new GregorianCalendar(2019, 9, 18, 17, 30, 00)).getTime());
           
    }

    /**
     * Teste de unidade para verificar se adição de projetos ao sistema está
     * ocorrendo corretamente.
     */
    @Test
    public void testAdcionarProjetos() {
        assertEquals(0, Sistema.getProjetos().size());
        assertTrue(Sistema.getProjetos().isEmpty());
        
        Sistema.getProjetos().add(p1);
        Sistema.getProjetos().add(p2);
        Sistema.getProjetos().add(p3);
        assertEquals(3, Sistema.getProjetos().size());
        
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertEquals(p3, Sistema.getProjetos().get(2));
        
        assertFalse(Sistema.getProjetos().isEmpty());
    }
    
    /**
     * Teste de unidade que verifica a remoção de projetos do sistema.
     */
    @Test
    public void testRemoverProjeto(){
        //Todas a tarefas pendentes projetos P1
        assertTrue(Sistema.getProjetos().isEmpty());
        Sistema.getProjetos().add(p1);
        Sistema.getProjetos().get(0).getTarefasPendentes().add(t1);
        Sistema.getProjetos().get(0).getTarefasPendentes().add(t2);
        Sistema.getProjetos().get(0).getTarefasPendentes().add(t3);
        assertEquals(1, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertFalse(Sistema.getProjetos().isEmpty());
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(1, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        
        //Todas as tarefas em execução projeto P2
        Sistema.getProjetos().add(p2);
        Sistema.getProjetos().get(1).getTarefasPendentes().add(t4);
        Sistema.getProjetos().get(1).getTarefasPendentes().add(t5);
        Sistema.getProjetos().get(1).getTarefasPendentes().add(t6);
        Sistema.getProjetos().get(1).trocarTarefaQuadro(t4, StatusTarefa.EM_EXECUCAO);
        Sistema.getProjetos().get(1).trocarTarefaQuadro(t5, StatusTarefa.EM_EXECUCAO);
        Sistema.getProjetos().get(1).trocarTarefaQuadro(t6, StatusTarefa.EM_EXECUCAO);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        Sistema.getProjetos().removerProjeto(p2);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Sem tarefas projeto P3
        Sistema.getProjetos().add(p3);
        assertEquals(3, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertEquals(p3, Sistema.getProjetos().get(2));
        assertFalse(Sistema.getProjetos().isEmpty());
        Sistema.getProjetos().removerProjeto(p3);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Tarefas pendente e em execução do projeto P1
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t3, StatusTarefa.EM_EXECUCAO);
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Tarefas pendete e concluída do projeto P1
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t3, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Tarefas pendente, em execução e concluída projeto P1
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t2, StatusTarefa.EM_EXECUCAO);
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Tarefas em execução e concluída
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t1, StatusTarefa.EM_EXECUCAO);
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(2, Sistema.getProjetos().size());
        assertEquals(p1, Sistema.getProjetos().get(0));
        assertEquals(p2, Sistema.getProjetos().get(1));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Tarefas concluídas do projeto P1
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t1, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t2, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().removerProjeto(p1);
        assertEquals(1, Sistema.getProjetos().size());
        assertEquals(p2, Sistema.getProjetos().get(0));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Uma tarefa em execução e duas concluídas do projeto P2
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t4, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t5, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().removerProjeto(p2);
        assertEquals(1, Sistema.getProjetos().size());
        assertEquals(p2, Sistema.getProjetos().get(0));
        assertFalse(Sistema.getProjetos().isEmpty());
        
        //Todas concluídas do projeto P2
        Sistema.getProjetos().get(0).trocarTarefaQuadro(t6, StatusTarefa.CONCLUIDA);
        Sistema.getProjetos().removerProjeto(p2);
        assertEquals(0, Sistema.getProjetos().size());
        assertTrue(Sistema.getProjetos().isEmpty());
    }
}