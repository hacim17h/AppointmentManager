package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddAppointmentController {

    @FXML
    private Button addAppointmentCancelBtn;

    @FXML
    private ComboBox<?> addAppointmentContactCombo;

    @FXML
    private ComboBox<?> addAppointmentCustomerIDCombo;

    @FXML
    private DatePicker addAppointmentDate;

    @FXML
    private TextField addAppointmentDescriptionTxt;

    @FXML
    private ComboBox<?> addAppointmentEndCombo;

    @FXML
    private Label addAppointmentErrorLbl;

    @FXML
    private TextField addAppointmentIDTxt;

    @FXML
    private TextField addAppointmentLocationTxt;

    @FXML
    private Button addAppointmentSaveBtn;

    @FXML
    private ComboBox<?> addAppointmentStartCombo;

    @FXML
    private TextField addAppointmentTitleTxt;

    @FXML
    private ComboBox<?> addAppointmentTypeCombo;

    @FXML
    private ComboBox<?> addAppointmentUserIDCombo;

}

