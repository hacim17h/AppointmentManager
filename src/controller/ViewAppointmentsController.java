package controller;

import DAO.AppointmentsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;


import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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
    private TableColumn<Appointments, Contacts> appointmentContactCol;

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

        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment != null){
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("/view/EditAppointmentForm.fxml"));
            scene = sceneLoader.load();
            EditAppointmentController editController = sceneLoader.getController();
            editController.prefillData(selectedAppointment);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Removes the selected appointment. When the button is pressed, the selected appointment is removed from the
     * tableview as well as the database after confirming with the user that they are sure of their actions.
     */
    @FXML
    void onActionDeleteAppointment() {
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
                int rowsDeleted = AppointmentsDAO.delete(selectedAppointment.getId());
                appointmentTableView.setItems(AppointmentsDAO.selectAll());
                appointmentTableView.refresh();

                if(rowsDeleted > 0){
                    //Displays information pertaining to the deleted appointment upon success.
                    Alert notification = new Alert(Alert.AlertType.INFORMATION);
                    notification.setTitle("Delete successful");
                    notification.setHeaderText(null);
                    notification.setContentText("The Appointment with the ID " + selectedAppointment.getId() +
                            " of type " + selectedAppointment.getType() + " has been deleted.");
                    notification.showAndWait();
                }
                else {
                    //Displays information pertaining to the failed deletion of the appointment.
                    Alert notification = new Alert(Alert.AlertType.WARNING);
                    notification.setTitle("Delete unsuccessful");
                    notification.setHeaderText(null);
                    notification.setContentText("The Appointment with the ID " + selectedAppointment.getId() +
                            " of type " + selectedAppointment.getType() + " has not been deleted.");
                    notification.showAndWait();
                }
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

    /**
     * Displays all appointments. When the "All" radio button is pressed, the table updates to display all appointments.
     */
    @FXML
    void onActionSelectAll() {
        appointmentTableView.setItems(AppointmentsDAO.selectAll());
        appointmentTableView.refresh();
    }

    /**
     * Displays all appointments of the current week. When the "This Week" radio button is pressed,
     * the table updates to display the appointments of the current week.
     */
    @FXML
    void onActionSelectThisWeek() {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        appointments.addAll(AppointmentsDAO.selectAll());
        ObservableList<Appointments> appointmentsThisWeek = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        for (Appointments appointment : appointments){
            if(appointment.getStartTime().toLocalDateTime().isAfter(weekStart.atStartOfDay().minusNanos(1)) &&
               appointment.getStartTime().toLocalDateTime().isBefore(weekEnd.atStartOfDay())){
                appointmentsThisWeek.add(appointment);
            }
        }
        appointmentTableView.setItems(appointmentsThisWeek);
        appointmentTableView.refresh();
    }

    /**
     * Displays all appointments of the current month. When the "This Month" radio button is pressed,
     * the table updates to display the appointments of the current month.
     */
    @FXML
    void onActionSelectThisMonth() {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        appointments.addAll(AppointmentsDAO.selectAll());
        ObservableList<Appointments> appointmentsThisMonth = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());
        for (Appointments appointment : appointments){
            if(appointment.getStartTime().toLocalDateTime().isAfter(monthStart.atStartOfDay().minusNanos(1)) &&
                    appointment.getStartTime().toLocalDateTime().isBefore(monthEnd.atStartOfDay().plusDays(1))){
                appointmentsThisMonth.add(appointment);
            }
        }
        appointmentTableView.setItems(appointmentsThisMonth);
        appointmentTableView.refresh();
    }

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
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactDisplay"));
    }
}
