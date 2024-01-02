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
import model.Countries;
import model.Divisions;
import utilities.ListManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.CountriesDAO.*;
import static utilities.ListManager.getFilteredDivisions;

public class AddCustomerController implements Initializable {

    public Label postalCodeLabel;

    public ComboBox<Countries> countriesCombinationBox;

    public ComboBox<Divisions> divisionCombinationBox;

    public Label divisionLabel;

    public Label addressLabel;

    public Label phoneNumberLabel;

    public Label stateProvinceLabel;

    public Button saveButton;

    public Label customerNameLabel;

    public Label addCustomerScreenLabel;

    public Label countryIDLabel;

    public Label divisionIDLabel;

    public TextField countryIDTextField;

    public TextField divisionTextField;

    public TextField stateProvinceTextField;

    @FXML
    private AnchorPane addCustomerScreen;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField postalCodeTextField;

    public int countryID;

    String customerName;

    String address;

    String postalCode;

    String phone;

    int divisionID;

    Stage stage;

    Parent scene;

    /**
     * This is the cancel function. It returns the user to the main menu.
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
     * This is the save function. It gets the data entered by the user. It then qualifies it to ensure that none of the
     * boxes are left blank. If all entries are valid, the CustomersDAO and CustomersDAOImpl methods are used to save
     * the new customer entry into the database.
     * @param event
     * @throws IOException
     */
    @FXML
    void saveButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Save button has been clicked.");
        try {
            boolean formattingError = false;
            customerName = customerNameTextField.getText();
            address = addressTextField.getText();
            postalCode = postalCodeTextField.getText();
            phone = phoneNumberTextField.getText();
            divisionID = divisionCombinationBox.getSelectionModel().getSelectedItem().getDivisionID();
            if (customerName.isBlank()) {
                System.out.println();
                formattingError = true;
            } else if (address.isBlank()) {
                System.out.println();
                formattingError = true;
            } else if (postalCode.isBlank()) {
                System.out.println();
                formattingError = true;
            } else if (phone.isBlank()) {
                System.out.println();
                formattingError = true;
            }
            if (!formattingError) {
                CustomersDAO custDAO = new CustomersDAOImpl();
                custDAO.addCustomer(customerName, address, postalCode, phone, divisionID);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/customersMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                JDBC.closeConnection();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** This is the populate country and divisions method. Essentially, when adding a customer,
     * this method populates the countries table with all possible countries. Once a country
     * is selected, it subsequently populates the divisions table with the 'filteredDivisions' method so that
     * a state/province can be selected.
     *
     * @param actionEvent
     */
    public void selectCountryButtonPressed(ActionEvent actionEvent) {
        Countries country = countriesCombinationBox.getValue();
        System.out.println("country selected" + country.getCountryName());
        DivisionsDAO divDAO = new DivisionsDAOImpl();
        ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
        try {
            allDivisions = divDAO.getAllDivisions();
        } catch (Exception e) {
            e.printStackTrace();

        }
        ObservableList<Divisions> filteredDivisions = FXCollections.observableArrayList();
        for (Divisions div : allDivisions) {
            System.out.println(div.getCountryID() + " " + country.getCountryID());
            if (div.getCountryID() == country.getCountryID()) {
                filteredDivisions.add(div);
            }
        }
        divisionCombinationBox.setItems(filteredDivisions);
    }

    /**
     * This is the initialize method in the 'Add Customer Controller'. It determines what happens first
     * when the 'Add Customer Controller' is opened. In this case, it connects with the mysql database. It then use the
     * DAO model to retrieve data for the 'Countries Combination Box'.
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
