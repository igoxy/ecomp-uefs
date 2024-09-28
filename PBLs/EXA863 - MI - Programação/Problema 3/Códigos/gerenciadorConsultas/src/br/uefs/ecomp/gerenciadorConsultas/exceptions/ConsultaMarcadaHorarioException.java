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
package br.uefs.ecomp.gerenciadorConsultas.exceptions;  //Pacote da classe

/**
 * Implementa o comportamento de uma exceção. A classe estende de {@link Exception}
 * e sobrescreve o método <code>getMessage</code>. Essa exceção é lançada se for solicitado
 * a criação de um novo horário para a marcação de consulta e o horário já apresentar uma 
 * consulta marcada.
 * 
 * @author Igor
 */
public class ConsultaMarcadaHorarioException extends Exception{
    
    /**
     * O método retorna a mensagem de erro.
     * 
     * @return uma String com a mensagem de erro.
     */
    @Override
    public String getMessage(){
        return "Já existe uma consulta marcada para o horário";
    }
}
