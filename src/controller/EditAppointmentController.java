package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import helper.TimeHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.sql.Timestamp;
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
    private Label editAppointmentErrorLbl;

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
        //If the input is valid the appointment data is inserted into the database and if not an error displays.

        if(isValidInput()){
            //Creates UTC timestamps after parsing the text from the appointment start and end combo boxes.
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
            LocalDateTime startTime =  LocalDateTime.parse(editAppointmentStartCombo.getValue(), format);
            LocalDateTime endTime = LocalDateTime.parse(editAppointmentEndCombo.getValue(), format);

            ZoneId local = ZoneId.systemDefault();
            ZonedDateTime zonedStart = ZonedDateTime.of(startTime, local);
            ZonedDateTime zonedEnd = ZonedDateTime.of(endTime, local);
            Timestamp utcStartTimestamp = Timestamp.from(zonedStart.toInstant());
            Timestamp utcEndTimestamp = Timestamp.from(zonedEnd.toInstant());

            //Checks to see if the time is within business hours. If true, then the appointment is added.
            //if not an error is displayed.
            if(TimeHelper.duringBusinessHours(utcStartTimestamp, utcEndTimestamp)){
                if(isValidAppointment()){
                    int rowsUpdated = AppointmentsDAO.update(editAppointmentTitleTxt.getText(),
                        editAppointmentDescriptionTxt.getText(), editAppointmentLocationTxt.getText(),
                        editAppointmentTypeTxt.getText(), utcStartTimestamp, utcEndTimestamp,
                        editAppointmentCustomerIDCombo.getValue().getId(),
                        editAppointmentUserIDCombo.getValue().getId(),
                        editAppointmentContactCombo.getValue().getId(),
                        Integer.parseInt(editAppointmentIDTxt.getText()));
                    if (rowsUpdated > 0){
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    else{
                        editAppointmentErrorLbl.setText("The appointment has failed to be updated. Please try again.");
                    }
                }
                else{
                    editAppointmentErrorLbl.setText("");
                    Alert failWarning = new Alert(Alert.AlertType.WARNING);
                    failWarning.setTitle("Unable to schedule appointment");
                    failWarning.setHeaderText(null);
                    failWarning.setContentText("The appointment time selected overlaps with an existing appointment. " +
                            "Please select a different date or time.");
                    failWarning.showAndWait();
                }
            }
            else {
                editAppointmentErrorLbl.setText("");
                Alert failWarning = new Alert(Alert.AlertType.WARNING);
                failWarning.setTitle("Unable to schedule appointment");
                failWarning.setHeaderText(null);
                failWarning.setContentText("The appointment time you've selected is not during business hours. " +
                        "Please select a different time between the hours of 8:00AM ET and 10:00PM ET.");
                failWarning.showAndWait();
            }
        }
        else {
            editAppointmentErrorLbl.setText("One or more of the fields have been left blank.");
        }
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

        //Populates start time and end time combo boxes and selects proper start time.
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

        //Selects proper end time.
        appointmentEndHours = FXCollections.observableArrayList(
                appointmentHours.subList(startIndex+1, appointmentHours.size()));
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

    /**
     * Checks for valid input in the fields of the add appointment form. If the fields of the add appointment form are
     * blank, the method return false which indicates the input is invalid. If everything is filled in however it will
     * return true.
     * @return true if valid and false if not
     */
    @FXML
    Boolean isValidInput(){
        return !editAppointmentTitleTxt.getText().isBlank() && !editAppointmentDescriptionTxt.getText().isBlank() &&
            !editAppointmentLocationTxt.getText().isBlank() && !editAppointmentTypeTxt.getText().isBlank() &&
            !(editAppointmentStartCombo.getValue() == null) && !(editAppointmentEndCombo.getValue() == null) &&
            !(editAppointmentContactCombo.getValue() == null) && !(editAppointmentCustomerIDCombo.getValue() == null) &&
            !(editAppointmentUserIDCombo.getValue() == null) && !(editAppointmentDate.getValue() == null);
    }

    /**
     * Checks if the appointment doesn't conflict with others. The method checks the database to see if there
     * are any appointments that match the contact or the customer and see if the time frame overlaps at all. If
     * it does not overlap it will return true as it is valid. But, if there is any time conflict it will return false
     * for invalid.
     * @return if the appointment is at a valid time or not
     */
    boolean isValidAppointment(){
        ObservableList<Appointments> customerAppointments = FXCollections.observableArrayList();
        customerAppointments.addAll(AppointmentsDAO.selectByCustomerId(
                editAppointmentCustomerIDCombo.getValue().getId()));
        boolean isValid = true;

        //Creates UTC timestamps after parsing the text from the appointment start and end combo boxes.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        LocalDateTime startTime =  LocalDateTime.parse(editAppointmentStartCombo.getValue(), format);
        LocalDateTime endTime = LocalDateTime.parse(editAppointmentEndCombo.getValue(), format);

        ZoneId local = ZoneId.systemDefault();
        ZonedDateTime zonedStart = ZonedDateTime.of(startTime, local);
        ZonedDateTime zonedEnd = ZonedDateTime.of(endTime, local);
        Timestamp utcStartTimestamp = Timestamp.from(zonedStart.toInstant());
        Timestamp utcEndTimestamp = Timestamp.from(zonedEnd.toInstant());

        //Removes the appointment that is currently being edited from being compared to avoid overlapping with itself.
        Appointments selectedAppointment = AppointmentsDAO.selectByAppointmentId(
                Integer.parseInt(editAppointmentIDTxt.getText()));
        int deleteIndex = 0;

        for (int i = 0; i < customerAppointments.size(); i++){
            if (customerAppointments.get(i).getId() == selectedAppointment.getId()){
                deleteIndex = i;
                break;
            }
        }
        customerAppointments.remove(deleteIndex);

        //Checks to see if there are any appointments for the specific customer that overlap.
        for (Appointments appointment : customerAppointments){
            if(TimeHelper.isOverlapping(appointment.getStartTime(), appointment.getEndTime(),
                    utcStartTimestamp, utcEndTimestamp)){
                isValid = false;
            }
        }
        return isValid;
    }
}


