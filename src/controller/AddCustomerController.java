package controller;

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
import model.Divisions;

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
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Enables the first-level division combo box upon selecting the country. When the country is selected, the
     * first-level division combo box is enabled and the list is populated with the names of all the divisions
     * tied to the country selected.
     * @param event helps get the window that caused the event
     */
    @FXML
    void onActionSelectCountry(ActionEvent event) {
        String comboValue = addCustomerCountryCombo.getValue();
        for (Countries country : countries){
            if(country.getName().equals(comboValue)){
                addCustomerDivisionCombo.setDisable(false);
                addCustomerDivisionCombo.setItems(country.getDivisionNames());
                break;
            }
        }
    }

    /**
     * A special method that displays the initial values. The combo boxes are appropriately set with the proper
     * country and first-level division data from the database upon selection.
     */
    public void initialize(){
        countries = FXCollections.observableArrayList();
        countries.addAll(LocationDAO.selectAll());
        for (Countries country : countries){
            countryNames.add(country.getName());
        }
        addCustomerCountryCombo.setItems(countryNames);
    }

}
