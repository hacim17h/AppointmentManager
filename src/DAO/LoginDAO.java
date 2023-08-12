package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        }
        return false;
    }
}
