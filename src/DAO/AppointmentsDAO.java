package DAO;

import helper.StatementHelper;
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
    public static ObservableList<Appointments> selectByCustomerId(int customerId){
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
     * Returns all the appointments in the database that match the selected appointment id. The method creates a query that
     * selects all appointments in the appointments database that match the appointment id the user enters. It then
     * returns all relevant appointment data as an ObservableList<Appointments>.
     * @param appointmentId the id of the appointment
     * @return a list containing all appointments
     */
    public static Appointments selectByAppointmentId(int appointmentId){
        String query = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = ?";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, appointmentId);
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
        return appointments.get(0);
    }

    /**
     * Returns all the appointments in the database that match the selected contact id. The method creates a query that
     * selects all appointments in the appointments database that match the contact id the user enters. It then
     * returns all relevant appointment data as an ObservableList<Appointments>.
     * @param contactId the id of the contact
     * @return a list containing all appointments
     */
    public static ObservableList<Appointments> selectByContactId(int contactId){
        String query = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, contactId);
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
                int storedContactId = result.getInt("Contact_ID");
                appointments.add(new Appointments(id, title, description, location, type, start, end, storedCustomerId,
                        userId, storedContactId));
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
     * id and adds a new appointment to the appointment table. If the add is successful the number of rows that have
     * been inserted are returned.
     * LAMBDA_EXPRESSION_INFORMATION - The lambda expression helper was used to ensure the prepared statements were
     * consistent. It reduces the amount of errors when trying to parse then information since the data switches
     * between a lot of data types. The helper gives added readability as well. Being a lambda would also allow it
     * to take on whatever form it needed to instead of having several overloaded methods for different types.
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
            StatementHelper helper = (stmt, index, item) -> {
                if (item instanceof Integer) {
                    stmt.setInt(index, ((Integer) item));
                } else if (item instanceof String) {
                    stmt.setString(index, ((String)item));
                }
                else if (item instanceof Timestamp){
                    stmt.setTimestamp(index, ((Timestamp) item));
                }
            };

            helper.setItem(statement, 1, title);
            helper.setItem(statement, 2, description);
            helper.setItem(statement, 3, location);
            helper.setItem(statement, 4, type );
            helper.setItem(statement, 5, start);
            helper.setItem(statement, 6, end);
            helper.setItem(statement, 7, customerId);
            helper.setItem(statement, 8, userId);
            helper.setItem(statement, 9, contactId);
            rowsAdded = statement.executeUpdate();
            return rowsAdded;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAdded;
    }

    /**
     * Updates an appointment to the appointments table. The method takes the title, description, location, appointment
     * type, the appointment start time, the appointment end time, the customer's id, the user's id, and the contact's
     * id and updates a previous appointment in the appointment table. If the update is successful the number of rows
     * that have been updated are returned.
     * LAMBDA_EXPRESSION_INFORMATION - The lambda expression helper was used to ensure the prepared statements were
     * consistent. It reduces the amount of errors when trying to parse then information since the data switches
     * between a lot of data types. The helper gives added readability as well. Being a lambda would also allow it
     * to take on whatever form it needed to instead of having several overloaded methods for different types.
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
    public static int update(String title, String description, String location, String type, Timestamp start,
                             Timestamp end, int customerId, int userId, int contactId, int appointmentId){
        String query = "UPDATE client_schedule.appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, " +
                "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        int rowsUpdated = 0;
        try{

            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            StatementHelper helper = (stmt, index, item) -> {
                if (item instanceof Integer) {
                    stmt.setInt(index, ((Integer) item));
                } else if (item instanceof String) {
                    stmt.setString(index, ((String)item));
                }
                else if (item instanceof Timestamp){
                    stmt.setTimestamp(index, ((Timestamp) item));
                }
            };

            helper.setItem(statement,1, title);
            helper.setItem(statement,2, description);
            helper.setItem(statement,3, location);
            helper.setItem(statement,4, type );
            helper.setItem(statement,5, start);
            helper.setItem(statement,6, end);
            helper.setItem(statement,7, customerId);
            helper.setItem(statement,8, userId);
            helper.setItem(statement,9, contactId);
            helper.setItem(statement,10, appointmentId);
            rowsUpdated = statement.executeUpdate();
            return rowsUpdated;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return rowsUpdated;
    }

    /**
     * Deletes an appointment. The method takes an appointment ID and then compares it against the database. If the
     * appointment ID exists, the appointment is deleted.
     * @param appointmentId appointment ID
     * @return the number of rows deleted
     */
    public static int delete(int appointmentId ){
        int rowsDeleted = 0;
        String query = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        try {
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, appointmentId);
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }
}
