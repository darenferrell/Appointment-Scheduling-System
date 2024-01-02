package model;

/**
 * This is the Users model class. It defines the parameters to be used for all Users classes/documents.
 */
public class Users {
    private int userID;
    private String userName;
    private String password;

    /**
     * This is the Users constructor.
     * @param userID
     * @param userName
     * @param password
     */
    public Users(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Specifies the user ID to be set
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Specifies which userName to be set
     * @param username
     */
    public void setUserName(String username) {
        this.userName = username;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Specifies which password to be set
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This is the Users toString method. It defines appropriate syntax and converts hash code to string.
     * @return
     */
    @Override
    public String toString() {
        return ("[" + Integer.toString(userID) + "]" + userName);
    }
}
