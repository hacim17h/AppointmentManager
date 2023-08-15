package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TableColumn<?, ?> byContactAppointmentIDCol;

    @FXML
    private ComboBox<?> byContactCombo;

    @FXML
    private TableColumn<?, ?> byContactCustomerIDCol;

    @FXML
    private TableColumn<?, ?> byContactDescriptionCol;

    @FXML
    private TableColumn<?, ?> byContactEndCol;

    @FXML
    private Button byContactGenerateReportBtn;

    @FXML
    private Button byContactMainMenuBtn;

    @FXML
    private TableColumn<?, ?> byContactStartCol;

    @FXML
    private TableView<?> byContactTableView;

    @FXML
    private TableColumn<?, ?> byContactTitleCol;

    @FXML
    private TableColumn<?, ?> byContactTypeCol;

    @FXML
    void onActionGenerateReport(ActionEvent event) {

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

}
