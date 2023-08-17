package controller;

import DAO.CustomersDAO;
import DAO.LocationDAO;
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

    /**
     * Stores the country data from the database.
     */
    ObservableList<Countries>  countries = FXCollections.observableArrayList();

    /**
     * Stores a list of the country names.
     */
    ObservableList<String> countryNames = FXCollections.observableArrayList();

    /**
     * Stores a list of the first-level division ids.
     */
    ObservableList<Integer> divisionIds = FXCollections.observableArrayList();

    @FXML
    private TextField addCustomerAddressTxt;

    @FXML
    private Button addCustomerCancelBtn;

    @FXML
    private ComboBox<String> addCustomerCountryCombo;

    @FXML
    private ComboBox<String> addCustomerDivisionCombo;

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
        if(isValidInput()){
            int rowsAdded = CustomersDAO.insert(addCustomerNameTxt.getText(), addCustomerAddressTxt.getText(),
                    addCustomerPostalTxt.getText(),addCustomerPhoneTxt.getText(),
                    divisionIds.get(addCustomerDivisionCombo.getSelectionModel().getSelectedIndex()));
            if (rowsAdded > 0){
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                addCustomerErrorLbl.setText("The customer has failed to be added. Please try again.");
            }

        }
        else {
            addCustomerErrorLbl.setText("One or more of the fields have been left blank.");
        }

    }

    /**
     * Enables the first-level division combo box upon selecting the country. When the country is selected, the
     * first-level division combo box is enabled and the list is populated with the names of all the divisions
     * tied to the country selected.
     */
    @FXML
    void onActionSelectCountry() {
        String comboValue = addCustomerCountryCombo.getValue();
        for (Countries country : countries){
            if(country.getName().equals(comboValue)){
                addCustomerDivisionCombo.setDisable(false);
                addCustomerDivisionCombo.setItems(country.getDivisionNames());
                divisionIds.addAll(country.getDivisionIds());
                break;
            }
        }
    }

    /**
     * Returns the division id of the selected division. When the combo box choice is selected, it stores the division
     * keeps track of which division id was selected.
     */
    @FXML
    void onActionSelectDivision() {

    }

    /**
     * Checks for valid input in the fields of the add customer form. If the fields of the add customer form are blank,
     * the method return false which indicates the input is invalid. If everything is filled in however it will return
     * true.
     * @return true if valid and false if not
     */
    @FXML
    Boolean isValidInput(){
        return !addCustomerAddressTxt.getText().isBlank() && !addCustomerNameTxt.getText().isBlank() &&
                !addCustomerPhoneTxt.getText().isBlank() && !addCustomerPostalTxt.getText().isBlank() &&
                !(addCustomerCountryCombo.getValue() == null) && !(addCustomerDivisionCombo.getValue() == null);
    }

    /**
     * A special method that displays the initial values. The combo boxes are appropriately set with the proper
     * country and first-level division data from the database upon selection.
     */
    public void initialize(){
        countries = FXCollections.observableArrayList();
        countries.addAll(LocationDAO.selectAllCountries());
        for (Countries country : countries){
            countryNames.add(country.getName());
        }
        addCustomerCountryCombo.setItems(countryNames);
    }

}
