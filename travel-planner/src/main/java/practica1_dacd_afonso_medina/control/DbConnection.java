package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.SqliteException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection connect(String dbPath) throws SqliteException {
        try {
            String url = "jdbc:sqlite:" + dbPath;
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            throw new SqliteException("Error establishing SQLite connection.", e);
        }
    }
}
