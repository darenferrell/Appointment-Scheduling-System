package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import model.Appointments;
import java.time.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import static helper.JDBC.connection;

/**
 * This is the AppointmentsDAOImpl class. This class implements the methods that are defined by the AppointmentsDAO interface.
 */
    public class AppointmentsDAOImpl implements AppointmentsDAO {

        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        public boolean appointmentFound;

    /**
     * This is the getAllAppointments method. This method searches the mySQL database for all appointments. It then adds
     * said appointments to an observable list called allAppointments.
     * @return
     */
    @Override
        public ObservableList<Appointments> getAllAppointments() {
            try {
                String sql = "SELECT * FROM appointments";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    int appointmentID = result.getInt("Appointment_ID");
                    int customerID = result.getInt("Customer_ID");
                    int userID = result.getInt("User_ID");
                    int contactID = result.getInt("Contact_ID");
                    String title = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    LocalDateTime startDateTime = result.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endDateTime = result.getTimestamp("End").toLocalDateTime();
                    LocalDate startDate = startDateTime.toLocalDate();
                    LocalDate endDate = endDateTime.toLocalDate();
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    Appointments appointment = new Appointments(appointmentID, customerID, userID, contactID, title, description,
                            location, type, startDateTime, endDateTime, startDate, endDate, startTime, endTime);
                    allAppointments.add(appointment);
                    System.out.println("\n ID " + appointment.getAppointmentID()+" title " + appointment.getTitle());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return allAppointments;
        }

    /**
     * This is the getAppointment method. This method searches the database for a particular appointment using the Appointment
     * ID parameter.
     * @param appointmentID
     * @return
     */
    @Override
        public Appointments getAppointment(int appointmentID) {
            try {
                String sql = "SELECT * FROM appointments WHERE Appointment_ID=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, appointmentID);

                ResultSet result = ps.executeQuery();
                Appointments appointmentResult = null;
                if (result.next()) {
                    appointmentID = result.getInt("Appointment_ID");
                    int customerID = result.getInt("Customer_ID");
                    int userID = result.getInt("User_ID");
                    int contactID = result.getInt("Contact_ID");
                    String title = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    LocalDateTime startDateTime = result.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endDateTime = result.getTimestamp("End").toLocalDateTime();
                    LocalDate startDate = startDateTime.toLocalDate();
                    LocalDate endDate = endDateTime.toLocalDate();
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    appointmentResult = new Appointments(appointmentID, customerID, userID, contactID, title, description,
                            location, type, startDateTime, endDateTime, startDate, endDate, startTime, endTime);
                }
                return appointmentResult;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

    /**
     * This is the getAppointmentBasedOnCustomer method. This method searches the database for any and all customers
     * associated with a particular customer ID. It then adds said customer(s) to an observable list called
     * 'customerAppointments'.
     * @param customerID
     * @return
     */
        @Override
        public ObservableList<Appointments> getAppointmentBasedOnCustomer(int customerID) {
            ObservableList<Appointments> customerAppointments = FXCollections.observableArrayList();

            try {
                String sql = "SELECT * FROM appointments WHERE Customer_ID=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, customerID);

                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    int appointmentID = result.getInt("Appointment_ID");
                    customerID = result.getInt("Customer_ID");
                    int userID = result.getInt("User_ID");
                    int contactID = result.getInt("Contact_ID");
                    String title = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    LocalDateTime startDateTime = result.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endDateTime = result.getTimestamp("End").toLocalDateTime();
                    LocalDate startDate = startDateTime.toLocalDate();
                    LocalDate endDate = endDateTime.toLocalDate();
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    Appointments appointment = new Appointments(appointmentID, customerID, userID, contactID, title, description,
                            location, type, startDateTime, endDateTime, startDate, endDate, startTime, endTime);
                    customerAppointments.add(appointment);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return customerAppointments;
        }

    /**
     * This is the getAppointmentBasedOnContact method. This method searches the database for any and all appointments
     * associated with a particular contact ID. It then adds said appointments to an observable list called
     * 'appointmentsBasedOnContact'.
     * @param contactID
     * @return
     */
    @Override
        public ObservableList<Appointments> getAppointmentBasedOnContact(int contactID) {
            ObservableList<Appointments> appointmentsBasedOnContact = FXCollections.observableArrayList();
            try {
                String sql = "SELECT * FROM appointments WHERE Contact_ID=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, contactID);

                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    int appointmentID = res.getInt("Appointment_ID");
                    int customerID = res.getInt("Customer_ID");
                    int userID = res.getInt("User_ID");
                    contactID = res.getInt("Contact_ID");
                    String title = res.getString("Title");
                    String description = res.getString("Description");
                    String location = res.getString("Location");
                    String type = res.getString("Type");
                    LocalDateTime startDateTime = res.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endDateTime = res.getTimestamp("End").toLocalDateTime();
                    LocalDate startDate = startDateTime.toLocalDate();
                    LocalDate endDate = endDateTime.toLocalDate();
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    Appointments a = new Appointments(appointmentID, customerID, userID, contactID, title, description,
                            location, type, startDateTime, endDateTime, startDate, endDate, startTime, endTime);
                    appointmentsBasedOnContact.add(a);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return appointmentsBasedOnContact;
        }

    /**
     * This is the modifyAppointment method. This method searches the database for a particular appointment. It then checks
     * proposed parameters with the previously defined parameters and updates where differences exist.
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
        @Override
        public int modifyAppointment(int appointmentID, int customerID, int userID, int contactID, String title, String description,
                                     String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
            int affectedRows = 0;
            try {
                String sql = "UPDATE appointments SET Customer_ID=?, User_ID=?, Contact_ID=?, Title=?, Description=?, " +
                        "Location=?, Type=?, Start=?, End=? WHERE Appointment_ID=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, customerID);
                ps.setInt(2, userID);
                ps.setInt(3, contactID);
                ps.setString(4, title);
                ps.setString(5, description);
                ps.setString(6, location);
                ps.setString(7, type);
                ps.setTimestamp(8, Timestamp.valueOf(startDateTime));
                ps.setTimestamp(9, Timestamp.valueOf(endDateTime));
                ps.setInt(10, appointmentID);
                affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Appointment UPDATE was successful!");
                } else {
                    System.out.println("Appointment UPDATE Failed!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return affectedRows;
        }

    /**
     * This is the deleteAppointment method. This method searches the database for any appointment associated with a given
     * appointment ID, customer ID, and type. It then removes the appointment from the database. It also displays a message
     * informing the user whether or not the operation was successful.
     * @param appointmentID
     * @param customerID
     * @param type
     * @return
     */
        @Override
        public int deleteAppointment(int appointmentID, int customerID, String type) {
            int affectedRows = 0;
            try {
                String sql = "DELETE FROM appointments WHERE Appointment_ID=? AND Customer_ID=? AND Type=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, appointmentID);
                ps.setInt(2, customerID);
                ps.setString(3, type);
                affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("The selected " + type + "appointment [ appt #" + appointmentID + "] was deleted successfully.");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("appointment deletion");
                    alert.setContentText("The " + type + "appointment [" + appointmentID + "] was deleted successfully.");
                    alert.showAndWait();
                } else {
                    System.out.println("Appointment DELETE failed!");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment DELETE");
                    alert.setContentText("Appointment [" + appointmentID + "] " + type + " failed to deleted!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return affectedRows;
        }

    /**
     * This is the addAppointment method. This method adds an appointment into the database after data has been entered
     * for each of the required parameters.
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
        @Override
        public int addAppointment(int customerID, int userID, int contactID, String title, String description, String location,
                                  String type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
            int affectedRows = 0;
            try {
                String sql = "INSERT INTO appointments (Customer_ID, User_ID, Contact_ID, Title, Description, Location, Type, " +
                        "Start, End) VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, customerID);
                ps.setInt(2, userID);
                ps.setInt(3, contactID);
                ps.setString(4, title);
                ps.setString(5, description);
                ps.setString(6, location);
                ps.setString(7, type);
                ps.setTimestamp(8, Timestamp.valueOf(startDateTime));
                ps.setTimestamp(9, Timestamp.valueOf(endDateTime));
                affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Appointment INSERT was successful!");
                } else {
                    System.out.println("Appointment INSERT failed!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            return affectedRows;
        }

    /**
     * This is the lookUpAppointment method. This method searches the database for an appointment based on data. It then
     * adds said appointment to an observable list called filteredAppointments.
     * @param selectedDate
     * @return
     */
    @Override
        public ObservableList<Appointments> lookUpAppointment(LocalDate selectedDate) {
            ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();
            appointmentFound = false;

            for (Appointments appointment : allAppointments) {
                if (appointment.getStartDate().equals(selectedDate)) {
                    filteredAppointments.add(appointment);
                }
            }
            if (filteredAppointments.isEmpty()) {
                return allAppointments;
            }
            appointmentFound = true;
            return filteredAppointments;
        }

    /**
     * This is the upcomingAppointmentAlert method. This method generates an alert of any upcoming appointments each time
     * that the user logs in. This method also alerts the user if there are no upcoming appointments within the next
     * fifteen minutes.
     * @param ldt
     */
    @Override
        public void upcomingAppointmentAlert(LocalDateTime ldt) {
            try {
                ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();
                ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
                AppointmentsDAO aDAO = new AppointmentsDAOImpl();
                allAppointments = aDAO.getAllAppointments();

                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime loginZDT = ldt.atZone(zoneId);
                LocalDateTime apptStart = ldt.plusMinutes(15);
                for (Appointments appointment : allAppointments) {
                    ZonedDateTime zonedAppointment = ZonedDateTime.from(appointment.getStartDateAndTime().atZone(zoneId));
                    if (zonedAppointment.isAfter(loginZDT) && zonedAppointment.isBefore(apptStart.atZone(zoneId))) {
                        upcomingAppointments.add(appointment);
                        System.out.println("Upcoming appointment: " + appointment);
                    }
                }
                if (upcomingAppointments.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Upcoming Appointments");
                    alert.setContentText("There are no appointments scheduled to begin in the next 15 minutes.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Upcoming Appointments");
                    alert.setHeaderText("The following Appointments are scheduled to begin in the next 15 minutes:");
                    String alertText = "";

                    for (Appointments upAppt : upcomingAppointments) {
                        alertText = ("Appointment: [" + upAppt.getAppointmentID() + "] at " + upAppt.getStartTime() +
                                " (" + upAppt.getStartDate() + ")\n") + alertText;
                    }
                    alert.setContentText(alertText);
                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }

    /**
     * This is the upcomingAppointmentsBasedOnMonth method. This method searches the database and builds an observable
     * list of future appointments within a particular month (selected by the user).
     * @param dateAtLogin
     * @return
     */
    @Override
        public FilteredList<Appointments> upcomingAppointmentsBasedOnWeek(LocalDate dateAtLogin) {
            ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
            allAppointments = getAllAppointments();
            FilteredList<Appointments> filteredAppointments = new FilteredList<>(allAppointments);

            filteredAppointments.setPredicate(appointment -> {
                LocalDate appointmentDate = appointment.getStartDate();

                return ((appointmentDate.isEqual(dateAtLogin) || appointmentDate.isAfter(dateAtLogin)) &&
                        appointmentDate.isBefore(dateAtLogin.plusDays(7)));
            });
            return filteredAppointments;
        }

    /**
     * This is the checkAppointmentStartTime method. This method checks a requested start time for an appointment converting
     * it from Eastern Standard Time to local time to make sure that it falls within business hours.
     * @param dateAtLogin
     * @return
     */
    @Override
        public FilteredList<Appointments> upcomingAppointmentsBasedOnMonth(LocalDate dateAtLogin) {
            ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
            allAppointments = getAllAppointments();
            FilteredList<Appointments> filteredAppointments = new FilteredList<>(allAppointments);

            filteredAppointments.setPredicate(appointment -> {
                LocalDate appointmentDate = appointment.getStartDate();

                return (appointmentDate.isEqual(dateAtLogin) || appointmentDate.isAfter(dateAtLogin)) &&
                        appointmentDate.getMonth().equals(dateAtLogin.getMonth());
            });
            return filteredAppointments;
        }

    /**
     * This is the checkAppointmentStartTime method. This method checks a requested start time for an appointment converting
     * it from Eastern Standard Time to local time to make sure that it falls within business hours.
     * @param apptStartTime
     * @return
     */
    @Override
        public boolean checkAppointmentStartTime(LocalDateTime appointmentStartTime) {
            ZonedDateTime apptZone = appointmentStartTime.atZone(ZoneId.systemDefault());
            apptZone = apptZone.withZoneSameInstant(ZoneId.of("US/Eastern"));
            appointmentStartTime = apptZone.toLocalDateTime();

            LocalTime businessOpen = LocalTime.of(8, 0);
            LocalTime businessClose = LocalTime.of(22, 0);
            return ((appointmentStartTime.toLocalTime().isAfter(businessOpen) || appointmentStartTime.toLocalTime().equals(businessOpen)) &&
                    (appointmentStartTime.toLocalTime().isBefore(businessClose)));
        }

    /**
     *  This is the checkNewAppointmentForOverlap method. This method compares the proposed new appointment start and end
     *  time to make sure that it doesn't conflict with another previously scheduled appointment.
     * @param appointmentEndTime
     * @return
     */
        @Override
        public boolean checkAppointmentEndTime(LocalDateTime appointmentEndTime) {
            ZonedDateTime apptZone = appointmentEndTime.atZone(ZoneId.systemDefault());
            apptZone = apptZone.withZoneSameInstant(ZoneId.of("US/Eastern"));
            appointmentEndTime = apptZone.toLocalDateTime();

            LocalTime businessOpen = LocalTime.of(8, 0);
            LocalTime businessClose = LocalTime.of(22, 0);
            return ((appointmentEndTime.toLocalTime().isAfter(businessOpen)) &&
                    (appointmentEndTime.toLocalTime().isBefore(businessClose) || appointmentEndTime.toLocalTime().equals(businessClose)));    //can end at close!
        }

    /**
     * This is the checkNewAppointmentForOverlap method. This method checks a new appointment to make sure
     * that it does not conflict with a previously scheduled appointment.
     * @param customerID
     * @param selectedStartDate
     * @param selectedEndDate
     * @param selectedStartTime
     * @param selectedEndTime
     * @return
     */
        @Override
        public boolean checkNewAppointmentForOverlap(int customerID, LocalDate selectedStartDate, LocalDate selectedEndDate, LocalTime selectedStartTime,
                                              LocalTime selectedEndTime) {
            AppointmentsDAO aDAO = new AppointmentsDAOImpl();
            ObservableList<Appointments> customerAppointments = aDAO.getAppointmentBasedOnCustomer(customerID);
            boolean overlap = false;

            for (Appointments appointment : customerAppointments) {

                if ((appointment.getStartDate().isEqual(selectedStartDate)) || (appointment.getEndDate().isEqual(selectedEndDate))) {

                    if (appointment.getStartTime().equals(selectedStartTime)) {
                        overlap = true;
                        break;

                    } else if(appointment.getStartTime().isAfter(selectedStartTime) && appointment.getStartTime().isBefore(selectedEndTime)) {
                        overlap = true;
                        break;

                    }else if(selectedStartTime.isBefore(appointment.getStartTime()) && (selectedEndTime.isAfter(appointment.getStartTime()))) {
                        overlap = true;
                        break;
                    }
                }
            }
            return overlap;
        }

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
        @Override
        public boolean checkModifiedAppointmentForOverlap(int customerID, LocalDate selectedStartDate, LocalDate selectedEndDate, LocalTime selectedStartTime,
                                                  LocalTime selectedEndTime, int appointmentID) {
            AppointmentsDAO aDAO = new AppointmentsDAOImpl();
            ObservableList<Appointments> customerAppointments = aDAO.getAppointmentBasedOnCustomer(customerID);
            boolean overlap = false;

            for(Appointments appointment : customerAppointments) {
                if((appointment.getAppointmentID() == appointmentID) && (selectedStartTime.equals(appointment.getStartTime()) && (selectedEndTime.equals(appointment.getEndTime())))) {
                    break;
                }else {
                    checkNewAppointmentForOverlap(customerID, selectedStartDate, selectedEndDate, selectedStartTime, selectedEndTime);
                }
            }
            return overlap;
        }
    }