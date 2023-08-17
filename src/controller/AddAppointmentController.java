package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.*;

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

    /**
     * A special method that displays the initial values. Time combo boxes are populated with valid times that adjust
     * based upon the local time of the users computer to stay within business hours.
     */
    //8:00 a.m. to 10:00 p.m. ET
    public void initialize(){
        addAppointmentDate.setValue(LocalDate.now());
        LocalDate date = addAppointmentDate.getValue();
        ObservableList<LocalTime> appointmentHours = FXCollections.observableArrayList();
        ZoneId local = ZoneId.systemDefault();
        ZoneId utc = ZoneId.of("UTC");
        ZoneId eastern = ZoneId.of("America/New_York");
        LocalTime start = LocalTime.of(4,0);
        LocalTime end = LocalTime.of(18,0);
        LocalDateTime startTime = LocalDateTime.of(date, start);
        LocalDateTime endTime = LocalDateTime.of(date, end);
        ZonedDateTime localStartTime = ZonedDateTime.of(startTime, local);
        ZonedDateTime localEndTime = ZonedDateTime.of(endTime, local);
        ZonedDateTime convertedStartTime = ZonedDateTime.ofInstant(localStartTime.toInstant(), utc);
        ZonedDateTime convertedEndTime = ZonedDateTime.ofInstant(localEndTime.toInstant(), utc);
        System.out.println("My local time: " + localStartTime);
        System.out.println("My utc time: " + convertedStartTime);
        System.out.println("My local time: " + convertedStartTime.toLocalTime());
        /*while (start.isBefore(end)){
            appointmentHours.add(start);
            start = start.plusMinutes(30);
        }
        appointmentHours.add(end);*/

        while (convertedStartTime.isBefore(convertedEndTime)){
            appointmentHours.add(convertedStartTime.toLocalTime());
            convertedStartTime = convertedStartTime.plusMinutes(30);
        }
        appointmentHours.add(convertedEndTime.toLocalTime());

        addAppointmentStartCombo.setItems(appointmentHours);
        addAppointmentEndCombo.setItems(appointmentHours);
    }

}

