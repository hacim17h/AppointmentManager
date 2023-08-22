package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;
    @FXML
    private Button mainExit;

    @FXML
    private Button mainGenerateReports;

    @FXML
    private Button mainViewAppointments;

    @FXML
    private Button mainViewCustomers;

    /**
     * Exits the program. When the button, is pressed the program closes.
     */
    @FXML
    public void onActionExit(ActionEvent event){
        System.exit(0);
    }

    /**
     * Displays the generate report menu. When the button is pressed, the scene is changed to the ReportForm.fxml which
     * has options for various kinds of reports to generate.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionGenerateReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the view and manage appointments screen. When the button is pressed, the scene is changed to the
     * ViewAppointmentsForm.fxml which has options for various kinds of CRUD operations related to appointments.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the view and manage customers screen. When the button is pressed, the scene is changed to the
     * ViewCustomerForm.fxml which has options for various kinds of CRUD operations related to customers.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /*A custom message should be displayed in the user interface and include the appointment ID, date, and time.
     If the user does not have any appointments within 15 minutes of logging in, display a custom message in the user
     interface indicating there are no upcoming appointments.*/

    /**
     * Notifies the user of any upcoming appointments. Gives a notification if there is an appointment within 15
     * minutes of the time they have logged in. This is based upon local time.
     */
    void loginAlert(){

    }

}
