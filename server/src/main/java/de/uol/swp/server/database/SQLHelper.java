package de.uol.swp.server.database;

import java.sql.*;

public class SQLHelper {
    private static final String DB_URL = "jdbc:mysql://duemmer.informatik.uni-oldenburg.de:2151";
    private static final String DB_USER = "swp2022A";
    private static final String DB_PASS = "*softwareprojekt2022A*";
    private static Connection SqlConnection;

    /**
     * Gets a connection to the database.
     *
     * @return The connection to the database.
     * @author Jann Bruns
     * @since 2022-12-02
     * @throws SQLException If the connection is invalid.
     */
    public static Connection GetSqlConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /**
     * Executes an update statement and returns the number of affected rows.
     *
     * @param statement The statement to be executed.
     * @return The number of affected rows.
     * @author Jann Bruns
     * @since 2022-12-02
     * @throws Exception If the statement is invalid.
     */
    public static int Update(String statement) throws Exception {
        if (SqlConnection == null) SqlConnection = GetSqlConnection();
        Connection con = SqlConnection;
        Statement sqlStmt = con.createStatement();
        return sqlStmt.executeUpdate(statement);
    }

    /**
     * Executes a select statement and returns the result.
     *
     * @param statement The statement to be executed.
     * @return The result of the statement.
     * @author Jann Bruns
     * @since 2022-12-02
     * @throws SQLException If the statement is invalid.
     */
    public static ResultSet Select(String statement) throws SQLException {
        SqlConnection = GetSqlConnection();
        Connection con = SqlConnection;
        Statement sqlStmt = con.createStatement();
        return sqlStmt.executeQuery(statement);
    }
}
