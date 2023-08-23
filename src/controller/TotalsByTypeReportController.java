package controller;

import DAO.AppointmentsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.time.Month;


public class TotalsByTypeReportController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;
    /**
     * Stores all appointments.
     */
    ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> byTypeCombo;

    @FXML
    private ComboBox<String> byTypeMonthCombo;

    @FXML
    private Label byTypeResultLbl;

    /**
     * Generates a report when the generate button is clicked. The method checks if there is selections made, and if
     * there are selections, a report with the user data requested is displayed.
     */
    @FXML
    void onActionGenerateReport() {
        //Preforms input validation for if the combo boxes are populated and then generates the report.
        if(!(byTypeMonthCombo.getValue() == null || byTypeCombo.getValue() == null)){
            int typeCount = 0;
            Month monthSelection = Month.valueOf(byTypeMonthCombo.getValue().toUpperCase());
            String typeSelection = byTypeCombo.getValue();

            for(Appointments appointment : appointments){
                if((appointment.getType().equals(typeSelection)) &&
                        appointment.getStartTime().toLocalDateTime().getMonth().equals(monthSelection)){
                    typeCount++;
                }
            }
            byTypeResultLbl.setText(byTypeMonthCombo.getValue() + " - " + typeSelection + " - " + typeCount);
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
     * A special method that displays the initial values. The combo boxes are populated with the type and month
     * information from the database.
     */
    public void initialize(){
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December");
        appointments.addAll(AppointmentsDAO.selectAll());
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("Planning Session", "De-Briefing", "Celebration", "Lunch", "Team Building",
                "Gaming Session", "Training");

        //Goes through the appointments and adds any new types that exist if they are new types.
        for(Appointments appointment : appointments){
            boolean exists = false;
            for (String type : types){
                if (appointment.getType().equals(type)) {
                    exists = true;
                    break;
                }
            }
            if (!exists){
                types.add(appointment.getType());
            }
        }
        byTypeCombo.setItems(types);
        byTypeMonthCombo.setItems(months);
    }
}
