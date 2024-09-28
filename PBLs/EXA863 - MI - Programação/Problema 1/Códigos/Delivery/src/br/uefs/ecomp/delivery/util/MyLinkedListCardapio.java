/*******************************************************************************
Autor: Igor Figueredo Soares
Componente Curricular: MI - PROGRAMAÇÃO
Concluido em: 14/09/2021
Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
trecho de código de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************************************************************************/
package br.uefs.ecomp.delivery.util;

import br.uefs.ecomp.delivery.model.Cardapio;
import java.util.Iterator;

/**
 * É uma extensão da classe {@link br.uefs.ecomp.delivery.util.MyLinkedList} 
 * do tipo Cardapio e implementa o método, específico da lista de cardápio,
 * reponsável por exibir os pratos cadastrados no sistema.
 * 
 * @author Igor
 * @see br.uefs.ecomp.delivery.model.Cardapio
 * @see br.uefs.ecomp.delivery.util.MyLinkedList
 */
public class MyLinkedListCardapio extends MyLinkedList<Cardapio>{
    
    /**
     * Retorna todas as opções de pratos cadastrados no sistema.
     * 
     * @return uma String com as opções do cardápio.
     */
    public String showCardapio(){
        String pratos = "";  //Armazena as descrições que referenciam cada prato do cardápio
        for (Iterator<Cardapio> it = this.iterator(); it.hasNext();){
            Cardapio item = it.next();
            if ("".equals(pratos)) {
                pratos = pratos.concat(item.getDescricao());
            }
            else {
                pratos = pratos.concat("\n".concat(item.getDescricao()));
            }
        } 
        return pratos;
    }  
}
