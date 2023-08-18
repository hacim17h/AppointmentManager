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
import java.time.format.DateTimeFormatter;

public class AddAppointmentController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    /**
     * Stores the date currently on the date picker.
     */
    LocalDate date;

    /**
     * Stores the hours of operation for the business in local time.
     */
    ObservableList<LocalDateTime> appointmentHours = FXCollections.observableArrayList();

    /**
     * Stores the converted date time into a more readable string format.
     */
    ObservableList<String> displayTime = FXCollections.observableArrayList();

    @FXML
    private Button addAppointmentSaveBtn;

    @FXML
    private Button addAppointmentCancelBtn;

    @FXML
    private TextField addAppointmentDescriptionTxt;

    @FXML
    private DatePicker addAppointmentDate;

    @FXML
    private ComboBox<String> addAppointmentStartCombo;

    @FXML
    private ComboBox<String> addAppointmentEndCombo;

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
     * Sets the appointment start and end times properly. When a date is selected, the appointment start and end
     * combo boxes are populated with correct times and dates based upon the buisness hours and the users local time
     * zone.
     */
    @FXML
    void onActionSelectDate() {
        date = addAppointmentDate.getValue();
        appointmentHours = FXCollections.observableArrayList();
        displayTime = FXCollections.observableArrayList();
        ZoneId local = ZoneId.systemDefault();
        ZoneId utc = ZoneId.of("UTC");
        ZoneId eastern = ZoneId.of("America/New_York");
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);
        LocalDateTime startTime = LocalDateTime.of(date, start);
        LocalDateTime endTime = LocalDateTime.of(date, end);
        ZonedDateTime estStartTime = ZonedDateTime.of(startTime, eastern);
        ZonedDateTime estEndTime = ZonedDateTime.of(endTime, eastern);
        ZonedDateTime utcStartTime = ZonedDateTime.ofInstant(estStartTime.toInstant(), utc);
        ZonedDateTime utcEndTime = ZonedDateTime.ofInstant(estEndTime.toInstant(), utc);
        ZonedDateTime convertedStartTime = ZonedDateTime.ofInstant(utcStartTime.toInstant(), local);
        ZonedDateTime convertedEndTime = ZonedDateTime.ofInstant(utcEndTime.toInstant(), local);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");


        while (convertedStartTime.isBefore(convertedEndTime)){
            displayTime.add(convertedStartTime.toLocalDateTime().format(format));
            appointmentHours.add(convertedStartTime.toLocalDateTime());
            convertedStartTime = convertedStartTime.plusHours(1);
        }
        displayTime.add(convertedEndTime.toLocalDateTime().format(format));
        appointmentHours.add(convertedEndTime.toLocalDateTime());
        addAppointmentEndCombo.setItems(displayTime);

        //Removes the last element for the start time since the appointment cant start at business close.
        ObservableList<String> startTimes = FXCollections.observableArrayList(
                                            displayTime.subList(0, displayTime.size()-1));
        addAppointmentStartCombo.setItems(startTimes);
    }

    /**
     * Sets the appointment end time based upon the start time. When the start time is selected, the end time
     * dynamically changes to ensure the user cannot choose an appointment time in the past. This serves as input
     * validation to also ensure the appointments are within business hours.
     */
    @FXML
    void onActionSelectStart() {
        int startIndex = addAppointmentStartCombo.getSelectionModel().getSelectedIndex() + 1;
        addAppointmentEndCombo.setDisable(false);
        ObservableList<String> endTimes = FXCollections.observableArrayList(
                        displayTime.subList(startIndex, displayTime.size()));
        addAppointmentEndCombo.setItems(endTimes);
    }

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
    public void initialize() {
        addAppointmentDate.setValue(LocalDate.now());
        onActionSelectDate();
    }
}

