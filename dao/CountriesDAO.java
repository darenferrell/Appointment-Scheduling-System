package dao;

import javafx.collections.ObservableList;
import model.Countries;

/**
 * This is the CountriesDAO interface. This interface defines the methods that are implemented by the CountriesDAOImpl
 * class.
 */
public interface CountriesDAO {

    /**
     * This is the getAllCountries method. This method searches the database for any and all countries  and then adds
     * them to an observable list called allCountries.
     * @return
     */
    public ObservableList<Countries> getAllCountries();

    /**
     * This is the getCountry method. This method searches the database for any and all country records that match the
     * given parameter which is country ID.
     * @param countryID
     * @return
     */
    public Countries getCountry(int countryID);

    /**
     * This is the modifyCountry method. This method searches the database for previously entered records that match the
     * country ID and country name parameters. It then updates the name via the newCountryName field.
     * @param countryID
     * @param currentCountryName
     * @param newCountryName
     * @return
     */
        public int modifyCountry(int countryID, String currentCountryName, String newCountryName);

    /**
     * This is the deleteCountry method. This method searches the database via countryID and countryName parameters and
     * then allows the user to remove the specified records from the database.
     * @param countryID
     * @param countryName
     * @return
     */
        public int deleteCountry(int countryID, String countryName);

    /**
     * This is the addCOuntry method. This method allows the user to add a new record based on the countryName parameter
     * to the database.
     * @param countryName
     * @return
     */
        public int addCountry(String countryName);
    }

