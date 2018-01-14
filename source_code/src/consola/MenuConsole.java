package consola;

import RMI.RMIServerInterface;
import common.Response;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cyberfox21 on 17/10/17.
 */
public class MenuConsole{

    private RMIServerInterface svInterface;

    public MenuConsole(RMIServerInterface svInterface) throws RemoteException, ParseException {
        this.svInterface = svInterface;
        menu();
    }

    public void registarPessoas() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("REGISTAR PESSOAS\n\nNome: ");
        String nome = sc.nextLine();
        System.out.println("Tipo (estudante, funcionario, docente): ");
        String tipo = sc.nextLine();
        System.out.println("Contacto: ");
        String contacto = sc.nextLine();
        System.out.println("Username: ");
        String username = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        System.out.println("Morada: ");
        String morada = sc.nextLine();
        System.out.println("Cartão do cidadão: ");
        String cartao = sc.nextLine();
        System.out.println("Validade: ");
        Date data = Date.valueOf(sc.nextLine());
        System.out.println("\nDepartamento a que o utilizador pertence: \n");
        Response answer = svInterface.mostrarDepartamentos();
        answer.getInfo().forEach(p ->{
            System.out.println(p+"\n");
        });
        int dep_id = Integer.parseInt(sc.nextLine());
        if(!svInterface.verificaUtilizador("cartao",cartao).isSuccess())
            svInterface.insereUtilizador(nome,tipo,contacto,username,password,dep_id,morada,cartao,data);
        else System.out.println("utilizador já existe!!");

    }

    public void adicionarDepartamento() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ADICIONAR DEPARTAMENTO\n\nNome do departamento: ");
        String nome = sc.nextLine();
        System.out.println("\n\t\tFACULDADES \n");
        svInterface.mostrarFaculdades().getInfo().forEach(p ->{
            System.out.println(p+"\n");
        });
        System.out.println("\nFaculdade a que o departamento pertence: ");
        int fac_id = Integer.parseInt(sc.nextLine());
        if(!svInterface.verificaDepartamento(nome).isSuccess())
            svInterface.novoDepartamento(nome,fac_id);
        else System.out.println("Departamento já existe!!");
    }

    private void alterarDepartamento() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ALTERAR DEPARTAMENTO\n");
        svInterface.mostrarDepartamentos().getInfo().forEach(System.out::println);
        System.out.println("\nId do departamento: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println("\n1 - Alterar nome\n2 - Alterar id da faculdade");
        int data = Integer.parseInt(sc.nextLine());
        if(data == 1){
            System.out.println("Novo nome: ");
            String nome = sc.nextLine();
            if(!svInterface.verificaDepartamento(nome).isSuccess()){
                svInterface.mudarDepartamento(id,nome);
                System.out.println("Nome do departamento alterado!");
            }else {
                System.out.println("Nome já existente!");
            }
        }
        else if(data == 2){
            svInterface.mostrarFaculdades().getInfo().forEach(System.out::println);
            System.out.println("Nova faculdade a que o departamento pertence: ");
            int fac_id = Integer.parseInt(sc.nextLine());
            if(svInterface.verificaFaculdadeId(fac_id).isSuccess()){
                svInterface.mudarDepartamentoFacId(id,fac_id);
                System.out.println("Parâmetro alterado!");
            }
            else System.out.println("Id da faculdade não existe!");
        }
    }

    public void removerDepartamento() throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("\nREMOVER DEPARTAMENTO\n");
        svInterface.mostrarDepartamentos().getInfo().forEach(System.out::println);
        System.out.println("\nId do departamento");
        int id = Integer.parseInt(sc.nextLine());
        if(svInterface.verificaDepartamentoId(id).isSuccess()){

            if(svInterface.removerDepartamentoPorId(id).isSuccess()){
                System.out.println("Departamento removido com sucesso!");
            }
        }
        else{
            System.out.println("Departamento não existe");
        }
    }

    public void gestaoDepartamenos() throws RemoteException {
        //adicionar, remover ou alterar
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Adicionar departamento\n2 - Alterar departamento\n3 - Remover departamento");
        int data = Integer.parseInt(sc.nextLine());
        switch (data){
            case 1:
                adicionarDepartamento();
                break;
            case 2:
                alterarDepartamento();
                break;
            case 3:
                removerDepartamento();
                break;
        }

    }

    public void adicionarFaculdade() throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("ADICIONAR FACULDADE\n\nNome da faculdade: ");
        String nome = sc.nextLine();
        if(!svInterface.verificaFaculdade(nome).isSuccess()){

            svInterface.novaFaculdade(nome);
            System.out.println("Faculdade adicionada com sucesso!");
        }
        else
            System.out.println("Faculdade já existe!!");
    }

    private void alterarFaculdade() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nALTERAR FACULDADE\n\n\t\tFACULDADES \n");
        svInterface.mostrarFaculdades().getInfo().forEach(System.out::println);
        System.out.println("\nId da faculdade: ");
        int id = Integer.parseInt(sc.nextLine());
        if(svInterface.verificaFaculdadeId(id).isSuccess()){

            System.out.println("Novo nome: ");
            String data = sc.nextLine();
            if(!svInterface.verificaFaculdade(data).isSuccess()){
                svInterface.novaFaculdade(data);
                System.out.println("Nome da faculdade alterada!");
            }else {
                System.out.println("Nome já existente!");
            }
        }
        else System.out.println("Id da faculdade não existente!");
    }

    public void removerFaculdade() throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("\nREMOVER FACULDADE\n\n\t\tFACULDADES \n");
        svInterface.mostrarFaculdades().getInfo().forEach(System.out::println);
        System.out.println("\nId da faculdade");
        int id = Integer.parseInt(sc.nextLine());
        if(svInterface.verificaFaculdadeId(id).isSuccess()){
            if(svInterface.removerFaculdadePorId(id).isSuccess()){
                System.out.println("Faculdade removida com sucesso!");
            }
        }
        else System.out.println("Id da faculdade não existente!");
    }

    public void gestaoFaculdades() throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Adicionar faculdade\n2 - Alterar faculdade\n3 - Remover faculdade");
        int data = Integer.parseInt(sc.nextLine());
        switch (data){
            case 1:
                adicionarFaculdade();
                break;
            case 2:
                alterarFaculdade();
                break;
            case 3:
                removerFaculdade();
                break;
        }
    }

    public void criarEleicao() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("CRIAR ELEIÇÃO\n\nTítulo da eleição: ");
        String titulo = sc.nextLine();
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();
        System.out.println("Tipo de eleição: ");
        String tipo = sc.nextLine();
        int dept_id = 0;
        if(tipo.equalsIgnoreCase("nucleo de estudantes")){
            svInterface.mostrarDepartamentos().getInfo().forEach(System.out::println);
            System.out.println("\nDepartamento associado: ");
            dept_id = Integer.parseInt(sc.nextLine());
        }
        System.out.println("Inicio da eleição (yyyy-mm-dd HH:MM:SS): ");
        Timestamp inicio = Timestamp.valueOf(sc.nextLine());
        System.out.println("Fim da eleição (yyyy-mm-dd HH:MM:SS): ");
        Timestamp fim = Timestamp.valueOf(sc.nextLine());
        if(!svInterface.verificaEleicao(titulo,tipo).isSuccess()){
            if(svInterface.novaEleicao(titulo,descricao,inicio,fim,tipo,dept_id).isSuccess()){
                System.out.println("Eleição criada com sucesso");
            }
        }
        else System.out.println("Eleição já existente\n");
    }

    public void adicionarLista() throws RemoteException {
        int id,lista_id = 0;
        String tipoEleicao;
        Scanner sc = new Scanner(System.in);
        System.out.println("ADICIONAR LISTA\n\nEleições existentes: \n");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nEleição a que se quer adicionar uma lista: ");
        int eleicao_id = Integer.parseInt(sc.nextLine());
        Response resp = svInterface.verificaEleicaoId(eleicao_id);
        if(!resp.isSuccess()){
            System.out.println("Eleição não existe");
            return;
        }
        else {
            tipoEleicao = resp.getInfo().get(0);
            //criar lista
            ArrayList<String> temp = svInterface.escolheEleicao(eleicao_id).getInfo();
            System.out.println("Nome da lista:");
            String nome = sc.nextLine();
            System.out.println("Tipo de lista (estudante,docente,funcionario): ");
            String tipo = sc.nextLine();
            if(!svInterface.verificaLista(eleicao_id,nome).isSuccess()){
                svInterface.novaLista(eleicao_id,tipo,nome);
                lista_id = Integer.parseInt(svInterface.verificaLista(eleicao_id,nome).getInfo().get(0));

            }
            //associar pessoas
            if(tipo.equalsIgnoreCase("estudante") && tipoEleicao.equalsIgnoreCase("nucleo de estudantes")){
                System.out.println("\nALUNOS ASSOCIADOS AO DEPARTAMENTO: \n");
                svInterface.verEstudantesDepartamento(Integer.parseInt(temp.get(2))).getInfo().forEach(System.out::println);
                colocaEstudantes(lista_id,eleicao_id);

            }
            else if(tipo.equalsIgnoreCase("docente") && tipoEleicao.equalsIgnoreCase("conselho geral")){
                System.out.println("\nDOCENTES DA UNIVERSIDADE: \n");
                svInterface.mostraDocentes().getInfo().forEach(System.out::println);
                colocaDocentes(lista_id,eleicao_id);
            }

            else if(tipo.equalsIgnoreCase("funcionario") && tipoEleicao.equalsIgnoreCase("conselho geral")){
                System.out.println("\nDOCENTES DA UNIVERSIDADE: \n");
                svInterface.mostraFuncionarios().getInfo().forEach(System.out::println);
                colocaFuncionario(lista_id,eleicao_id);

            }

            else if(tipo.equalsIgnoreCase("estudante") && tipoEleicao.equalsIgnoreCase("conselho geral")){
                System.out.println("\nESTUDANTES DA UNIVERSIDADE: \n");
                svInterface.mostraEstudantes().getInfo().forEach(System.out::println);
                colocaEstudantes(lista_id,eleicao_id);

            }
        }
    }

    public void removerLista() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int eleicao_id,lista_id;
        System.out.println("ADICIONAR LISTA\n\nEleições existentes: \n");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nEleição a que se quer remover uma lista: ");
        eleicao_id = Integer.parseInt(sc.nextLine());
        Response resp = svInterface.verificaEleicaoId(eleicao_id);
        if(!resp.isSuccess()){
            System.out.println("Eleição não existe");
            return;
        }
        else{
            System.out.println("LISTAS ASSOCIADAS A ELEIÇÃO:  \n");
            svInterface.imprimeLista(eleicao_id).getInfo().forEach(System.out::println);
            System.out.println("\nId da lista que pretende remover: ");
            lista_id = Integer.parseInt(sc.nextLine());
            if(svInterface.verificaListaId(lista_id).isSuccess()){
                svInterface.removerPessoasLista(lista_id);
                svInterface.removerLista(lista_id);
                System.out.println("Lista removida com sucesso!");
            }
            else {
                System.out.println("Lista não existe");
            }
        }
    }

    public void adicionarUtilizadores(int lista_id,String tipoLista,int eleicao_id) throws RemoteException {
        //ver o tipo de eleicao
        Scanner sc = new Scanner(System.in);
        Response answer = svInterface.verTipoEleicaoLista(lista_id);
        String tipoEleicao = answer.getInfo().get(0);
        int id;
        int dept_id = Integer.parseInt(answer.getInfo().get(1));
        if(tipoEleicao.equalsIgnoreCase("nucleo de estudantes")){
            System.out.println("ALUNOS ASSOCIADOS AO DEPARTAMENTO: \n");
            svInterface.verEstudantesDepartamento(dept_id).getInfo().forEach(System.out::println);
            colocaEstudantes(lista_id, eleicao_id);
        }
        else if(tipoEleicao.equalsIgnoreCase("conselho geral")){
            if (tipoLista.equalsIgnoreCase("estudante")){
                System.out.println("ESTUDANTES REGISTADOS NA UNIVERSIDADE");
                svInterface.verTipoPessoa("estudante").getInfo().forEach(System.out::println);
                colocaEstudantes(lista_id, eleicao_id);
            }
            else if(tipoLista.equalsIgnoreCase("docente")){
                System.out.println("DOCENTES REGISTADOS NA UNIVERSIDADE");
                svInterface.verTipoPessoa("docente").getInfo().forEach(System.out::println);
                colocaDocentes(lista_id, eleicao_id);
            }
            else if(tipoLista.equalsIgnoreCase("funcionario")){
                System.out.println("FUNCIONARIOS REGISTADOS NA UNIVERSIDADE");
                svInterface.verTipoPessoa("funcionario").getInfo().forEach(System.out::println);
                colocaFuncionario(lista_id, eleicao_id);
            }
        }
    }

    private void colocaFuncionario(int lista_id, int eleicao_id) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Id do funcionario: ");
        id = Integer.parseInt(sc.nextLine());
        while(id != 0){
            if(svInterface.verificaUtilizador("user_id",String.valueOf(id)).isSuccess())
                if(!svInterface.verAlunosLista(id,eleicao_id).isSuccess())
                    svInterface.adicionarALista(id,lista_id,eleicao_id);
                else System.out.println("Funcionario já se encontra associado a uma lista");
            else
            if(id != 0) System.out.println("Docente não existente!");
            System.out.println("Id do funcionario: ");
            id = Integer.parseInt(sc.nextLine());


        }
    }

    private void colocaDocentes(int lista_id, int eleicao_id) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("Id do docente: ");
        id = Integer.parseInt(sc.nextLine());
        while(id != 0){
            if(svInterface.verificaUtilizador("user_id",String.valueOf(id)).isSuccess())
                if(!svInterface.verAlunosLista(id,eleicao_id).isSuccess())
                    svInterface.adicionarALista(id,lista_id,eleicao_id);
                else System.out.println("Docente já se encontra associado a uma lista");
            else
            if(id != 0) System.out.println("Docente não existente!");
            System.out.println("Id do docente: ");
            id = Integer.parseInt(sc.nextLine());


        }
    }

    private void colocaEstudantes(int lista_id, int eleicao_id) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int id;
        System.out.println("\nId do aluno: ");
        Response answer;
        id = Integer.parseInt(sc.nextLine());
        while(id != 0){
            if(svInterface.verificaUtilizador("user_id",String.valueOf(id)).isSuccess()){
                answer = svInterface.verAlunosLista(id,eleicao_id);
                if(!answer.isSuccess()){
                    svInterface.adicionarALista(id,lista_id,eleicao_id);
                    System.out.println("Aluno adicionado com sucesso");
                }
                else System.out.println("Aluno já se encontra associado a uma lista");
            }
            else
            if(id != 0) System.out.println("Aluno não existente!");
            System.out.println("\nId do aluno: ");
            id = Integer.parseInt(sc.nextLine());


        }
    }


    private void removerUtilizadores(int lista_id) throws RemoteException {
        int id;
        Scanner sc = new Scanner(System.in);
        System.out.println("ELEMENTOS DA LISTA:\n\n");
        svInterface.membrosAssociadosALista(lista_id).getInfo().forEach(System.out::println);
        System.out.println("Id da pessoa: ");
        id = Integer.parseInt(sc.nextLine());
        while(id != 0){
            if(svInterface.verificaUtilizador("user_id",String.valueOf(id)).isSuccess()){
                svInterface.removerPessoaDaLista(id,lista_id);
                System.out.println("Pessoa retirada da lista");
            }
            else
            if(id != 0) System.out.println("Pessoa não existente!");
            System.out.println("Id da pessoa: ");
            id = Integer.parseInt(sc.nextLine());


        }

    }

    public void alterarLista() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int eleicao_id,lista_id;
        String escolha,tipoLista;
        Response answer;
        System.out.println("ADICIONAR LISTA\n\nEleições existentes: \n");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nEleição a que se quer alterar uma lista: ");
        eleicao_id = Integer.parseInt(sc.nextLine());
        Response resp = svInterface.verificaEleicaoId(eleicao_id);
        if(!resp.isSuccess()){
            System.out.println("Eleição não existe");
            return;
        }
        else {
            System.out.println("LISTAS ASSOCIADAS A ELEIÇÃO:  \n");
            svInterface.imprimeLista(eleicao_id).getInfo().forEach(System.out::println);
            System.out.println("\nId da lista que pretende alterar: ");
            lista_id = Integer.parseInt(sc.nextLine());
            answer = svInterface.verificaListaId(lista_id);
            tipoLista = answer.getInfo().get(0);
            if (answer.isSuccess()) {
                System.out.println("\nPretende adicionar ou remover utilizadores a lista? ");
                escolha = sc.nextLine();
                if (escolha.equalsIgnoreCase("adicionar")) {
                    adicionarUtilizadores(lista_id, tipoLista, eleicao_id);
                } else if (escolha.equalsIgnoreCase("remover")) {
                    removerUtilizadores(lista_id);
                }
            } else {
                System.out.println("Lista não existe");
            }
        }
    }



    public void gerirListas() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Criar lista\n2 - Alterar lista\n3 - Remover lista");
        int data = Integer.parseInt(sc.nextLine());
        switch (data){
            case 1:
                adicionarLista();
                break;
            case 2:
                alterarLista();
                break;
            case 3:
                removerLista();
                break;
        }
    }

    public void adicionarMesa() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ADICIONAR MESA\n\nEleições existentes: \nMáquinas existentes");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nEleição a que se quer adicionar uma mesa de voto: ");
        int eleicao_id = Integer.parseInt(sc.nextLine());
        int id;
        Response answer = svInterface.escolheEleicao(eleicao_id);
        String tipoEleicao = answer.getInfo().get(1);
        int dept_id = Integer.parseInt(answer.getInfo().get(2));
        if(tipoEleicao.equalsIgnoreCase("conselho geral")){
            //svInterface.maquinasPorFaculdade().getInfo().forEach(System.out::println);
            System.out.println("\nNome da faculdade: ");
            String fac = sc.nextLine();
            answer = svInterface.verificaFaculdade(fac);
            if(answer.isSuccess()){
                int temp = Integer.parseInt(answer.getInfo().get(0));
                svInterface.mostrarDepartamentosPorFaculdade(temp).getInfo().forEach(System.out::println);
                System.out.println("\nId do departamento: ");
                id = Integer.parseInt(sc.nextLine());
                if(!svInterface.verificaDepartamentoMaquina(id).isSuccess()){
                    svInterface.adicionarMesa(id,eleicao_id);
                    System.out.println("Mesa adicionada com sucesso!");
                }
                else System.out.println("Departamento já possui uma mesa");
            }
            else System.out.println("Faculdade não existente!");
        }
        else if(tipoEleicao.equalsIgnoreCase("nucleo de estudantes")){
            if(!svInterface.verificaDepartamentoMaquina(dept_id).isSuccess()){
                svInterface.adicionarMesa(dept_id,eleicao_id);
                System.out.println("Mesa adicionada com sucesso!");
            }
            else System.out.println("Departamento já possui uma mesa");
        }
    }

    public void removerMesa() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("REMOVER MESA\n\nEleições existentes: ");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nEleição a que se quer remover uma mesa de voto: ");
        int id = Integer.parseInt(sc.nextLine());
        svInterface.verMaquinasEleicao(id).getInfo().forEach(System.out::println);
        System.out.println("\nMesa que pretende remover: ");
        int maquina_id = Integer.parseInt(sc.nextLine());
        if(svInterface.verificaMaquinaId(maquina_id).isSuccess()){
            svInterface.removerMesa(maquina_id,id);
            System.out.println("Mesa removida com sucesso");
        }
        else System.out.println("Mesa nao existe no departamento indicado");

    }

    public void gerirMesas() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Criar mesa de voto\n2 - Remover mesa de voto");
        int data = Integer.parseInt(sc.nextLine());
        switch (data){
            case 1:
                adicionarMesa();
                break;
            case 2:
                removerMesa();
                break;
        }
    }

    public void alterarPropriedadesEleicao() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ALTERAR PROPRIEDADES DE UMA ELEIÇÃO\n\nEleições existentes: ");
        svInterface.verEleicao().getInfo().forEach(System.out::println);
        System.out.println("\nId da eleição a alterar: ");
        int id = Integer.parseInt(sc.nextLine());
        Response answer = svInterface.verificaComecoEleicao(id);
        if(answer.isSuccess()){
            System.out.println("Eleição já começou. Parâmetros não podem ser alterados");
            return;
        }
        System.out.println("Parâmetro a alterar: ");
        String parametro = sc.nextLine();
        System.out.println("Novo valor: ");
        String valor = sc.nextLine();
        if(svInterface.alteraParametroEleicao(id,parametro,valor).isSuccess()){
            System.out.println("Parâmetro alterado com sucesso!");
        }

    }

    public void localEleicao() throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Username do utilizador: ");
        String username = sc.nextLine();
        Response r = svInterface.pesquisaVoto(username);
        if(!r.isSuccess()){
            System.out.println("Utilizador ainda não votou");
        }
        else {
            r.getInfo().forEach(System.out::println);
        }
    }

    public void consultaDadosEleicao() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ELEIÇÕES PASSADAS: \n");
        Response answer = svInterface.mostraEleicoesPassadas();
        if(answer.getInfo().size()==0){
            System.out.println("Não existem eleicoes passadas");
            return;
        }else {
            answer.getInfo().forEach(System.out::println);
            System.out.println("Id da eleição: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.println("RESULTADOS: \n\n");
            estatisticasEleicao(id);
        }


    }

    public void estatisticasEleicao(int eleicao_id) throws RemoteException{
        int votantesGeral,dept_id,numeroVotantes;
        float abstencao = 0;
        Response answer = svInterface.imprimeLista2(eleicao_id);
        HashMap<String,Integer> resultados = new HashMap<>();
        for(String str : answer.getInfo()){
            resultados.put(str,Integer.parseInt(svInterface.votosValidosEleicao(eleicao_id,str).getInfo().get(0)));
        }
        int votosBrancos = Integer.parseInt(svInterface.votosBrancosEleicao(eleicao_id).getInfo().get(0));
        int votosNulos = Integer.parseInt(svInterface.votosNulosEleicao(eleicao_id).getInfo().get(0));
        if(svInterface.verificaEleicaoId(eleicao_id).getInfo().get(0).equalsIgnoreCase("conselho geral")){
            votantesGeral = Integer.parseInt(svInterface.numeroTotalVotantesGeral().getInfo().get(0));
            for(Integer a: resultados.values()){
                abstencao += a;
            }
        }
        else {
            dept_id = Integer.parseInt(svInterface.verDeptId(eleicao_id).getInfo().get(1));
            votantesGeral = Integer.parseInt(svInterface.numeroTotalVotantesConselho(dept_id).getInfo().get(0));
            for(Integer a: resultados.values()){
                abstencao += a;
            }
        }
        numeroVotantes = (int)abstencao + votosBrancos + votosNulos;
        abstencao = ((float)(votantesGeral - numeroVotantes) / votantesGeral)*100;
        System.out.println("Número de votos da eleicao: "+eleicao_id);
        System.out.println("Número de votantes: "+numeroVotantes);
        for (HashMap.Entry<String, Integer> entry : resultados.entrySet())
        {
            float count = ((float)entry.getValue()/numeroVotantes) * 100;
            System.out.printf("%s -----> %d (%.2f%%)%n",entry.getKey(),entry.getValue(),count);
        }
        System.out.printf("Votos nulos: %d -----> (%.2f%%)%n",votosNulos,((float)votosNulos/numeroVotantes)*100);
        System.out.printf("Votos brancos: %d -----> (%.2f%%)%n",votosBrancos,((float)votosBrancos/numeroVotantes)*100);
        System.out.printf("Abstenção: %.2f%%\n",abstencao);

    }

    public String listaParaResultados(String lista,int eleicao_id) throws RemoteException{
        if(lista.isEmpty() || lista.replaceAll("\\s+","").isEmpty()){
            return "branco";
        }
        else if(!lista.matches("[a-zA-Z0-9 ]*") || !svInterface.verificaLista(eleicao_id,lista).isSuccess()){
            return "nulo";
        }
        else
            return "valido";

    }

    public void votar(int eleicao_id,String tipo,String username,int user_id) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        String voto,lista;
        svInterface.boletimVoto(eleicao_id,tipo).getInfo().forEach(System.out::println);
        System.out.println("Voto: ");
        lista = sc.nextLine();
        voto = listaParaResultados(lista,eleicao_id);
        svInterface.novoEstado(eleicao_id,0,"voto","utilizador "+username+" votou",new Timestamp(new java.util.Date().getTime()),0);
        switch (voto){
            case "nulo":
                svInterface.votar(user_id,0,eleicao_id,"nulo",new Timestamp(new java.util.Date().getTime()));
                break;
            case "valido":
                svInterface.votar(user_id,0,eleicao_id,lista,new Timestamp(new java.util.Date().getTime()));
                break;
            case "branco":
                svInterface.votar(user_id,0,eleicao_id,"branco",new Timestamp(new java.util.Date().getTime()));
                break;
        }

    }

    public void autorizarVotacao(int eleicao_id) throws RemoteException {
        String username,password,tipo,nome = new String();
        int id,deptPessoa,eleicaoDeptId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Id do utilizador: ");
        id = Integer.parseInt(sc.nextLine());
        System.out.println("Username: ");
        username = sc.nextLine();
        System.out.println("Password: ");
        password = sc.nextLine();
        Response answer = svInterface.escolheEleicao(eleicao_id);
        tipo = answer.getInfo().get(1);
        if(tipo.equalsIgnoreCase("nucleo de estudantes")){
            eleicaoDeptId = Integer.parseInt(answer.getInfo().get(2));
        }
        answer = svInterface.verificaUtilizador("user_id",String.valueOf(id));
        nome = answer.getInfo().get(0);
        Response answer2 = svInterface.identificaVotante(nome,eleicao_id);
        deptPessoa = Integer.parseInt(svInterface.deptPessoa(nome).getInfo().get(0));
        if(tipo.equalsIgnoreCase("nucleo de estudantes")){
            if(deptPessoa != eleicaoDeptId){
                System.out.println("Pessoa não pertence a este departamento");
                return;
            }
            else {
                if(answer.isSuccess() && !answer2.isSuccess()){
                    System.out.println("Utilizador autorizado a votar!!");
                    votar(eleicao_id,tipo,username,id);
                }
                else {
                    System.out.println("Utilizador já votou para esta eleicao!!");
                    return;
                }
            }
        }
        else {
            if(answer.isSuccess() && !answer2.isSuccess()){
                System.out.println("Utilizador autorizado a votar!!");
                votar(eleicao_id,tipo,username,id);
            }
            else {
                System.out.println("Utilizador já votou para esta eleicao!!");
                return;
            }
        }
    }

    public void votoAntecipado() throws RemoteException {
        Scanner sc = new Scanner(System.in);

        System.out.println("ELEIÇÕES AINDA POR DECORRER: \n");
        Response answer = svInterface.mostraEleicoesPorComecar();
        if(answer.getInfo().size()==0){
            System.out.println("Não existem eleicoes ainda por decorrer");
            return;
        }else {
            answer.getInfo().forEach(System.out::println);
            System.out.println("Id da eleição: ");
            int id = Integer.parseInt(sc.nextLine());
            if(svInterface.verificaEleicaoId(id).isSuccess()){
                autorizarVotacao(id);
            }
            else {
                System.out.println("Eleição não existe!");
                return;
            }
        }

    }

    public boolean verificaValidade(String valor) throws ParseException {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        java.util.Date date = format.parse(valor);
        java.util.Date now = new java.util.Date(Calendar.getInstance().getTime().getTime());
        if(date.before(now))
            return false;
        else return true;
    }

    public boolean verificaDeptId(String valor) throws RemoteException {
        return svInterface.verificaDepartamentoId(Integer.parseInt(valor)).isSuccess();
    }

    public void alterarDadosPessoais() throws RemoteException, ParseException {
        int id;
        String campo, valor = new String();
        List<String> parametros = Arrays.asList("nome", "tipo", "contacto", "username", "password", "dept_id", "morada", "cartao", "validade");
        Response answer;
        Scanner sc = new Scanner(System.in);
        System.out.println("ALTERAR DADOS PESSOAIS: \nId do utilizador: ");
        id = Integer.parseInt(sc.nextLine());
        answer = svInterface.verificaUtilizador("user_id", Integer.toString(id));
        if (answer.isSuccess()) {
            System.out.println("Campos existentes: ");
            parametros.forEach(System.out::println);
            System.out.println("Campo a alterar: ");
            campo = sc.nextLine();
            if (parametros.stream().anyMatch(str -> str.equalsIgnoreCase(campo))) {
                System.out.println("Novo valor: ");
                valor = sc.nextLine();
                if (campo.equalsIgnoreCase("validade")) {
                    if (!verificaValidade(valor)) {
                        System.out.println("Novo valor não é válido!");
                        return;
                    }
                } else if (campo.equalsIgnoreCase("dept_id")) {
                    if (!verificaDeptId(valor)) {
                        System.out.println("Novo valor não é válido!");
                        return;
                    }
                }
                if (svInterface.alteraDados(id, campo, valor).isSuccess()) {
                    System.out.println("Dados alterados!");
                } else {
                    System.out.println("Novo valor não é válido!");
                }
            } else {
                System.out.println("Parâmetro não existente!");
                return;
            }
        }
    }

    public void membrosMesaVoto(){

    }


    public void menu() throws RemoteException, ParseException {
        Scanner sc = new Scanner(System.in);
        int data;

            while(true){
                System.out.println("1 - Registar pessoas\n2 - Gerir departamentos\n3 - Gerir faculdades\n4 - Criar eleição\n5 - Gerir listas de candidatos a uma eleição\n6 - Gerir mesas de voto\n7 - Alterar propriedades de uma eleição\n8 - Local de eleição de um eleitor\n9 - Consultar detalhes de uma eleição passada\n10 - Voto antecipado\n11 - Alterar dados pessoais\n12 - Gerir membros da mesa de voto\n13 - Sair");
                data = Integer.parseInt(sc.nextLine());
                switch (data){
                    case 1:
                        registarPessoas();
                        break;
                    case 2:
                        gestaoDepartamenos();
                        break;
                    case 3:
                        gestaoFaculdades();
                        break;
                    case 4:
                        criarEleicao();
                        break;
                    case 5:
                        gerirListas();
                        break;
                    case 6:
                        gerirMesas();
                        break;
                    case 7:
                        alterarPropriedadesEleicao();
                        break;
                    case 8:
                        localEleicao();
                        break;

                    case 9:
                        consultaDadosEleicao();
                        break;

                    case 10:
                        votoAntecipado();
                        break;

                    case 11:
                        alterarDadosPessoais();
                        break;

                    case 12:

                        break;

                    case 13:
                        System.exit(0);

                }
            }
    }

}
