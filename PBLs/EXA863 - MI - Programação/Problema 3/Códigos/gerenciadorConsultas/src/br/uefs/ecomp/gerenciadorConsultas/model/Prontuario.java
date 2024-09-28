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
package br.uefs.ecomp.gerenciadorConsultas.model;  //Pacote da classe

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * A classe implementa o comportamento de um <b>Prontuario</b>. A classe inclui os atributos
 * de um prontuário. Esses atributos são: identificação do paciente, anamnese, exame físico,
 * hispótese diagnóstica, diagnóstico definitivo, tratamentos efetuados e a receita.
 * 
 * <p><b>Exemplo de uso</b>:</p>
 * <p><code>Prontuario prontuario = new Prontuario("paciente","anamnese","exameFisico","hipoteseDiagnostica","diagnosticoDefinitivo","tratamentosEfetuados");</code></p>
 * 
 * @author Igor
 */
public class Prontuario {
    private String idenficicacoPaciente;
    private String anamnese;
    private String exameFisico;
    private String hipoteseDiagnostica;
    private String diagnosticoDefinitivo;
    private String tratamentosEfetuados;
    private String receita;
    
    /**
     * Construtor da classe Prontuario. Inicializa os atributos, idenficicacoPaciente,
     * anamnese, exameFisico, hipoteseDiagnostica, diagnosticoDefinitivo, tratamentosEfetuados
     * passados por parâmetro na instância do objeto do tipo Prontuario. Além disso, inicializa
     * o atributo receita como null.
     * @param identificao a identificação do paciente.
     * @param anamnese a anamnese.
     * @param exameFisico o exame físico.
     * @param hipotese a hipótese diagnóstica.
     * @param diagnostico o diagnóstico definitivo.
     * @param tratamentos os tratamentos efetuados.
     */
    public Prontuario(String identificao, String anamnese, String exameFisico, String hipotese, String diagnostico, String tratamentos){
        this.idenficicacoPaciente = identificao;
        this.anamnese = anamnese;
        this.exameFisico = exameFisico;
        this.hipoteseDiagnostica = hipotese;
        this.diagnosticoDefinitivo = diagnostico;
        this.tratamentosEfetuados = tratamentos;
        this.receita = null;
    }
    
    /**
     * Construtor da classe Prontuario. Construtor vazio.
     */
    public Prontuario(){
        //Construtor vazio.
    }

    /**
     * Obtém a indentificação do paciente.
     * @return uma String com a identificação do paciente.
     */
    public String getIdenficicacoPaciente() {
        return idenficicacoPaciente;
    }

    /**
     * Altera a identificação do paciente. Para isso, um nova identificação deve ser fornecida.
     * @param idenficicacoPaciente a nova identificação do paciente.
     */
    public void setIdenficicacoPaciente(String idenficicacoPaciente) {
        this.idenficicacoPaciente = idenficicacoPaciente;
    }

    /**
     * Obtém a anamnese.
     * @return uma String representando a anamnese.
     */
    public String getAnamnese() {
        return anamnese;
    }

    /**
     * Altera a anamnese. Para isso, uma anamnese deve ser fornecida.
     * @param anamnese a nova anamnese.
     */
    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    /**
     * Obtém o exame físico.
     * @return uma String representando o exame físico.
     */
    public String getExameFisico() {
        return exameFisico;
    }

    /**
     * Altera o exame físico. Para isso, um novo exame físico deve ser fornecido.
     * @param exameFisico o novo exame físico.
     */
    public void setExameFisico(String exameFisico) {
        this.exameFisico = exameFisico;
    }

    /**
     * Obtém a hipótese diagnóstica.
     * @return uma String representando a hipótese diagnóstica.
     */
    public String getHipoteseDiagnostica() {
        return hipoteseDiagnostica;
    }

    /**
     * Altera a hipótese diagnóstica. Para isso, uma nova hipótese diagnóstica deve ser fornecida.
     * @param hipoteseDiagnostica a nova hipótese diagnóstica.
     */
    public void setHipoteseDiagnostica(String hipoteseDiagnostica) {
        this.hipoteseDiagnostica = hipoteseDiagnostica;
    }

    /**
     * Obtém o diagnóstico definitivo.
     * @return uma String com o diagnóstico definitivo.
     */
    public String getDiagnosticoDefinitivo() {
        return diagnosticoDefinitivo;
    }

    /**
     * Altera o diagnóstico definitivo. Para isso, um novo diagnóstico definitivo deve ser fornecido.
     * @param diagnosticoDefinitivo o novo diagnóstio definitivo.
     */
    public void setDiagnosticoDefinitivo(String diagnosticoDefinitivo) {
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;
    }

    /**
     * obtém os tratamentos efetuados.
     * @return uma String com os tratamentos efetuados.
     */
    public String getTratamentosEfetuados() {
        return tratamentosEfetuados;
    }

    /**
     * Altera os tratamentos efetuados. Para isso, novos tratamentos efetuados devem ser fornecidos.
     * @param tratamentosEfetuados os novos tratamentos efetuados.
     */
    public void setTratamentosEfetuados(String tratamentosEfetuados) {
        this.tratamentosEfetuados = tratamentosEfetuados;
    }

    /**
     * Obtém a receita.
     * @return uma String representando a receita.
     */
    public String getReceita() {
        return receita;
    }

    /**
     * Altera a receita. Para isso, uma nova receita deve ser fornecida.
     * @param receita a nova receita.
     */
    public void setReceita(String receita) {
        this.receita = receita;
    }
    
    //****** Métodos complementares ******
    /**
    * O método é responsável por efetuar a impressão da receita.
    * 
    * @param caminhoArquivo o caminho do arquivo para a impressão da receita.
    */
    public void imprimirReceita(String caminhoArquivo){
        try (
                FileWriter criarArquivo = new FileWriter(caminhoArquivo, false);  BufferedWriter bw = new BufferedWriter(criarArquivo);  PrintWriter escreverArquivo = new PrintWriter(bw);
            ) 
        {
            escreverArquivo.append(receita);  //Coloca o texto da receita no arquivo txt
            Desktop desktop = Desktop.getDesktop();  
            File arqImprimir = new File(caminhoArquivo);  //   Carrega o arquivo a ser impresso
            desktop.print(arqImprimir);  //Solicita a impressão
            arqImprimir.deleteOnExit();
        } catch (java.io.IOException ex) {
            //Erro
        }
    }
}
