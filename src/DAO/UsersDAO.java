package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.Customers;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class that handles basic retrieval functionality for the Users table.*/
public class UsersDAO {
    /**
     * Returns all the users in the database. The method creates a query that selects all users in the
     * users database and returns all relevant user data as an ObservableList<Users>.
     * @return a list containing all users
     */
    public static ObservableList<Users> selectAllUsers(){
        String query = "SELECT * FROM client_schedule.users";
        ObservableList<Users> users = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("User_ID");
                String name = result.getString("User_Name");
                String password = result.getString("Password");
                users.add(new Users(id, name, password));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Returns a specific user by id in the database. The method creates a query that selects a user in the
     * users database that matches the given user id. It then returns all relevant user data as a
     * Users object.
     * @return the user that matches the specific user id
     */
    public static Users selectUsersById(int userId){
        String query = "SELECT * FROM client_schedule.users WHERE User_ID = ?";
        ObservableList<Users> users = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("User_ID");
                String name = result.getString("User_Name");
                String password = result.getString("Password");
                users.add(new Users(id, name, password));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users.get(0);
    }
}
