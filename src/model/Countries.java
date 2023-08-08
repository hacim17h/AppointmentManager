package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**A class that stores and retrieving various country information.*/
public class Countries {
    /**
     * Stores the country ID.
     */
    private int id;

    /**
     * Stores the country name.
     */
    private String name;

    /**
     * Stores the first-level divisions of the country.
     */
    private ObservableList<Divisions> divisions = FXCollections.observableArrayList();

    /**
     * A class constructor that initializes the id, name, and first-level divisions for the country.
     * @param id country id
     * @param name country name
     * @param divisions country first-level divisions
     */
    public Countries(int id, String name, ObservableList<Divisions> divisions){
        setId(id);
        setName(name);
        setDivisions(divisions);
    }

    /**
     * Gets the id. A getter method that returns the country id.
     * @return country id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the country id.
     * @param id country id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name. A getter method that returns the country name.
     * @return country name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name. A setter method that returns the country name.
     * @param name country name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the divisions. A getter method that returns the first-level divisions.
     * @return first-level divisions
     */
    public ObservableList<Divisions> getDivisions() {
        return divisions;
    }

    /**
     * Sets the divisions. A setter method that returns the first-level divisions.
     * @param divisions  first-level divisions
     */
    public void setDivisions(ObservableList<Divisions> divisions) {
        this.divisions.addAll(divisions);
    }
}
