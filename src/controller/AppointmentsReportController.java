package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentsReportController  {

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

}
