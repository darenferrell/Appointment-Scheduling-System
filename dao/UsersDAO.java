package dao;

import javafx.collections.ObservableList;
import model.Users;

import java.sql.SQLException;

/**
 * This is the UsersDAO interface. This interface defines how and what will be implemented by the UsersDAOImpl class.
 */
public interface UsersDAO {

    /**
     * This is the loginQuery method. This method takes in the parameters of userName and password and then compares them
     * to the values possessed in the database. If these match, the loginQuery method then allows the user entry into the
     * scheduler and loads the main appointments screen.
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    boolean loginQuery(String userName, String password) throws SQLException;

    /**
     * This is the getAllUsers method. This method searches the database for any and all users, and then adds them to an
     * observable list called 'allUsers'.
     * @return
     */
    public ObservableList<Users> getAllUsers();

    /**
     * This is the getUser method. This method searches the database and then returns for a user that matches the entered
     * userID parameter.
     * @param userID
     * @return
     */
    public Users getUser(int userID);

    /**
     * This is the modifyUserPassword method. This method allows the user to update the database with a new password
     * which replaces an old password.
     * @param userName
     * @param newPassword
     * @param currentPassword
     * @return
     */
    public int modifyUserPassword(String userName, String newPassword, String currentPassword);

    /**
     * This is the modifyUserName method. This method allows the user to change and save his/her preferred user name using
     * the parameters of currentUserName, newUserName and password.
     * @param currentUserName
     * @param newUserName
     * @param password
     * @return
     */
    public int modifyUserName(String currentUserName, String newUserName, String password);

    /**
     * This is the deleteUser method. This method allows the user to delete a user record from the database.
     * @param userID
     * @return
     */
    public int deleteUser(int userID);

    /**
     * This is the addUser method. This method allows a user to add a new user record to the database.
     * @param userName
     * @param password
     * @return
     */
    public int addUser(String userName, String password);
}


