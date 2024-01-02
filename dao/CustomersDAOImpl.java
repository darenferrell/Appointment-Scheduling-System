package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Countries;
import model.Customers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static helper.JDBC.connection;

/**
 * This is the CustomersDAOImpl class. It implements the methods defined in the CustomersDAO interface.
 */
public class CustomersDAOImpl implements CustomersDAO {

    ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    public boolean customerLocated;

    /**
     * This is the getAllCustomers method. This method searches the database for any and all customers and then adds
     * them to an observable list called allCustomers.
     * @return
     */
    @Override
    public ObservableList<Customers> getAllCustomers() {
        try {
            String sql = "SELECT * FROM Customers, first_level_divisions, countries WHERE " + "customers.Division_ID = " +
                    "first_level_divisions.Division_ID AND " + "first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int customerID = result.getInt("Customer_ID");
                int divisionID = result.getInt("Division_ID");
                int countryID = result.getInt("Country_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("postal_Code");
                String phone = result.getString("Phone");
                String countryName = result.getString("Country");
                String divisionName = result.getString("Division");
                Customers cust = new Customers(customerID, divisionID, countryID, customerName, address, postalCode, phone, countryName, divisionName);
                allCustomers.add(cust);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return allCustomers;

    }

    @Override
    public ObservableList<Appointments> getAllAppointments(int customerID) {
        return null;
    }

    /**
     * This is the getCustomersBasedOnCountry method. This method searches the database for any customers that correspond
     * to the parameter of country ID. It then adds said customers to an observable list called 'customersBasedOnCountry'.
     * @param countryID
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Customers> getCustomersBasedOnCountry(int countryID) throws SQLException {
        ObservableList<Customers> customersBasedOnCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE\n" +
                    "customers.Division_ID = first_level_divisions.Division_ID AND \n" +
                    "first_level_divisions.Country_ID = countries.Country_ID AND countries.Country_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                int divisionID = rs.getInt("Division_ID");
                countryID = rs.getInt("Country_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String countryName = rs.getString("Country");
                String divisionName = rs.getString("Division");
                Customers cust = new Customers(customerID, divisionID, countryID, customerName, address, postalCode,
                        phone, countryName, divisionName);
                customersBasedOnCountry.add(cust);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return customersBasedOnCountry;
    }

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
    @Override
    public int modifyCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        int affectedRows = 0;
        try {
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Customer modification was successful.");
            } else {
                System.out.println("Customer modification was not successful.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the deleteCustomer method. This method allows the user to remove a customer record from the database based
     * on the parameters of customer ID and customer name.
     * @param customerID
     * @param customerName
     * @return
     */
    @Override
    public int deleteCustomer(int customerID, String customerName) {
        int affectedRows = 0;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID=? AND Customer_Name=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setString(2, customerName);
            affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer record was deleted successfully.");
            } else {
                System.out.println("Customer record deletion was not successful.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

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
    @Override
    public int addCustomer(String customerName, String address, String postalCode, String phone, int divisionID) {
        int affectedRows = 0;
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)" + " VALUES(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Customer record addition was successful.");
            } else {
                System.out.println("Customer record addition was not successful.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the getCustomer method. This method allows the user to look up a customer record based on the parameter
     * of customer ID.
     * @param customerID
     * @return
     */
    @Override
    public Customers getCustomer(int customerID) {
        customerLocated = false;
        for (Customers cust : allCustomers) {
            if (cust.getCustomerID() == customerID) {
                customerLocated = true;
                return cust;
            }
        }
        return null;
    }

    /**
     * This is the getCustomer method. This method looks up a customer record and then adds it to an observable list
     * called 'Customers'.
     * @param customerName
     * @return
     */
    @Override
    public ObservableList<Customers> getCustomer(String customerName) {
        ObservableList<Customers> filteredCustomers = FXCollections.observableArrayList();
        customerLocated = false;
        for (Customers cust : allCustomers) {
            if (cust.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) {
                filteredCustomers.add(cust);
            }
        }
        if (filteredCustomers.isEmpty()) {
            return allCustomers;
        }
        customerLocated = true;
        return filteredCustomers;
    }
}

