package br.uefs.ecomp.gerenciadorTarefas;


import br.uefs.ecomp.gerenciadorTarefas.model.ProjetoTest;
import br.uefs.ecomp.gerenciadorTarefas.model.SistemaTest;
import br.uefs.ecomp.gerenciadorTarefas.model.TarefaTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Executa todos os testes.
 * @author Igor
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ProjetoTest.class,
    SistemaTest.class,
    TarefaTest.class,
})

public class AllTests {}

