package DAO;

import helper.LogMessage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**A class that handles authenticating the user by checking the database to see if they exist while tracking
 * attempts.*/
public abstract class LoginDAO {
    /**
     * Determines if the user is a valid user. The method takes a username and a password and compares it against
     * all the of the users in the database. If the database returns no users that match the given username and
     * password, it is determined that the user does not exist and returns false.
     * @param username the user's username
     * @param password the user's password
     * @return true if the user is valid and false if the user is not valid
     */
    public static Boolean isValidUser(String username, String password) {
        String query = "SELECT * FROM client_schedule.users WHERE User_Name = ? AND Password = ?";

        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }

    /**
     * Records information about the user login. The method creates a new login file if one isn't present and records
     * whether the user has successfully logged in as well as the timestamp of the attempt. If there is already
     * a log file present, it updates the file and adds to it. The lambda successfulMessage and unsuccessfulMessage
     * were used to simplify the redundancy of the message text. Due to having repeat the text for the condition of
     * the file already existing, it was helpful in cutting it down and gives added readability and clarity on the
     * success states.
     * @param success whether the login was successful or not
     */
    public static void recordLogins (Boolean success){
        try{
            Path path = Paths.get("login_activity.txt");

            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
            String timestamp = dateTime.format(formatter);
            LogMessage successfulMessage = () -> "Login attempt on " + timestamp + " was successful.";
            LogMessage unsuccessfulMessage = () -> "Login attempt on " + timestamp + " was unsuccessful.";
            if(Files.notExists(path)){
                Files.createFile(path);
                String logEntry;
                if (success){
                    logEntry = successfulMessage.getMessage();
                }
                else{
                    logEntry = unsuccessfulMessage.getMessage();
                }
                Files.write(path, logEntry.getBytes());

            }
            else {
                String logEntry;
                if (success){
                    logEntry = successfulMessage.getMessage();
                }
                else{
                    logEntry = unsuccessfulMessage.getMessage();
                }
                Files.write(path, ("\n" + logEntry).getBytes(), StandardOpenOption.APPEND);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
