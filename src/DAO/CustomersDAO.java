package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** A class that handles basic CRUD functionality for the customers table.*/
public abstract class CustomersDAO {
    /**
     * Inserts a customer to the customers table. The method takes the name, address, phone number, and division ID
     * and adds a new customer to the customer table. If the add is successful the number of rows that have been
     * inserted are returned.
     * @param name name of the customer
     * @param address address of the customer
     * @param phone phone number of the customer
     * @param divisionId division ID of the customer
     * @return number of rows added to the customer table
     */
    public static int insert(String name, String address, String phone, int divisionId ){
        return 0;
    }

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
}
