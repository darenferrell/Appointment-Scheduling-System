package controller;

import dao.*;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.ChronologySetter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.CustomersDAO;
import dao.CustomersDAOImpl;
import dao.ContactsDAO;
import dao.ContactsDAOImpl;
import dao.AppointmentsDAO;
import dao.AppointmentsDAOImpl;

import static javax.swing.UIManager.getInt;

public class ModifyAppointmentController implements Initializable {

    @FXML
    private Label appointmentIDLabel;

    @FXML
    private TextField appointmentIDTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Contacts> contactIDCombinationBox;

    @FXML
    private Label contactIDLabel;

    @FXML
    private ComboBox<Customers> customerIDCombinationBox;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label endDateLabel;

    @FXML
    private DatePicker endDateSelectionBox;

    @FXML
    private ComboBox<LocalTime> endTimeCombinationBox;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField locationTextField;

    @FXML
    private AnchorPane modifyAppointmentScreen;

    @FXML
    private Label modifyAppointmentScreenLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label startDateLabel;

    @FXML
    private DatePicker startDateSelectionBox;

    @FXML
    private ComboBox<LocalTime> startTimeCombinationBox;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label timeFormatLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private Label typeLabel;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<Users> userIDCombinationBox;

    @FXML
    private Label userIDLabel;

    Stage stage;

    Parent scene;

    Appointments chosenAppointment = null;

    public int customerID;

    public int userID;

    public int contactID;

    public String title;

    public String description;

    public String location;

    public String type;

    public LocalDate startDate;

    public LocalDate endDate;

    public LocalTime startTime;

    public LocalTime endTime;

    public LocalDateTime startDateTime;

    public LocalDateTime endDateTime;

    public DatePicker pickerForStartDate;

    public DatePicker pickerForEndDate;

    public Label titleError;

    public Label descriptionError;

    public Label locationError;

    public Label typeError;

    public Label startDateError;

    public Label endDateError;

    public Label startTimeError;

    public Label endTimeError;

    public Label startDateAndTimeError;

    public Label endDateAndTimeError;

    public Label pickerForStartDateError;

    public Label pickerForEndDateError;

    public Appointments selectedAppointment;

    Appointments chosAppt = null;

    /**
     * This is the cancel method. If the user clicked on the 'modify appointment' button by mistake, this is the button
     * returns to the main menu. It also features a doublecheck question. It then sends the user back to the 'CustomersMain'
     * screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelButtonPressed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Would you like to cancel and return to the main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This is the 'Modify Appointment' method. It does all kinds of fun tricks.
     * First, it establishes a connection with the database. Then it calls on the 'ContactsDAO', 'CustomersDAO', and
     * 'UsersDAO' classes to get contact, customer, and user data from the mySQL database. Then, the text fields are
     * populated with the appointment information that was selected. Three 'for/if loops' (one for contacts, one for
     * customers, and one for users) are implemented in order to engage with the corresponding combination boxes. The
     * data is then loaded into the instance 'chosAppt'.
     *
     * @param chosenAppointment
     */
    void modify(Appointments chosenAppointment) {
        JDBC.openConnection();
        ContactsDAO contDAO = new ContactsDAOImpl();
        CustomersDAO custDAO = new CustomersDAOImpl();
        UsersDAO uDAO = new UsersDAOImpl();

        chosAppt = chosenAppointment;
        titleTextField.setText(String.valueOf(chosAppt.getTitle()));
        descriptionTextField.setText(String.valueOf(chosAppt.getDescription()));
        locationTextField.setText(String.valueOf(chosAppt.getLocation()));
        typeTextField.setText(String.valueOf(chosAppt.getType()));
        Contacts chosenContact = null;
        for (Contacts cont : contDAO.getAllContacts()) {
            if (cont.getContactID() == chosAppt.getContactID()) {
                chosenContact = cont;
                break;
            }
        }
        contactIDCombinationBox.getSelectionModel().select(chosenContact);
        Customers chosenCustomer = null;
        for (Customers cust : custDAO.getAllCustomers()) {
            if (cust.getCustomerID() == chosAppt.getCustomerID()) {
                chosenCustomer = cust;
                break;
            }
        }
        customerIDCombinationBox.getSelectionModel().select(chosenCustomer);
        Users chosenUser = null;
        for (Users user : uDAO.getAllUsers()) {
            if (user.getUserID() == chosAppt.getUserID()) {
                chosenUser = user;
                break;
            }
        }
        userIDCombinationBox.getSelectionModel().select(chosenUser);
        pickerForStartDate.setValue(chosAppt.getStartDate());
        pickerForEndDate.setValue(chosAppt.getEndDate());
        startTimeCombinationBox.getSelectionModel().select(chosAppt.getStartTime());
        endTimeCombinationBox.getSelectionModel().select(chosAppt.getEndTime());
    }

    /**
     * This is the save method for the appointment modification. The user updates the combination boxes, date pickers, and
     * text fields. If there is a blank text field, the 'errorMsg' method is called. Then the method checks to see if the
     * chosen appointment is within business hours. If this is the case, then the start date/time are verified. If not,
     * another 'errorMsg' is displayed. Once the program has reached this point without any errors, it is time for the
     * method to check whether or not there is overlap. The 'checkModifiedAppointmentForOverlap' method is called. If
     * there is no overlap issues, then the 'modifyAppointment' method is called, and the information is updated in the
     * mySQL database.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Save button has been clicked.");
        try {
            ZoneId osZId = ZoneId.systemDefault();
            ZoneId businessZId = ZoneId.of("America/New_York");
            LocalTime startTime = LocalTime.of(8, 0);
            int workHours = 13;

            JDBC.openConnection();
            ContactsDAO contDAO = new ContactsDAOImpl();
            CustomersDAO custDAO = new CustomersDAOImpl();
            UsersDAO uDAO = new UsersDAOImpl();

            boolean formattingError = false;
            int appointmentID = chosenAppointment.getAppointmentID();
            title = titleTextField.getText();
            description = descriptionTextField.getText();
            location = locationTextField.getText();
            type = typeTextField.getText();
            contactID = contactIDCombinationBox.getSelectionModel().getSelectedItem().getContactID();
            int customerID = customerIDCombinationBox.getSelectionModel().getSelectedItem().getCustomerID();
            int userID = userIDCombinationBox.getSelectionModel().getSelectedItem().getUserID();
            startDate = startDateSelectionBox.getValue();
            endDate = endDateSelectionBox.getValue();
            startTime = startTimeCombinationBox.getSelectionModel().getSelectedItem();
            endTime = endTimeCombinationBox.getSelectionModel().getSelectedItem();
            startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute());
            endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute());
            if (title.isBlank()) {
                errorMsg(1);
                formattingError = true;
            } else if (description.isBlank()) {
                errorMsg(2);
                formattingError = true;
            } else if (location.isBlank()) {
                errorMsg(3);
                formattingError = true;
            } else if (type.isBlank()) {
                errorMsg(4);
                formattingError = true;
            }
            if (!formattingError) {
                AppointmentsDAO aDAO = new AppointmentsDAOImpl();
                if (aDAO.checkAppointmentStartTime(startDateTime) && aDAO.checkAppointmentEndTime(endDateTime)) {
                    if (startDateTime.toLocalTime().isBefore(endDateTime.toLocalTime())) {
                        if (!aDAO.checkNewAppointmentForOverlap(customerID, startDate, endDate, startTime, endTime)){
                            aDAO.addAppointment(customerID, userID, contactID, title, description, location, type, startDateTime, endDateTime);

                            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                            helper.JDBC.closeConnection();
                        } else {
                            errorMsg(7);
                        }
                    } else {
                        errorMsg(5);
                    }
                } else {
                    errorMsg(6);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the error message method which also employs LAMBDA Expressions. The error messages are employed to inform
     * the user when the appropriate protocols have not been followed. I chose to use Lambda Expressions here
     * because it simplifies the code involved. Instead of 'System.out.println() etc, etc, I can simply incorporate
     * the -> sign.
     *
     * @param correpondingErrorNumber
     */

    public void errorMsg(int correpondingErrorNumber) {

        switch (correpondingErrorNumber) {
            case 1 -> {
                titleError.setText("\"Title\" text field cannot be empty.");
            }
            case 2 -> {
                descriptionError.setText("\"description\" text field cannot be empty.");
            }
            case 3 -> {
                locationError.setText("\"location\" text field cannot be empty.");
            }
            case 4 -> {
                typeError.setText("\"type\" text field cannot be empty.");
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not a legitimate start/end time.");
                alert.setContentText("You have chosen an appointment that is outside of \"Business Hours\". Please choose a time " +
                        "between 08:00 and 22:00 EST.");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not a legitimate start/end time.");
                alert.setContentText("\"Start Date/Time\" must come prior to \"End Date/Time\"");
                alert.showAndWait();

            }
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting appointment.");
                alert.setContentText("This particular customer has overlapping appointments. Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * This is the 'Send Appointment' method. This method calls the 'Appointments' class and creates an instance titled
     * 'selectedAppointment' Then, it checks the timezone to make sure that the appointment falls within business hours.
     * Three observable lists are created (contacts, customers, and users) It calls on the 'ContactsDAO', 'CustomersDAO',
     * and 'UsersDAO' classes to get contact, customer, and user data from the mySQL database. Then, the text fields are
     * populated with the appointment information that was selected. Three 'for/if loops' (one for contacts, one for
     * customers, and one for users) are implemented in order to engage with the corresponding combination boxes. Once
     * called, this method sends the information to the 'AppointmentsMain' controller.
     *
     * @param selectedAppointment
     */
    public void sendAppointment(Appointments selectedAppointment) {
        chosenAppointment = selectedAppointment;
        System.out.println(selectedAppointment.getAppointmentID());
        ZoneId osZId = ZoneId.systemDefault();
        ZoneId businessZId = ZoneId.of("America/New_York");
        LocalTime startTime = LocalTime.of(8, 0);
        int workHours = 13;
        JDBC.openConnection();
        ContactsDAO contDAO = new ContactsDAOImpl();
        CustomersDAO custDAO = new CustomersDAOImpl();
        UsersDAO uDAO = new UsersDAOImpl();
        ObservableList<Contacts> allContacts = contDAO.getAllContacts();
        ObservableList<Customers> allCustomers = custDAO.getAllCustomers();
        ObservableList<Users> allUsers = uDAO.getAllUsers();
        contactIDCombinationBox.setItems(allContacts);
        customerIDCombinationBox.setItems(allCustomers);
        userIDCombinationBox.setItems(allUsers);
        startTimeCombinationBox.setItems(ChronologySetter.dynamicBusinessHoursInit(osZId, businessZId, startTime, workHours));
        endTimeCombinationBox.setItems(ChronologySetter.dynamicBusinessHoursInit(osZId, businessZId, LocalTime.of(9, 0), workHours));
        appointmentIDTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleTextField.setText(String.valueOf(selectedAppointment.getTitle()));
        descriptionTextField.setText(String.valueOf(selectedAppointment.getDescription()));
        locationTextField.setText(String.valueOf(selectedAppointment.getLocation()));
        for (Contacts contact : allContacts) {
            if (contact.getContactID() == selectedAppointment.getContactID()) {
                contactIDCombinationBox.setValue(contact);
            }
        }
        typeTextField.setText(String.valueOf(selectedAppointment.getType()));
        startDateSelectionBox.setValue(selectedAppointment.getStartDate());
        endDateSelectionBox.setValue(selectedAppointment.getEndDate());
        startTimeCombinationBox.setValue(selectedAppointment.getStartTime());
        endTimeCombinationBox.setValue(selectedAppointment.getEndTime());
        for (Customers cust : allCustomers) {
            if (cust.getCustomerID() == selectedAppointment.getCustomerID()) {
                customerIDCombinationBox.setValue(cust);
            }
        }
        for (Users user : allUsers) {
            if (user.getUserID() == selectedAppointment.getUserID()) {
                userIDCombinationBox.setValue(user);
            }
        }
    }

    /**
     * This is the initialize method. With respect to the 'Modify Appointments Controller', the initialize method only
     * calls upon the URL and Resource Bundle, and communicates to the user that it has been initialized.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized.");
    }
}