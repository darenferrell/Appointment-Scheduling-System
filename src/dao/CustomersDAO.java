package dao;

import javafx.collections.ObservableList;
import model.Appointments;
import model.Countries;
import model.Customers;

import java.sql.SQLException;

/**
 * This is the CustomersDAO interface. It defines the methods that are implemented in the CustomersDAOImpl class.
 */
public interface CustomersDAO {

    /**
     * This is the getAllCustomers method. This method searches the database for any and all customers and then adds
     * them to an observable list called allCustomers.
     * @return
     */
    ObservableList<Customers> getAllCustomers();


    ObservableList<Appointments> getAllAppointments(int customerID);

    /**
     * This is the getCustomersBasedOnCountry method. This method searches the database for any customers that correspond
     * to the parameter of country ID. It then adds said customers to an observable list called 'customersBasedOnCountry'.
     * @param countryID
     * @return
     * @throws SQLException
     */
    public ObservableList<Customers> getCustomersBasedOnCountry(int countryID) throws SQLException;

    /**
     * This is the modifyCustomer method. This method searches the database for the selected customer and then allows
     * the user to modify the six parameters that make up the method.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @return
     */
    public int modifyCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID);

    /**
     * This is the deleteCustomer method. This method allows the user to remove a customer record from the database based
     * on the parameters of customer ID and customer name.
     * @param customerID
     * @param customerName
     * @return
     */
    public int deleteCustomer(int customerID, String customerName);

    /**
     * This is the addCustomer method. This method allows the user to add a new customer record to the database based on
     * the five parameters listed.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @return
     */
    public int addCustomer(String customerName, String address, String postalCode, String phone, int divisionID);

    /**
     * This is the getCustomer method. This method allows the user to look up a customer record based on the parameter
     * of customer ID.
     * @param customerID
     * @return
     */
    public Customers getCustomer(int customerID);

    /**
     * This is the getCustomer method. This method looks up a customer record and then adds it to an observable list
     * called 'Customers'.
     * @param customerName
     * @return
     */
    public ObservableList<Customers> getCustomer(String customerName);
}

