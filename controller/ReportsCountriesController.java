package controller;

import dao.*;
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
import model.Countries;
import model.Customers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsCountriesController implements Initializable {

    private Stage stage;

    private Parent scene;

    public TableView<Customers> countriesReportsTableView;

    public TableColumn customerIDColumn;

    public TableColumn customerNameColumn;

    public TableColumn addressColumn;

    public TableColumn postalCodeColumn;

    public TableColumn phoneColumn;

    public TableColumn countryNameColumn;

    public ComboBox<Countries> countriesCombinationBox;

    public Label totalCustomersLabel;

    /**
     * This is the return to main menu method for the 'Country Reports Controller'. It allows the user to return to
     * the main menu.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButtonPressed (ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button has been clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the log out method for the Country Reports Controller. It allows the user to log out of and close the
     * scheduler.
     * @param actionEvent
     */
    public void logoutButtonPressed (ActionEvent actionEvent) {
        System.out.println("Logout button has been clicked.");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Would you like to close the program and exit?");
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    /**
     * This method sends the user to the 'Contact Reports' screen.
     * @param actionEvent
     * @throws IOException
     */
    public void contactsReportsButtonPressed (ActionEvent actionEvent) throws IOException {
        System.out.println("Contacts report button has been clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsContact.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method sends the user to the 'Month Type Reports' screen.
     * @param actionEvent
     * @throws IOException
     */
    public void monthTypeReportsButtonPressed (ActionEvent actionEvent) throws IOException {
        System.out.println("Month and type report button has been clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsMonthType.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the populate countries combination box method. It opens up the mySQL database. It then uses the
     * CustomersDAO method to retrieve customer data based on a particular country. It then populates the countries
     * combination box with this data.
     * @param actionEvent
     * @throws SQLException
     */
    public void countriesCombinationBoxPressed (ActionEvent actionEvent) throws SQLException {
        JDBC.openConnection();
        CustomersDAO custDAO = new CustomersDAOImpl();
        int countryID = countriesCombinationBox.getSelectionModel().getSelectedItem().getCountryID();
        countriesReportsTableView.setItems(custDAO.getCustomersBasedOnCountry(countryID));
        System.out.println(custDAO.getCustomersBasedOnCountry(countryID).get(0).getCountryName());
        totalCustomersLabel.setText("Total Customers: " + custDAO.getCustomersBasedOnCountry(countryID).size());
    }

    /**
     * This is the initialize method for the 'Country Reports Controller'. It assigns labels to each of the columns.
     * It then opens up the mySQL database and uses the CustomersDAO method to retrieve data and to build two lists.
     * The first is a Customer List entitled 'allCustomers'. The second is a filtered list entitled 'filteredCustomers'.
     * The filtered customers list is a list of customers filtered for each country. The method then populates the
     * 'Countries Combination Box' and the 'Countries Reports Table View'.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Country reports have been initialized.");
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        JDBC.openConnection();
        CustomersDAO customerDAO = new CustomersDAOImpl();
        ObservableList<Customers> allCustomers = customerDAO.getAllCustomers();

        ObservableList<Customers> filteredCustomers = FXCollections.observableArrayList();

        CountriesDAO countDAO = new CountriesDAOImpl();
        countriesCombinationBox.setItems(countDAO.getAllCountries());
        countriesReportsTableView.setItems(allCustomers);
        CustomersDAO custDAO = new CustomersDAOImpl();
        totalCustomersLabel.setText("Total Customers: " + custDAO.getAllCustomers().size());
    }
}