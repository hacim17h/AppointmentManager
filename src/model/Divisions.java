package model;

/**A class that stores and retrieves various first-level division information.*/
public class Divisions {
    /**
     * Stores the first-level division ID.
     */
    private int id;

    /**
     * Stores the first-level division name.
     */
    private String name;

    /**
     * Stores the country ID.
     */
    private int countryId;

    /**
     * A class constructor that initializes the id, name, and country id for the first-level division.
     * @param id first-level division id
     * @param name first-level division name
     * @param countryId country id for the first-level division
     */
    public Divisions(int id, String name, int countryId){
        setId(id);
        setName(name);
        setCountryId(countryId);
    }

    /**
     * Gets the id. A getter method that returns the first-level division id.
     * @return first-level division id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the first-level division id.
     * @param id first-level division id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name. A getter method that returns the first-level division name.
     * @return first-level division name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name. A setter method that returns the first-level division name.
     * @param name first-level division name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the id. A getter method that returns the country id.
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the id. A setter method that sets the country id.
     * @param id country id
     */
    public void setCountryId(int id) {
        this.countryId = id;
    }
}
