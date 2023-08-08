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
        launch(args);
        JDBC.closeConnection();
    }
}
