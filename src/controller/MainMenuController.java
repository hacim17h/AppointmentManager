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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenuController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    /**
     * Exits the program. When the button, is pressed the program closes.
     */
    @FXML
    public void onActionExit(){
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

    /**
     * Notifies the user of any upcoming appointments. Gives a notification if there is an appointment within 15
     * minutes of the time they have logged in. This is based upon local time.
     */
    void loginAlert(){
        if(!Main.loginSuccess)
        {
            ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
            ZoneId local = ZoneId.systemDefault();
            ZonedDateTime zonedTimeNow = ZonedDateTime.of(LocalDateTime.now(), local);

            ObservableList<Appointments> appointments = FXCollections.observableArrayList();
            ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();
            appointments.addAll(AppointmentsDAO.selectAll());

            //If there are appointments upcoming in 15 minutes store them and then show an alert with the details.
            for (Appointments appointment : appointments) {
                LocalDateTime localStartTime = appointment.getStartTime().toLocalDateTime();
                ZonedDateTime zonedStartTime =  ZonedDateTime.of(localStartTime, local);
                if (zonedTimeNow.isEqual(zonedStartTime.minusMinutes(15)) ||
                    (zonedTimeNow.isAfter(zonedStartTime.minusMinutes(15)) && zonedTimeNow.isBefore(zonedStartTime))){
                    upcomingAppointments.add(appointment);
                }
            }
            if (!(upcomingAppointments.isEmpty())) {
                for (Appointments upcoming : upcomingAppointments) {
                    Alert notification = new Alert(Alert.AlertType.INFORMATION);
                    notification.setTitle(rb.getString("Upcoming Appointment"));
                    notification.setHeaderText(null);
                    notification.setContentText(rb.getString(
                            "You have an upcoming appointment. Here are the details") + ": \n" +
                            upcoming.toString() + ".");
                    notification.showAndWait();
                }
            } else {
                Alert notification = new Alert(Alert.AlertType.INFORMATION);
                notification.setTitle(rb.getString("Upcoming Appointment"));
                notification.setHeaderText(null);
                notification.setContentText(rb.getString("You have no upcoming appointments."));
                notification.showAndWait();
            }
        }
    }
    /**
     * A special method that initializes starting values. When the method is called, the login alert activates and
     * mentions if any appointments are upcoming in addition to reporting the login was successful.
     */
    public void initialize(){
        loginAlert();
        Main.loginSuccess = true;
    }

}
