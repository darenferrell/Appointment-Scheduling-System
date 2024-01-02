package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static helper.JDBC.connection;

/**
 * This is the CountriesDAOImpl class. This class implements the methods defined in the CountriesDAO interface.
 */
public class CountriesDAOImpl implements CountriesDAO {

    ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    /**
     * This is the getAllCountries method. This method searches the database for any and all countries  and then adds
     * them to an observable list called allCountries.
     * @return
     */
    @Override
    public ObservableList<Countries> getAllCountries() {
            try {
                String sql = "SELECT * FROM countries";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    int countryID = res.getInt("Country_ID");
                    String countryName = res.getString("Country");
                    Countries count = new Countries (countryID, countryName);
                    allCountries.add(count);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return allCountries;
        }

    /**
     * This is the getCountry method. This method searches the database for any and all country records that match the
     * given parameter which is country ID.
     * @param countryID
     * @return
     */
    @Override
    public Countries getCountry(int countryID) {
            try {
                String sql = "SELECT * FROM countries where country_ID = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, countryID);
                ResultSet result = ps.executeQuery();
                Countries countryResult = null;
                if (result.next()) {
                    countryID = result.getInt("Country_ID");
                    String countryName = result.getString("Country");
                    countryResult = new Countries(countryID, countryName);
                }
                return countryResult;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
        }

    /**
     * This is the modifyCountry method. This method searches the database for previously entered records that match the
     * country ID and country name parameters. It then updates the name via the newCountryName field.
     * @param countryID
     * @param currentCountryName
     * @param newCountryName
     * @return
     */
    @Override
    public int modifyCountry(int countryID, String currentCountryName, String newCountryName) {
            int affectedRows = 0;
            try {
                String sql = "UPDATE contacts set Contact_Name=? WHERE Contact_Name=? AND Contact_ID=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newCountryName);
                ps.setString(2, currentCountryName);
                ps.setInt(3, countryID);
                affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(currentCountryName + "modification was successful!");
                    System.out.println("New Contact name: " + newCountryName);
                } else {
                    System.out.println(currentCountryName + " name modification has failed.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return affectedRows;
        }

    /**
     * This is the deleteCountry method. This method searches the database via countryID and countryName parameters and
     * then allows the user to remove the specified records from the database.
     * @param countryID
     * @param countryName
     * @return
     */
    @Override
    public int deleteCountry(int countryID, String countryName) {
        int affectedRows = 0;
        helper.JDBC.openConnection();
        DivisionsDAO dDAO = new DivisionsDAOImpl();
        try {
            String sql = "DELETE FROM countries WHERE Country_ID=? AND Country=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ps.setString(2, countryName);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Country " + countryName + " " + countryID + "was successfully deleted.");

            } else {
                System.out.println("Country " + countryName + " " + countryID + "has not been deleted.");
            }
            if (!dDAO.getDivisionsBasedOnCountry(countryID).isEmpty()) {
                System.out.println(countryName + " was not deleted.");
                System.out.println(countryName + " is associated with additional divisions. Please be sure to delete any remaining divisions before attempting to delete country.");
            }
        }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            return affectedRows;
        }

    /**
     * This is the addCOuntry method. This method allows the user to add a new record based on the countryName parameter
     * to the database.
     * @param countryName
     * @return
     */
    @Override
    public int addCountry(String countryName) {
        int affectedRows = 0;
        try {
            String sql = "INSERT INTO countries (Country) VALUES(?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, countryName);
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Country addition was successful.");
            }
            else {
                System.out.println("Country addition was unsuccessful.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }
}