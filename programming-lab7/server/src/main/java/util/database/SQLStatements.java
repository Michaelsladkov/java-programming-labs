package util.database;

public class SQLStatements {
    public static final String insertWorker = "INSERT INTO s311770workers " +
            "(id, name, xCoordinate, yCoordinate, salary, startDate, endDate," +
            " status,height, eyeColor, hairColor, nationality, creator ) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

    public static final String GENERATE_ID = "SELECT nextval('ids');";

    public static final String INSERT_USER_WITH_PASSWORD = "INSERT INTO s311770users (login, hashed_password) VALUES(?, ?);";

    public static final String CHECK_USER = "SELECT * FROM s311770users WHERE login=? AND hashed_password=?;";

    public static final String UPDATE_WORKER = "UPDATE s311770workers SET " +
            "name=?, xcoordinate=?, ycoordinate=?, salary=?, startdate=?, enddate=?," +
            " status=?,height=?, eyecolor=?, haircolor=?, nationality=? " +
            "WHERE id = ?";

    public static final String GET_BY_ID = "SELECT * FROM s311770workers WHERE id = ?";

    public static final String DELETE_BY_ID = "DELETE FROM s311770workers WHERE id = ?";

    public static final String CLEAR_ALL_BY_USER = "DELETE FROM s311770workers WHERE creator = ?";

    public static final String REMOVE_ALL_BY_STATUS_AND_USER = "DELETE FROM s311770workers WHERE status = ?" +
            "AND creator = ?";

    public static final String TAKE_ALL = "SELECT * FROM s311770workers";
}
