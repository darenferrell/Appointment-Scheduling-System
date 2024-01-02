package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static helper.JDBC.connection;

/**
 * This is the ContactsDAOImpl class. This class implements any and all methods that are defined by the ContactsDAO interface.
 */
public class ContactsDAOImpl implements ContactsDAO {

    ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    /**
     * This is the getAllContacts method. This method searches the database for all the contacts. It then adds them
     * all to an observable list called 'allContacts'.
     * @return
     */
    @Override
    public ObservableList<Contacts> getAllContacts() {
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int contactID = res.getInt("Contact_ID");
                String contactName = res.getString("Contact_Name");
                Contacts cont = new Contacts(contactID, contactName);
                allContacts.add(cont);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allContacts;
    }

    /**
     * This is the getContactID method. This method searches the database for any and all contacts that match the
     * given contact ID.
     * @param contactID
     * @return
     */
    @Override
    public Contacts getContactID(int contactID) {
        try {
            String sql = "SELECT FROM contacts where Contact_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet result = ps.executeQuery();
            Contacts completeContact = null;

            if(result.next()) {
                contactID = result.getInt("Contact_ID");
                String contactName = result.getString("Contact_Name");
                completeContact = new Contacts(contactID, contactName);
            }
            return completeContact;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This is the modifyContact method. This method takes in the selected contact and searches the database for
     * the allows the user to modify its parameters.
     * parameters.
     * @param contactID
     * @param currentContactName
     * @param newContactName
     * @return
     */
    @Override
    public int modifyContact(int contactID, String currentContactName, String newContactName) {

        int affectedRows = 0;
        try {
            String sql = "UPDATE contacts set Contact_Name=? WHERE Contact_Name=? AND Contact_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newContactName);
            ps.setString(2, currentContactName);
            ps.setInt(3, contactID);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(currentContactName + "modification was successful!");
                System.out.println("New Contact name: " + newContactName);

            } else {
                System.out.println(currentContactName + " name modification has failed.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the deleteContact method. This method searches the database for any and all records that match the
     * entered contact ID and then allows the user to have them deleted from the database.
     * @param contactID
     * @return
     */
    @Override
    public int deleteContact(int contactID) {
        int affectedRows = 0;

        try {
            String sql = "DELETE FROM contacts WHERE Contact_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Contact " + contactID + "was successfully deleted.");

            } else {
                System.out.println("Contact " + contactID + "has not been deleted.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }

    /**
     * This is the addContact method. This method allows the user to add a new contact to the database with the contact
     * name parameter.
     * @param contactName
     * @return
     */
    @Override
    public int addContact(String contactName) {
        int affectedRows = 0;
        try {
            String sql = "INSERT INTO contacts (Contact_Name) WHERE Contact_ID=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, contactName);
            affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Contact " + contactName + "was added successfully.");

            } else {
                System.out.println("Contact " + contactName + "has not been added.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return affectedRows;
    }
}