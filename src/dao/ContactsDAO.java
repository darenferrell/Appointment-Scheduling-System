package dao;
import javafx.collections.ObservableList;
import model.Contacts;

/**
 * This is the ContactsDAO interface.
 */
public interface ContactsDAO {

        /**
         * This is the getAllContacts method. This method searches the database for all the contacts. It then adds them
         * all to an observable list called 'allContacts'.
         * @return
         */
        public ObservableList<Contacts> getAllContacts();

        /**
         * This is the getContactID method. This method searches the database for any and all contacts that match the
         * given contact ID.
         * @param contactID
         * @return
         */

        public Contacts getContactID(int contactID);

        /**
         * This is the modifyContact method. This method takes in the selected contact and searches the database for
         * the allows the user to modify its parameters.
         * parameters.
         * @param contactID
         * @param currentContactName
         * @param newContactName
         * @return
         */
        public int modifyContact(int contactID, String currentContactName, String newContactName);

        /**
         * This is the deleteContact method. This method searches the database for any and all records that match the
         * entered contact ID and then allows the user to have them deleted from the database.
         * @param contactID
         * @return
         */
        public int deleteContact(int contactID);

        /**
         * This is the addContact method. This method allows the user to add a new contact to the database with the contact
         * name parameter.
         * @param contactName
         * @return
         */
        public int addContact(String contactName);

    }

