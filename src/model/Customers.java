package model;

/**
 * This is the Customers model class. This class specifies the parameters to be used in all Customers classes/documents.
 */
public class Customers {

    private int customerID;
    private int divisionID;
    private int countryID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String countryName;
    private String divisionName;

    /**
     * This is the Customers constructor.
     * @param customerID
     * @param divisionID
     * @param countryID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param countryName
     * @param divisionName
     */
    public Customers(int customerID, int divisionID, int countryID, String customerName, String address,
                     String postalCode, String phone, String countryName, String divisionName) {

        this.customerID = customerID;
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }


    public Customers(int customerID) {
    }

    public Customers(String customerName) {
    }

    public Customers(int customerID, String customerName, String address, String postalCode, int phone, int divisionID) {
    }

    /**
     *
     * @return CustomerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Specifies which customerID to set
     */
    public void setCustomerID() {
        this.customerID = customerID;
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
     */
    public void setDivisionID() {
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
     */
    public void setCountryID() {
        this.countryID = countryID;
    }

    /**
     *
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Specifies which customerName to set.
     */
    public void setCustomerName() {
        this.customerName = customerName;
    }

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Specifies which address to set
     */
    public void setAddress() {
        this.address = address;
    }

    /**
     *
     * @return postalCode
     */
    public String getPostalCode() { return postalCode; }

    /**
     * Specifies which postalCode to set
     */
    public void setPostalCode() {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Specifies which phone to set
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     */
    public void setCountryName() {
        this.countryName = countryName;
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
     */
    public void setDivisionName() {
        this.divisionName = divisionName;
    }

    /**
     * This is the Customers toString method. This method defines proper syntax and coverts hash code to string.
     * @return
     */
    @Override
    public String toString() {
        return ("[" + Integer.toString(customerID) + "] " + customerName);
    }
}
