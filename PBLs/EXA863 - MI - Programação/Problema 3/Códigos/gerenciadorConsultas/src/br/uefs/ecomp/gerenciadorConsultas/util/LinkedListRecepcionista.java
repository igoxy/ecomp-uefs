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

import br.uefs.ecomp.gerenciadorConsultas.model.Recepcionista;
import java.util.LinkedList;

/**
 * É uma extensão da classe {@link java.util.LinkedList} do tipo Recepcionista e implementa
 * o método, específico da lista de recepcionista, para a validação das credenciais do recepcionista.
 * 
 * @author Igor
 * @see java.util.LinkedList
 */
public class LinkedListRecepcionista extends LinkedList<Recepcionista> {
    
    /**
     * Verifica se as informações de cpf e senha fornecidas pelo recepcionista ao tentar 
     * acessar o sistema correspondem a algum dos recepcionistas cadastrados.
     * 
     * @param cpf o CPF fornecido.
     * @param senha a senha fornecida.
     * @return true se os dados correspondem a algum recepcionista, false caso contrário.
     */
    public Recepcionista validarDadosRecepcionista(String cpf, String senha){
        for (Recepcionista recepcionista: this){
            if (recepcionista.getCpf().equals(cpf) && recepcionista.getSenha().equals(senha)){
                return recepcionista;
            }
        }
        return null;
        //return this.stream().anyMatch(recep -> (recep.getCpf().equals(cpf) && recep.getSenha().equals(senha)));
    }
    
    /**
     * O método verifica se o cpf não está cadastrado no sistema.
     *
     * @param cpf o cpf.
     * @return true se o cpf não estiver cadastrado no sistema, false caso
     * contrário.
     */
    public boolean cpfNaoCadastrado(String cpf) {
        return this.stream().noneMatch(recepcionista -> (recepcionista.getCpf().equals(cpf)));
    }
}
