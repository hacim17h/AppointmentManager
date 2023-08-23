package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class that handles basic CRUD functionality for the customers table.*/
public abstract class CustomersDAO {
    /**
     * Inserts a customer to the customers table. The method takes the name, address, phone number, and division ID
     * and adds a new customer to the customer table. If the add is successful the number of rows that have been
     * inserted are returned.
     * @param name name of the customer
     * @param address address of the customer
     * @param postalCode postal code of the customer
     * @param phone phone number of the customer
     * @param divisionId division ID of the customer
     * @return number of rows added to the customer table
     */
    public static int insert(String name, String address, String postalCode, String phone, int divisionId ){
        String query = "INSERT INTO client_schedule.customers " +
                       "(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";
        int rowsAdded = 0;
        try{

            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setInt(5, divisionId);
            rowsAdded = statement.executeUpdate();
            return rowsAdded;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAdded;
    }

    /**
     * Updates a customer in the customers table. The method takes the name, address, phone number, and division ID
     * and updates the previously existing customer data in the customer table. If the update is successful the number
     * of rows that have been updated are returned.
     * @param name name of the customer
     * @param address address of the customer
     * @param postalCode postal code of the customer
     * @param phone phone number of the customer
     * @param divisionId division ID of the customer
     * @return number of rows updated in the customer table
     */
    public static int update(int customerId, String name, String address, String postalCode, String phone,
                             int divisionId ){
        int rowsUpdated = 0;
        String query = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setInt(5, divisionId);
            statement.setInt(6, customerId);
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }

    /**
     * Returns all the customers in the database. The method creates a query that selects all customers in the
     * customers database and returns all relevant customer data as an ObservableList<Customers>.
     * @return a list containing all customers
     */
    public static ObservableList<Customers> selectAll(){
        String query = "SELECT * FROM client_schedule.customers";
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Customer_ID");
                String name = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                int divisionId = result.getInt("Division_ID");
                customers.add(new Customers(id,name,address,postalCode,phone,divisionId));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    return customers;
    }

    /**
     * Returns a specific customer by id in the database. The method creates a query that selects a customer in the
     * customers database that matches the given customer id. It then returns all relevant customer data as a
     * customers object.
     * @return the customer that matches the specific customer id
     */
    public static Customers selectCustomersById(int customerId){
        String query = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Customer_ID");
                String name = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                int divisionId = result.getInt("Division_ID");
                customers.add(new Customers(id, name, address, postalCode, phone, divisionId));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return customers.get(0);
    }

    /**
     * Deletes a customer. The method takes a customer ID and then compares it against the database. If the
     * customer ID exists, the customer is deleted.
     * @param customerId customer ID
     * @return the number of rows deleted
     */
    public static int delete(int customerId ){
        int rowsDeleted = 0;
        String query = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        try {
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, customerId);
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }
}
