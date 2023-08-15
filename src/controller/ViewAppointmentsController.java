package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Button appointmentAddButton;

    @FXML
    private RadioButton appointmentAllRButton;

    @FXML
    private TableColumn<?, ?> appointmentContactCol;

    @FXML
    private TableColumn<?, ?> appointmentCustomerIDCol;

    @FXML
    private Button appointmentDeleteButton;

    @FXML
    private TableColumn<?, ?> appointmentDescriptionCol;

    @FXML
    private Button appointmentEditButton;

    @FXML
    private TableColumn<?, ?> appointmentEndCol;

    @FXML
    private ToggleGroup appointmentGroup;

    @FXML
    private TableColumn<?, ?> appointmentIDCol;

    @FXML
    private TableColumn<?, ?> appointmentLocationCol;

    @FXML
    private Button appointmentMainMenu;

    @FXML
    private RadioButton appointmentMonthRButton;

    @FXML
    private TableColumn<?, ?> appointmentStartCol;

    @FXML
    private TableColumn<?, ?> appointmentTitleCol;

    @FXML
    private TableColumn<?, ?> appointmentTypeCol;

    @FXML
    private TableColumn<?, ?> appointmentUserIDCol;

    @FXML
    private RadioButton appointmentWeekRButton;


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
