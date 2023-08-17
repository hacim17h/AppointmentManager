package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

public class AddAppointmentController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    @FXML
    private Button addAppointmentSaveBtn;

    @FXML
    private Button addAppointmentCancelBtn;

    @FXML
    private TextField addAppointmentDescriptionTxt;

    @FXML
    private DatePicker addAppointmentDate;

    @FXML
    private ComboBox<LocalTime> addAppointmentStartCombo;

    @FXML
    private ComboBox<LocalTime> addAppointmentEndCombo;

    @FXML
    private ComboBox<String> addAppointmentContactCombo;

    @FXML
    private ComboBox<Integer> addAppointmentCustomerIDCombo;

    @FXML
    private ComboBox<Integer> addAppointmentUserIDCombo;

    @FXML
    private TextField addAppointmentIDTxt;

    @FXML
    private TextField addAppointmentLocationTxt;

    @FXML
    private TextField addAppointmentTitleTxt;

    @FXML
    private TextField addAppointmentTypeTxt;

    @FXML
    private Label addAppointmentErrorLbl;

    /**
     * Returns to the view customer form. When the button, is pressed the returns to the previous view appointment form.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /* d.  Write code to implement input validation and logical error checks to prevent each of the following changes
           when adding or updating information; display a custom message specific for each error check in the user
           interface:
       •  scheduling an appointment outside business hours defined as 8:00 a.m. to 10:00 p.m. ET, including weekends
       •  scheduling overlapping appointments for customers
       •  entering an incorrect username and password*/

    /*Note: There are up to three time zones in effect. Coordinated Universal Time (UTC) is used for storing the time
     in the database, the user’s local time is used for display purposes, and Eastern Time (ET) is used for the
     company’s office hours. Local time will be checked against ET business hours before they are stored in the
     database as UTC.*/

    /**
     * Returns to the view appointment form after saving input. When the button is pressed, the form returns to the
     * previous view appointment form and saves the updated input data to the database as well as displaying it to the
     * tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}

