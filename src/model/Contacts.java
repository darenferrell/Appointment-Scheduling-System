package model;

/**
 * This is the Contacts model class. This class defines the parameters that will be used in the Contacts controller.
 * It also contains getters and setters so that each of the field's entered information can be set and retrieved securely.
 */
public class Contacts {

    private int contactID;
    private String contactName;

    /**
     * This is the Contacts constructor.
     * @param contactID
     * @param contactName
     */
    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     *
     * @return the appointmentID.
     */
    public int getContactID() {

        return contactID;
    }

    /**
     * Specifies the approparite contactID to set
     * @param contactID
     */
    public void setContactID(int contactID) {

        this.contactID = contactID;
    }

    /**
     *
     * @return contactName
     */
    public String getContactName() {

        return contactName;
    }

    /**
     * Specifies the contactName to set.
     * @param contactName
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    /**
     * This is the Contacts toString method. This method provides appropriate syntax for contact information
     * and converts hashcode to string.
     * @return
     */
    @Override
    public String toString() {

        return ("[" + Integer.toString(contactID) + "] " + contactName);
    }
}