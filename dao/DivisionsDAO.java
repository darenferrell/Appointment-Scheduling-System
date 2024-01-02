package dao;

import javafx.collections.ObservableList;
import model.Divisions;
import java.sql.SQLException;

/**
 * This is the DivisionsDAO interface. This interface defines how and what the DivisionsDAOImpl class will implement.
 */
public interface DivisionsDAO {

        /**
         * This is the getAllDivisions method. This method searches the database for any and all divisions and then adds
         * them to an observable list called 'Divisions'.
         * @return
         */
        public ObservableList<Divisions> getAllDivisions();

        /**
         * This is the getDivision method. This method searches the database for a division that matches the given division
         * ID.
         * @param divisionID
         * @return
         */
        public Divisions getDivision(int divisionID);

        /**
         * This is the getDivisionsBasedOnCountry method. This method searches the databasse for any divisions that correspond
         * to a particular country. The method uses the country ID parameter to determine matches. It then adds said countries
         * to an observable list called divisionsBasedOnCountry.
         * @param countryID
         * @return
         */
        public ObservableList<Divisions> getDivisionsBasedOnCountry(int countryID);

        /**
         * This method searches the database for matching division names using the parameters of currentDivisionName,
         * and countryID. It then allows the user to modify the division name by entering the string newDivisionName.
         * @param currentDivisionName
         * @param countryID
         * @param newDivisionName
         * @return
         */
        public int modifyDivisionName(String currentDivisionName, int countryID, String newDivisionName);

        /**
         * This is the modifyDivisionCountry method. This method allows the user to search the database based on the
         * parameters of divisionName and currentCountryID and then based on thse parameters to modify the country ID in
         * the database.
         * @param divisionName
         * @param currentCountryID
         * @param newCountryID
         * @return
         */
        public int modifyDivisionCountry(String divisionName, int currentCountryID, int newCountryID);

        /**
         * This is the deleteDivision method. This method allows the user to search the database given the parameters of
         * divisionID, and divisionName, and then to remove a division record from the database.
         * @param divisionID
         * @param divisionName
         * @return
         * @throws SQLException
         */
        public int deleteDivision(int divisionID, String divisionName) throws SQLException;

        /**
         * This is the addDivision method. This method allows the user to add a new division to the database using the
         * parameters of divisionName and countryID.
         * @param divisionName
         * @param countryID
         * @return
         */
        public int addDivision(String divisionName, int countryID);
    }

