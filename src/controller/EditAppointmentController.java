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
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class EditAppointmentController {
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

    /**
     * Stores the contact ids.
     */
    ObservableList<Integer> contactIds = FXCollections.observableArrayList();

    /**
     * Stores the valid end time for the appointments.
     */
    ObservableList<LocalDateTime> appointmentEndHours = FXCollections.observableArrayList();

    @FXML
    private Label addAppointmentErrorLbl;

    @FXML
    private Button editAppointmentCancelBtn;

    @FXML
    private ComboBox<String> editAppointmentContactCombo;

    @FXML
    private ComboBox<Integer> editAppointmentCustomerIDCombo;

    @FXML
    private DatePicker editAppointmentDate;

    @FXML
    private TextField editAppointmentDescriptionTxt;

    @FXML
    private ComboBox<String> editAppointmentEndCombo;

    @FXML
    private ComboBox<String> editAppointmentStartCombo;

    @FXML
    private TextField editAppointmentIDTxt;

    @FXML
    private TextField editAppointmentLocationTxt;

    @FXML
    private Button editAppointmentSaveBtn;



    @FXML
    private TextField editAppointmentTitleTxt;

    @FXML
    private TextField editAppointmentTypeTxt;

    @FXML
    private ComboBox<Integer> editAppointmentUserIDCombo;

    /**
     * Sets the appointment start and end times properly. When a date is selected, the appointment start and end
     * combo boxes are populated with correct times and dates based upon the buisness hours and the users local time
     * zone.
     */
    @FXML
    void onActionSelectDate() {
        date = editAppointmentDate.getValue();
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
        editAppointmentEndCombo.setItems(displayTime);

        //Removes the last element for the start time since the appointment cant start at business close.
        ObservableList<String> startTimes = FXCollections.observableArrayList(
                displayTime.subList(0, displayTime.size()-1));
        editAppointmentStartCombo.setItems(startTimes);
    }

    /**
     * Sets the appointment end time based upon the start time. When the start time is selected, the end time
     * dynamically changes to ensure the user cannot choose an appointment time in the past. This serves as input
     * validation to also ensure the appointments are within business hours.
     */
    @FXML
    void onActionSelectStart() {
        int startIndex = editAppointmentStartCombo.getSelectionModel().getSelectedIndex() + 1;
        editAppointmentEndCombo.setDisable(false);
        ObservableList<String> endTimes = FXCollections.observableArrayList(
                displayTime.subList(startIndex, displayTime.size()));
        //Creates a sublist of times which allows the proper time to be selected in the combo box when editing.
        appointmentEndHours = FXCollections.observableArrayList(
                appointmentHours.subList(startIndex, appointmentHours.size()));
        editAppointmentEndCombo.setItems(endTimes);
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
     * Prefills the edit appointment form. The method takes passed appointment information from the previous form. Depending
     * upon which appointment was selected, the options are prefilled and all text fields and combo boxes are set
     * appropriately.
     * @param selectedAppointment the appointment that was selected in the view appointments table
     */
    void prefillData(Appointments selectedAppointment){
        editAppointmentIDTxt.setText(String.valueOf(selectedAppointment.getId()));
        editAppointmentTitleTxt.setText(selectedAppointment.getTitle());
        editAppointmentDescriptionTxt.setText(selectedAppointment.getDescription());
        editAppointmentLocationTxt.setText(selectedAppointment.getLocation());
        editAppointmentTypeTxt.setText(selectedAppointment.getType());
        editAppointmentDate.setValue(selectedAppointment.getLocalStartTime().toLocalDateTime().toLocalDate());
        onActionSelectDate();
        int startIndex = 0;
        for(LocalDateTime hours : appointmentHours){
            if(hours.isEqual(selectedAppointment.getLocalStartTime().toLocalDateTime())){
                break;
            }
            startIndex++;
        }
        editAppointmentStartCombo.getSelectionModel().select(startIndex);
        onActionSelectStart();

        int endIndex = 0;
        for(LocalDateTime hours : appointmentEndHours){
            if(hours.isEqual(selectedAppointment.getLocalEndTime().toLocalDateTime())){
                System.out.println("Hours is: " + hours);
                System.out.println("Selected appointment time is: " + selectedAppointment.getLocalEndTime().toLocalDateTime());
                System.out.println("I made it to the if statement and endIndex is: " + endIndex);
                break;
            }
            System.out.println("I made it here and endIndex is: " + endIndex);
            endIndex++;
        }
        editAppointmentEndCombo.getSelectionModel().select(endIndex);

    }
}


