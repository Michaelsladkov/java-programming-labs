package util.database;

import data.Color;
import data.Person;
import data.Worker;
import util.StorageManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.Instant;

public class DatabaseWorks {
    private final Connection database;
    private final MessageDigest digest;

    public DatabaseWorks(Connection connection) throws NoSuchAlgorithmException {
        database = connection;
        digest = MessageDigest.getInstance("SHA-256");
    }

    public boolean insertWorker(Worker worker) {
        try {
            PreparedStatement insertStatement = database.prepareStatement(SQLStatements.insertWorker);
            setWorkerToStatement(insertStatement, worker);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Integer generateId() {
        try {
            Statement idRequest = database.createStatement();
            ResultSet resultSet = idRequest.executeQuery(SQLStatements.GENERATE_ID);
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertUser(String login, String password) {
        try {
            PreparedStatement insertStatement;
            insertStatement = database.prepareStatement(SQLStatements.INSERT_USER_WITH_PASSWORD);
            insertStatement.setString(1, login);
            insertStatement.setBytes(2, getHash(password));
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Checks if user with this login and password hash exists
     *
     * @param login
     * @param password
     * @return true if such user is registered, otherwise false
     */
    public boolean checkUser(String login, String password) {
        try {
            PreparedStatement checkStatement = database.prepareStatement(SQLStatements.CHECK_USER);
            checkStatement.setString(1, login);
            checkStatement.setBytes(2, getHash(password));
            ResultSet user = checkStatement.executeQuery();
            return user.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String deleteWorker(int id, String login) {
        try {
            PreparedStatement workerToBeDeletedRq = database.prepareStatement(SQLStatements.GET_BY_ID);
            workerToBeDeletedRq.setInt(1, id);
            ResultSet workerToBeDeleted = workerToBeDeletedRq.executeQuery();
            if (!workerToBeDeleted.next()) {
                return "no worker with this id found";
            }
            if (!workerToBeDeleted.getString("creator").equals(login)) {
                return "permission denied";
            }
            PreparedStatement delete = database.prepareStatement(SQLStatements.DELETE_BY_ID);
            delete.setInt(1, id);
            delete.executeUpdate();
            return "successfully removed";
        }
        catch (SQLException e) {
            e.printStackTrace();
            return "something went wrong "+e.getMessage();
        }
    }

    public String updateWorker(int id, Worker newValue) {
        try {
            PreparedStatement workerToUpdateRq = database.prepareStatement(SQLStatements.GET_BY_ID);
            workerToUpdateRq.setInt(1, id);
            ResultSet workerToUpdate = workerToUpdateRq.executeQuery();
            if (!workerToUpdate.next()) {
                return "no worker with this id found";
            }
            if (!workerToUpdate.getString("creator").equals(newValue.getCreator())) {
                return "permission denied";
            }
            PreparedStatement updateRq = database.prepareStatement(SQLStatements.UPDATE_WORKER);
            updateRq.setString(1, newValue.getName());
            updateRq.setLong(2, newValue.getCoordinates().getX());
            updateRq.setLong(3, newValue.getCoordinates().getY());
            updateRq.setLong(4, newValue.getSalary());
            updateRq.setTimestamp(5, Timestamp.from(newValue.getStartDate().toInstant()));
            Date endDate = newValue.getEndDate() != null ? Date.valueOf(newValue.getEndDate()) : null;
            updateRq.setDate(6, endDate);
            updateRq.setString(7, newValue.getStatus().toString());
            updateRq.setDouble(8, newValue.getPerson().getHeight());
            Color eyeColor, hairColor;
            eyeColor = newValue.getPerson().getEyeColor();
            hairColor = newValue.getPerson().getHairColor();
            updateRq.setString(9, eyeColor == null ? "null" : eyeColor.toString());
            updateRq.setString(10, hairColor == null ? "null" : hairColor.toString());
            updateRq.setString(11, newValue.getPerson().getNationality().toString());
            updateRq.setInt(12, id);
            updateRq.executeUpdate();
            return "successfully updated";
        } catch (SQLException e) {
            e.printStackTrace();
            return "something went wrong\n"+e.getMessage();
        }
    }

    public void updateAll() {
        try {
            Statement statement = database.createStatement();
            StorageManager.getInstance().updateAll(statement.executeQuery(SQLStatements.TAKE_ALL));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear(String login) {
        try {
            PreparedStatement clearRq = database.prepareStatement(SQLStatements.CLEAR_ALL_BY_USER);
            clearRq.setString(1, login);
            clearRq.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param status
     * @param login
     * @return number of removed elements
     */
    public int removeAllByStatus(String status, String login) {
        try {
            PreparedStatement removeRq = database.prepareStatement(SQLStatements.REMOVE_ALL_BY_STATUS_AND_USER);
            removeRq.setString(1, status);
            removeRq.setString(2, login);
            return removeRq.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setWorkerToStatement(PreparedStatement st, Worker w) throws SQLException{
        Person person = w.getPerson();
        st.setInt(1, w.getId());
        st.setString(2, w.getName());
        st.setLong(3, w.getCoordinates().getX());
        st.setLong(4, w.getCoordinates().getY());
        st.setLong(5, w.getSalary());
        Timestamp startDate = Timestamp.from(Instant.from(w.getStartDate()));
        st.setTimestamp(6, Timestamp.from(w.getStartDate().toInstant()));
        Date endDate = w.getEndDate() != null ? Date.valueOf(w.getEndDate()) : null;
        st.setDate(7, endDate);
        st.setString(8, w.getStatus().toString());
        st.setDouble(9, person.getHeight());
        st.setString(10, person.getEyeColor() != null ? person.getEyeColor().toString() : "null");
        st.setString(11, person.getHairColor() != null ? person.getHairColor().toString() : "null");
        st.setString(12, person.getNationality().toString());
        st.setString(13, w.getCreator());
    }

    private byte[] getHash(String str) {
        return digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }
}
