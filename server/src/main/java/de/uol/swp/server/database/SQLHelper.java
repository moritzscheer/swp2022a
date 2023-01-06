package de.uol.swp.server.database;

import com.jcraft.jsch.*;

import java.sql.*;
class localUserInfo implements UserInfo{
    String passwd;
    public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){return true;}
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){return true; }
    public boolean promptPassword(String message){return true;}
    public void showMessage(String message){}
}
public class SQLHelper {
    private static final String DB_URL = "jdbc:mysql://zimmermannnetwork.ddns.net:3306/owncloud";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "swp2022A";
    private static Connection SqlConnection;
    public static Connection GetSqlConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    public static int Update(String statement)throws Exception{
        if(SqlConnection == null)
            SqlConnection = GetSqlConnection();
        Connection con = SqlConnection;
        Statement sqlStmt = con.createStatement();
        return sqlStmt.executeUpdate(statement);
    }
    public static ResultSet Select(String statement) throws SQLException {
            SqlConnection = GetSqlConnection();
        Connection con = SqlConnection;
        Statement sqlStmt = con.createStatement();
        return sqlStmt.executeQuery(statement);
    }
}
