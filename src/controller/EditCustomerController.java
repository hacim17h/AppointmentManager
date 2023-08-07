package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditCustomerController {

    @FXML
    private TextField editCustomerAddressTxt;

    @FXML
    private Button editCustomerCancelBtn;

    @FXML
    private ComboBox<?> editCustomerCountryCombo;

    @FXML
    private ComboBox<?> editCustomerDivisionCombo;

    @FXML
    private Label editCustomerErrorLbl;

    @FXML
    private TextField editCustomerIDTxt;

    @FXML
    private TextField editCustomerNameTxt;

    @FXML
    private TextField editCustomerPhoneTxt;

    @FXML
    private TextField editCustomerPostalTxt;

    @FXML
    private Button editCustomerSaveBtn;

}
