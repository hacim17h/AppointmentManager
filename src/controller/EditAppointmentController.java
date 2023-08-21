package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
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
import model.Contacts;
import model.Customers;
import model.Users;

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
    private ComboBox<Contacts> editAppointmentContactCombo;

    @FXML
    private ComboBox<Customers> editAppointmentCustomerIDCombo;

    @FXML
    private ComboBox<Users> editAppointmentUserIDCombo;

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



    /**
     * Sets the appointment start and end times properly. When a date is selected, the appointment start and end
     * combo boxes are populated with correct times and dates based upon the business hours and the users local time
     * zone.
     */
    @FXML
    void onActionSelectDate() {
        date = editAppointmentDate.getValue();
        appointmentHours = FXCollections.observableArrayList();
        displayTime = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(0,0);
        LocalDateTime startTime = LocalDateTime.of(date, start);
        LocalDateTime endTime = LocalDateTime.of(date, start).plusDays(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");

        //Populates the combo boxes with start times incremented by one hour.
        while (startTime.isBefore(endTime)){
            appointmentHours.add(startTime);
            displayTime.add(startTime.format(format));
            startTime = startTime.plusHours(1);
        }
        appointmentHours.add(endTime);
        displayTime.add(endTime.format(format));
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
     * Prefills the edit appointment form. The method takes passed appointment information from the previous form.
     * Depending upon which appointment was selected, the options are prefilled and all text fields and combo boxes are
     * set appropriately.
     * @param selectedAppointment the appointment that was selected in the view appointments table
     */
    void prefillData(Appointments selectedAppointment){
        //Initializes text fields.
        editAppointmentIDTxt.setText(String.valueOf(selectedAppointment.getId()));
        editAppointmentTitleTxt.setText(selectedAppointment.getTitle());
        editAppointmentDescriptionTxt.setText(selectedAppointment.getDescription());
        editAppointmentLocationTxt.setText(selectedAppointment.getLocation());
        editAppointmentTypeTxt.setText(selectedAppointment.getType());
        editAppointmentDate.setValue(selectedAppointment.getStartTime().toLocalDateTime().toLocalDate());

        //Populates time combo boxes.
        onActionSelectDate();
        int startIndex = 0;
        for(LocalDateTime hours : appointmentHours){
            if(hours.isEqual(selectedAppointment.getStartTime().toLocalDateTime())){
                break;
            }
            startIndex++;
        }
        editAppointmentStartCombo.getSelectionModel().select(startIndex);
        onActionSelectStart();

        int endIndex = 0;
        for(LocalDateTime hours : appointmentEndHours){
            if(hours.isEqual(selectedAppointment.getEndTime().toLocalDateTime())){
                break;
            }
            endIndex++;
        }
        editAppointmentEndCombo.getSelectionModel().select(endIndex);

        //Populates and selects the remaining combo boxes.
        editAppointmentUserIDCombo.setItems(UsersDAO.selectAllUsers());
        editAppointmentUserIDCombo.getSelectionModel().select(
                UsersDAO.selectUsersById(selectedAppointment.getUserId()));
        editAppointmentCustomerIDCombo.setItems(CustomersDAO.selectAll());
        editAppointmentCustomerIDCombo.getSelectionModel().select(
                CustomersDAO.selectCustomersById(selectedAppointment.getCustomerId()));
        editAppointmentContactCombo.setItems(ContactsDAO.selectAllContacts());
        editAppointmentContactCombo.getSelectionModel().select(
                ContactsDAO.selectContactsById(selectedAppointment.getContactId()));
    }
}


