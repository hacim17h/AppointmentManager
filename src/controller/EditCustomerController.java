package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditCustomerController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

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

    /**
     * Returns to the view customer form. When the button, is pressed the returns to the previous view customer form.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Returns to the view customer form after saving input. When the button is pressed, the form returns to the
     * previous view customer form and saves the input data to the database as well as displaying it to the tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
