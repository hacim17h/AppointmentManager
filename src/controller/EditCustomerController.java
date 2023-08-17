package controller;

import DAO.CustomersDAO;
import DAO.LocationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Countries;
import model.Customers;

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

    /**
     * Stores the country data from the database.
     */
    ObservableList<Countries> countries = FXCollections.observableArrayList();

    /**
     * Stores a list of the country names.
     */
    ObservableList<String> countryNames = FXCollections.observableArrayList();

    /**
     * Stores a list of the first-level division ids.
     */
    ObservableList<Integer> divisionIds = FXCollections.observableArrayList();

    @FXML
    private TextField editCustomerAddressTxt;

    @FXML
    private Button editCustomerCancelBtn;

    @FXML
    private ComboBox<String> editCustomerCountryCombo;

    @FXML
    private ComboBox<String> editCustomerDivisionCombo;

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
     * If it is invalid data, it will not save and instead not leave the screen until valid data is entered.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        if(isValidInput()){
            int rowsAdded = CustomersDAO.update(Integer.parseInt(editCustomerIDTxt.getText()),
                    editCustomerNameTxt.getText(), editCustomerAddressTxt.getText(),
                    editCustomerPostalTxt.getText(),editCustomerPhoneTxt.getText(),
                    divisionIds.get(editCustomerDivisionCombo.getSelectionModel().getSelectedIndex()));
            if (rowsAdded > 0){
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                editCustomerErrorLbl.setText("The customer has failed to be added. Please try again.");
            }

        }
        else {
            editCustomerErrorLbl.setText("One or more of the fields have been left blank.");
        }
    }

    /**
     * Prefills the edit customer form. The method takes passed customer information from the previous form. Depending
     * upon which customer was selected, the options are prefilled and all text fields and combo boxes are set
     * appropriately.
     * @param selectedCustomer the customer that was selected in the view customers table
     */
    void prefillData(Customers selectedCustomer){
        editCustomerIDTxt.setText(String.valueOf(selectedCustomer.getId()));
        editCustomerNameTxt.setText(selectedCustomer.getName());
        editCustomerAddressTxt.setText(selectedCustomer.getAddress());
        editCustomerPostalTxt.setText(selectedCustomer.getPostalCode());
        editCustomerPhoneTxt.setText(selectedCustomer.getPhoneNum());
        int countryIndex = 0;
        int divisionIndex = 0;
        for (int i = 0; i < countries.size(); i++){
            for(int j = 0; j < countries.get(i).getDivisions().size(); j++){
                if(countries.get(i).getDivisions().get(j).getId() == selectedCustomer.getDivisionId()){
                    divisionIndex = j;
                    countryIndex = i;
                }
            }
        }
        editCustomerCountryCombo.getSelectionModel().select(countryIndex);
        onActionSelectCountry();
        editCustomerDivisionCombo.getSelectionModel().select(divisionIndex);
    }

    /**
     * Enables the first-level division combo box upon selecting the country. When the country is selected, the
     * first-level division combo box is enabled and the list is populated with the names of all the divisions
     * tied to the country selected.
     */
    @FXML
    void onActionSelectCountry() {
        String comboValue = editCustomerCountryCombo.getValue();
        divisionIds.clear();
        for (Countries country : countries){
            if(country.getName().equals(comboValue)){
                editCustomerDivisionCombo.setDisable(false);
                editCustomerDivisionCombo.setItems(country.getDivisionNames());
                divisionIds.addAll(country.getDivisionIds());
                break;
            }
        }
    }

    /**
     * Checks for valid input in the fields of the add customer form. If the fields of the add customer form are blank,
     * the method return false which indicates the input is invalid. If everything is filled in however it will return
     * true.
     * @return true if valid and false if not
     */
    @FXML
    Boolean isValidInput(){
        return !editCustomerAddressTxt.getText().isBlank() && !editCustomerNameTxt.getText().isBlank() &&
                !editCustomerPhoneTxt.getText().isBlank() && !editCustomerPostalTxt.getText().isBlank() &&
                !(editCustomerCountryCombo.getValue() == null) && !(editCustomerDivisionCombo.getValue() == null);
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
        editCustomerCountryCombo.setItems(countryNames);
    }
}
