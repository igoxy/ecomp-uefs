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

import br.uefs.ecomp.gerenciadorConsultas.exceptions.CpfCadastradoException;

/**
 * A classe implementa o comportamento de um <b>Recepcionita</b>. A classe estende a classe abstrata
 * Funcionario a qual fornece o atributo nome, cpf e senha.
 * 
 * @author Igor
 * @see br.uefs.ecomp.gerenciadorConsultas.model.Funcionario
 */
public class Recepcionista extends Funcionario{
    
    /**
     * Construtor da classe Recepcionista. O construtor invoca o construtor da classe abstrata
     * Funcionario (super) a qual Recepcionista estende.
     * 
     * @param nome o nome.
     * @param cpf o CPF.
     * @param senha a senha.
     */
    public Recepcionista(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }
    
    /**
     * Obtém o nome que representa o objeto quando instanciado. O nome que representa
     * um objeto do tipo Recepcionista inclui: o nome e o CPF.
     * 
     * @return uma String com um nome que representa o objeto.
     */
    public String toString(){
       String identificador = "Nome: ".concat(getNome()).concat("\nCPF: ".concat(getCpf()));
       return identificador;
    }

    //******* Métodos funcionais ********
    //Paciente
    /**
     * O método é responsável por cadastrar um novo paciente no sistema. É verificado
     * se o CPF informado para o cadastro do paciente já está cadastrado no sistema, se
     * não tiver o cadastro é efetuado, caso contrário um exceção é lançada.
     * 
     * @param nome o nome do paciente.
     * @param cpf o CPF do paciente
     * @throws CpfCadastradoException lançada se o CPF informado já estiver cadastrado no sistema.
     */
    public void cadastrarPaciente(String nome, String cpf) throws CpfCadastradoException{
        if (Sistema.getPacientes().cpfNaoCadastrado(cpf)) {
            Paciente paciente = new Paciente(nome, cpf);
            Sistema.getPacientes().add(paciente);
        } else {
            throw new CpfCadastradoException();
        }
    }
    
    /**
     * Método responsável por remover um paciente do sistema.
     * 
     * @param paciente o paciente a ser removido
     * @return true se o paciente for removido, false caso contrário.
     */
    public boolean removerPaciente(Paciente paciente){
        return Sistema.getPacientes().removerPaciente(paciente); //Tenta remover o paciente
    }
    
    /**
     * Método responsável por editar um paciente. Ao fornecer as informações para a edição do
     * paciente, é verificado se o CPF informado já está cadastrado no sistema e se não é o atual
     * do paciente. Se o CPF estiver cadastrado e não o atual CPF do paciente, a edição
     * não é completada e um exceção é lançada, caso contrário a edição é efetuada com 
     * sucesso.
     * 
     * @param paciente o paciente a ser editado.
     * @param nome o novo nome.
     * @param cpf o novo CPF.
     * @throws CpfCadastradoException lançada caso o CPF informado já pertença a outro paciente cadastrado.
     */
    public void editarPaciente(Paciente paciente, String nome, String cpf) throws CpfCadastradoException{
        if (Sistema.getPacientes().cpfNaoCadastrado(cpf) || paciente.getCpf().equals(cpf)) {
            paciente.setNome(nome);
            paciente.setCpf(cpf);
        } else {
            throw new CpfCadastradoException();
        }
    }
    
    //Médico
    /**
     * O método é responsável por cadastrar médicos no sistema. É verificado
     * se o CPF fornecido já está cadastrado no sistema, senão tiver o cadastro é realizado,
     * caso contrário uma exceção é lançada.
     * 
     * @param nome o nome do médico.
     * @param crm o CRM do médico.
     * @param cpf o CPF do médico.
     * @param senha a senha do médico.
     * @param especialidade a especialidade do médico.
     * @param subespecialidade a subespecialidade do médico.
     * @throws CpfCadastradoException lançada caso o CPF informado já pertença a algum médico cadastrado no sistema.
     */
    public void cadastrarMedico(String nome , String crm, String cpf, String senha, Especialidade especialidade, Especialidade subespecialidade) throws CpfCadastradoException {
        if (Sistema.getMedicos().cpfNaoCadastrado(cpf)) {
            Medico medico = new Medico(nome, crm, cpf, senha, especialidade, subespecialidade);
            Sistema.getMedicos().add(medico);
        }else {
            throw new CpfCadastradoException();  //Lança a exceção de cpf já cadastrado no sistema
        }
    }
    
    /**
     * O método é responsável por editar um determinado médico. É verificado se o
     * CPF informado pertence algum médico já cadastrado no sistema que não seja o
     * próprio médico. Se não pertencer, a edição é concluída. Caso contrário uma
     * exceção é lançada.
     * 
     * @param medico o médico.
     * @param nome o novo nome.
     * @param crm o novo CRM.
     * @param cpf o novo CPF.
     * @param senha a nova senha.
     * @param especialidade a nova especialidade.
     * @param subespecialidade a nova subespecialidade.
     * @throws CpfCadastradoException lançada se o CPF informado já pertencer a outro médico cadastrado no sistema.
     */
    public void editarMedico(Medico medico, String nome, String crm, String cpf, String senha, Especialidade especialidade, Especialidade subespecialidade) throws CpfCadastradoException {
        if (Sistema.getMedicos().cpfNaoCadastrado(cpf) || medico.getCpf().equals(cpf)) {
            medico.setNome(nome);
            medico.setCrm(crm);
            medico.setCpf(cpf);
            medico.setSenha(senha);
            medico.setEspecialidade(especialidade);
            medico.setSubespecialidade(subespecialidade);
        }else{
            throw new CpfCadastradoException();
        }
    }
    
    /**
     * O método é responsável por remover um médico do sistema. É retornado um
     * boolean para indicar se a operação foi efetuada com sucesso.
     * 
     * @param medico o médico.
     * @return true se o médico foi removido, false caso contrário.
     */
    public boolean removerMedico(Medico medico) {
        if (Sistema.getMedicos().removerMedico(medico)) {
            int indiceEspecialidade = Sistema.getEspecialidades().indexOf(medico.getEspecialidade());  //Obtém o indice da especiliadade do médico removido
            if (medico.getSubespecialidade() != null) {  //Se o médico tiver subespecialidade
                int indiceSubespecialidade = Sistema.getEspecialidades().indexOf(medico.getSubespecialidade()); //Obtém o indice da subespecialidade do médico removido
                Sistema.getEspecialidades().get(indiceSubespecialidade).getMedicos().remove(medico);  //Remove o médico da lista de médicos da subespecialidade
            }
            Sistema.getEspecialidades().get(indiceEspecialidade).getMedicos().remove(medico);  //Remove o médico da lista de médicos da especialidade
            return true;
        } else {
            return false;
        }
    }
    
    //Recepcionista
    /**
     * O método é responsável por cadastrar um novo recepcionista no sistema. É verificado
     * se o CPF informado já não pertence a outro recepcionista cadastrado no sistema.
     * Se não pertencer, o cadastro é efetuado com sucesso, caso contrário uma exceção
     * é lançada.
     * 
     * @param nome o nome.
     * @param cpf o CPF.
     * @param senha a senha.
     * @throws CpfCadastradoException lançada caso o CPF informado já pertença a outro recepcionista cadastrado no sistema.
     */
    public void cadastrarRecepcionista(String nome, String cpf, String senha) throws CpfCadastradoException {
        if (Sistema.getRecepcionistas().cpfNaoCadastrado(cpf)) {
            Recepcionista recepcionista = new Recepcionista(nome, cpf, senha);
            Sistema.getRecepcionistas().add(recepcionista);
        }else{
            throw new CpfCadastradoException();
        }
    }
    
    /**
     * O método é responsável por editar as informações de um determinado recepcionista.
     * É verificado se o CPF informado pertence a algum recepcionista que não seja o
     * próprio editado. Se não pertencer, a edição é concluída, caso constrário uma
     * exceção é lançada.
     * 
     * @param recepcionista o recepcionista.
     * @param nome o novo nome.
     * @param cpf o novo CPF.
     * @param senha a nova senha.
     * @throws CpfCadastradoException lançada se o CPF informado pertencer a outro recepcionista.
     */
    public void editarRecepcionista(Recepcionista recepcionista, String nome, String cpf, String senha) throws CpfCadastradoException {
        if (Sistema.getRecepcionistas().cpfNaoCadastrado(cpf) || recepcionista.getCpf().equals(cpf)) {
            recepcionista.setNome(nome);
            recepcionista.setCpf(cpf);
            recepcionista.setSenha(senha);
        }else{
            throw new CpfCadastradoException();
        }
    }
    
    //Especialidade
    /**
     * O método é responsável por cadastrar uma nova especialidade no sistema.
     * 
     * @param nome o nome da especialidade.
     */
    public void cadastrarEspecialidade(String nome){
        String nomeEspec = nome;  //O nome inserido para a especialidade
            Especialidade especialidade = new Especialidade(nomeEspec);  //Cria um objeto do tipo especialidade
            Sistema.getEspecialidades().add(especialidade); //Adiciona a especialidade
    }
    
    /**
     * O método é responsável por editar uma determinada especialidade.
     * 
     * @param especialidade a especialidade.
     * @param nome o novo nome da especialidade.
     */
    public void editarEspecialidade(Especialidade especialidade, String nome) {
        int indexEspecialidade = Sistema.getEspecialidades().indexOf(especialidade); //Encontra o índice da especialidade 
        Sistema.getEspecialidades().get(indexEspecialidade).setNome(nome); //Chama o método de alterar o nome da especialidade que foi editado e fornce o nome
    }
    
    /**
     * O método é responsável por remover uma determinada especialidade do sistema.
     * Retorna um booelan indicando se a especialidade foi removida ou não.
     * 
     * @param especialidade a especialidade a ser removida.
     * @return true se a especialidade foi removida, caso contrário false.
     */
    public boolean removerEspecialidade(Especialidade especialidade){
        int tamanhoListaEspecialidades = Sistema.getEspecialidades().size();
        Sistema.getEspecialidades().removerEspecialidade(especialidade);
        return tamanhoListaEspecialidades != Sistema.getEspecialidades().size();
    }
}
