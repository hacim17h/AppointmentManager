package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;

/** A class that handles basic CRUD functionality for the appointments table.*/
public class AppointmentsDAO {
    /**
     * Returns all the appointments in the database. The method creates a query that selects all appointments in the
     * appointments database and returns all relevant customer data as an ObservableList<Appointments>.
     * @return a list containing all appointments
     */
    public static ObservableList<Appointments> selectAll(){
        String query = "SELECT * FROM client_schedule.appointments";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp start = result.getTimestamp("Start");
                Timestamp end = result.getTimestamp("End");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");
                appointments.add(new Appointments(id, title, description, location, type, start, end, customerId,
                                    userId, contactId));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }
}
