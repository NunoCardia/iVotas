package RMI;

import common.Configuracao;
import common.Response;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private DBQueries queries;
    private Configuracao configuracao;
    Connection connection;
    Calendar calendar;
    Timer time;

    RMIServer() throws IOException,ClassNotFoundException,SQLException{
        super();
        queries = new DBQueries();
        configuracao = new Configuracao();
        calendar = Calendar.getInstance();
        time = new Timer();
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + configuracao.getDb_address() + ":" + configuracao.getDb_port()
        + "/" + configuracao.getData_base(), configuracao.getAdmin(), configuracao.getPass());

        time.schedule(new DateControllerInicio(connection, queries), 0, 1000);
        time.schedule(new DateControllerFim(connection, queries), 0, 1000);

        System.out.println("RMISERVER");
    }

    public Response verificaLogin(String username, String password) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaUtilizador);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaId(String nome) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaId);
            ps.setString(1,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public  synchronized Response insereUtilizador(String nome, String tipo, String contacto, String username,String password, int dept_id, String morada, String cartao, Date validade){
        Response answer = new Response();
        try {
            if(!verificaLogin(username,password).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.insertUtilizador);
                ps.setString(1,nome);
                ps.setString(2,tipo);
                ps.setString(3,username);
                ps.setString(4,contacto);
                ps.setString(5,password);
                ps.setInt(6,dept_id);
                ps.setString(7,morada);
                ps.setString(8,cartao);
                ps.setDate(9,validade);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraDocentes(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraDocentes);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                temp.add(result.getString(2));
                temp.add(result.getString(3));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraFuncionarios(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraFuncionarios);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                temp.add(result.getString(2));
                temp.add(result.getString(3));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraEstudantes(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraEstudantes);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                temp.add(result.getString(2));
                temp.add(result.getString(3));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response boletimVoto(int eleicao_id,String tipo){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.boletimVoto);
            ps.setInt(1,eleicao_id);
            ps.setString(2,tipo);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                String toAdd = String.valueOf(result.getInt(1))+"\t"+result.getString(2);
                temp.add(toAdd);
            }
            answer.setInfo(temp);
            answer.setSuccess(true);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response tipoPessoa(int user_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.tipoPessoa);
            ps.setInt(1,user_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if (result.next()){
                temp.add(result.getString(1));
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else {
                answer.setSuccess(false);
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response deptPessoa(String nome){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.deptPessoa);
            ps.setString(1,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if (result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else {
                answer.setSuccess(false);
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verTipoEleicaoLista(int lista_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verTipoEleicaoLista);
            ps.setInt(1,lista_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(result.getString(1));
                temp.add(String.valueOf(result.getInt(2)));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verAlunosLista(int user_id,int eleicao_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verAlunosLista);
            ps.setInt(1,user_id);
            ps.setInt(2,eleicao_id);
            ResultSet result = ps.executeQuery();

            connection.commit();
            if (result.next()){
                answer.setSuccess(true);
            }
            else{
                answer.setSuccess(false);
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaFaculdade(String nome){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaFaculdade);
            ps.setString(1,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                answer.setSuccess(true);
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaFaculdadeId(int id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaFaculdadeId);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response novaFaculdade(String nome){
        Response answer = new Response();
        try {
            if(!verificaFaculdade(nome).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.novaFaculdade);
                ps.setString(1,nome);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mudarFaculdade(int id,String novo_nome){
        Response answer = new Response();
        try {
            if(verificaFaculdadeId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.mudarFaculdade);
                ps.setInt(1,id);
                ps.setString(2,novo_nome);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerFaculdadePorId(int id){
        Response answer = new Response();
        try {
            if(verificaFaculdadeId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removerFaculdadePorId);
                ps.setInt(1,id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostrarFaculdades(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostrarFaculdades);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<String>();
            connection.commit();
            while(result.next()){
                String toAdd = Integer.toString(result.getInt("fac_id")) + "\t" + result.getString("nome");
                temp.add(toAdd);
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaDepartamento(String nome){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaDepartamento);
            ps.setString(1,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setSuccess(true);
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaDepartamentoId(int id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaDepartamentoId);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response novoDepartamento(String nome, int fac_id){
        Response answer = new Response();
        try {
            if(!verificaDepartamento(nome).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.novoDepartamento);
                ps.setString(1,nome);
                ps.setInt(2,fac_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mudarDepartamentoFacId(int id,int nova_fac_id){
        Response answer = new Response();
        try {
            if(verificaFaculdadeId(nova_fac_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.alterarFacIdDepartamento);
                ps.setInt(1,id);
                ps.setInt(2,nova_fac_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mudarDepartamento(int id,String novo_nome){
        Response answer = new Response();
        try {
            if(verificaDepartamentoId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.alterarNomeDepartamento);
                ps.setString(1,novo_nome);
                ps.setInt(2,id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerDepartamentoPorId(int id){
        Response answer = new Response();
        try {
            if(verificaDepartamentoId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removerDepartamentoPorId);
                ps.setInt(1,id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostrarDepartamentos(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostrarDepartamentos);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<String>();
            connection.commit();
            while(result.next()){
                String toAdd = Integer.toString(result.getInt("dept_id")) + "\t" + result.getString("nome") + "\t" + result.getString("fac_nome");
                temp.add(toAdd);
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostrarUmDepartamento(int id){
        Response answer = new Response();
        try {
            if(verificaDepartamentoId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.mostrarUmDepartamento);
                ps.setInt(1,id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostrarDepartamentosPorFaculdade(int id){
        Response answer = new Response();
        try {
            if(verificaFaculdadeId(id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.mostrarDepartamentosFaculdade);
                ps.setInt(1,id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    String toAdd = String.valueOf(result.getInt(1)) + "\t" + result.getString(2);
                    temp.add(toAdd);
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaEleicao(String titulo, String tipo){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaEleicao);
            ps.setString(1,titulo);
            ps.setString(2,tipo);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response novaEleicao(String titulo,String descricao,Timestamp inicio,Timestamp fim,String tipo,int dept_id){
        Response answer = new Response();
        try {
            if(!verificaEleicao(titulo,tipo).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.novaEleicao);
                ps.setString(1,titulo);
                ps.setString(2,descricao);
                ps.setTimestamp(3,inicio);
                ps.setTimestamp(4,fim);
                ps.setString(5,tipo);
                if(dept_id == 0){
                    ps.setNull(6,Types.INTEGER);
                }
                else {
                    ps.setInt(6,dept_id);
                }
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verEleicao(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verEleicao);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<String>();
            connection.commit();
            while(result.next()){
                String toAdd = Integer.toString(result.getInt("eleicao_id")) + "\t" + result.getString("titulo") + "\t" + result.getString("tipo");
                if(result.getInt("dept_id") != 0)
                    toAdd += "\t" + Integer.toString(result.getInt("dept_id"));
                temp.add(toAdd);
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verEleicaoDepartamento(int dept_id){
        String toAdd;
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verEleicaoDepartamento);
            ps.setInt(1,dept_id);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<String>();
            connection.commit();
            while(result.next()){
                if(result.getInt("dept_id") != 0)
                    toAdd = Integer.toString(result.getInt("eleicao_id")) + "\t" + result.getString("titulo") + "\t" + result.getString("tipo") + "\t"+Integer.toString(result.getInt("dept_id"));
                else
                    toAdd = Integer.toString(result.getInt("eleicao_id")) + "\t" + result.getString("titulo") + "\t" + result.getString("tipo");
                temp.add(toAdd);
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response escolheEleicao(int eleicao_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.escolheEleicao);
            ps.setInt(1,eleicao_id);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<String>();
            connection.commit();
            while(result.next()){
                temp.add(result.getString("titulo"));
                temp.add(result.getString("tipo"));
                temp.add(Integer.toString(result.getInt("dept_id")));
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }


    public synchronized Response verificaLista(int eleicao_id,String nome){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaLista);
            ps.setInt(1,eleicao_id);
            ps.setString(2,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaListaId(int lista_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaListaId);
            ps.setInt(1,lista_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                answer.setSuccess(true);
                temp.add(result.getString(1));
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response novaLista(int eleicao_id,String tipo,String nome){
        Response answer = new Response();
        try {
            if(!verificaLista(eleicao_id,nome).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.criarLista);
                ps.setInt(1,eleicao_id);
                ps.setString(2,tipo);
                ps.setString(3,nome);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verEstudantesDeUmDepartamento(int dept_id){
        Response answer = new Response();
        try {
            if(verificaDepartamentoId(dept_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.verEstudantesDeUmDepartamento);
                ps.setInt(1,dept_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<String>();
                while(result.next()){
                    String toAdd = Integer.toString(result.getInt("user_id")) + "\t" + result.getString("nome");
                    temp.add(toAdd);
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response adicionarALista(int user_id,int lista_id,int eleicao_id){
        Response answer = new Response();
        try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.adicionarALista);
                ps.setInt(1,user_id);
                ps.setInt(2,lista_id);
                ps.setInt(3,eleicao_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verTipoPessoa(String tipo){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verTipoPessoa);
            ps.setString(1,tipo);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while(result.next()){
                temp.add(Integer.toString(result.getInt("user_id")));
                temp.add(result.getString("nome"));
                temp.add(result.getString("contacto"));
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaEleicaoId(int eleicao_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaEleicaoId);
            ps.setInt(1,eleicao_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                ArrayList<String> resp = new ArrayList<>();
                resp.add(result.getString(1));
                answer.setInfo(resp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verListaEleicao(int eleicao_id){
        Response answer = new Response();
        try {
            if(verificaEleicaoId(eleicao_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.verlistasEleicao);
                ps.setInt(1,eleicao_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    temp.add(Integer.toString(result.getInt("lista_id")));
                    temp.add(result.getString("nome"));
                    temp.add(result.getString("tipo"));
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verEstudantesDepartamento(int dept_id){
        Response answer = new Response();
        try {
            if(verificaDepartamentoId(dept_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.verEstudantesDepartamento);
                ps.setInt(1,dept_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    temp.add(Integer.toString(result.getInt("user_id")));
                    temp.add(result.getString("nome"));
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response membrosAssociadosALista(int lista_id){
        Response answer = new Response();
        try {
            if(verificaListaId(lista_id).isSuccess()){

                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.membrosAssociadosALista);
                ps.setInt(1,lista_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    temp.add(Integer.toString(result.getInt("user_id")));
                    temp.add(result.getString("nome"));
                    temp.add(Integer.toString(result.getInt("dept_id")));
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerPessoaDaLista(int user_id,int lista_id){
        Response answer = new Response();
        try {
            if(verificaListaId(lista_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removePessoaDaLista);
                ps.setInt(1,user_id);
                ps.setInt(2,lista_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerPessoasLista(int lista_id){
        Response answer = new Response();
        try {
            if(verificaListaId(lista_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removerPessoasLista);
                ps.setInt(1,lista_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerLista(int lista_id){
        Response answer = new Response();
        try {
            if(verificaListaId(lista_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removerLista);
                ps.setInt(1,lista_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response imprimeLista(int eleicao_id){
        Response answer = new Response();
        try {
            if(verificaEleicaoId(eleicao_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.imprimeLista);
                ps.setInt(1,eleicao_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    String toAdd = Integer.toString(result.getInt("lista_id")) + "\t" + result.getString("nome") + "\t" + result.getString("tipo");
                    temp.add(toAdd);
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response imprimeLista2(int eleicao_id){
        Response answer = new Response();
        try {
            if(verificaEleicaoId(eleicao_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.imprimeLista2);
                ps.setInt(1,eleicao_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    String toAdd = result.getString(1);
                    temp.add(toAdd);
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response adicionarMesa(int dept_id, int eleicao_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.adicionarMesa);
            ps.setInt(1,dept_id);
            ps.setInt(2,eleicao_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verMaquinasEleicao(int eleicao_id){
        Response answer = new Response();
        try {
            if(verificaEleicaoId(eleicao_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.verMaquinasEleicao);
                ps.setInt(1,eleicao_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    String toAdd = Integer.toString(result.getInt("maquina_id")) + "\t" + result.getString("titulo") + "\t" + result.getString("tipo") + "\t" + Integer.toString(result.getInt("dept_id"));
                    temp.add(toAdd);
                }
                answer.setInfo(temp);
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaMaquinaId(int maquina_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaMaquinaId);
            ps.setInt(1,maquina_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removerMesa(int maquina_id, int eleicao_id){
        Response answer = new Response();
        try {
            if(verificaMaquinaId(maquina_id).isSuccess() && verificaEleicaoId(eleicao_id).isSuccess()){
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removerMesa);
                ps.setInt(1,maquina_id);
                ps.setInt(2,eleicao_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response verificaUtilizador(String campo,String valor){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(String.format(queries.identificarEleitor, campo,valor));
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(result.getString(1));
                answer.setSuccess(true);
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response identificaVotante(String nome,int eleicao_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.identificarVotante);
            ps.setString(1,nome);
            ps.setInt(2,eleicao_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getString(1)));
                answer.setSuccess(true);
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response autenticarEleitor(String nome,String password){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.autenticarEleitor);
            ps.setString(1,nome);
            ps.setString(1,password);
            ResultSet result = ps.executeQuery();
            connection.commit();
            if(result.next()){
                answer.setSuccess(true);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response votar(int user_id,int dept_id,int eleicao_id,String voto,Timestamp time){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.criaVoto);
            ps.setInt(1,user_id);
            ps.setInt(2,dept_id);
            ps.setInt(3,eleicao_id);
            ps.setString(4,voto);
            ps.setTimestamp(5,time);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }


    public synchronized Response verificaComecoEleicao(int eleicao_id){
        Response answer = new Response();
        try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.verificaComecoEleicao);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                while(result.next()){
                    temp.add(Integer.toString(result.getInt("eleicao_id")));
                }
                if(temp.contains(String.valueOf(eleicao_id))){
                    answer.setSuccess(true);
                }else{
                    answer.setSuccess(false);
                }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response alteraParametroEleicao(int eleicao_id,String parametro,String valor){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(String.format(queries.alteraParametroEleicao, parametro,valor));
            ps.setInt(1,eleicao_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response pesquisaUtilizadorVoto(int user_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.pesquisaUtilizadorVoto);
            ps.setInt(1,user_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                temp.add(String.valueOf(result.getInt(2)));
                temp.add(String.valueOf(result.getInt(3)));
                temp.add(result.getString(4));
                temp.add(String.valueOf(result.getDate(5)));
                answer.setInfo(temp);
                answer.setSuccess(true);
            }
            else {
                answer.setSuccess(false);
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response pesquisaVoto(String username){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            int id = Integer.parseInt(verificaId(username).getInfo().get(0));
            Response r = pesquisaUtilizadorVoto(id);
            if(!r.isSuccess()){
                answer.setSuccess(false);
            }
            else {
                int dept_id = Integer.parseInt(pesquisaUtilizadorVoto(id).getInfo().get(1));
                PreparedStatement ps = connection.prepareStatement(queries.pesquisaVoto);
                ps.setInt(1,id);
                ps.setInt(2,dept_id);
                ResultSet result = ps.executeQuery();
                connection.commit();
                ArrayList<String> temp = new ArrayList<>();
                if(result.next()){
                    String toAdd = result.getString(1) + "\t" + result.getString(2) + "\t" + result.getString(3) + "\t" + result.getTimestamp(4).toString() + "\t" +result.getString(5);
                    temp.add(toAdd);
                    answer.setInfo(temp);
                    answer.setSuccess(true);
                }
                else{
                    answer.setSuccess(false);
                }
            }
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraEleicoesPassadas(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraEleicoesPassadas);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while(result.next()){
                temp.add(Integer.toString(result.getInt(1)));
                temp.add(result.getString(2));
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraEleicoesPorComecar(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraEleicoesPorComecar);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while(result.next()){
                temp.add(Integer.toString(result.getInt(1)));
                temp.add(result.getString(2));
            }
            answer.setInfo(temp);
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }


    public synchronized Response verificaDepartamentoMaquina(int dept_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verificaDepartamentoMaquina);
            ps.setInt(1,dept_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                answer.setSuccess(true);
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response novoEstado(int eleicao_id,int maquina_id,String tipo,String estado,java.sql.Timestamp tempo,int apresentado){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.novoEstado);
            ps.setInt(1,eleicao_id);
            ps.setInt(2,maquina_id);
            ps.setString(3,tipo);
            ps.setString(4,estado);
            ps.setTimestamp(5,tempo);
            ps.setInt(6,apresentado);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response votosValidosEleicao(int eleicao_id,String nome) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.votosValidosEleicao);
            ps.setInt(1,eleicao_id);
            ps.setString(2,nome);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response votosBrancosEleicao(int eleicao_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.votosBrancosEleicao);
            ps.setInt(1,eleicao_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response votosNulosEleicao(int eleicao_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.votosNulosEleicao);
            ps.setInt(1,eleicao_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response numeroTotalVotantesGeral() {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.numeroTotalVotantesGeral);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response numeroTotalVotantesConselho(int dept_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.numeroTotalVotantesConselho);
            ps.setInt(1,dept_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;

    }

    public synchronized Response verDeptId(int dept_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.verTempoLista);
            ps.setInt(1,dept_id);
            ResultSet result = ps.executeQuery();
            ArrayList<String> temp = new ArrayList<>();
            connection.commit();
            if (result.next()){
                temp.add(Integer.toString(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);
            }
            else{
                answer.setSuccess(false);
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraVotantes(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraVotantes);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(Integer.toString(result.getInt(1)));
                temp.add(Integer.toString(result.getInt(2)));
                temp.add(Integer.toString(result.getInt(3)));
                temp.add(Integer.toString(result.getInt(4)));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response mostraEstado(){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.mostraEstado);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            while (result.next()){
                temp.add(Integer.toString(result.getInt(1)));
                temp.add(Integer.toString(result.getInt(2)));
                temp.add(Integer.toString(result.getInt(3)));
                temp.add(result.getString(5));
            }
            answer.setInfo(temp);

        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response updateVotante(int voto_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.updateVotantes);
            ps.setInt(1,voto_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response updateEstado(int estado_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.updateEstado);
            ps.setInt(1,estado_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response alteraDados(int user_id,String parametro,String valor){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(String.format(queries.alteraDados, parametro,valor));
            ps.setInt(1,user_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response numeroMembrosMesa(int maquina_id, int eleicao_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.numeroMembrosMesa);
            ps.setInt(1,maquina_id);
            ps.setInt(2,eleicao_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                temp.add(String.valueOf(result.getInt(1)));
                answer.setInfo(temp);
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response insereUtilMesa(int eleicao_id,int maquina_id,int user_id){
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.insereUtilMesa);
            ps.setInt(1,user_id);
            ps.setInt(2,maquina_id);
            ps.setInt(3,eleicao_id);
            ps.execute();
            connection.commit();
            answer.setSuccess(true);
        } catch(SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response pesquisaUtilEleicao(int maquina_id, int eleicao_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.pesquisaUtilEleicao);
            ps.setInt(1,eleicao_id);
            ps.setInt(2,maquina_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            String toAdd;
            while(result.next()){
                toAdd = Integer.toString(result.getInt(1))+ "\t" + result.getString(2);
                temp.add(toAdd);
                answer.setInfo(temp);
                answer.setSuccess(true);

            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response pesquisaUtilMesa(int maquina_id, int eleicao_id,int user_id) {
        Response answer = new Response();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(queries.pesquisaUtilMesa);
            ps.setInt(1,user_id);
            ps.setInt(2,eleicao_id);
            ps.setInt(3,maquina_id);
            ResultSet result = ps.executeQuery();
            connection.commit();
            ArrayList<String> temp = new ArrayList<>();
            if(result.next()){
                answer.setSuccess(true);

            }else{
                answer.setSuccess(false);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            answer.setSuccess(false);
        }
        return answer;
    }

    public synchronized Response removeUtilMesa(int maquina_id, int eleicao_id,int user_id){
        Response answer = new Response();
        try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(queries.removeUtilMesa);
                ps.setInt(1,user_id);
                ps.setInt(2,eleicao_id);
                ps.setInt(3,maquina_id);
                ps.execute();
                connection.commit();
                answer.setSuccess(true);
            }
            catch (SQLException e1) {
                try {
                    connection.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                answer.setSuccess(false);
        }
        return answer;
    }

}

