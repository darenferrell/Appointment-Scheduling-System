package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import helper.ErrorMessages;
import static helper.JDBC.connection;

/**
 * This is the DivisionsDAOImpl class. This class implements the methods defined in the DivisionsDAO interface.
 */
public class DivisionsDAOImpl implements DivisionsDAO {
    ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
    ObservableList<Divisions> divisionsBasedOnCountry = FXCollections.observableArrayList();

    /**
     * This is the getAllDivisions method. This method searches the database for any and all divisions and then adds
     * them to an observable list called 'Divisions'.
     * @return
     */
    @Override
    public ObservableList<Divisions> getAllDivisions() {
        try {
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID " + "= countries.Country_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int divisionID = res.getInt("Division_ID");
                int countryID = res.getInt("Country_ID");
                String divisionName = res.getString("Division");
                String countryName = res.getString("Country");
                Divisions div = new Divisions(divisionID, countryID, divisionName, countryName);
                allDivisions.add(div);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allDivisions;
    }

    /**
     * This is the getDivision method. This method searches the database for a division that matches the given division
     * ID.
     * @param divisionID
     * @return
     */
    @Override
    public Divisions getDivision(int divisionID) {
        try {
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID " + "= countries.Country_ID AND Division_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, divisionID);

            ResultSet res = ps.executeQuery();
            Divisions divResult = null;
            if (res.next()) {
                divisionID = res.getInt("Division_ID");
                int countryID = res.getInt("Country_ID");
                String divisionName = res.getString("Division");
                String countryName = res.getString("Country");
                divResult = new Divisions(divisionID, countryID, divisionName, countryName);
            }
            return divResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This is the getDivisionsBasedOnCountry method. This method searches the databasse for any divisions that correspond
     * to a particular country. The method uses the country ID parameter to determine matches. It then adds said countries
     * to an observable list called divisionsBasedOnCountry.
     * @param countryID
     * @return
     */
    @Override
    public ObservableList<Divisions> getDivisionsBasedOnCountry(int countryID) {
        try {
            String sql = "\"SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID \" +\n" +
                    "                    \"= countries.Country_ID AND Division_ID=?\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet res = ps.executeQuery();
            Divisions countryDiv = null;
            while (res.next()) {
                countryID = res.getInt("Country_ID");
                String countryName = res.getString("Country");
                int divisionID = res.getInt("Division_ID");
                String divisionName = res.getString("Division");
                divisionsBasedOnCountry.add(countryDiv);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return divisionsBasedOnCountry;
    }

    /**
     * This method searches the database for matching division names using the parameters of currentDivisionName,
     * and countryID. It then allows the user to modify the division name by entering the string newDivisionName.
     * @param currentDivisionName
     * @param countryID
     * @param newDivisionName
     * @return
     */
    @Override
    public int modifyDivisionName(String currentDivisionName, int countryID, String newDivisionName) {
        int affectedRows = 0;
        {
            try {
                String sql = "UPDATE first_level_divisions SET division = ? WHERE Division = ? AND country = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, currentDivisionName);
                ps.setInt(2, countryID);
                ps.setString(3, newDivisionName);
                ResultSet res = ps.executeQuery();
                affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Division name has been successfully modified.");
                } else {
                    System.out.println("Division name was not successfully modified.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return affectedRows;
        }
    }

    /**
     * This is the modifyDivisionCountry method. This method allows the user to search the database based on the
     * parameters of divisionName and currentCountryID and then based on thse parameters to modify the country ID in
     * the database.
     * @param divisionName
     * @param currentCountryID
     * @param newCountryID
     * @return
     */
    @Override
    public int modifyDivisionCountry(String divisionName, int currentCountryID, int newCountryID) {
        int affectedRows = 0;
        try {
            String sql = "UPDATE first_level_divisions SET Country_ID WHERE division = ? AND country = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, divisionName);
            ps.setInt(2, currentCountryID);
            ps.setInt(3, newCountryID);
            ResultSet res = ps.executeQuery();
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Division country was successfully modified.");
            } else {
                System.out.println("Division country failed to be modified.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the deleteDivision method. This method allows the user to search the database given the parameters of
     * divisionID, and divisionName, and then to remove a division record from the database.
     * @param divisionID
     * @param divisionName
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteDivision(int divisionID, String divisionName) throws SQLException {
        int affectedRows = 0;
        helper.JDBC.openConnection();
        DivisionsDAO dDAO = new DivisionsDAOImpl();
        try {
            String sql = "DELETE FROM first_level_divisions WHERE Division_ID=? AND Division_Name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ps.setString(2, divisionName);
            affectedRows = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the addDivision method. This method allows the user to add a new division to the database using the
     * parameters of divisionName and countryID.
     * @param divisionName
     * @param countryID
     * @return
     */
    @Override
    public int addDivision(String division, int countryID) {
        int affectedRows = 0;
        helper.JDBC.openConnection();
        DivisionsDAO dDAO = new DivisionsDAOImpl();
        try {
            String sql = "INSERT INTO first_level_divisions WHERE Division_Name = ? AND Country_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, division);
            ps.setInt(2, countryID);
            affectedRows = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }
}
