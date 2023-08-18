package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;

/** A class that handles basic CRUD functionality for the appointments table.*/
public class AppointmentsDAO {
    /**
     * Returns all the appointments in the database. The method creates a query that selects all appointments in the
     * appointments database and returns all relevant appointment data as an ObservableList<Appointments>.
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

    /**
     * Returns all the appointments in the database that match the selected customer id. The method creates a query that
     * selects all appointments in the appointments database that match the customer id the user enters. It then
     * returns all relevant appointment data as an ObservableList<Appointments>.
     * @param customerId the id of the customer
     * @return a list containing all appointments
     */
    public static ObservableList<Appointments> selectById(int customerId){
        String query = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Timestamp start = result.getTimestamp("Start");
                Timestamp end = result.getTimestamp("End");
                int storedCustomerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");
                appointments.add(new Appointments(id, title, description, location, type, start, end, storedCustomerId,
                        userId, contactId));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Inserts an appointment to the appointments table. The method takes the title, description, location, appointment
     * type, the appointment start time, the appointment end time, the customer's id, the user's id, and the contact's
     * id and adds a new appointment to the appointment table.If the add is successful the number of rows that have been
     * inserted are returned.
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param customerId the customer id associated with the appointment
     * @param userId the user id associated with the appointment
     * @param contactId the contact id associated with the appointment
     * @return the number of rows added to the database
     */
    public static int insert(String title, String description, String location, String type, Timestamp start,
                              Timestamp end, int customerId, int userId, int contactId){
        String query = "INSERT INTO client_schedule.appointments " +
                "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        int rowsAdded = 0;
        try{

            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setString(4, type );
            statement.setTimestamp(5, start);
            statement.setTimestamp(6, end);
            statement.setInt(7, customerId);
            statement.setInt(8, userId);
            statement.setInt(9, contactId);
            rowsAdded = statement.executeUpdate();
            return rowsAdded;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAdded;
    }
}
