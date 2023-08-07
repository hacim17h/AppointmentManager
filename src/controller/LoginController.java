package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LoginController {

    @FXML
    private HBox loginErrorLbl;

    @FXML
    private Button loginExitButton;

    @FXML
    private Label loginLocaleLbl;

    @FXML
    private Button loginLoginButton;

    @FXML
    private TextField loginPasswordText;

    @FXML
    private TextField loginUsernameText;

}
