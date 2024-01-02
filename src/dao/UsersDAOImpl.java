package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static helper.JDBC.connection;

/**
 * This is the UsersDAOImpl class. This class implements the methods defined in the UsersDAO interface.
 */
public class UsersDAOImpl implements UsersDAO {

    ObservableList<Users> allUsers = FXCollections.observableArrayList();

    /**
     * This is the loginQuery method. This method takes in the parameters of userName and password and then compares them
     * to the values possessed in the database. If these match, the loginQuery method then allows the user entry into the
     * scheduler and loads the main appointments screen.
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public boolean loginQuery(String userName, String password) throws SQLException {
        Connection connection = JDBC.connection;
        String sqlStatement = "select * FROM users WHERE User_Name  = ? and Password = ?";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ps.setString(1, userName);
        ps.setString(2, password);
        Users userResult;
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            return true;
        }
        return false;
    }

    /**
     * This is the getAllUsers method. This method searches the database for any and all users, and then adds them to an
     * observable list called 'allUsers'.
     * @return
     */
    @Override
    public ObservableList<Users> getAllUsers() {
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int userID = res.getInt("User_ID");
                String userName = res.getString("User_Name");
                String password = res.getString("Password");
                Users user = new Users(userID, userName, password);
                allUsers.add(user);
            }
        } catch (Exception e) {
            System.out.println("This attempt has failed.");
        }
        return allUsers;
    }

    /**
     * This is the getUser method. This method searches the database and then returns for a user that matches the entered
     * userID parameter.
     * @param userID
     * @return
     */
    @Override
    public Users getUser(int userID) {
        try {
            String sql = "SELECT FROM users WHERE User_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet res = ps.executeQuery();
            Users userResult = null;
            if (res.next()) {
                userID = res.getInt("User_ID");
                String userName = res.getString("User_Name");
                String password = res.getString("password");
                userResult = new Users(userID, userName, password);
            }
            return userResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This is the modifyUserPassword method. This method allows the user to update the database with a new password
     * which replaces an old password.
     * @param userName
     * @param newPassword
     * @param currentPassword
     * @return
     */
    @Override
    public int modifyUserPassword(String userName, String newPassword, String currentPassword) {
        int affectedRows = 0;
        try {
            String sql = "UPDATE users set password WHERE User_Name = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, newPassword);
            ps.setString(3, currentPassword);
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Password was successfully modified.");
                System.out.println("New Password: " + newPassword);
            } else {
                System.out.println("Attempt to modify password has failed.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the modifyUserName method. This method allows the user to change and save his/her preferred user name using
     * the parameters of currentUserName, newUserName and password.
     * @param currentUserName
     * @param newUserName
     * @param password
     * @return
     */
    @Override
    public int modifyUserName(String currentUserName, String newUserName, String password) {
        int affectedRows = 0;
        try {
            String sql = "UPDATE users SET User_Name WHERE User_Name = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, currentUserName);
            ps.setString(2, newUserName);
            ps.setString(3, password);
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User Name has been successfully modified.");
                System.out.println("New User Name: " + newUserName);
            } else {
                System.out.println("Attempt to modify username has failed.");
            }
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the deleteUser method. This method allows the user to delete a user record from the database.
     * @param userID
     * @return
     */
    @Override
    public int deleteUser(int userID) {
        int affectedRows = 0;
        {
            try {
                String sql = "DELETE FROM users set User_ID WHERE User_ID = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, userID);
                affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("User has been deleted successfully.");
                } else {
                    System.out.println("Attempt to delete user has failed.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return affectedRows;
    }

    /**
     * This is the addUser method. This method allows a user to add a new user record to the database.
     * @param userName
     * @param password
     * @return
     */
    @Override
    public int addUser(String userName, String password) {
        int affectedRows = 0;
        try {
            String sql = "INSERT INTO users SET User_Name WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User has been successfully added.");
                System.out.println("Username: " + userName);
                System.out.println("Password: " + password);
            }
        } catch (Exception e) {
            System.out.println("e.getMessage");
        }
        return affectedRows;
    }
}
