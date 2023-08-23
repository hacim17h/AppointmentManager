package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/** A simple helper class provided by WGU that manages the connection to the underlying preexisting database. */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName +
            "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * Opens the connection to the database server. The method connects to the database server if successful and
     * prints an error message otherwise.
     */
    public static void  openConnection(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful!");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Closes the connection to the database server. The method disconnects to the database server if successful and
     * prints an error message otherwise.
     */
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Connection Closed!");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
