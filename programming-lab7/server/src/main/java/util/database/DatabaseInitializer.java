package util.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private final Connection database;

    public DatabaseInitializer(Connection connection) {
        database = connection;
    }

    public void checkOrInitTables() throws SQLException {
        Statement st = database.createStatement();

        st.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

        st.executeUpdate("CREATE TABLE IF NOT EXISTS s311770workers (" +
                "id int  PRIMARY KEY," +
                "name varchar(255) NOT NULL," +
                "xCoordinate int NOT NULL," +
                "yCoordinate int NOT NULL," +
                "creationDate date DEFAULT (current_date)," +
                "salary bigint NOT NULL CHECK(salary > 0)," +
                "startDate timestamp with time zone NOT NULL," +
                "endDate date," +
                "status varchar(30) NOT NULL " +
                "CHECK(status='FIRED' OR status='HIRED' OR status='REGULAR' OR status='PROBATION' OR status='RECOMMENDED_FOR_PROMOTION')," +
                "height float NOT NULL CHECK(height > 0)," +
                "eyeColor varchar(15)," +
                "hairColor varchar(15)," +
                "nationality varchar(15)," +
                "creator varchar(255)" +
                ")");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS s311770users (" +
                "login varchar(255) PRIMARY KEY," +
                "hashed_password BYTEA DEFAULT (null)" +
                ")");
    }
}
