<h1  align="center">Problema 3 - Serviço de Marketplace Distribuído </h1>

<p  align="center">
TEC502 - MI - Concorrência e Conectividade
</p>

<h4  id="status"  align="center"> ✅ Finalizado ✅ </h4>

## índice

<p  align="left">
• <a  href="#tec">Tecnologias</a> <br>
• <a  href="#sistema">Sistema</a> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <a  href="#exesistema"> Como executar</a> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <a  href="#configsistema"> Configurações iniciais</a> <br>
• <a  href="#discente">Discente</a> <br>
</p>

<h2  id="tec" >🛠 Tecnologias </h2>

- Linguagem de programação Python

- Biblioteca socket

- Biblioteca Pandas

- Biblioteca Requests

- Framework Flask

- Docker

<h2  id="sistema">Hidrômetro</h2>

<p  align="justify">
O sistema foi desenvolvido como uma aplicação web. A página inicial é o catálogo de produtos à venda no Marketplace que o usuário está acessando. Quando houver produtos cadastrados no sistema aparecerá em forma de lista para o cliente, ele poderá selecionar os itens que deseja comprar e efetuar a compra. Antes de prosseguir com a compra o cliente deve informar alguns dados para poder identificá-lo. Após isso, receberá a resposta se sua compra foi aprovada ou não.
</p>
<p  align="justify">
O vendedor pode cadastrar novos produtos, editar o estoque dos produtos já cadastrados e também visualizar quais produtos foram vendidos. Para isso, deve acessar o endereço <strong>/adm_login</strong> e inserir o login e a senha. A senha e o login padrão é <strong>admin</strong>. Após efetuar o login, o vendedor irá se deparar com uma página que apresenta os botões: cadastrar um novo produto, editar um produto e visualizar os produtos vendidos. 
</p>
<p  align="justify">  
Ao clicar no botão de cadastrar um novo produto, o vendedor deve adicionar os dados do produto: código, nome, preço e quantidade. 
</p>
<p  align="justify">
Já para editar um produto ele primeiro deve selecionar o produto e então clicar em editar, ao clicar uma página semelhante a de cadastro de produtos será aberta, permitindo a modificação da quantidade daquele produto.
</p>
<p  align="justify">
Enquanto que para visualizar os produtos vendidos, basta clicar no botão de produtos vendidos que será aberta uma página com a lista de produtos vendidos e algumas informações do comprador.
</p>

<h3  id="exesistema">Como executar</h3>

<p  align="justify">
Com o docker instalado no dispositivo, basta acessar a pasta do sistema via terminal e executar o shell script (run-sistema.sh) disponível na pasta. O script criará a imagem docker a partir do Dockerfile e inicializará o container em modo interativo.
<br>
<br>
Caso deseje obter a imagem docker a partir do Docker Hub ao invés do Dockerfile, basta abrir o shell script e seguir os passos indicados dentro do mesmo para efetuar tal ação. 
<br>
<br>
Executar o shell script:
</p>

```bash
$ sudo chmod +x run-sistema.sh      #Atribui a permissão de execução do script
$ ./run-sistema.sh                  #Executa o script
```
<h3 id="configsistema">Configurações iniciais</h3>
<p  align="justify">
Ao iniciar a aplicação o servidor web já estará funcionando, porém para que o sistema funcione de maneira distribuída efetivamente, deve ser instanciado dois Marketplaces, pelo menos. Ao instanciar todos os Marketplaces que irão compor o sistema, deve ser acessada a rota <strong>/sistema/cadastrar_servidores</strong> e deve ser inseridos os endereços IPs de cada um dos outros Marketplaces. O primeiro endereço inserido será o vizinho do daquele Marketplace do sistema de Token Ring, portanto, não deve ser inserido o mesmo IP primeiro em dois Marketplaces diferentes. Faça esse processo para cada um dos Marketplaces de modo a formar um anel. Lembre-se que cada Marketplace deve conhecer  o endereço IP de todos os outros Marketplaces para efetuar as comunicações via API.
<br>
<br>
Por fim, a passagem do Token deve ser iniciada. Para isso, a partir de qualquer Marketplace, deve ser acessada a rota <strong>/sistema/ativar_sistema</strong> e clicar no botão Ativar. Assim, o Token será gerado no sistema de Token Ring e começará a ser transferido entre os Nós.
</p>

<h2 id="discente">Discente</h2>

- Igor Figueredo Soares
