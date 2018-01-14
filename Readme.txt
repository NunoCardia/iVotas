iVotas: voto eletrónico da UC

O sistema é constituído por cinco aplicações:
	RMIServer - servidor de RMI (primário e secundário);
	AdminConsole - consola de administração;
	TCPServer - servidor de TCP;
	TCPClient - aplicação cliente.
	Servidor Web - browser para o cliente.
	
Antes de iniciar a execução do projeto o utilizador deve ter instalado uma versão atualizada do MYSQL. A seguir deve criar um novo utilizador para este projeto ou alternativamente utilizar "root" que é configurado quando o MYSQL é instalado. Posteriormente deve entrar na conta e executar o script de criação de tabelas("create_table.sql") que se encontra na pasta "bd_scripts". Segue em anexo, na mesma pasta, um caso de teste usado durante a execução do projeto que contém multiplos departamentos, faculdades e utilizadores. Para correr estes scripts basta fazer:

	1) - abrir o terminal e ir até á pasta "bd_scripts";
	2) - abrir o mysql com utilizador e password (mysql -u<username> -p<password>);
	3) - criar as tabelas (source create_table.sql);
	4) - se quiser adicionar os dados que são incluidos no ficheiro de teste "populate_db.sql" basta executar o comando anterior alterando apenas o nome do ficheiro.

Para que o correcto funcionamento do sistema seja assegurado, dever-se-á alterar os ficheiros de configurações (config.properties) por forma a incluir os endereços e portos adequados aos diferentes servidores e o username e password de acesso á conta de utilizador do MYSQL.

Os ficheiros necessários à execução do sistema encontram-se na pasta "jar".
Para a utilização deste sistema, as aplicações devem ser executadas na linha de comandos pela ordem seguinte:
	java -jar RMIServer.jar (deverão ser executadas duas instâncias para garantir a existência de um servidor primário e secundário)
	java -jar AdminConsole.jar
	java -jar TCPServer.jar 
	java -jar TCPClient.jar
	java -jar iVotas.war

Poderá correr o projeto no IDE IntelliJ IDEA, criando um projeto Web e fazendo import do ficheiro iVotas.war que se encontra na pasta "jar".
O ficheiro de configuração deverá estar na mesma directoria dos ficheiros *.jar, directoria a partir da qual se deverão executar as diferentes aplicações. As aplicações podem ser executadas em diferentes máquinas desde que correctamente configurado o ficheiro de configurações. O ficheiro "source_code" contém todo o código fonte do projecto, tal como acessível a partir do IDE usado.