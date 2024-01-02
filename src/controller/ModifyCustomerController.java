package controller;

import dao.*;
import helper.JDBC;
import javafx.collections.FXCollections;
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
import model.*;
import utilities.ChronologySetter;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML
    public Label postalCodeLabel;

    @FXML
    public TextField postalCodeTextField;

    @FXML
    private Label addressLabel;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Label countryLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private AnchorPane modifyCustomerScreen;

    @FXML
    private Label modifyCustomerTitle;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Label stateProvinceLabel;

    @FXML
    private Label zipCodeLabel;

    @FXML
    private TextField zipCodeTextField;

    @FXML
    private ComboBox<Countries> countriesCombinationBox;

    @FXML
    private ComboBox<Divisions> stateProvinceCombinationBox;

    String customerName;

    String address;

    String postalCode;

    String phone;

    int countryID;

    int customerID;

    int divisionID;

    public Label nameError;

    public Label addressError;

    public Label postalCodeError;

    public Label customerPhoneError;

    public Customers selectedCustomer;

    Stage stage;

    Parent scene;

    /**
     * This is the cancel method. It returns the user to the 'Customers Main' menu. It also doublechecks that this is
     * the intended action on behalf of the user.
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
                scene = FXMLLoader.load(getClass().getResource("/view/customersMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }

    /**
     * This is the save method. It first informs the user that the save button has been clicked. It then qualifies all
     * of the entries to ensure that there are no formatting errors. If there are (e.g., empty spaces, etc), it displays
     * an error message. If not, it calls on the CustomersDAO class to interface with the mySQL database and save the
     * modifed customer data.
     * @param actionEvent
     */
    @FXML
        void saveButtonPressed(ActionEvent actionEvent) {
            System.out.println("The save button has been clicked.");
            try {
                boolean formattingError = false;
                customerID = selectedCustomer.getCustomerID();
                customerName = customerNameTextField.getText();
                address = addressTextField.getText();
                postalCode = postalCodeTextField.getText();
                phone = phoneNumberTextField.getText();
                countryID = countriesCombinationBox.getValue().getCountryID();
                divisionID = stateProvinceCombinationBox.getValue().getDivisionID();

                if (customerName.isBlank()) {
                    errorMsg(1);
                    formattingError = true;
                } else if (address.isBlank()) {
                    errorMsg(2);
                    formattingError = true;
                } else if (postalCode.isBlank()) {
                    errorMsg(3);
                    formattingError = true;
                } else if (phone.isBlank()) {
                    errorMsg(4);
                    formattingError = true;
                }

                if (!formattingError) {
                    CustomersDAO custDAO = new CustomersDAOImpl();
                    custDAO.modifyCustomer(customerID, customerName, address, postalCode, phone, divisionID);
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/customersMain.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

    /**
     * This is the error message method which also employs Lambda Expressions. The error messages are employed to inform
     * the user when the appropriate protocols have not been followed. I chose to use Lambda Expressions here
     * because it simplifies the code involved. Instead of 'System.out.println() etc, etc, I can simply incorporate
     * the -> sign.
     * @param correspondingErrorNumber
     */
    public void errorMsg(int correspondingErrorNumber) {
            switch (correspondingErrorNumber) {
                case 1 -> {
                nameError.setText("Customer name text field cannot be blank.");
                }
                case 2 -> {
                addressError.setText("Customer address text field cannot be blank.");
                }
                case 3 -> {
                postalCodeError.setText("Postal Code text field cannot be blank.");
                }
                case 4 -> {
                customerPhoneError.setText("Phone number text field cannot be blank.");
                }
            }
        }

    /**
     * This is the populate countries method. It gets the data from the 'Countries Combination Box' that has been
     * selected by the user. It then calls the DivisionsDAO class to get ta filtered list of divisions based on the
     * country that is selected.
     * @param actionEvent
     */
    public void countriesCombinationBoxPressed(ActionEvent actionEvent) {
        Countries count = countriesCombinationBox.getValue();
        System.out.println("country selected" + count.getCountryName());
        DivisionsDAO divDAO = new DivisionsDAOImpl();
        ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
        try {
            allDivisions = divDAO.getAllDivisions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Divisions> filteredDivisions = FXCollections.observableArrayList();
        for (Divisions div : allDivisions) {
            System.out.println(div.getCountryID() + " " + count.getCountryID());
            if (div.getCountryID() == count.getCountryID()) {
                filteredDivisions.add(div);
            }
        }
        stateProvinceCombinationBox.setItems(filteredDivisions);
        stateProvinceCombinationBox.getSelectionModel().select(divisionID);
    }



    /**
     * This is the 'Send Customer' method. This method takes the selected Customer for modification. It opens up the
     * mySQL database. Then it uses the CountriesDAO and CustomersDAO methods to retrieve information from the mySQL
     * database. It then builds an observable list titled 'allCustomers' and 'allCountries'. Then it retrieves
     * data from each of the text fields and combination boxes. Finally, it iterates through a for/if loop and matches the
     * selected customer with the matching customer in the database, sending all pertinent customer information to the
     * CustomersMainController.
     * @param selectedCustomer
     */
    public void sendCustomer(Customers selectedCustomer) {
        System.out.println(selectedCustomer.getCustomerID());
        this.selectedCustomer = selectedCustomer;
        JDBC.openConnection();
        CountriesDAO countDAO = new CountriesDAOImpl();
        ObservableList<Countries> allCountries = countDAO.getAllCountries();
        CustomersDAO custDAO = new CustomersDAOImpl();
        ObservableList<Customers> allCustomers = custDAO.getAllCustomers();
        customerIDTextField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        customerNameTextField.setText(String.valueOf(selectedCustomer.getCustomerName()));
        addressTextField.setText(String.valueOf(selectedCustomer.getAddress()));
        postalCodeTextField.setText(String.valueOf(selectedCustomer.getPostalCode()));
        phoneNumberTextField.setText(String.valueOf(selectedCustomer.getPhone()));
        countriesCombinationBox.setItems(allCountries);
        for (Countries count: allCountries) {
            if (count.getCountryID()==selectedCustomer.getCountryID()) {
                countriesCombinationBox.setValue(count);
            }
        }
        DivisionsDAO divDAO = new DivisionsDAOImpl();
        ObservableList<Divisions> allDivisions = divDAO.getAllDivisions();
        int customerDivisionID = stateProvinceCombinationBox.getValue().getDivisionID();
        stateProvinceCombinationBox.setItems(allDivisions);
        for (Divisions div: allDivisions) {
            if (div.getDivisionID()==selectedCustomer.getDivisionID()) {
                stateProvinceCombinationBox.setValue(div);
            }
        }
    }

    /**
     * This is the initialize method for the 'Modify Customer Controller'. It starts the URL & resource bundles. It then
     * opens up the database and uses the CountriesDAO class to communicate with the database and pre-populate the
     * 'Countries Combination Box' with all of the countries.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized.");
        JDBC.openConnection();
        CountriesDAO countDAO = new CountriesDAOImpl();
        countriesCombinationBox.setItems(((CountriesDAOImpl) countDAO).getAllCountries());
    }
}


