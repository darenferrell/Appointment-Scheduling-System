package controller;

import dao.AppointmentsDAO;
import dao.AppointmentsDAOImpl;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsMainController implements Initializable {

    @FXML
    public TableView<model.Appointments> appointmentsMainTableView;

    @FXML
    public TableColumn<?, ?> contactNameColumn;

    @FXML
    public Button modifyAppointmentButton;

    @FXML
    public Button addAppointmentButton;

    @FXML
    public Button deleteAppointmentButton;

    public TableColumn<Object, Object> startTimeColumn;

    public TableColumn<Object, Object> startDateColumn;

    public TableColumn<Object, Object> endTimeColumn;

    public TableColumn <Contacts, String>contactIDColumn;

    public TableColumn<Object, Object> endDateColumn;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIDColumn;

    @FXML
    private AnchorPane appointmentsMainScreen;

    @FXML
    private Label appointmentsMainTitle;

    @FXML
    private TableColumn<Customers, String> customerIDColumn;

    @FXML
    private Button customersButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, String> descriptionColumn;

    @FXML
    private TableColumn<?, ?> endDateTimeColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private Button modifyButton;

    @FXML
    private Button reportsButton;

    @FXML
    private TableColumn<?, ?> startDateTimeColumn;

    @FXML
    private TableColumn<Appointments, String> titleColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> userIDColumn;

    public DatePicker datePicker;

    Stage stage;

    Parent scene;

    /**
     * This method sends the user to the 'Add Appointment' screen in order to add a new appointment.
     * @param event
     * @throws IOException
     */
    @FXML
    void addAppointmentButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method sends the user to the 'Modify Appointment' screen in order to update/modify an existing appointment.
     * It pre-populates the screen with the appointment information and calls upon the 'Modify Appointment Controller'
     * to proceed with the modification.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void modifyAppointmentButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        Appointments selectedAppointment = appointmentsMainTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyAppointment.fxml"));
        loader.load();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();

        ModifyAppointmentController modAppt = loader.getController();
        modAppt.sendAppointment(selectedAppointment);

    }

    /**
     * This method allows the user to be transferred to the 'Main Customers' screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void customersButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customersMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to delete an appointment. It opens a connection with the mySQL database. Then it uses
     * the AppointmentsDAO interface and then calls on the AppointmentsDAOImpl class to remove the data from the
     * mySQL database.
     * @param event
     */
    @FXML
    void deleteAppointmentButtonPressed(ActionEvent event) {
    System.out.println("The delete button has been clicked.");
    JDBC.openConnection();
    AppointmentsDAO aDAO = new AppointmentsDAOImpl();
    Appointments selectedAppointment = appointmentsMainTableView.getSelectionModel().getSelectedItem();
    int appointmentID = selectedAppointment.getAppointmentID();
    int customerID = selectedAppointment.getCustomerID();
    String type = selectedAppointment.getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The appointment that you have chosen will be deleted. Would you like to continue to delete this appointment?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.out.println(aDAO.deleteAppointment(appointmentID, customerID, type));

            JDBC.openConnection();
            appointmentsMainTableView.setItems(aDAO.getAllAppointments());
        }
    }

    /**
     * This method allows the user to log out of and close the scheduler.
     * @param event
     */
    @FXML
    void exitButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure that you would like to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    /**
     * This method sends the user to the 'Contact Reports' screen to obtain more detailed information about a customer
     * and/or appointment.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void reportsButtonPressed(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsContacts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void currentWeekAppointmentsButtonSelected(ActionEvent actionEvent) {
    System.out.println("Current week appointments radio button has been selected.");
    JDBC.openConnection();
    AppointmentsDAO aDAO = new AppointmentsDAOImpl();
    appointmentsMainTableView.setItems(aDAO.upcomingAppointmentsBasedOnWeek(LocalDate.from(dao.DatabaseLogin.getLocalDateTime())));

    }

    public void currentMonthAppointmentsButtonSelected(ActionEvent actionEvent) {
    System.out.println("Current month appointments view radio button has been selected.");
    JDBC.openConnection();
    AppointmentsDAO aDAO = new AppointmentsDAOImpl();
    appointmentsMainTableView.setItems(aDAO.upcomingAppointmentsBasedOnMonth(LocalDate.from(dao.DatabaseLogin.getLocalDateTime())));
    }

    public void allAppointmentsButtonSelected(ActionEvent actionEvent) {
        System.out.println("All appointments view radio button has been selected.");
        JDBC.openConnection();
        AppointmentsDAO aDAO = new AppointmentsDAOImpl();
        appointmentsMainTableView.setItems(aDAO.getAllAppointments());
    }

    public void searchForAppointmentByDate(ActionEvent actionEvent) {
        JDBC.openConnection();
        AppointmentsDAO aDAO = new AppointmentsDAOImpl();
        appointmentsMainTableView.setItems(aDAO.getAllAppointments());
        LocalDate chosenDate = datePicker.getValue();

        try {
            ObservableList<Appointments> appointments = aDAO.lookUpAppointment(chosenDate);
            appointmentsMainTableView.setItems(appointments);
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (!((AppointmentsDAOImpl) aDAO).appointmentFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No appointments were found that match the entered criteria.");
            alert.showAndWait();
        }
    }

    /**
     * This is the initlialize method for the 'Appointments Main Controller'. It pre-populates each of the columns in
     * the 'Appointments Main Controller'. It then establishes a connection with the mySQL database and then uses the
     * AppointmentsDAO and AppointmentsDAOImpl methods to retrieve all appointments.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointment Schedule (Main Menu): I am initialized.");
        System.out.println(String.valueOf(ZoneId.systemDefault()));

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        JDBC.openConnection();
        AppointmentsDAO aDAO = new AppointmentsDAOImpl();
        appointmentsMainTableView.setItems(aDAO.getAllAppointments());
    }
}






