package controller;

import dao.AppointmentsDAO;
import dao.AppointmentsDAOImpl;
import dao.CustomersDAO;
import dao.CustomersDAOImpl;
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
import model.Customers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class CustomersMainController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Customers, String> addressColumn;

    @FXML
    private TableColumn<Customers, Integer> countryIDColumn;

    @FXML
    private TableColumn<Customers, String> countryNameColumn;

    @FXML
    private TableColumn<Customers, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private Button customersButton;

    @FXML
    private AnchorPane customersMainScreen;

    @FXML
    private TableView<Customers> customersMainTableView;

    @FXML
    private Label customersTitle;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Customers, Integer> divisionIDColumn;

    @FXML
    private TableColumn<Customers, String> divisionNameColumn;

    @FXML
    private Button exitButton;

    @FXML
    private Button modifyButton;

    @FXML
    private TableColumn<Customers, Integer> phoneColumn;

    @FXML
    private TableColumn<Customers, Integer> postalCodeColumn;

    @FXML
    private Button reportsButton;

    Stage stage;

    Parent scene;

    private int customerID;

    @FXML
    private RadioButton currentMonthRadioButton;

    @FXML
    private RadioButton currentWeekRadioButton;

    @FXML
    private RadioButton allAppointmentsRadioButton;


    Customers selectedCustomer;
    /**
     * This is the 'Add Customer' method in the 'CustomersMainController'. It sends the user to the 'Add Customer' screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void addCustomerButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method sends the user to the 'AppointmentsMain' screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void appointmentsButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to delete a customer from the database.
     * @param event
     */
    @FXML
    void deleteCustomerButtonPressed(ActionEvent actionEvent) {
            System.out.println("The delete button has been clicked.");
            JDBC.openConnection();
            CustomersDAO custDAO = new CustomersDAOImpl();
            Customers selectedCustomer = customersMainTableView.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer.getCustomerID();
            String customerName = selectedCustomer.getCustomerName();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The customer that you have chosen will be deleted. " +
                    "Would you like to continue to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent() && result.get() == ButtonType.OK)) {
                System.out.println(custDAO.deleteCustomer(customerID, customerName));

                customersMainTableView.setItems(custDAO.getAllCustomers());
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
     * This is the 'Modify Customer' method. Essentially, it creates an instance called 'selectedCustomer' in the
     * Customers class. It then instructs the method to retrieve the data from the tableview and assign it to
     * 'selectedCustomer'. Then, it loads the 'modifyCustomer' screen and sends the user to that screen in order to
     * complete the modification.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
        @FXML
        public void modifyCustomerButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
            Customers selectedCustomer = customersMainTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyCustomer.fxml"));
            loader.load();

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();

            ModifyCustomerController modCust = loader.getController();
            modCust.sendCustomer(selectedCustomer);
        }

    /**
     * This method sends the user to the 'Customer Reports' screen where more detailed and isolated information is
     * able to be viewed about a particular customer.
      * @param event
     * @throws IOException
     */
    @FXML
    void customerReportsButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsContacts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the initialize method. When the 'Customers Main Controller' is called, these are the first instructions
     * to be implemented. Each of pertinent columns is pre-populated. Then the mySQL database is opened and the
     * 'CustomersDAO' class is called to retrieve the data for all the customers from the database.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customer: I am initialized!");
        System.out.println(String.valueOf(ZoneId.systemDefault()));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        divisionNameColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        JDBC.openConnection();
        CustomersDAO cDAO = new CustomersDAOImpl();
        customersMainTableView.setItems(cDAO.getAllCustomers());
    }
}

