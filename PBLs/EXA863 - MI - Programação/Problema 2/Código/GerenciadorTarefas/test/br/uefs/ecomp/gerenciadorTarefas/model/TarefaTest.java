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

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes de unidades para a classe {@link Tarefa}.
 * @author Igor
 */
public class TarefaTest {
    
    private Tarefa t1; 
    
    /**
    * Este método é executado antes de cada teste de unidade (testes a seguir),
    * e serve para inicializar objetos que são utilizados nos testes.
    */
    @Before
    public void setUp() {
        Date data = (new GregorianCalendar(2025, 9, 18, 13, 30, 00)).getTime(); // O mês contabiliza de 0 a 11 no GregorianCalendar(ANO, MÊS, DIA, HORA, MINUTOS, SEGUNDOS).
        t1 = new Tarefa("Tarefa1", "Executar análise de dados", data);
    }
    
    /**
     * Teste de unidade que verifica se os atributos de uma tarefa são atribuídos e
     * modificados corretamente.
     */
    @Test
    public void testBasic() {
        assertEquals("Tarefa1", t1.getNome());
        assertEquals("Executar análise de dados", t1.getDescricao());
        assertEquals((new GregorianCalendar(2025, 9, 18, 13, 30, 00)).getTime(), t1.getDataConclusao());
        assertEquals(StatusTarefa.PENDENTE, t1.getStatus());
        
        t1.setNome("Tarefa1 Novo");
        t1.setDescricao("Executar análise de dados2");
        t1.setDataConclusao((new GregorianCalendar(2024, 8, 17, 12, 00, 00)).getTime());
        t1.setStatus(StatusTarefa.EM_EXECUCAO);
        assertEquals("Tarefa1 Novo", t1.getNome());
        assertEquals("Executar análise de dados2", t1.getDescricao());
        assertEquals((new GregorianCalendar(2024, 8, 17, 12, 00, 00)).getTime(), t1.getDataConclusao());
        assertEquals(StatusTarefa.EM_EXECUCAO, t1.getStatus());
        
        t1.setNome("Tarefa1 Novo2");
        t1.setDescricao("Executar análise de dados3");
        t1.setDataConclusao((new GregorianCalendar(2023, 8, 17, 12, 00, 00)).getTime());
        t1.setStatus(StatusTarefa.CONCLUIDA);
        assertEquals("Tarefa1 Novo2", t1.getNome());
        assertEquals("Executar análise de dados3", t1.getDescricao());
        assertEquals((new GregorianCalendar(2023, 8, 17, 12, 00, 00)).getTime(), t1.getDataConclusao());
        assertEquals(StatusTarefa.CONCLUIDA, t1.getStatus());
    }
    
    /**
     *
     * Teste de unidade que verifica se o método toString retorna uma String representando
     * o objeto corretamente.
     */
    @Test
    public void testToString(){
        
        Tarefa temp1 = new Tarefa("temp1", "temp1", new Date(1234567));
        Tarefa temp2 = new Tarefa("temp2", "temp2", new Date(99876543));
        
        String stringTemp1 = "Nome: temp1\nDescrição: temp1\nData prevista: 31/12/1969\nAtrasada";
        String stringTemp2 = "Nome: temp2\nDescrição: temp2\nData prevista: 02/01/1970\nAtrasada";
        String stringT1 = "Nome: Tarefa1\nDescrição: Executar análise de dados\nData prevista: 18/10/2025";
        
        assertEquals(stringT1, t1.toString());
        assertEquals(stringTemp1, temp1.toString());
        assertEquals(stringTemp2, temp2.toString());
    }
    /**
     * Teste de unidade que verifica se foi implementado corretamente o 
     * método para verificar se uma tarefa está atrasada.
     * 
     * @throws java.text.ParseException lançada caso ocorra um erro ao converter a data.
     */
    @Test
    public void testVerificarAtraso() throws ParseException{
        //Não atrasadas
        assertFalse(t1.isAtrasada());
        
        t1.setDataConclusao((new GregorianCalendar(2024, 8, 17, 12, 00, 00)).getTime());
        assertFalse(t1.isAtrasada());
        
        Tarefa temp = new Tarefa("Tarefa2", "Varrer galpão.", (new GregorianCalendar(2023, 8, 17, 18, 00, 00)).getTime());
        assertFalse(temp.isAtrasada());
        
        //Atrasadas
        Tarefa temp1 = new Tarefa("Tarefa3", "Carregar caminhão", (new GregorianCalendar(2019, 8, 20, 9, 00, 00)).getTime());
        assertTrue(temp1.isAtrasada());
        
        t1.setDataConclusao(new Date(132456));
        assertTrue(t1.isAtrasada());
        
        temp1.setDataConclusao((new GregorianCalendar(21, 1, 1, 10, 45, 10)).getTime());
        assertTrue(temp1.isAtrasada());
    }
}
