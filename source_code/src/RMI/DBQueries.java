package RMI;

/**
 * Created by cyberfox21 on 10/10/17.
 */
public class DBQueries {

    //1
    String insertUtilizador = "INSERT INTO utilizador (nome,tipo,username,contacto,password,dept_id,morada,cartao,validade) VALUES (?,?,?,?,?,?,?,?,?);";
    String verificaUtilizador = "SELECT user_id FROM utilizador WHERE username = ? AND password = ?";
    String tipoPessoa = "SELECT tipo FROM utilizador WHERE user_id = ?;";
    String deptPessoa = "SELECT dept_id FROM utilizador WHERE nome = ?;";
    String verificaId = "SELECT user_id FROM utilizador WHERE username = ?;";
    String alteraDados = "UPDATE utilizador SET %s = '%s' WHERE user_id = ?;";
    String mostraDocentes = "SELECT user_id,utilizador.nome,departamento.nome FROM utilizador INNER JOIN departamento WHERE departamento.dept_id = utilizador.dept_id AND tipo = 'docente';";
    String mostraFuncionarios = "SELECT user_id,utilizador.nome,departamento.nome FROM utilizador INNER JOIN departamento WHERE departamento.dept_id = utilizador.dept_id AND tipo = 'funcionario';";
    String mostraEstudantes = "SELECT user_id,utilizador.nome,departamento.nome FROM utilizador INNER JOIN departamento WHERE departamento.dept_id = utilizador.dept_id AND tipo = 'estudante';";
    //2 - faculdades
    String novaFaculdade = "INSERT INTO faculdade (nome) VALUES (?);";
    String verificaFaculdade = "SELECT fac_id FROM faculdade WHERE nome = ?;";
    String verificaFaculdadeId = "SELECT nome FROM faculdade WHERE fac_id = ?;";
    String mudarFaculdade = "UPDATE faculdade set nome='%s' WHERE fac_id = ?;";
    String removerFaculdadePorId = "DELETE FROM faculdade WHERE fac_id = ?;";
    String mostrarFaculdades = "SELECT * FROM faculdade;";
    //2 - departamentos
    String verificaDepartamento = "SELECT dept_id FROM departamento WHERE nome = ?;";
    String verificaDepartamentoId = "SELECT nome FROM departamento WHERE dept_id = ?;";
    String novoDepartamento = "INSERT INTO departamento (nome, fac_id) VALUES (?,?);";
    String mostrarDepartamentos = "SELECT departamento.dept_id,departamento.nome,faculdade.nome AS fac_nome FROM departamento INNER JOIN faculdade WHERE departamento.fac_id = faculdade.fac_id;";
    String mostrarUmDepartamento = "SELECT * FROM departamento WHERE dept_id = ?;";
    String alterarNomeDepartamento = "UPDATE departamento set nome=? WHERE dept_id = ?;";
    String alterarFacIdDepartamento = "UPDATE departamento set fac_id = ? WHERE dept_id = ?;";
    String removerDepartamentoPorId = "DELETE FROM departamento WHERE dept_id = ?;";
    String mostrarDepartamentosFaculdade = "SELECT * FROM departamento WHERE fac_id = ?;";
    //3 - criar eleições
    String verificaEleicao = "SELECT eleicao_id FROM eleicao WHERE titulo = ? AND tipo = ?;";
    String verificaEleicaoId = "SELECT tipo FROM eleicao WHERE eleicao_id = ?;";
    String novaEleicao = "INSERT INTO eleicao (titulo,descricao,inicio,fim,tipo,dept_id) VALUES (?,?,?,?,?,?);";
    String verEleicaoDepartamento = "select * from eleicao where now() >= inicio AND (dept_id is NULL OR dept_id = ?);";
    //4 - GERIR LISTAS DE CANDIDATOS A UMA ELEIÇÃO - CRIAR LISTAS
    String verEleicao = "SELECT eleicao_id,titulo,tipo,dept_id FROM eleicao WHERE terminado = 0;";
    String escolheEleicao = "SELECT titulo,tipo,dept_id FROM eleicao WHERE terminado = 0 AND eleicao_id = ?;";
    String verificaListaId = "SELECT tipo FROM lista WHERE lista_id = ?;";
    String verificaLista = "SELECT lista_id FROM lista WHERE eleicao_id = ? AND nome = ?;";
    String criarLista = "INSERT INTO lista (eleicao_id,tipo,nome) VALUES (?,?,?);";
    String verEstudantesDeUmDepartamento ="SELECT user_id,nome FROM utilizador WHERE dept_id = ? AND tipo = 'estudante';";
    String adicionarALista = "INSERT INTO lista_util (user_id,lista_id,eleicao_id) VALUES (?,?,?);";
    String verTipoPessoa = "SELECT user_id,nome,contacto FROM utilizador WHERE tipo = ?;";
    //4 - GERIR LISTAS DE CANDIDATOS A UMA ELEIÇÃO - ALTERAR LISTAS(adicionar ou remover utilizadores da lista)
    String verlistasEleicao = "SELECT lista_id,nome,tipo FROM lista WHERE eleicao_id = ?;";
    String verEstudantesDepartamento = "SELECT user_id,nome FROM utilizador WHERE user_id AND dept_id = ?;";
    String verAlunosLista = "SELECT * FROM lista_util WHERE user_id = ? AND eleicao_id = ?;";
    String membrosAssociadosALista = "SELECT lista_util.user_id,utilizador.nome,utilizador.dept_id FROM lista_util INNER JOIN utilizador ON lista_util.user_id = utilizador.user_id AND lista_util.lista_id = ?;";
    String removePessoaDaLista = "DELETE FROM lista_util WHERE user_id = ? AND lista_id = ?;";
    //4 - GERIR LISTAS DE CANDIDATOS A UMA ELEIÇÃO - REMOVER LISTAS
    String removerPessoasLista = "DELETE FROM lista_util WHERE lista_id = ?";
    String removerLista = "DELETE FROM lista WHERE lista_id = ?;";
    //4 - GERIR LISTAS DE CANDIDATOS A UMA ELEIÇÃO - IMPRIMIR LISTAS
    String imprimeLista = "SELECT nome,tipo,lista_id FROM lista WHERE eleicao_id = ?;";
    String imprimeLista2 = "SELECT nome FROM lista WHERE eleicao_id = ?;";
    //5 - GERIR MESAS DE VOTO - ADICIONAR
    String adicionarMesa = "INSERT INTO maquina (dept_id,eleicao_id) VALUES (?,?);";
    //5 - GERIR MESAS DE VOTO - REMOVER
    String verificaMaquinaId = "SELECT dept_id FROM maquina WHERE maquina_id = ?;";
    String verMaquinasEleicao = "SELECT maquina.maquina_id,eleicao.titulo,eleicao.tipo,eleicao.dept_id FROM maquina INNER JOIN eleicao ON eleicao.eleicao_id = maquina.eleicao_id AND maquina.eleicao_id = ?;";
    String removerMesa = "DELETE FROM maquina WHERE maquina_id = ? AND eleicao_id = ?;";
    //6 - IDENTIFICAR ELEITOR NA MESA
    String identificarEleitor = "SELECT nome FROM utilizador WHERE %s = '%s';";
    String identificarVotante = "SELECT voto.user_id FROM utilizador INNER JOIN voto ON voto.user_id = utilizador.user_id AND utilizador.nome = ? AND voto.eleicao_id = ?;";
    //7 - AUTENTICAÇÂO DO ELEITOR
    String autenticarEleitor = "SELECT user_id FROM utilizador WHERE nome = ? AND password = ?;";
    //8 - VOTAR
    String boletimVoto = "select lista_id,nome from lista WHERE eleicao_id = ? AND tipo = ?;";
    String criaVoto = " INSERT INTO voto (user_id,dept_id,eleicao_id,voto,tempo) VALUES (?,?,?,?,?)";
    //9 - ALTERAR PROPRIEDADES DE UMA ELEICAO
    String verificaComecoEleicao = "SELECT eleicao_id FROM eleicao WHERE now() >= inicio;";
    String alteraParametroEleicao = "UPDATE eleicao SET %s = '%s' WHERE eleicao_id = ?;";
    //10 - SABER EM QUE LOCAL VOTOU CADA ELEITOR
    String pesquisaUtilizadorVoto = "SELECT user_id,dept_id,eleicao_id,voto,tempo FROM voto WHERE user_id = ?;";
    //11 - ESTADO DAS MESAS DE VOTO
    String novoEstado = "INSERT INTO estado (eleicao_id,maquina_id,tipo,estado,tempo,apresentado) VALUES (?,?,?,?,?,?);";
    String mostraEstado = "SELECT * FROM estado WHERE apresentado = 0;";
    String updateEstado = "UPDATE estado SET apresentado = 1 WHERE estado_id = ?;";
    //12 - ELEITORES EM TEMPO REAL
    String mostraVotantes = "SELECT * FROM voto WHERE apresentado = 0;";
    String updateVotantes = "UPDATE voto SET apresentado = 1 WHERE voto_id = ?;";
    //13 - TERMINO DAS ELEIÇOES
    String selecionarEleicao = "SELECT * FROM eleicao WHERE now() >= inicio AND terminado = 0;";
    String selecionarFechoEleicao = "SELECT * FROM eleicao WHERE now() >= fim AND terminado = 0;";
    String fecharEleicao = "UPDATE eleicao SET terminado = 1 WHERE eleicao_id = ?;";
    //14 - CONSULTAR OS DETALHES DAS ELEIÇOES PASSADAS
    String mostraEleicoesPassadas = "SELECT eleicao_id,titulo FROM eleicao WHERE now() >= fim;";
    String mostraEleicoesPorComecar = "SELECT eleicao_id,titulo FROM eleicao WHERE now() < inicio;";
    String verTempoLista = "SELECT * FROM eleicao WHERE eleicao_id = ?;";

    //adicionais - assim que a eleicao começa sao criadas tres linhas na tabela de resultados. Assim que o user votar as linhas sao atualizadas
    //de acordo com o seu voto
    String verTipoEleicaoLista = "SELECT eleicao.tipo,eleicao.dept_id FROM eleicao INNER JOIN lista ON lista.eleicao_id = eleicao.eleicao_id AND lista.lista_id = ?;";
    String pesquisaVoto = "select utilizador.nome AS utilizador,departamento.nome AS departamento,faculdade.nome AS faculdade,tempo,voto FROM voto,faculdade INNER JOIN utilizador,departamento WHERE voto.user_id = utilizador.user_id AND departamento.fac_id = faculdade.fac_id AND voto.user_id = ? AND departamento.dept_id = ?;";
    String verificaDepartamentoMaquina = "SELECT * FROM maquina WHERE dept_id = ?;";
    String votosValidosEleicao = "SELECT COUNT(*) FROM voto WHERE eleicao_id = ? AND voto = ?;";
    String votosBrancosEleicao = "SELECT COUNT(*) FROM voto WHERE eleicao_id = ? AND voto = 'branco';";
    String votosNulosEleicao = "SELECT COUNT(*) FROM voto WHERE eleicao_id = ? AND voto = 'nulo';";
    String numeroTotalVotantesGeral = "SELECT COUNT(*) FROM utilizador;";
    String numeroTotalVotantesConselho = "SELECT COUNT(*) FROM utilizador WHERE dept_id = ?;";
    //MEMBROS DA MESA DE VOTO
    String insereUtilMesa = "INSERT INTO maquina_util(user_id,maquina_id,eleicao_id) VALUES (?,?,?);";
    String pesquisaUtilMesa = "SELECT * FROM maquina_util WHERE user_id = ? AND eleicao_id= ? AND maquina_id = ?;";
    String pesquisaUtilEleicao = "SELECT utilizador.user_id,utilizador.nome FROM utilizador INNER JOIN maquina_util WHERE utilizador.user_id = maquina_util.user_id AND maquina_util.eleicao_id = ? AND maquina_util.maquina_id = ?;";
    String removeUtilMesa = "DELETE FROM maquina_util WHERE user_id = ? AND eleicao_id = ? AND maquina_id = ?;";
    String numeroMembrosMesa = "SELECT count(user_id) FROM maquina_util WHERE maquina_id = ? AND eleicao_id = ?;";


}
