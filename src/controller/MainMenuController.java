package controller;

import DAO.AppointmentsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;
import model.Appointments;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        if(!Main.loginSuccess)
        {
            LocalDateTime now = LocalDateTime.now();
            ObservableList<Appointments> appointments = FXCollections.observableArrayList();
            ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();
            appointments.addAll(AppointmentsDAO.selectAll());

            //If there are appointments upcoming in 15 minutes store them and then show an alert with the details.
            for (Appointments appointment : appointments) {
                LocalDateTime localStartTime = appointment.getStartTime().toLocalDateTime();
                if ((localStartTime.isEqual(now) || localStartTime.isAfter(now)) &&
                        (localStartTime.isEqual(now.plusMinutes(15)) || localStartTime.isBefore(now.plusMinutes(15)))) {
                    upcomingAppointments.add(appointment);
                }
            }
            if (!(upcomingAppointments.isEmpty())) {
                for (Appointments upcoming : upcomingAppointments) {
                    Alert notification = new Alert(Alert.AlertType.INFORMATION);
                    notification.setTitle("Upcoming Appointment");
                    notification.setHeaderText(null);
                    notification.setContentText("You have an upcoming appointment. Here are the details: \n" +
                            upcoming.toString() + ".");
                    notification.showAndWait();
                }
            } else {
                Alert notification = new Alert(Alert.AlertType.INFORMATION);
                notification.setTitle("Upcoming Appointment");
                notification.setHeaderText(null);
                notification.setContentText("You have no upcoming appointments.");
                notification.showAndWait();
            }
        }
    }
    /**
     * A special method that displays the initial values. The table views are populated with the customer information
     * from the database and the columns values are set properly.
     */
    public void initialize(){
        loginAlert();
        Main.loginSuccess = true;
    }

}
