package controller;

import DAO.CustomersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;

public class ViewCustomerController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;

    @FXML
    private Button customerAddButton;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    @FXML
    private TableColumn<Customers, Integer> customerCustomerIDCol;

    @FXML
    private TableColumn<Customers, String> customerCustomerNameCol;

    @FXML
    private TableColumn<Customers, Integer> customerDivisionCol;

    @FXML
    private TableColumn<Customers, String> customerPhoneCol;

    @FXML
    private TableColumn<Customers, String> customerPostalCodeCol;

    @FXML
    private Button customerEditButton;

    @FXML
    private Button customerMainMenu;

    @FXML
    private Button customerDeleteButton;

    /**
     * Displays the add customer form. When the button is pressed, the scene is changed to the
     * AddCustomerForm.fxml which allows the user to add customer details to be inserted into the database and
     * tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the edit customer form. When the button is pressed, the scene is changed to the
     * EditCustomerForm.fxml which allows the user to edit customer details that have been retrieved from the
     * database. Once the information is changed the data is updated in the database and the tableview.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionEditCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/EditCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes the selected customer. When the button is pressed, the selected customer is removed from the tableview
     * as well as the database after confirming with the user that they are sure of their actions.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    /**
     * Returns to the main menu. When the button is pressed, the form returns to the previous main menu.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exception
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /*id name address postal code phone number division id*/
    public void initialize(){
        customerTableView.setItems(CustomersDAO.selectAll());
        customerCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}

