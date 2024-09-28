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


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes de unidade para a classe {@link StatusTarefa}.
 * @author Igor
 */
public class StatusTarefaTest {
    
    private StatusTarefa st1;
    private StatusTarefa st2;
    private StatusTarefa st3;
    
    
    /**
    * Este método é executado antes de cada teste de unidade (testes a seguir),
    * e serve para inicializar objetos que são utilizados nos testes.
    */
    @Before
    public void setUp() {
        st1 = StatusTarefa.PENDENTE;
        st2 = StatusTarefa.EM_EXECUCAO;
        st3 = StatusTarefa.CONCLUIDA;
    }

    /**
     * Teste para verificar se o método que retorna a String referente ao tipo de 
     * enum funciona corretamente.
     */
    @Test
    public void testBasic() {
        assertEquals("Pendente", st1.toString());
        assertEquals("Em execução", st2.toString());
        assertEquals("Concluída", st3.toString());
        
    }
}