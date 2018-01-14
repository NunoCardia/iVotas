package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public class Configuracao implements Serializable{

    private String server1;
    private String db_address;
    private int db_port;
    private int tcp1;
    private String rmiserver1;
    private String rmiserver2;
    private int rmiport1;
    private int rmiport2;
    private String data_base;
    private String admin;
    private String pass;
    private String console_host;
    private int console_port;

    public Configuracao() {
        Properties prop = new Properties();
        InputStream config = null;
        try {
            config = new FileInputStream("config.properties");
            prop.load(config);

        } catch (IOException ex) {
            ex.printStackTrace();
            ;
        } finally {
            if (config != null) {
                try {
                    config.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // SETTERS
        setServer1(prop.getProperty("SERVER1"));
        setDb_address(prop.getProperty("DB_ADDRESS"));
        setDb_port(Integer.parseInt(prop.getProperty("DB_PORT").trim()));
        setTcp1(Integer.parseInt(prop.getProperty("TCP1").trim()));
        setRmiport1(Integer.parseInt(prop.getProperty("RMIPORT1").trim()));
        setRmiport2(Integer.parseInt(prop.getProperty("RMIPORT2").trim()));
        setRmiserver1(prop.getProperty("RMISERVER1"));
        setRmiserver2(prop.getProperty("RMISERVER2"));

        setData_base(prop.getProperty("DATABASE_NAME"));
        setAdmin(prop.getProperty("ADMIN"));
        setPass(prop.getProperty("ADMIN_PASSWORD"));
        setConsole_host(prop.getProperty("CONSOLE_HOST"));
        setConsole_port(Integer.parseInt(prop.getProperty("CONSOLE_PORT")));


    }

    public String getServer1() {
        return server1;
    }

    public void setServer1(String server1) {
        this.server1 = server1;
    }

    public String getDb_address() {
        return db_address;
    }

    public void setDb_address(String db_address) {
        this.db_address = db_address;
    }

    public int getDb_port() {
        return db_port;
    }

    public void setDb_port(int db_port) {
        this.db_port = db_port;
    }

    public int getTcp1() {
        return tcp1;
    }

    public void setTcp1(int tcp1) {
        this.tcp1 = tcp1;
    }

    public String getData_base() {
        return data_base;
    }

    public void setData_base(String data_base) {
        this.data_base = data_base;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConsole_host() {
        return console_host;
    }

    public void setConsole_host(String console_host) {
        this.console_host = console_host;
    }

    public int getConsole_port() {
        return console_port;
    }

    public void setConsole_port(int console_port) {
        this.console_port = console_port;
    }

    public String getRmiserver1() {
        return rmiserver1;
    }

    public void setRmiserver1(String rmiserver1) {
        this.rmiserver1 = rmiserver1;
    }

    public String getRmiserver2() {
        return rmiserver2;
    }

    public void setRmiserver2(String rmiserver2) {
        this.rmiserver2 = rmiserver2;
    }

    public int getRmiport1() {
        return rmiport1;
    }

    public void setRmiport1(int rmiport1) {
        this.rmiport1 = rmiport1;
    }

    public int getRmiport2() {
        return rmiport2;
    }

    public void setRmiport2(int rmiport2) {
        this.rmiport2 = rmiport2;
    }
}
