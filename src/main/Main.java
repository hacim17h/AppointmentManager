package main;

import DAO.*;
import helper.TimeHelper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.sql.SQLOutput;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Main extends Application {
    public static Users sessionUser = new Users(999,"Username", "Password");
    public static boolean loginSuccess = false;

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

        //test adding a customer in CustomersDAO
        /*int rowsAdded = CustomersDAO.insert("Wendell Skonch", "641 Scootersville Lane",
                        "84219", "347-985-4333",16);
        if(rowsAdded > 0){
            System.out.println("The customer has been added to the database correctly!");
        }
        else{
            System.out.println("The customer has failed to be added to the database!");
        }*/

        //test select all in LocationDAO
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        countries.addAll(LocationDAO.selectAllCountries());
        for (Countries country : countries){
            System.out.println("Country: " + country.getName());
            System.out.println("================================");
            System.out.println("Divisions:");
            for (Divisions division : country.getDivisions()){
                System.out.println(division.getName());
            }
            System.out.println();
        }
        //test update in CustomersDao
        /*int rowsUpdated = CustomersDAO.update(12, "Really Happy Guy", "Really Happy Place",
                                            "12345","1212-111-2222", 3);*/

        //test select all in CustomersDAO
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        customers.addAll(CustomersDAO.selectAll());
        for (Customers customer : customers){
            System.out.println(customer.getId() + ": " + customer.getName() + " " + customer.getAddress() + " " +
                    customer.getPostalCode() + " " + customer.getPhoneNum() + " Division ID: " +
                    customer.getDivisionId());
        }
        //test insert in AppointmentsDAO
        /*int rowsAdded = AppointmentsDAO.insert("A very important meeting",
                "I get tea while drawing my cat doing various poses.", "In my room",
                "Art and Tea", Timestamp.valueOf("2023-08-17 14:00:00"),
                Timestamp.valueOf("2023-08-17 15:00:00"), 12, 1, 2);
        if (rowsAdded > 0){
            System.out.println("The add was a success");
        }
        else{
            System.out.println("The add was a failure");
        }*/
        //ZoneId test
        ZoneId denver = ZoneId.of("America/Denver");
        ZoneId la = ZoneId.of("America/Los_Angeles");
        ZoneId buenosAires = ZoneId.of("America/Buenos_Aires");
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZoneId etc = ZoneId.of("Etc/GMT-14");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        //test select all for users and contacts
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        ObservableList<Users> users = FXCollections.observableArrayList();

        contacts.addAll(ContactsDAO.selectAllContacts());
        users.addAll(UsersDAO.selectAllUsers());

        for (Contacts contact : contacts){
            System.out.println("Contact ID: " + contact.getId());
            System.out.println("Contact Name: " + contact.getName());
            System.out.println("Contact Email: " + contact.getEmail());
        }
        for (Users userGroup : users){
            System.out.println("User ID: " + userGroup.getId());
            System.out.println("Username: " + userGroup.getUsername());
            System.out.println("Password: " + userGroup.getPassword());
        }

        LocalDateTime dateTime = LocalDateTime.of(2023,8,18,8,0);
        LocalDateTime dateTime2 = LocalDateTime.of(2023,8,18,13,0);
        LocalDateTime dateTime3 = LocalDateTime.of(2023,8,19,10,0);
        LocalDateTime dateTime4 = LocalDateTime.of(2023,8,19,12,0);

        Timestamp startTime = Timestamp.valueOf(dateTime);
        Timestamp endTime = Timestamp.valueOf(dateTime2);

        Timestamp otherStartTime = Timestamp.valueOf(dateTime3);
        Timestamp otherEndTime = Timestamp.valueOf(dateTime4);

        if(TimeHelper.isOverlapping(startTime,endTime,otherStartTime,otherEndTime)){
            System.out.println("The times " + startTime + " - " + endTime + " and " + otherStartTime + " - " +
                                otherEndTime + " are overlapping.");
        }
        else{
            System.out.println("The times " + startTime + " - " + endTime + " and " + otherStartTime + " - " +
                    otherEndTime + " are not overlapping.");
        }

        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime date = LocalDateTime.of(2023,8,17,0,0);
        LocalDateTime otherDate = LocalDateTime.of(2023, 8, 17,0,0).minusNanos(1);

        if(date.isAfter(otherDate)){
            System.out.println("This date is after the other one");
        }
        else {
            System.out.println("This date is not after the other one");
        }

        System.out.println("The First day of this month is " + today.with(TemporalAdjusters.firstDayOfMonth()));


        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        appointments.addAll(AppointmentsDAO.selectAll());
        Timestamp start = appointments.get(0).getStartTime();
        ZoneId local = ZoneId.systemDefault();
        ZonedDateTime localTime = ZonedDateTime.of(start.toLocalDateTime(),local);
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime newUTC = ZonedDateTime.ofInstant(localTime.toInstant(), utcZone);
        ZonedDateTime estTime = ZonedDateTime.ofInstant(newUTC.toInstant(), local);
        ZonedDateTime normalized = start.toInstant().atZone(ZoneId.of("America/New_York"));
        LocalDateTime testTime =  LocalDateTime.of(normalized.getYear(), normalized.getMonth(), normalized.getDayOfMonth(), normalized.getHour(), normalized.getMinute());
        ZonedDateTime utc = ZonedDateTime.ofInstant(normalized.toInstant(), ZoneId.of("UTC"));
        System.out.println("This is the start timestamp " + normalized);
        System.out.println("This is the start utc timestamp " + utc);
        System.out.println("This is the start testtime timestamp " + testTime);
        System.out.println("This is the start localtime " + localTime);
        System.out.println("This is the start newUTC " + newUTC);
        System.out.println("This is the start esttime " + estTime);
        System.out.println("The raw timestamp is " + start);
        System.out.println("The local time is " + start.toLocalDateTime());
        System.out.println("The time now is " + LocalDateTime.now());

        //Test during business hours method
        Timestamp testStart = Timestamp.valueOf("2023-08-21 8:00:00");
        Timestamp testEnd = Timestamp.valueOf("2023-08-21 22:00:00");
        System.out.println("Start time: " + testStart);
        System.out.println("End time: " + testEnd);
        if (TimeHelper.duringBusinessHours(testStart, testEnd)){
            System.out.println("These are during business hours");
        }
        else{
            System.out.println("These are not during business hours.");
        }

        //TimeZone.setDefault(TimeZone.getTimeZone(denver));
        //Locale.setDefault(new Locale("fr","FR"));
        System.out.println(LocalDateTime.now());
        launch(args);
        JDBC.closeConnection();
    }
}
