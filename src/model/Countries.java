package model;
/**
 * This is the Countries model class. This class specifies the parameters to be used in the other Countries classes/files.
 * @param countryID
 * @param countryName
 */
public class Countries {
    private int countryID;
    private String countryName;
    private int countryMonthTotal;
    private String specificCountryMonth;

    /**
     * This is the Countries constructor.
     * @param countryID
     * @param countryName
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Specifies the countryID to be set.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Specifies the countryName to be set
     * @param countryName
     */
    public void setCountryName(String countryName) {

        this.countryName = countryName;
    }

    /**
     * This is the Countries toString method. This method defines appropriate syntax and converts hashcode to string.
     * @return
     */
    @Override
    public String toString() {
        return (countryName);
    }
}



