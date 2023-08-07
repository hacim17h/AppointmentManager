package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewCustomerController {

    @FXML
    private Button customerAddButton;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private TableColumn<?, ?> customerCustomerIDCol;

    @FXML
    private TableColumn<?, ?> customerCustomerNameCol;

    @FXML
    private TableColumn<?, ?> customerDivisionCol;

    @FXML
    private Button customerEditButton;

    @FXML
    private Button customerMainMenu;

    @FXML
    private TableColumn<?, ?> customerPhoneCol;

    @FXML
    private TableColumn<?, ?> customerPostalCodeCol;

    @FXML
    private Button customerRemoveButton;

    @FXML
    private TableView<?> customerTableView;

}

