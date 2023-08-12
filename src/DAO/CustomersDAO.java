package DAO;

import java.sql.SQLException;
import java.sql.Statement;

/** A class that handles basic CRUD functionality for the customers table.*/
public class CustomersDAO {
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
}
