package controller;

import DAO.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ViewAppointmentsController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    @FXML
    private TableView<Appointments> appointmentTableView;

    @FXML
    private TableColumn<Appointments, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointments, Integer> appointmentCustomerIDCol;

    @FXML
    private TableColumn<Appointments, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointments, Timestamp> appointmentStartCol;

    @FXML
    private TableColumn<Appointments, Timestamp> appointmentEndCol;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointments, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointments, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointments, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointments, Integer> appointmentUserIDCol;

    @FXML
    private ToggleGroup appointmentGroup;

    @FXML
    private RadioButton appointmentAllRButton;

    @FXML
    private RadioButton appointmentWeekRButton;

    @FXML
    private RadioButton appointmentMonthRButton;

    @FXML
    private Button appointmentDeleteButton;

    @FXML
    private Button appointmentEditButton;

    @FXML
    private Button appointmentMainMenu;

    @FXML
    private Button appointmentAddButton;

    /**
     * Displays the add appointment form. When the button is pressed, the scene is changed to the
     * AddAppointmentForm.fxml which allows the user to add appointment details to be inserted into the database and
     * tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the edit appointment form. When the button is pressed, the scene is changed to the
     * EditAppointmentForm.fxml which allows the user to edit appointment details that have been retrieved from the
     * database. Once the information is changed the data is updated in the database and the tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionEditAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/EditAppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes the selected appointment. When the button is pressed, the selected appointment is removed from the
     * tableview as well as the database after confirming with the user that they are sure of their actions.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        if (appointmentTableView.getSelectionModel().getSelectedItem() != null){
            Alert deleteWarning = new Alert(Alert.AlertType.WARNING);
            deleteWarning.setTitle("Confirmation");
            deleteWarning.setHeaderText(null);
            deleteWarning.setContentText("Are you sure you want to delete the appointment?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            deleteWarning.getButtonTypes().setAll(yesButton, noButton);
            deleteWarning.showAndWait();
            if (deleteWarning.getResult() == yesButton){
                Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
                AppointmentsDAO.delete(selectedAppointment.getId());
                appointmentTableView.setItems(AppointmentsDAO.selectAll());
                appointmentTableView.refresh();
            }
        }
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

    /*
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param type appointment type
     * @param location appointment location
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerId customer id associated with the appointment
     * @param userId user id associated with the appointment
     * @param contactId contact id associated with the appointment*/
    /**
     * A special method that displays the initial values. The table views are populated with the appointment information
     * from the database and the columns values are set properly.
     */
    public void initialize(){

        appointmentTableView.setItems(AppointmentsDAO.selectAll());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("localStartTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("localEndTime"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }
}
