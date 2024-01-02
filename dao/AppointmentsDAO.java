package dao;

import javafx.collections.ObservableList;
import model.Appointments;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This is the AppointmentsDAO class. This class acts as an interface for the CustomersDAOImpl class.
 */
public interface AppointmentsDAO {

    /**
     * This is the getAllAppointments method. This method builds an observable list from the database of all appointments.
     * @return
     */
    public ObservableList<Appointments> getAllAppointments();

    /**
     * This is the getAppointment method. This method searches the database for a particular appointment based on its
     * appointment ID.
     * @param appointmentID
     * @return
     */

    public Appointments getAppointment(int appointmentID);

    /**
     * This is the getAppointmentBasedOnCustomer method. It searches the database for all appointments associated with
     * the entered customer ID. It then builds an Observable List of said appointments.
     * @param customerID
     * @return
     */
    public ObservableList<Appointments> getAppointmentBasedOnCustomer(int customerID);

    /**
     * This is the getAppointmentBasedOnContact method. This method searches the database for all appointments associated
     * with a particular contact ID. It then builds an Observable List of said appointments.
     * @param contactID
     * @return
     */

    public ObservableList<Appointments> getAppointmentBasedOnContact(int contactID);

    /**
     * This is the modifyAppointment method. This method modifies all of the elements of an appointment based on its
     * Contact ID.
     * @param appointmentID
     * @param customerID
     * @param userID
     * @param contactID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public int modifyAppointment(int appointmentID, int customerID, int userID, int contactID, String title, String description,
                                 String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * This is the deleteAppointment method. This method deletes an appointment associated with a particular appointment ID,
     * customer ID, and type.
     * @param appointmentID
     * @param customerID
     * @param type
     * @return
     */
    public int deleteAppointment(int appointmentID, int customerID, String type);

    /**
     * This is the addAppointment method. This method adds an appointment with all of the data points included in the method.
     */
    public int addAppointment(int customerID, int userID, int contactID, String title, String description,
                              String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * This is the lookUpAppointment method. This method looks up an appointment on a particular data.
     * @param date
     * @return
     */

    public ObservableList<Appointments> lookUpAppointment(LocalDate date);

    /**
     * This is the upcomingAppointmentAlert method. This method generates an alert for an upcoming appointment each time
     * a user logs in. It also informs the user if there are no upcoming appointments in the next fifteen minutes.
     * @param loginLDT
     */
    public void upcomingAppointmentAlert(LocalDateTime loginLDT);

    /**
     * This is the upcomingAppointmentsBasedOnWeek method. This method searches the database and builds an observable
     * list of future appointments within a particular week (selected by the user).
     * @param loginLD
     * @return
     */
    public ObservableList<Appointments> upcomingAppointmentsBasedOnWeek(LocalDate loginLD);

    /**
     * This is the upcomingAppointmentsBasedOnMonth method. This method searches the database and builds an observable
     * list of future appointments within a particular month (selected by the user).
     * @param loginLD
     * @return
     */
    public ObservableList<Appointments> upcomingAppointmentsBasedOnMonth(LocalDate loginLD);

    /**
     * This is the checkAppointmentStartTime method. This method checks a requested start time for an appointment converting
     * it from Eastern Standard Time to local time to make sure that it falls within business hours.
     * @param appointmentStartTime
     * @return
     */
    public boolean checkAppointmentStartTime(LocalDateTime appointmentStartTime);

    /**
     * This is the checkAppointmentEndTime method. This method checks a requested end time for an appointment converting it
     * from Eastern Standard Time to local time to make sure that it falls within business hours.
     * @param appointmentEndTime
     * @return
     */
    public boolean checkAppointmentEndTime(LocalDateTime appointmentEndTime);

    /**
     * This is the checkNewAppointmentForOverlap method. This method compares the proposed new appointment start and end
     * time to make sure that it doesn't conflict with another previously scheduled appointment.
     * @param customerID
     * @param selectedStartDate
     * @param selectedEndDate
     * @param selectedStartTime
     * @param selectedEndTime
     * @return
     */

    public boolean checkNewAppointmentForOverlap(int customerID, LocalDate selectedStartDate, LocalDate selectedEndDate, LocalTime selectedStartTime,
                                          LocalTime selectedEndTime);

    /**
     * This is the checkModifiedAppointmentForOverlap method. This method checks an appointment that has been modified to make sure
     * that it does not conflict with a previously scheduled appointment.
     * @param customerID
     * @param selectedStartDate
     * @param selectedEndDate
     * @param selectedStartTime
     * @param selectedEndTime
     * @param appointmentID
     * @return
     */
    public boolean checkModifiedAppointmentForOverlap(int customerID, LocalDate selectedStartDate, LocalDate selectedEndDate, LocalTime selectedStartTime,
                                              LocalTime selectedEndTime, int appointmentID);


}
