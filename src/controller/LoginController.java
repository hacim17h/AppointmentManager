package controller;

import DAO.LoginDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    /**
     * Stores the stage.
     */
    Stage stage;

    /**
     * Stores the scene.
     */
    Parent scene;
    @FXML
    private Label loginErrorLbl;

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

    /**
     * Switches to the main menu form. When the button is pressed,the scene is properly shifted to the main menu
     * form if the user credentials are correct. If not an error is displayed, and they are forced to try again.
     * Upon successful or unsuccessful logins, data is stored to the login_activity.txt file. This includes the
     * attempts, dates, and time stamps, and if the attempt was successful or not.
     * @param event helps get the window that caused the event
     * @throws IOException for handling any input output exceptions
     */
    @FXML
    public void onActionLogin(ActionEvent event) throws IOException {
        String username = loginUsernameText.getText();
        String password = loginPasswordText.getText();
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        Boolean success = LoginDAO.isValidUser(username,password);
        if(success){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenuForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            LoginDAO.recordLogins(success);
        }
        else
        {
            loginErrorLbl.setText(rb.getString("The username and/or password is incorrect."));
            LoginDAO.recordLogins(success);
        }

    }

    /**
     * Exits the program. When the button, is pressed the program closes.
     */
    @FXML
    public void onActionExit(ActionEvent event){
        System.exit(0);
    }

    /**
     * A special method that displays the initial values. The login form is dynamically translated based upon the
     * language settings of the users computer. The location of the user is also displayed in the location area of
     * the UI.
     */
    public void initialize(){
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        loginUsernameTitleLbl.setText(rb.getString("Username") + ":");
        loginPasswordTitleLbl.setText(rb.getString("Password") + ":");
        loginLoginButton.setText(rb.getString("Login"));
        loginExitButton.setText(rb.getString("Exit"));
        loginTitleLbl.setText(rb.getString("Appointment Manager Login"));
        loginLocationTitleLbl.setText(rb.getString("Location") + ":");
        loginLocationLbl.setText(ZoneId.systemDefault().toString().replace('_',  ' '));
    }

}
