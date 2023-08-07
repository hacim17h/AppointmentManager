package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditAppointmentController {

    @FXML
    private Label addAppointmentErrorLbl;

    @FXML
    private Button editAppointmentCancelBtn;

    @FXML
    private ComboBox<?> editAppointmentContactCombo;

    @FXML
    private ComboBox<?> editAppointmentCustomerIDCombo;

    @FXML
    private DatePicker editAppointmentDate;

    @FXML
    private TextField editAppointmentDescriptionTxt;

    @FXML
    private ComboBox<?> editAppointmentEndCombo;

    @FXML
    private TextField editAppointmentIDTxt;

    @FXML
    private TextField editAppointmentLocationTxt;

    @FXML
    private Button editAppointmentSaveBtn;

    @FXML
    private ComboBox<?> editAppointmentStartCombo;

    @FXML
    private TextField editAppointmentTitleTxt;

    @FXML
    private ComboBox<?> editAppointmentTypeCombo;

    @FXML
    private ComboBox<?> editAppointmentUserIDCombo;

}


