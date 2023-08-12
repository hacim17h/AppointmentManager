package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private HBox loginErrorLbl;

    @FXML
    private Button loginExitButton;

    @FXML
    private Label loginLocationTitleLbl;

    @FXML
    private Label loginLocationLbl;

    @FXML
    private Label loginTitleLbl;
    @FXML
    private Button loginLoginButton;

    @FXML
    private Label loginUsernameTitleLbl;

    @FXML
    private TextField loginPasswordText;

    @FXML
    private Label loginPasswordTitleLbl;

    @FXML
    private TextField loginUsernameText;


    public void initialize(){
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        loginUsernameTitleLbl.setText(rb.getString("Username") + ":");
        loginPasswordTitleLbl.setText(rb.getString("Password") + ":");
        loginLoginButton.setText(rb.getString("Login"));
        loginExitButton.setText(rb.getString("Exit"));
        loginTitleLbl.setText(rb.getString("Appointment Manager Login"));
        loginLocationTitleLbl.setText(rb.getString("Location") + ":");
        loginLocationLbl.setText(ZoneId.systemDefault().toString());


    }
}
