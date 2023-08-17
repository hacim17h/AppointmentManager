package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**A class that accesses the location information for the countries and the first level divisions*/
public class LocationDAO {
    /**
     * Returns all the countries. The method accesses the database and goes through adding every country to the
     * ObservableList<Countries>. It also populates the first-level divisions that the Countries objects have
     * inside of them.
     * @return a list of all the countries in the database
     */
    public static ObservableList<Countries> selectAll(){
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        String query = "SELECT * FROM client_schedule.countries";
        String fldQuery = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = ?";
        try{
            PreparedStatement statement = JDBC.connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("Country_ID");
                String name = results.getString("Country");
                ObservableList<Divisions> divisions = FXCollections.observableArrayList();
                PreparedStatement fldStatement = JDBC.connection.prepareStatement(fldQuery);
                fldStatement.setInt(1, id);
                ResultSet fldResults = fldStatement.executeQuery();
                //Goes through the first level divisions of that specific country and adds them to the divisions list.
                while (fldResults.next()){
                    divisions.add(new Divisions(fldResults.getInt("Division_ID"),
                              fldResults.getString("Division"), fldResults.getInt("Country_ID")));
                }
                Countries country = new Countries(id, name, divisions);
                countries.add(country);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return countries;
    }
}
