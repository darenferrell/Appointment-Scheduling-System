package model;

/**
 * This is the Divisions model class. This class specifies which parameters are to be used in all other Divisions classes/documents.
 */
public class Divisions {
    private int divisionID;
    private int countryID;
    private String divisionName;
    private String countryName;

    /**
     * This is the divisions constructor
     * @param divisionID
     * @param countryID
     * @param divisionName
     * @param countryName
     */
    public Divisions(int divisionID, int countryID, String divisionName, String countryName) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /**
     *
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Specifies which divisionID to set
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Specifies which country ID to set
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Specifies which division name to set
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Specifies which country name to set
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * This is the divisions toString method. This method defines default syntax and converts hash code to string.
     * @return
     */
    @Override
    public String toString() {
        return (divisionName);
    }
}
