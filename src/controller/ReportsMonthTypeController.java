package controller;

import dao.ReportsDAO;
import dao.ReportsDAOImpl;
import helper.JDBC;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import java.util.Optional;

public class ReportsMonthTypeController implements Initializable {

        private Stage stage;

        private Parent scene;

        public TableView reportsMonthTypeTableview;

        public TableColumn monthColumn;

        public TableColumn typeColumn;

        public TableColumn countColumn;

    /**
     * This method sends the user to the 'Country Reports screen.
     * @param actionEvent
     * @throws IOException
     */
    public void generateCountriesReportButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Country report button has been clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsCountries.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method sends the user to the 'Contacts Reports' screen.
     * @param actionEvent
     * @throws IOException
     */
    public void generateContactsReportButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Contact report button has been clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reportsContacts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the logout method. It allows the user to log out of and close the scheduler.
     * @param actionEvent
     */
    public void logoutButtonPressed(javafx.event.ActionEvent actionEvent) {
        System.out.println("Logout button pressed.");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Would you like to close the program?");
        alert.showAndWait();
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    /**
     * This method returns to user to the main appointments menu.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button clicked.");
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the initialize method for the 'Month Type Reports Controller'. It assignes titles to each of the columns.
     * It then opens up the mySQL database and uses the 'ReportsDAO' method to retrieve all reports and
     * populate the 'Month Type Table View'.
     * @param url
     * @param resourceBundle
     */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            System.out.println("The month type reports controller has been initialized.");
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
            JDBC.openConnection();
            ReportsDAO rDAO = new ReportsDAOImpl();
            reportsMonthTypeTableview.setItems(rDAO.getAllReports());
        }
    }

