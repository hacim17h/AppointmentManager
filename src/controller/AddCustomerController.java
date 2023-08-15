package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;

import java.io.IOException;

public class AddCustomerController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    @FXML
    private TextField addCustomerAddressTxt;

    @FXML
    private Button addCustomerCancelBtn;

    @FXML
    private ComboBox<Countries> addCustomerCountryCombo;

    @FXML
    private ComboBox<?> addCustomerDivisionCombo;

    @FXML
    private Label addCustomerErrorLbl;

    @FXML
    private TextField addCustomerIDTxt;

    @FXML
    private TextField addCustomerNameTxt;

    @FXML
    private TextField addCustomerPhoneTxt;

    @FXML
    private TextField addCustomerPostalTxt;

    @FXML
    private Button addCustomerSaveBtn;

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
     * previous view customer form and saves the updated input data to the database as well as displaying it to the
     * tableview.
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

    /**
     * A special method that displays the initial values. The table views are populated with the customer information
     * from the database and the columns values are set properly.
     */
    public void initialize(){

    }

}
