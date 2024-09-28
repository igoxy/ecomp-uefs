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
package br.uefs.ecomp.gerenciadorTarefas.model;  //Pacote da classe

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A classe implementa o comportamento de uma <b>Tarefa</b>. A classe contém os atributos
 * de uma tarefa. Esses atributos são: nome, descrição, data prevista para conclusão
 * e o status da tarefa.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Tarefa tarefa = new Tarefa("nome", "descrição", new Date(6543210));</code></p>
 * 
 * @author Igor
 * @see StatusTarefa
 */
public class Tarefa {
    private String nome;  //Nome da tarefa
    private String descricao;  //Descrição da tarefa
    private Date dataConclusao;  //Data prevista para a conclusão
    private StatusTarefa status;  //Status da tarefa
    
    /**
     * Construtor da classe Tarefa. Inicializa os atributos nome, descricao e data
     * passados por parâmetro no momento da instância do objeto do tipo Tarefa.
     * Além disso, inicializa o atributo status do tipo <code>enum</code> PENDENTE,
     * ou seja, atribui a tarefa, na sua criação, o status de pendente.
     * 
     * @param nome o nome da tarefa.
     * @param descricao a descrição da tarefa.
     * @param data a data prevista para a conclusão da tarefa.
     */
    public Tarefa(String nome, String descricao, Date data){
        this.nome = nome;
        this.descricao = descricao;
        this.dataConclusao = data;
        this.status = StatusTarefa.PENDENTE;
    }

    //Getters e Setters
    /**
     * Obtém o nome da tarefa.
     * @return uma String com o nome da tarefa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome da tarefa. Para isso, é preciso fornecer um novo nome.
     * @param nome um novo nome para a tarefa.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a descrição da tarefa.
     * @return uma String com a descrição da tarefa.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Altera a descrição da tarefa. Para isso, é preciso fornecer uma nova descrição
     * para a tarefa.
     * @param descricao a nova descrição para a tarefa.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a data prevista para a conclusão da tarefa.
     * @return retorna um objeto do tipo <code>Date</code> representando a data prevista para a conclusão da tarefa
     */
    public Date getDataConclusao() {
        return dataConclusao;
    }

    /**
     * Altera a data prevista para a conclusão da tarefa. Para isso, é preciso fornecer
     * uma nova data.
     * @param data a nova data prevista para a conclusão da tarefa.
     */
    public void setDataConclusao(Date data) {
        this.dataConclusao = data;
    }

    /**
     * Obtém o status da tarefa.
     * @return um <code>enum</code> do tipo StatusTarefa representando o status da tarefa.
     */
    public StatusTarefa getStatus() {
        return status;
    }

    /**
     * Altera o status da tarefa. Para isso, um novo status deve ser fornecido para a
     * tarefa.
     * @param status o novo status para a tarefa.
     */
    public void setStatus(StatusTarefa status) {
        this.status = status;
    }
    //Fim Getters e Setters
    
    //Funcionalidade
    /**
     * Indica se a tarefa está atrasada, ou seja, verificar se a data prevista para 
     * a conclusão da tarefa é anterior a data atual.
     * 
     * @return true caso a tarefa esteja atrasa, false caso contrário. 
     * @throws java.text.ParseException caso exista algum problema na conversão do formato da data.
     */
    public boolean isAtrasada() throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String formatoData = dateFormat.format(data);
        data = dateFormat.parse(formatoData);
        return dataConclusao.before(data) && !(status == StatusTarefa.CONCLUIDA);
    }
    
    /**
     * Obtém o nome que representa o objeto quando instanciado. O nome que representa
     * um objeto do tipo Tarefa contém: o nome da tarefa, a descrição, a data prevista
     * para conclusão e, caso esteja atrasada, que está atrasada.
     * 
     * @return uma String com um nome que representa o objeto.
     */
    @Override
    public String toString(){
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");  //Indica o formato da data
        String data = formatar.format(dataConclusao);  //Formata a data de conclusão
        String toNome = "Nome: ".concat(nome);  //obtém a String do nome
        String toDescricao = "\nDescrição: ".concat(descricao); //obtém a String da descrição
        String toData = "\nData prevista: ".concat(data);  //obtém a String da data
        String str = "";
        str = str.concat(toNome.concat(toDescricao.concat(toData)));  //Concatena nome, descrição e data
        try {
            if (isAtrasada()){
                str = str.concat("\nAtrasada");  //Concatena se estiver atrasada.
            }
        } catch (ParseException ex) {
            Logger.getLogger(Tarefa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;  //Retorna a String com todas as informações
    }
}
