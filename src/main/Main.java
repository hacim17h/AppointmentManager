package main;

import DAO.CustomersDAO;
import DAO.JDBC;
import DAO.LoginDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.Divisions;
import model.Users;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        primaryStage.setTitle(rb.getString("Appointment Manager"));
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
        System.out.println("The Appointment Manager is: " + rb.getString("Appointment Manager"));
        System.out.println("The Appointment Manager Login is: " + rb.getString("Appointment Manager Login"));
        System.out.println("The Location is: " + rb.getString("Location"));
        System.out.println("The error message is: " + rb.getString("The username and/or password is incorrect."));

        //default locale test
        System.out.println("The default locale is: " + Locale.getDefault());

        //sql insert test
/*        int success = 0;

        if (success > 0){
            System.out.println("The row has been added successfully.");
        }
        else {
            System.out.println("The row has failed to be added.");
        }*/

        //users test
        Users user = new Users(1, "smokeybone", "420f");
        Users user2 = new Users(1, "test", "test");

        Boolean result = LoginDAO.isValidUser(user.getUsername(), user.getPassword());
        Boolean result2 = LoginDAO.isValidUser(user2.getUsername(), user2.getPassword());
        if (result2){
            System.out.println("The user is valid!");
        }
        else {
            System.out.println("The user is not valid!");
        }
        //test select all in CustomersDAO
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        customers.addAll(CustomersDAO.selectAll());
        for (Customers customer : customers){
            System.out.println(customer.getId() + ": " + customer.getName() + " " + customer.getAddress() + " " +
                               customer.getPostalCode() + " " + customer.getPhoneNum() + " Division ID: " +
                               customer.getDivisionId());
        }
        //test adding a customer in CustomersDAO
        int rowsAdded = CustomersDAO.insert("Wendell Skonch", "641 Scootersville Lane",
                        "84219", "347-985-4333",16);
        if(rowsAdded > 0){
            System.out.println("The customer has been added to the database correctly!");
        }
        else{
            System.out.println("The customer has failed to be added to the database!");
        }


        launch(args);
        JDBC.closeConnection();
    }
}
