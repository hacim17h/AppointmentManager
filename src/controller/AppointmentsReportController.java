package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.sql.Timestamp;

public class AppointmentsReportController  {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    @FXML
    private TableView<Appointments> byContactTableView;

    @FXML
    private TableColumn<Appointments, Integer> byContactAppointmentIDCol;

    @FXML
    private TableColumn<Appointments, Integer> byContactCustomerIDCol;

    @FXML
    private TableColumn<Appointments, String> byContactDescriptionCol;

    @FXML
    private TableColumn<Appointments, Timestamp> byContactEndCol;

    @FXML
    private TableColumn<Appointments, Timestamp> byContactStartCol;

    @FXML
    private TableColumn<Appointments, String> byContactTitleCol;

    @FXML
    private TableColumn<Appointments, String> byContactTypeCol;

    @FXML
    private ComboBox<Contacts> byContactCombo;

    /**
     * Generates a report when the generate button is clicked. The method checks if there is selections made, and if
     * there are selections, a report with the user data requested is displayed.
     */
    @FXML
    void onActionGenerateReport() {
        if(!(byContactCombo.getValue() == null)){
            ObservableList<Appointments> appointments = FXCollections.observableArrayList();
            appointments.addAll(AppointmentsDAO.selectByContactId(byContactCombo.getValue().getId()));
            byContactTableView.setItems(appointments);
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
        scene = FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * A special method that displays the initial values. The table views are prepared to display appointment
     * information from the database and the columns values are set properly.
     */
    public void initialize(){
        byContactAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        byContactTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        byContactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        byContactTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        byContactStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        byContactEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        byContactCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        contacts.addAll(ContactsDAO.selectAllContacts());
        byContactCombo.setItems(contacts);
    }
}
