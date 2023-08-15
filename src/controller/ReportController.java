package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;
    @FXML
    private Button generateAppointmentListBtn;

    @FXML
    private Button generateByCountryBtn;

    @FXML
    private Button generateByTypeBtn;

    @FXML
    private Button generateMainMenuBtn;

    /**
     * Displays the appointment list report. When the button is pressed, the scene is changed to the
     * AppointmentsReportForm.fxml which displays all the appointments based upon the contact selected
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionGenerateAppointmentList(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Displays the appointment totals by country report. When the button is pressed, the scene is changed to the
     * TotalsByCountryReportForm.fxml which displays the total number of appointments based upon the country.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionGenerateByCountry(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalsByCountryReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the appointment totals by appointment. When the button is pressed, the scene is changed to the
     * TotalsByTypeReportForm.fxml which displays the total number of appointments based upon the appointment type
     * and the month selected.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionGenerateByType(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalsByTypeReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Returns to the main menu. When the button is pressed, the form returns to the previous main menu.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
