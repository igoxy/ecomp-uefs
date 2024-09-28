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

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes de unidade para a classe {@link Projeto}.
 * @author Igor
 */
public class ProjetoTest {
    
    private Projeto p1;
    private Tarefa t1;
    private Tarefa t2;
    private Tarefa t3;
    
    
    /**
     * Este método é executado antes de cada teste de unidade (testes a seguir),
     * e serve para inicializar objetos que são utilizados nos testes.
     * 
     * @throws Exception 
     */
    @Before
    public void setUp() throws Exception {

        p1 = new Projeto("Projeto 1");
        
        t1 = new Tarefa("Roteiros da semana", "Elaborar os roteiros das frotas para a semana.", new Date(123456789));
        
        t2 = new Tarefa("Relatório financeiro", "Elaborar o relatório financeiro do mês.", new Date(123456));
        
        t3 = new Tarefa("Conferência de carga", "Conferir a carga de saída da tarde.", new Date(6543210));
        
        
    }
    
    /**
     * Teste de unidade que verifica se os atributos de um projeto são atribuídos e modificados
     * corretamente.
     */
    @Test
    public void testBasic() {
        assertEquals("Projeto 1", p1.getNome());
        assertTrue(p1.getTarefasPendentes().isEmpty());
        
        p1.setNome("Projeto 1 Novo");
        assertEquals("Projeto 1 Novo", p1.getNome());
    
        
        p1.setNome("  p1 ");
        assertEquals("  p1 ", p1.getNome());
        
        p1.setNome("projeto1");
        assertEquals("projeto1", p1.getNome());
    }
    
    /**
     * Teste de unidade que verifica a inserção de tarefas a um projeto.
     */
    @Test
    public void testAdicionarTarefas(){
        
        p1.getTarefasPendentes().add(t1);
        assertFalse(p1.getTarefasPendentes().isEmpty());
        assertEquals(t1, p1.getTarefasPendentes().get(0));
        
        p1.getTarefasPendentes().add(t2);
        assertEquals(t1, p1.getTarefasPendentes().get(0));
        assertEquals(t2, p1.getTarefasPendentes().get(1));
        
        p1.getTarefasPendentes().add(t3);
        assertEquals(t1, p1.getTarefasPendentes().get(0));
        assertEquals(t2, p1.getTarefasPendentes().get(1));
        assertEquals(t3, p1.getTarefasPendentes().get(2));
        
        assertEquals(3, p1.getTarefasPendentes().size());
    
    }
    
    /**
     * Teste de unidade que verifica a exclusão de tarefas do sistema presentes 
     * no quadro de tarefas pendentes.
     */
    @Test
    public void testRemoverTarefasPendentes(){
        p1.getTarefasPendentes().add(t1);
        p1.getTarefasPendentes().add(t2);
        p1.getTarefasPendentes().add(t3);
        assertEquals(3, p1.getTarefasPendentes().size());
        assertFalse(p1.getTarefasPendentes().isEmpty());
        
        p1.getTarefasPendentes().remove(t2);
        assertEquals(2, p1.getTarefasPendentes().size());
        assertEquals(t1, p1.getTarefasPendentes().get(0));
        assertEquals(t3, p1.getTarefasPendentes().get(1));
        
        p1.getTarefasPendentes().remove(t1);
        assertEquals(1, p1.getTarefasPendentes().size());
        assertEquals(t3, p1.getTarefasPendentes().get(0));
        
        p1.getTarefasPendentes().remove(t3);
        assertTrue(p1.getTarefasPendentes().isEmpty()); 
    }
    
    /**
     * Teste de unidade que verifica a exclusão de tarefas do sistema presentes 
     * no quadro de tarefas em execução.
     */
    @Test
    public void testRemoverTarefasEmExecucao(){
        p1.getTarefasExecucao().add(t1);
        p1.getTarefasExecucao().add(t2);
        p1.getTarefasExecucao().add(t3);
        
        p1.getTarefasExecucao().remove(t1);
        assertEquals(2, p1.getTarefasExecucao().size());
        assertEquals(t2, p1.getTarefasExecucao().get(0));
        assertEquals(t3, p1.getTarefasExecucao().get(1));
        
        p1.getTarefasExecucao().remove(t2);
        assertEquals(1, p1.getTarefasExecucao().size());
        assertEquals(t3, p1.getTarefasExecucao().get(0));
        
        p1.getTarefasExecucao().remove(t3);
        assertTrue(p1.getTarefasExecucao().isEmpty()); 
    }
    
    
    /**
     * Teste de unidade que verifica a exclusão de tarefas do sistema presentes 
     * no quadro de tarefas concluídas.
     */
    @Test
    public void testRemoverTarefasConcluidas(){
        p1.getTarefasConcluidas().add(t1);
        p1.getTarefasConcluidas().add(t2);
        p1.getTarefasConcluidas().add(t3);
        assertEquals(3, p1.getTarefasConcluidas().size());
        
        p1.getTarefasConcluidas().remove(t3);
        assertEquals(2, p1.getTarefasConcluidas().size());
        assertEquals(t1, p1.getTarefasConcluidas().get(0));
        assertEquals(t2, p1.getTarefasConcluidas().get(1));
        
        p1.getTarefasConcluidas().remove(t1);
        assertEquals(1, p1.getTarefasConcluidas().size());
        assertEquals(t2, p1.getTarefasConcluidas().get(0));
        
        p1.getTarefasConcluidas().remove(t2);
        assertTrue(p1.getTarefasConcluidas().isEmpty());
    }
    
     /**
     * Teste de unidade que verifica se o método toString retorna o texto representando
     * o objeto corretamente. O método deve retornar uma string com nome do projeto.
     */
    @Test
    public void testToString(){
        Projeto temp1 = new Projeto("Temp1");
        Projeto temp2 = new Projeto("Temp2");
        
        assertEquals("Projeto 1", p1.toString());
        assertEquals("Temp1", temp1.toString());
        assertEquals("Temp2", temp2.toString());
    }
    
    /**
     * Teste de unidade que verifica a troca de tarefas entre as listas de acordo 
     * com seus status.
     */
    @Test
    public void testTrocarTarefasDeQuadro(){
        p1.getTarefasPendentes().add(t1);
        p1.getTarefasPendentes().add(t2);
        p1.getTarefasPendentes().add(t3);
        
        //Transferindo a tarefa de PENDENTE para EM_EXECUCAO
        assertTrue(p1.getTarefasExecucao().isEmpty()); //Execucao
        p1.trocarTarefaQuadro(t1, StatusTarefa.EM_EXECUCAO); //Troca
        assertEquals(2, p1.getTarefasPendentes().size());  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(0)); //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(1)); //Pendente
        assertEquals(1, p1.getTarefasExecucao().size());  //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0)); //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus());
        
        assertFalse(p1.getTarefasExecucao().isEmpty());  //Execucao
        p1.trocarTarefaQuadro(t2, StatusTarefa.EM_EXECUCAO);  //Troca
        assertEquals(1, p1.getTarefasPendentes().size());  //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(0)); //Tarefa pendente
        assertEquals(2, p1.getTarefasExecucao().size()); //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0)); //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(1)); //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(1).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.EM_EXECUCAO);  //Execucao
        assertTrue(p1.getTarefasPendentes().isEmpty()); //Pendente
        assertEquals(3, p1.getTarefasExecucao().size()); //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0)); //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(1)); //Execucao
        assertEquals(t3, p1.getTarefasExecucao().get(2)); //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(1).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(2).getStatus()); //Verifica o Status
        
        //Transferindo a tarefa de EM_EXECUCAO para CONCLUIDA
        assertTrue(p1.getTarefasConcluidas().isEmpty()); //Concluida
        p1.trocarTarefaQuadro(t1, StatusTarefa.CONCLUIDA); //Troca
        assertEquals(2, p1.getTarefasExecucao().size()); //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(0)); //Execucao
        assertEquals(t3, p1.getTarefasExecucao().get(1)); //Execucao
        assertEquals(1, p1.getTarefasConcluidas().size());  //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0));  //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t2, StatusTarefa.CONCLUIDA); //Troca
        assertEquals(1, p1.getTarefasExecucao().size()); //Execucao
        assertEquals(t3, p1.getTarefasExecucao().get(0)); //Execucao
        assertEquals(2, p1.getTarefasConcluidas().size()); //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(1));  //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus());  //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(1).getStatus());  //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.CONCLUIDA); //Troca
        assertTrue(p1.getTarefasExecucao().isEmpty()); //Execucao
        assertEquals(3, p1.getTarefasConcluidas().size()); //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(1)); //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(2)); //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus());  //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(1).getStatus());  //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(2).getStatus());  //Verifica o Status
        assertFalse(p1.getTarefasConcluidas().isEmpty());  //Concluida 
        
        //Transferindo a tarefa de CONCLUIDA para EM_EXECUCAO
        assertTrue(p1.getTarefasExecucao().isEmpty());
        p1.trocarTarefaQuadro(t1, StatusTarefa.EM_EXECUCAO); //Troca
        assertEquals(2, p1.getTarefasConcluidas().size());  //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(1)); //Concluida
        assertEquals(1, p1.getTarefasExecucao().size());   //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0));  //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t2, StatusTarefa.EM_EXECUCAO); //Troca
        assertEquals(1, p1.getTarefasConcluidas().size()); //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(2, p1.getTarefasExecucao().size());  //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0));  //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(1));  //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus());  //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(1).getStatus());  //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.EM_EXECUCAO); //Troca
        assertTrue(p1.getTarefasConcluidas().isEmpty());  //Concluida
        assertEquals(3, p1.getTarefasExecucao().size());  //Execucao
        assertEquals(t1, p1.getTarefasExecucao().get(0));  //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(1));  //Execucao
        assertEquals(t3, p1.getTarefasExecucao().get(2)); //Execucao
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(1).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.EM_EXECUCAO, p1.getTarefasExecucao().get(2).getStatus()); //Verifica o Status
        
        //Transferindo a tarefa de EM_EXECUCAO para PENDENTE
        assertTrue(p1.getTarefasPendentes().isEmpty());  //Pendente
        p1.trocarTarefaQuadro(t1, StatusTarefa.PENDENTE); //Troca
        assertEquals(2, p1.getTarefasExecucao().size());  //Execucao
        assertEquals(t2, p1.getTarefasExecucao().get(0)); //Execaucao
        assertEquals(t3, p1.getTarefasExecucao().get(1)); //Execucao
        assertEquals(1, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t2, StatusTarefa.PENDENTE); //Troca
        assertEquals(1, p1.getTarefasExecucao().size());  //Execucao
        assertEquals(t3, p1.getTarefasExecucao().get(0)); //Execaucao
        assertEquals(2, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(1));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(1).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.PENDENTE); //Troca
        assertTrue(p1.getTarefasExecucao().isEmpty());  //Execucao
        assertEquals(3, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(1));  //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(2));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(1).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(2).getStatus()); //Verifica o Status
        
        //Transferindo a tarefa de PENDENTE para CONCLUIDA
        assertTrue(p1.getTarefasConcluidas().isEmpty());  //Concluida
        p1.trocarTarefaQuadro(t1, StatusTarefa.CONCLUIDA); //Troca
        assertEquals(2, p1.getTarefasPendentes().size());  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(0)); //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(1)); //Pendente
        assertEquals(1, p1.getTarefasConcluidas().size());   //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0));  //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus()); //Verifica o Status
        assertFalse(p1.getTarefasConcluidas().isEmpty());
        
        p1.trocarTarefaQuadro(t2, StatusTarefa.CONCLUIDA); //Troca
        assertEquals(1, p1.getTarefasPendentes().size());  //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(0)); //Pendente
        assertEquals(2, p1.getTarefasConcluidas().size());   //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0));  //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(1));  //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(1).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.CONCLUIDA); //Troca
        assertTrue(p1.getTarefasPendentes().isEmpty());  //Pendente
        assertEquals(3, p1.getTarefasConcluidas().size());   //Concluida
        assertEquals(t1, p1.getTarefasConcluidas().get(0));  //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(1));  //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(2));  //Concluida
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(1).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.CONCLUIDA, p1.getTarefasConcluidas().get(2).getStatus()); //Verifica o Status
        
        //Transferindo a tarefa de CONCLUIDA para PENDENTE
        assertTrue(p1.getTarefasPendentes().isEmpty());  //Pendente
        p1.trocarTarefaQuadro(t1, StatusTarefa.PENDENTE); //Troca
        assertEquals(2, p1.getTarefasConcluidas().size());  //Concluida
        assertEquals(t2, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(1)); //Concluida
        assertEquals(1, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        assertFalse(p1.getTarefasPendentes().isEmpty());  //Pendente
        
        p1.trocarTarefaQuadro(t2, StatusTarefa.PENDENTE); //Troca
        assertEquals(1, p1.getTarefasConcluidas().size());  //Concluida
        assertEquals(t3, p1.getTarefasConcluidas().get(0)); //Concluida
        assertEquals(2, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(1));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(1).getStatus()); //Verifica o Status
        
        p1.trocarTarefaQuadro(t3, StatusTarefa.PENDENTE); //Troca
        assertTrue(p1.getTarefasConcluidas().isEmpty()); //Concluida
        assertEquals(3, p1.getTarefasPendentes().size());   //Pendente
        assertEquals(t1, p1.getTarefasPendentes().get(0));  //Pendente
        assertEquals(t2, p1.getTarefasPendentes().get(1));  //Pendente
        assertEquals(t3, p1.getTarefasPendentes().get(2));  //Pendente
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(0).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(1).getStatus()); //Verifica o Status
        assertEquals(StatusTarefa.PENDENTE, p1.getTarefasPendentes().get(2).getStatus()); //Verifica o Status
    } 
}
