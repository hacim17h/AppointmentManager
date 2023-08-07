package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;

public class ViewAppointmentsController {

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

}
