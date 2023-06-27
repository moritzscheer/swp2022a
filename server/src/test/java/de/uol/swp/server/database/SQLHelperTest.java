package de.uol.swp.server.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNotNull;

public class SQLHelperTest {

    private static final String DB_URL = "jdbc:mysql://duemmer.informatik.uni-oldenburg.de:2151";
    private static final String DB_USER = "swp2022A";
    private static final String DB_PASS = "*softwareprojekt2022A*";
    private static Connection SqlConnection;

    @Test
    public void testGetSqlConnection() throws SQLException {
        SqlConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
       assertNotNull(SqlConnection);
    }
}
