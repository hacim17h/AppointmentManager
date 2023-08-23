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
import javafx.stage.Stage;
import model.Countries;
import model.Customers;

import java.io.IOException;

public class TotalsByCountryReportController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    /**
     * Stores all the countries.
     */
    ObservableList<Countries> countries = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Countries> byCountryCombo;

    @FXML
    private Label byCountryResultLbl;

    /**
     * Generates a report when the generate button is clicked. The method checks if there is selections made, and if
     * there are selections, a report with the user data requested is displayed.
     */
    @FXML
    void onActionGenerateReport() {
        if(!(byCountryCombo.getValue() == null)){
            ObservableList<Customers> customers = FXCollections.observableArrayList();
            customers.addAll(CustomersDAO.selectAll());
            Countries selectedCountry = byCountryCombo.getValue();
            int total = 0;
            for (Customers customer : customers){
                if(LocationDAO.getDivisionCountry(customer.getDivisionId()) == selectedCountry.getId()){
                    total++;
                }
            }
            byCountryResultLbl.setText(byCountryCombo.getValue().getName() + " - " + total);
        }
    }

    /**
     * Returns to the main menu. When the button is pressed, the form returns to the previous main menu.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * A special method that displays the initial values. The combo boxes for the countries are initialized providing
     * starting values for selection.
     */
    public void initialize(){
        countries.addAll(LocationDAO.selectAllCountries());
        byCountryCombo.setItems(countries);
    }

}

