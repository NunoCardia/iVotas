package RMI;

import common.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public interface RMIServerInterface extends Remote{

    Response verificaLogin(String nome, String password) throws RemoteException;
    Response insereUtilizador(String nome, String tipo, String contacto, String username,String password, int dept_id, String morada, String cartao, Date validade) throws RemoteException;
    Response mostraDocentes() throws RemoteException;
    Response mostraFuncionarios() throws RemoteException;
    Response mostraEstudantes() throws RemoteException;
    Response verificaFaculdade(String nome) throws RemoteException;
    Response verificaFaculdadeId(int id) throws RemoteException;
    Response novaFaculdade(String nome) throws RemoteException;
    Response removerFaculdadePorId(int id) throws RemoteException;
    Response mostrarFaculdades() throws RemoteException;
    Response verificaDepartamento(String nome) throws RemoteException;
    Response verificaDepartamentoId(int id) throws RemoteException;
    Response novoDepartamento(String nome,int fac_id) throws RemoteException;
    Response mudarDepartamentoFacId(int id,int nova_fac_id) throws RemoteException;
    Response mudarDepartamento(int id,String novo_nome) throws RemoteException;
    Response removerDepartamentoPorId(int id) throws RemoteException;
    Response mostrarDepartamentos() throws RemoteException;
    Response mostrarDepartamentosPorFaculdade(int id) throws RemoteException;
    Response verificaEleicao(String titulo, String tipo) throws RemoteException;
    Response novaEleicao(String titulo,String descricao,Timestamp inicio,Timestamp fim,String tipo,int dept_id) throws RemoteException;
    Response verEleicao() throws RemoteException;
    Response escolheEleicao(int eleicao_id) throws RemoteException;
    Response verificaLista(int eleicao_id,String nome) throws RemoteException;
    Response verificaListaId(int lista_id) throws RemoteException;
    Response novaLista(int eleicao_id,String tipo,String nome) throws RemoteException;
    Response adicionarALista(int user_id,int lista_id,int eleicao_id) throws RemoteException;
    Response verTipoPessoa(String tipo) throws RemoteException;
    Response verificaEleicaoId(int eleicao_id) throws RemoteException;
    Response verEstudantesDepartamento(int dept_id) throws RemoteException;
    Response membrosAssociadosALista(int lista_id) throws RemoteException;
    Response removerPessoaDaLista(int user_id,int lista_id) throws RemoteException;
    Response removerPessoasLista(int lista_id) throws RemoteException;
    Response removerLista(int lista_id) throws RemoteException;
    Response imprimeLista(int eleicao_id) throws RemoteException;
    Response imprimeLista2(int eleicao_id) throws RemoteException;
    Response adicionarMesa(int dept_id, int eleicao_id) throws RemoteException;
    Response verMaquinasEleicao(int eleicao_id) throws RemoteException;
    Response verificaMaquinaId(int maquina_id) throws RemoteException;
    Response removerMesa(int maquina_id, int eleicao_id) throws RemoteException;
    Response verificaUtilizador(String campo,String valor) throws RemoteException;
    Response identificaVotante(String nome,int eleicao_id) throws RemoteException;
    Response votar(int user_id,int dept_id,int eleicao_id,String voto,Timestamp time) throws RemoteException;
    Response verificaComecoEleicao(int eleicao_id) throws RemoteException;
    Response alteraParametroEleicao(int eleicao_id,String parametro,String valor) throws RemoteException;
    Response verTipoEleicaoLista(int lista_id) throws RemoteException;
    Response verAlunosLista(int user_id,int eleicao_id) throws RemoteException;
    Response verificaId(String nome) throws RemoteException;
    Response pesquisaVoto(String username) throws RemoteException;
    Response mostraEleicoesPassadas() throws RemoteException;
    Response verificaDepartamentoMaquina(int dept_id) throws RemoteException;
    Response verEleicaoDepartamento(int dept_id) throws RemoteException;
    Response novoEstado(int eleicao_id,int maquina_id,String tipo,String estado,java.sql.Timestamp tempo,int apresentado) throws RemoteException;
    Response tipoPessoa(int user_id) throws RemoteException;
    Response boletimVoto(int eleicao_id,String tipo) throws RemoteException;
    Response votosValidosEleicao(int eleicao_id,String nome) throws RemoteException;
    Response votosBrancosEleicao(int eleicao_id) throws RemoteException;
    Response votosNulosEleicao(int eleicao_id) throws RemoteException;
    Response numeroTotalVotantesConselho(int dept_id) throws RemoteException;
    Response numeroTotalVotantesGeral() throws RemoteException;
    Response verDeptId(int dept_id) throws RemoteException;
    Response mostraVotantes() throws RemoteException;
    Response updateVotante(int voto_id) throws RemoteException;
    Response mostraEstado() throws RemoteException;
    Response updateEstado(int estado_id) throws RemoteException;
    Response deptPessoa(String nome) throws RemoteException;
    Response alteraDados(int user_id,String parametro,String valor) throws RemoteException;
    Response mostraEleicoesPorComecar() throws RemoteException;
    Response numeroMembrosMesa(int maquina_id, int eleicao_id) throws RemoteException;
    Response insereUtilMesa(int eleicao_id,int maquina_id,int user_id) throws RemoteException;
    Response pesquisaUtilEleicao(int maquina_id, int eleicao_id) throws RemoteException;
    Response pesquisaUtilMesa(int maquina_id, int eleicao_id,int user_id) throws  RemoteException;
    Response removeUtilMesa(int maquina_id, int eleicao_id,int user_id) throws RemoteException;
}
