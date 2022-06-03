package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for establishing connection with database
 * Environmental variables DB_LOGIN and DB_PASSWORD have to be assigned on your server.
 * Environmental variable DB_HOST can be assigned to use your database.
 * Default host is jdbc:postgresql://localhost:5432/postgres
 */
public class DatabaseConnector {
    /**
     * @return Connection with your database
     * @throws SQLException
     * @throws NoVariablesException if DB_LOGIN or DB_PASSWORD are not defined in your environment
     */
    public static Connection connect() throws SQLException, NoVariablesException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String login = System.getenv("DB_LOGIN");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        if (login == null || password == null) {
            throw new NoVariablesException();
        }
        if (host == null) {
            host = "jdbc:postgresql://localhost:5432/postgres";
        }
        return DriverManager.getConnection(host, login, password);
    }
}
