package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class that handles basic retrieval functionality for the Contacts table.*/
public class ContactsDAO {
    /**
     * Returns all the contacts in the database. The method creates a query that selects all contacts in the
     * contacts database and returns all relevant contact data as an ObservableList<Contacts>.
     * @return a list containing all contacts
     */
    public static ObservableList<Contacts> selectAllContacts(){
        String query = "SELECT * FROM client_schedule.contacts";
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Contact_ID");
                String name = result.getString("Contact_Name");
                String email = result.getString("Email");
                contacts.add(new Contacts(id, name, email));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Returns a specific contact by id in the database. The method creates a query that selects a contact in the
     * contacts database that matches the given contact id. It then returns all relevant contact data as a
     * Contacts object.
     * @return the contact that matches the specific contact id
     */
    public static Contacts selectContactsById(int contactId){
        String query = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, contactId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Contact_ID");
                String name = result.getString("Contact_Name");
                String email = result.getString("Email");
                contacts.add(new Contacts(id, name, email));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contacts.get(0);
    }
}
