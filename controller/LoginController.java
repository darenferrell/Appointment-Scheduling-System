package controller;

import dao.AppointmentsDAO;
import dao.AppointmentsDAOImpl;
import dao.UsersDAO;
import dao.UsersDAOImpl;
import helper.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Users;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static dao.DatabaseLogin.loginQuery;


public class LoginController implements Initializable {

    @FXML
    public TextField userNameTextField;

    @FXML
    public Label userNameLabel;

    @FXML
    public Button resetButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginRequestLabel;

    @FXML
    private AnchorPane loginScreen;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label zoneIDLabel;

    @FXML
    private Stage stage;

    @FXML
    private Parent scene;

    ResourceBundle resourceBundle = ResourceBundle.getBundle("Languages/lang");

    /**
     * This is the login method. The user name and password fields (and any login attempts) are qualified and recorded in the filewriter
     * and printwriter. The local time specific to the user's location is also retrieved. The UsersDAO class is then called
     * upon to determine whether or not the username and password are accurate. Once the loginQuery's requirements are
     * met, the 'AppointmentsMain' screen is loaded. If the correct login information is not provided, the else statement
     * lets the user know that the username and/or password provided are invalid.
     * @param actionEvent
     */
    public void loginButtonPressed(javafx.event.ActionEvent actionEvent) {
        System.out.println("Login attempt has occurred.");
        try{
            String userName = userNameTextField.getText();
            String password = String.valueOf(passwordTextField.getText());
            Users userResult = loginQuery(userName, password);
            LocalDateTime now = LocalDateTime.now();
            String fileName = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            UsersDAO UDAO = new UsersDAOImpl();
            System.out.println(UDAO.loginQuery(userName, password));

            if (UDAO.loginQuery(userName, password) == true){
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                JDBC.openConnection();
                AppointmentsDAO aDAO = new AppointmentsDAOImpl();
                LocalDateTime localDateTimeForLogin = dao.DatabaseLogin.getLocalDateTime();
                aDAO.upcomingAppointmentAlert(localDateTimeForLogin);
                outputFile.println(userName + " The attempt to login was successful at " + now + " (" + ZoneId.systemDefault() + ")");
            }
            else if(Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle/language_fr", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(resourceBundle.getString("Invalid") + " " + resourceBundle.getString("Username") +
                        " " + resourceBundle.getString("and") + "/" + resourceBundle.getString("or") +
               " " + resourceBundle.getString("Password") + ". " + resourceBundle.getString("Please") + " " +
                        resourceBundle.getString("try") + " " + resourceBundle.getString("again") + ". " );
                alert.showAndWait();

                outputFile.println(userName + " The login attempt that occurred at " + now + " (" + ZoneId.systemDefault() + ")" + " was unsuccessful.");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The username and/or password provided are invalid. Please try again.");
                alert.showAndWait();
                outputFile.println(userName + " Login was unsuccessful at " + now + " (" + ZoneId.systemDefault() + ")");
            }
            outputFile.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void resetButtonPressed(javafx.event.ActionEvent actionEvent) {
        userNameTextField.setText("");
        passwordTextField.setText("");
    }

    /**
     * This is the initialize method for the login screen. This method uses the zoneId to determine which timezone the
     * user is in and then populates the timezone. If the zoneID corresponds with French speaking areas, the screen is
     * displayed in French, otherwise it is displayed in English.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIDLabel.setText(String.valueOf(ZoneId.systemDefault()));
        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = ResourceBundle.getBundle("languages/language_fr", Locale.getDefault());
                userNameLabel.setText(rb.getString("Username"));
                passwordLabel.setText(rb.getString("Password"));
                zoneIDLabel.setText(rb.getString("TimeZone"));
                resetButton.setText(rb.getString("Reset"));
                loginButton.setText(rb.getString("Login"));
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This is the exit method. The scheduler is closed after the user is asked to verify that this is the action that
     * should be taken.
     * @param actionEvent
     */
    public void exitButtonPressed(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure that you would like to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }
}