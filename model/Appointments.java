package model;
import java.time.*;

/**
 *
 * This is the Appointments model class. This class defines the parameters that will be used in the Appointments controller.
 * It also contains getters and setters so that each of the field's entered information can be set and retrieved securely.
 */
public class Appointments {

    private int appointmentID;
    private int customerID;
    private int userID;
    private int contactID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * This is the Appointments constructor.
     * @return
     */
    public Appointments(int appointmentID, int customerID, int userID, int contactID, String title, String description,
                       String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate startDate,
                       LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateAndTime = startDateTime;
        this.endDateAndTime = endDateTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    /**
     * returns the Appointment ID.
     * @return
     */
    public int getAppointmentID()
    {
        return appointmentID;
    }

    /**
     * Sets the appointmentID.
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * returns the customer ID.
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * determines the customer ID to set.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * returns the User ID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Determines the user ID to set
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns the Contact ID.
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Determines the Contact ID to set.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Specifies which title to be set
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Determines which description to be set
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Specifies which location to be set
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Specifies which type to be set
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return startDateAndTime
     */
    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    /**
     * Determines which startDateAndTime to be set
     * @param startDateAndTime
     */
    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    /**
     *
     * @return endDateAndTime
     */
    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    /**
     * Determines which endDateAndTime to be set
     * @param endDateAndTime
     */
    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    /**
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Determines which startDate to be set
     * @param startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Determines which endDate to be set
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Determines which startTime to be set
     * @param startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Determines which endTime to be set
     * @param endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This is the Appointment to String method. This method converts the hash code to string and provides the appropriate
     * syntax to be used.
     * @return
     */
    @Override
    public String toString() {
        return ("Appointment: [" + Integer.toString(appointmentID) + "] | Customer: [" + Integer.toString(customerID) + "] " +
                "| Contact: [" + Integer.toString(contactID) + "] | Type: " + type + "| Start: " + startDateAndTime
                + " | End: " + endDateAndTime );
    }
}
