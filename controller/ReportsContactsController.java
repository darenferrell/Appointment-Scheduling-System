package controller;

import dao.*;
import dao.AppointmentsDAO;
import dao.AppointmentsDAOImpl;
import dao.ContactsDAO;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import model.Appointments;
import model.Contacts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class ReportsContactsController implements Initializable {

    private Stage stage;

    private Parent scene;

    public TableView<Appointments> contactReportsTableView;

    public TableColumn appointmentIDColumn;

    public TableColumn titleColumn;

    public TableColumn descriptionColumn;

    public TableColumn locationColumn;

    public TableColumn contactColumn;

    public TableColumn typeColumn;

    public TableColumn startDateColumn;

    public TableColumn endDateColumn;

    public TableColumn startTimeColumn;

    public TableColumn endTimeColumn;

    public TableColumn customerIDColumn;

    public TableColumn userIDColumn;

    public ComboBox<Contacts> contactsCombinationBox;

    public Label totalAppointmentsLabel;

    public Label totalCustomersLabel;

    /**
     * This is the cancel method for the 'Contact Reports Controller'. This allows the user to return to the 'Appointments
     * Main' screen.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButtonPressed (ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel Button Clicked.");

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the log out method. It allows the user to log out of and close the scheduler.
     * @param actionEvent
     */
    public void logoutButtonPressed (ActionEvent actionEvent) {
        System.out.println("Logout Button Clicked!");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you wish to Exit the program?");
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    /**
     * This method sends the user to 'Country Reports' screen.
     * @param actionEvent
     * @throws IOException
     */
    public void generateCountriesReportAction (ActionEvent actionEvent) throws IOException {
        System.out.println("The country report button has been clicked.");

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsCountries.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This method sends the user to the 'Month/Type' report screen.
     * @param actionEvent
     * @throws IOException
     */
        public void generateMonthTypeReportAction (ActionEvent actionEvent) throws IOException {
            System.out.println("The month type report button has been clicked.");
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/reportsMonthType.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    /**
     * This is the populate contacts method. It opens up the mySQL database and then uses the AppointmentsDAO method to
     * retrieve contacts from the database and then populate the 'Contact Reports TableView'.
     * @param actionEvent
     */
    public void contactsCombinationBoxPressed(ActionEvent actionEvent) {
        JDBC.openConnection();
        AppointmentsDAO aDao = new AppointmentsDAOImpl();
        int contactID = contactsCombinationBox.getSelectionModel().getSelectedItem().getContactID();
        contactReportsTableView.setItems(aDao.getAppointmentBasedOnContact(contactID));
        totalAppointmentsLabel.setText("Total Appointments: " + aDao.getAppointmentBasedOnContact(contactID).size());
    }

    /**
     * This is the initialize method for the 'Contact Reports Controller'. It sets the titles for each of the columns.
     * Then, it opens up the mySQL database and uses the ContactsDAO method to retrieve contacts which it then uses to
     * populate the 'Contacts Combination Box'.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Contact reports are now initialized.");
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        JDBC.openConnection();
        ContactsDAO contDAO = new ContactsDAOImpl();
        AppointmentsDAO aDAO = new AppointmentsDAOImpl();
        contactsCombinationBox.setItems(contDAO.getAllContacts());
        int appointmentID = 0;
        totalAppointmentsLabel.setText("Total Appointments: " + aDAO.getAllAppointments().size());
    }
}