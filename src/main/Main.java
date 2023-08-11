package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;
import model.Divisions;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBC.openConnection();
        ObservableList<Divisions> states = FXCollections.observableArrayList();
        states.add(new Divisions(1, "Maryland", 1));
        states.add(new Divisions(1, "Pennsylvania", 1));
        states.add(new Divisions(1, "Colorado", 1));
        Countries unitedStates = new Countries(1, "The United States", states);
        System.out.println("The country " + unitedStates.getName() + " has " + unitedStates.getDivisions().size() + " states." );
        System.out.println("The states are: ");
        System.out.println("========================================");
        for (Divisions division : unitedStates.getDivisions()){
            System.out.println(division.getName());
        }

        //Locale.setDefault(new Locale("fr","FR"));
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        //language tests
        System.out.println("The username is: " + rb.getString("Username"));
        System.out.println("The password is: " + rb.getString("Password"));
        System.out.println("The Login is: " + rb.getString("Login"));
        System.out.println("The Exit is: " + rb.getString("Exit"));
        System.out.println("The Application Manager is: " + rb.getString("Application Manager"));
        System.out.println("The Application Manager Login is: " + rb.getString("Application Manager Login"));
        System.out.println("The Locale is: " + rb.getString("Locale"));
        System.out.println("The error message is: " + rb.getString("The username and/or password do not match."));
        //default locale test
        System.out.println("The default locale is: " + Locale.getDefault());

        launch(args);
        JDBC.closeConnection();
    }
}
