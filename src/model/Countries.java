package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**A class that stores and retrieves various country information.*/
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
     * Gets the division names. A getter method that returns the first-level division names.
     * @return a list of the division names
     */
    public ObservableList<String> getDivisionNames(){
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (Divisions division : getDivisions()){
            divisionNames.add(division.getName());
        }
        return divisionNames;
    }
    /**
     *
     * Gets the division ids. A getter method that returns the first-level division ids.
     * @return a list of the division ids
     */
    public ObservableList<Integer> getDivisionIds(){
        ObservableList<Integer> divisionIds = FXCollections.observableArrayList();
        for (Divisions division : getDivisions()){
            divisionIds.add(division.getId());
        }
        return divisionIds;
    }

    /**
     * Sets the divisions. A setter method that returns the first-level divisions.
     * @param divisions  first-level divisions
     */
    public void setDivisions(ObservableList<Divisions> divisions) {
        this.divisions.addAll(divisions);
    }

    /**
     * Returns the country name for display. A method override that displays the country name to be displayed when
     * printing out or displaying a Countries object.
     * @return the country name
     */
    @Override
    public String toString() {
        return name;
    }
}
