package main;

import DAO.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** The class that holds the main entry point of the program.*/
public class Main extends Application {
    /**
     * Stores the user who successfully or unsuccessfully logs in.
     */
    public static Users sessionUser = new Users(999,"Username", "Password");
    /**
     * Stores whether the login was successful.
     */
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

    /**
     * The beginning of the program's execution. The database connection and the launching of the program happens
     * during this method.
     * @param args a special parameter for arguments
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        //ZoneId test
        ZoneId denver = ZoneId.of("America/Denver");
        ZoneId la = ZoneId.of("America/Los_Angeles");
        ZoneId buenosAires = ZoneId.of("America/Buenos_Aires");
        ZoneId prague = ZoneId.of("Europe/Prague");
        ZoneId etc = ZoneId.of("Etc/GMT-14");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        TimeZone.setDefault(TimeZone.getTimeZone(prague));
        //System.out.println(LocalDateTime.now());
        //Locale.setDefault(new Locale("fr","FR"));

        launch(args);
        JDBC.closeConnection();
    }
}
