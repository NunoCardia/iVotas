package RMI;

import RMI.DBQueries;

import java.sql.*;
import java.util.TimerTask;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public class DateControllerFim extends TimerTask {

    Connection connection;
    DBQueries queries;

    public DateControllerFim(Connection connection, DBQueries queries){
        this.connection = connection;
        this.queries = queries;
    }

    @Override
    public void run() {

        try{
            Statement stmt = connection.createStatement();
            ResultSet result = null;
            connection.setAutoCommit(false);
            result = stmt.executeQuery(queries.selecionarFechoEleicao);
            while(result.next()){
                PreparedStatement ps = connection.prepareStatement(queries.fecharEleicao);
                ps.setInt(1, result.getInt(1));
                ps.execute();
                System.out.println("Eleição com id: "+result.getInt(1)+ " terminou!!");
                //apagar mesas de voto associadas a eleicao
            }
            connection.commit();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
