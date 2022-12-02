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
    private static final int lport = 2101;
    private static final String rhost = "localhost";
    private static final int rport = 3306;
    private static final String DB_URL = "jdbc:mysql://localhost:2101/rr";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "*swp22gruppeA$";
    private static final String SSH_USER = "jbruns4";
    private static final String SSH_PASSWORD = "cite65b";
    private static final String SSH_HOST = "duemmer.informatik.uni-oldenburg.de";
    private static final Integer SSH_PORT = 22;
    private static Connection SqlConnection;
    public static Connection GetSqlConnection() throws SQLException {
        try
        {
            JSch jsch = new JSch();

            Session session = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);
            localUserInfo lui = new localUserInfo();
            session.setPassword(SSH_PASSWORD);
            session.setUserInfo(lui);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            session.setPortForwardingL(lport, rhost, rport);
        }
        catch(JSchException je)
        {
            System.err.print(je);
        }
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
        //if(SqlConnection == null)
            SqlConnection = GetSqlConnection();
        Connection con = SqlConnection;
        Statement sqlStmt = con.createStatement();
        return sqlStmt.executeQuery(statement);
    }
}
