package dao;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import model.Users;
import static helper.JDBC.connection;

public class DatabaseLogin {
    public static Users loginQuery(String userName, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
            PreparedStatement prepStat = connection.prepareStatement(sql);
            prepStat.setString(1, userName);
            prepStat.setString(2, password);

            ResultSet resSet = prepStat.executeQuery();
            Users userResult = null;
            if (resSet.next()) {
                int userID = resSet.getInt("User_ID");
                userName = resSet.getString("User_Name");
                password = resSet.getString("Password");
                userResult = new Users(userID, userName, password);
            }
            return userResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static LocalDateTime getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        return localDateTime;
    }
}

