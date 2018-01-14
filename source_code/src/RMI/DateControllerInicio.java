package RMI;

import RMI.DBQueries;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public class DateControllerInicio extends TimerTask {

    Connection connection;
    DBQueries queries;
    ArrayList<Integer> id;

    public DateControllerInicio(Connection connection, DBQueries queries){
        this.connection = connection;
        this.queries = queries;
        this.id = new ArrayList<>();
    }

    @Override
    public void run() {

        try{
            Statement stmt = connection.createStatement();
            ResultSet result = null;
            connection.setAutoCommit(false);
            result = stmt.executeQuery(queries.selecionarEleicao);
            connection.commit();
            while(result.next()){

                if(!id.contains(result.getInt(1)))
                    System.out.println("Eleição com id: "+result.getInt(1)+ " começou!!");
                    id.add(result.getInt(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
