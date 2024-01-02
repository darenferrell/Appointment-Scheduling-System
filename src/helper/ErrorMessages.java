package helper;

import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;


    public class ErrorMessages implements Initializable {
        static ResourceBundle langBundle = ResourceBundle.getBundle("language/lang");


        public static void getError(int whichError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            switch (whichError) {

                case 1:
                    alert.setTitle(langBundle.getString("Username Error"));
                    alert.setContentText(langBundle.getString("Username is incorrect."));
                    alert.showAndWait();
                    break;

                case 2:
                    alert.setTitle(langBundle.getString("Password Error"));
                    alert.setContentText(langBundle.getString("Password is incorrect."));
                    alert.showAndWait();
                    break;

                case 3:
                    alert.setTitle(langBundle.getString("User/password Error"));
                    alert.setContentText(langBundle.getString("Username and/or password is incorrect."));
                    alert.showAndWait();
                    break;

                case 4:
                    alert.setTitle(langBundle.getString("Blank password field"));
                    alert.setContentText(langBundle.getString("The password field is blank."));
                    alert.showAndWait();
                    break;

                case 5:
                    alert.setTitle("Delete confirmation");
                    alert.setContentText("Are you sure that you would like to delete this customer?");
                    alert.showAndWait();
                    break;

                case 6:
                    alert.setTitle(langBundle.getString("Blank Username field"));
                    alert.setContentText(langBundle.getString("The username field is blank."));
                    alert.showAndWait();
                    break;

                case 7:
                    alert.setTitle("No customer was selected.");
                    alert.setContentText("In order to proceed, please select a customer.");
                    alert.showAndWait();
                    break;

                case 8:
                    alert.setTitle("Title field is empty.");
                    alert.setContentText("Please enter a title.");
                    alert.showAndWait();
                    break;

                case 9:
                    alert.setTitle("Description field is empty.");
                    alert.setContentText("Please enter a description.");
                    alert.showAndWait();
                    break;

                case 10:
                    alert.setTitle("Zone field is empty.");
                    alert.setContentText("Please enter a location.");
                    alert.showAndWait();
                    break;

                case 11:
                    alert.setTitle("Type field is empty.");
                    alert.setContentText("Please enter a type.");
                    alert.showAndWait();
                    break;

                case 12:
                    alert.setTitle("Appointment was not selected");
                    alert.setContentText("Please selected an appointment.");
                    alert.showAndWait();
                    break;

                case 13:
                    alert.setTitle("The division/country field is empty.");
                    alert.setContentText("Please enter division/country..");
                    alert.showAndWait();
                    break;

                case 14:
                    alert.setTitle("Customer name field is empty.");
                    alert.setContentText("Please enter a customer name. The customer name field cannot be blank.");
                    alert.showAndWait();
                    break;

                case 15:
                    alert.setTitle("Customer address field is empty.");
                    alert.setContentText("Please enter a valid customer address.");
                    alert.showAndWait();
                    break;

                case 16:
                    alert.setTitle("Customer zip code is empty.");
                    alert.setContentText("Please enter customer zipcode.");
                    alert.showAndWait();
                    break;

                case 17:
                    alert.setTitle("Chosen appointment overlaps with other appointment.");
                    alert.setContentText("Please selected an appointment that does not overlap with any of your other selected appointments.");
                    alert.showAndWait();
                    break;

                case 18:
                    alert.setTitle("Start time field is empty.");
                    alert.setContentText("Please selected a start time for the appointment.");
                    alert.showAndWait();
                    break;

                case 19:
                    alert.setTitle("Valid start date field is empty.");
                    alert.setContentText("Please select a date for the appointment.");
                    alert.showAndWait();
                    break;

                case 20:
                    alert.setTitle("End date field is empty.");
                    alert.setContentText("Please select a date for the appointment.");
                    alert.showAndWait();
                    break;

                case 21:
                    alert.setTitle("Customer field is empty.");
                    alert.setContentText("Please select a customer.");
                    alert.showAndWait();
                    break;

                case 22:
                alert.setTitle("End time field is empty.");
                alert.setContentText("Please select an end time for the appointment.");
                alert.showAndWait();
                break;

                case 23:
                    alert.setTitle("Please select a valid user");
                    alert.setContentText("Please select a valid user.");
                    alert.showAndWait();
                    break;

                case 24:
                    alert.setTitle("Phone number field is empty.");
                    alert.setContentText("Please enter phone number.");
                    alert.showAndWait();
                    break;

                case 25:
                    alert.setTitle("Contact field is empty.");
                    alert.setContentText("Please select a contact.");
                    alert.showAndWait();
                    break;

                case 26:
                    alert.setTitle("Location field is empty.");
                    alert.setContentText("Please select/enter a location.");
                    alert.showAndWait();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + whichError);
            }
        }


        public static void confirmation(int confirm) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            switch (confirm) {

                case 1:
                    alert.setTitle(langBundle.getString("Alert"));
                    alert.setContentText(langBundle.getString("There are no appointments available during the next 15 minutes."));
                    alert.showAndWait();
                    break;

                case 2:
                    alert.setTitle("Customer addition confirmation");
                    alert.setHeaderText("Success");
                    alert.setContentText("The customer has been added successfully. ");
                    alert.showAndWait();
                    break;

                case 3:
                    alert.setTitle("Appointment has been successfully added");
                    alert.setHeaderText("Success");
                    alert.setContentText("Appointment has been successfully added. ");
                    alert.showAndWait();
                    break;

                case 4:
                    alert.setTitle("Appointment modification confirmation.");
                    alert.setHeaderText("Success");
                    alert.setContentText("The appointment has been modified successfully. ");
                    alert.showAndWait();
                    break;

                case 5:
                    alert.setTitle("Customer removal confirmation");
                    alert.setHeaderText("Success");
                    alert.setContentText("The customer has been deleted successfully. ");
                    alert.showAndWait();
                    break;
            }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }
    }

