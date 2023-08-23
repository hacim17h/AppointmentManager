package controller;

import DAO.AppointmentsDAO;
import DAO.CustomersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
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

        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if(selectedCustomer != null){
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("/view/EditCustomerForm.fxml"));
            scene = sceneLoader.load();
            EditCustomerController editController = sceneLoader.getController();
            editController.prefillData(selectedCustomer);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Removes the selected customer. When the button is pressed, the selected customer is removed from the tableview
     * as well as the database after confirming with the user that they are sure of their actions.
     */
    @FXML
    void onActionDeleteCustomer() {
        if (customerTableView.getSelectionModel().getSelectedItem() != null){
            Alert deleteWarning = new Alert(Alert.AlertType.WARNING);
            deleteWarning.setTitle("Confirmation");
            deleteWarning.setHeaderText(null);
            deleteWarning.setContentText("Are you sure you want to delete the customer?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            deleteWarning.getButtonTypes().setAll(yesButton, noButton);
            deleteWarning.showAndWait();
            if (deleteWarning.getResult() == yesButton){
                //Delete all the appointments associated with the customer first.
                Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
                ObservableList<Appointments> appointments = FXCollections.observableArrayList();
                appointments.addAll(AppointmentsDAO.selectByCustomerId(selectedCustomer.getId()));
                for (Appointments appointment : appointments){
                    AppointmentsDAO.delete(appointment.getId());
                }

                //Delete the customer.
                int rowsDeleted = CustomersDAO.delete(selectedCustomer.getId());
                customerTableView.setItems(CustomersDAO.selectAll());
                customerTableView.refresh();

                if(rowsDeleted > 0){
                    //Displays information pertaining to the deleted customer upon success.
                    Alert notification = new Alert(Alert.AlertType.INFORMATION);
                    notification.setTitle("Delete successful");
                    notification.setHeaderText(null);
                    notification.setContentText("The Customer has been deleted successfully.");
                    notification.showAndWait();
                }
                else {
                    //Displays information pertaining to the failed deletion of the appointment.
                    Alert notification = new Alert(Alert.AlertType.WARNING);
                    notification.setTitle("Delete unsuccessful");
                    notification.setHeaderText(null);
                    notification.setContentText("The customer has not been deleted.");
                    notification.showAndWait();
                }
            }
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
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * A special method that displays the initial values. The table views are populated with the customer information
     * from the database and the columns values are set properly.
     */
        public void initialize(){
        customerTableView.setItems(CustomersDAO.selectAll());
        customerCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionDisplay"));
    }
}

