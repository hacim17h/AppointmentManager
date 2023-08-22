package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class that handles basic retrieval functionality for the Divisions table.*/
public class DivisionsDAO {
    /**
     * Returns a specific division by id in the database. The method creates a query that selects a division in the
     * Division database that matches the given division id. It then returns all relevant division data as a
     * Division object.
     * @return the division that matches the specific division id
     */
    public static Divisions selectByDivisionId(int divisionId){
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
        ObservableList<Divisions> division = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            statement.setInt(1, divisionId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("Division_ID");
                String name = result.getString("Division");
                int countryId = result.getInt("Country_ID");
                division.add(new Divisions(id, name, countryId));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return division.get(0);
    }
}
