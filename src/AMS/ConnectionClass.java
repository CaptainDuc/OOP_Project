package AMS;

import java.lang.*;
import java.sql.*;
public class ConnectionClass {
    public Connection con;
    public Statement stm;
    
    public ConnectionClass(String dbUser, String dbPassword){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://CAPTAINDUC-LAPT\\SQLEXPRESS;databaseName=a;encrypt=false;trustServerCertificate=true;";
            String user = dbUser; 
            String password = dbPassword;
            con = DriverManager.getConnection(url, user, password);
            stm = con.createStatement();
            }  
        catch(Exception ex){
            ex.printStackTrace();
            con = null;
        }
    }
//    public static void main(String[] args){
//        new ConnectionClass();
//    }
}
