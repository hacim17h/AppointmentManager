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
import java.sql.Time;
import java.sql.Timestamp;
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

    /**
     * Stores the contact ids.
     */
    ObservableList<Integer> contactIds = FXCollections.observableArrayList();

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
        //If the input is valid the appointment data is inserted into the database and if not an error displays.
        if(isValidInput()){
            //Creates UTC timestamps after parsing the text from the appointment start and end combo boxes.
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(addAppointmentStartCombo.getValue(), format);
            LocalDateTime endTime = LocalDateTime.parse(addAppointmentEndCombo.getValue(), format);
            ZoneId local = ZoneId.systemDefault();
            ZoneId utc = ZoneId.of("UTC");
            ZonedDateTime zonedStart = ZonedDateTime.of(startTime, local);
            ZonedDateTime zonedEnd = ZonedDateTime.of(endTime, local);
            ZonedDateTime utcStartTime = ZonedDateTime.ofInstant(zonedStart.toInstant(), utc);
            ZonedDateTime utcEndTime = ZonedDateTime.ofInstant(zonedEnd.toInstant(), utc);
            Timestamp utcStartTimestamp = Timestamp.valueOf(utcStartTime.toLocalDateTime());
            Timestamp utcEndTimestamp = Timestamp.valueOf(utcEndTime.toLocalDateTime());

            if(isValidAppointment()){
                int rowsAdded = AppointmentsDAO.insert(addAppointmentTitleTxt.getText(),
                        addAppointmentDescriptionTxt.getText(), addAppointmentLocationTxt.getText(),
                        addAppointmentTypeTxt.getText(), utcStartTimestamp, utcEndTimestamp,
                        addAppointmentCustomerIDCombo.getValue(), addAppointmentUserIDCombo.getValue(),
                        contactIds.get(addAppointmentContactCombo.getSelectionModel().getSelectedIndex()));
                if (rowsAdded > 0){
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointmentsForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else{
                    addAppointmentErrorLbl.setText("The appointment has failed to be added. Please try again.");
                }
            }
            else{
                addAppointmentErrorLbl.setText("");
                Alert failWarning = new Alert(Alert.AlertType.WARNING);
                failWarning.setTitle("Unable to schedule appointment");
                failWarning.setHeaderText(null);
                failWarning.setContentText("The appointment time you've selected is unavailable. Please select " +
                                            "a different date or time.");
                failWarning.showAndWait();


            }
        }
        else {
            addAppointmentErrorLbl.setText("One or more of the fields have been left blank.");
        }
    }

    /**
     * Checks for valid input in the fields of the add appointment form. If the fields of the add appointment form are
     * blank, the method return false which indicates the input is invalid. If everything is filled in however it will
     * return true.
     * @return true if valid and false if not
     */
    @FXML
    Boolean isValidInput(){
        return !addAppointmentTitleTxt.getText().isBlank() && !addAppointmentDescriptionTxt.getText().isBlank() &&
             !addAppointmentLocationTxt.getText().isBlank() && !addAppointmentTypeTxt.getText().isBlank() &&
             !(addAppointmentStartCombo.getValue() == null) && !(addAppointmentEndCombo.getValue() == null) &&
             !(addAppointmentContactCombo.getValue() == null) && !(addAppointmentCustomerIDCombo.getValue() == null) &&
             !(addAppointmentUserIDCombo.getValue() == null) && !(addAppointmentDate.getValue() == null);
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
        customerAppointments.addAll(AppointmentsDAO.selectById(addAppointmentCustomerIDCombo.getValue()));
        boolean isValid = true;

        //Creates UTC timestamps after parsing the text from the appointment start and end combo boxes.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(addAppointmentStartCombo.getValue(), format);
        LocalDateTime endTime = LocalDateTime.parse(addAppointmentEndCombo.getValue(), format);
        ZoneId local = ZoneId.systemDefault();
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime zonedStart = ZonedDateTime.of(startTime, local);
        ZonedDateTime zonedEnd = ZonedDateTime.of(endTime, local);
        ZonedDateTime utcStartTime = ZonedDateTime.ofInstant(zonedStart.toInstant(), utc);
        ZonedDateTime utcEndTime = ZonedDateTime.ofInstant(zonedEnd.toInstant(), utc);
        Timestamp utcStartTimestamp = Timestamp.valueOf(utcStartTime.toLocalDateTime());
        Timestamp utcEndTimestamp = Timestamp.valueOf(utcEndTime.toLocalDateTime());

        for (Appointments appointment : customerAppointments){
            if(TimeHelper.isOverlapping(appointment.getStartTime(), appointment.getEndTime(),
                    utcStartTimestamp, utcEndTimestamp)){
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * A special method that displays the initial values. Time combo boxes are populated with valid times that adjust
     * based upon the local time of the users computer to stay within business hours. The contact name combo box is
     * also populated with the names of the contacts and the user ID and customer IDs are also populated.
     */
    public void initialize() {
        addAppointmentDate.setValue(LocalDate.now());
        onActionSelectDate();
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        ObservableList<Users> users = FXCollections.observableArrayList();
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        contacts.addAll(ContactsDAO.selectAllContacts());
        users.addAll(UsersDAO.selectAllUsers());
        customers.addAll(CustomersDAO.selectAll());
        ObservableList<Integer> userIds = FXCollections.observableArrayList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();

        for (Users user : users){
            userIds.add(user.getId());
        }
        for (Contacts contact : contacts){
            contactNames.add(contact.getName());
            contactIds.add(contact.getId());
        }
        for (Customers customer : customers){
            customerIds.add(customer.getId());
        }

        addAppointmentUserIDCombo.setItems(userIds);
        addAppointmentContactCombo.setItems(contactNames);
        addAppointmentCustomerIDCombo.setItems(customerIds);

    }
}

