package controller;

import dao.*;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.ChronologySetter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private AnchorPane addAppointmentScreen;

    @FXML
    private Label addAppointmentTitle;

    @FXML
    private Button cancelButton;

    @FXML
    private Label contactIDLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label endDateLabel;

    @FXML
    private DatePicker endDateCombinationBox;

    @FXML
    private Label endTimeLabel;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField locationTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label startDateLabel;

    @FXML
    private DatePicker startDateCombinationBox;

    @FXML
    private Label startTimeLabel;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private Label timeFormatInstruction;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private Label typeLabel;

    @FXML
    private TextField typeTextField;

    @FXML
    private Label userIDLabel;

    public ComboBox<Users> userCombinationBox;

    public ComboBox<Customers> customerCombinationBox;

    public ComboBox<Contacts> contactCombinationBox;

    public DatePicker startDatePicker;

    public DatePicker endDatePicker;

    public ComboBox<LocalTime> startTimeCombinationBox;

    public ComboBox<LocalTime> endTimeCombinationBox;

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

    public Label titleError;

    public Label typeError;

    public Label descriptionError;

    public Label contactError;

    public Label customerError;

    public Label userError;

    public Label locationError;



    Stage stage;

    Parent scene;

    /**
     * This is the save function for the "Add Appointment Controller". It gets all relevant data about
     * the appointment from the customer and then qualifies it to ensure that there are no empty boxes or errors.
     * It also checks for overlap and then saves the appointment. Finally, it loads the main appointment screen for
     * the customer.
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonPressed(ActionEvent actionEvent) {
        System.out.println("Save button has been clicked.");
        try {
            boolean formattingError = false;
            title = titleTextField.getText();
            description = descriptionTextField.getText();
            location = locationTextField.getText();
            type = typeTextField.getText();
            contactID = contactCombinationBox.getSelectionModel().getSelectedItem().getContactID();
            customerID = customerCombinationBox.getSelectionModel().getSelectedItem().getCustomerID();
            userID = userCombinationBox.getSelectionModel().getSelectedItem().getUserID();
            startDate = startDatePicker.getValue();
            endDate = endDatePicker.getValue();
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

        public void errorMsg (int correspondingErrorNumber) {
        switch (correspondingErrorNumber) {
            case 1 -> {
                titleError.setText("\"Title\" field cannot be blank.");
            }
            case 2 -> {
                descriptionError.setText("\"Description\" field cannot be blank.");
            }
            case 3 -> {
                locationError.setText("\"Location\" field cannot be blank.");
            }
            case 4 -> {
                typeError.setText("\"Type\" field cannot be blank.");
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not a valid start/end time.");
                alert.setContentText("\"Start Date/Time\" must precede \"End Date/Time\".");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not a valid start/end time.");
                alert.setContentText("The appointment time that you have selected falls outside of \"Business Hours\". " +
                        "Please choose a time between the hours of 08:00 and 22:00 Eastern Standard Time.");
                alert.showAndWait();
            }
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Overlapping Appointment");
                alert.setContentText("The customer that you have selected has overlapping appointments. Please select a different time.");
                alert.showAndWait();
            }
        }
    }




    /**
     * This is the cancel function. It sends the user back to the main menu.
     * @param event
     * @throws IOException
     */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
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
     * This is the initialize method. It is the first method called for the Add Appointment Controller. It first assigns
     * values to the Dynamic Business Hours Init. method. OsZid is given the system's standard zone id. EST is assigned
     * to businessZid, startTime is set to 8:00, and the number of work hours is assigned 13.
     * The database is then opened and the contactsDAO class calls the getAllContacts() method. The same is done with
     * customers and users. Finally, the combination boxes are populated with the relevant data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Appointment: I am initialized!");
        try {
            ZoneId osZId = ZoneId.systemDefault();
            ZoneId businessZId = ZoneId.of("America/New_York");
            LocalTime startTime = LocalTime.of(8, 0);
            int workHours = 13;

            JDBC.openConnection();
            ContactsDAO contDAO = new ContactsDAOImpl();
            CustomersDAO custDAO = new CustomersDAOImpl();
            UsersDAO uDAO = new UsersDAOImpl();

            contactCombinationBox.setItems(contDAO.getAllContacts());
            contactCombinationBox.getSelectionModel().selectFirst();
            customerCombinationBox.setItems(custDAO.getAllCustomers());
            customerCombinationBox.getSelectionModel().selectFirst();
            userCombinationBox.setItems(uDAO.getAllUsers());
            userCombinationBox.getSelectionModel().selectFirst();
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeCombinationBox.setItems(ChronologySetter.dynamicBusinessHoursInit(osZId, businessZId, startTime, workHours));
            startTimeCombinationBox.getSelectionModel().selectFirst();
            endTimeCombinationBox.setItems(ChronologySetter.dynamicBusinessHoursInit(osZId, businessZId, LocalTime.of(9, 0), workHours));
            endTimeCombinationBox.getSelectionModel().selectFirst();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}