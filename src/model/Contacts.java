package model;

/**A class that stores and retrieves various contacts information.*/
public class Contacts {
    /**
     * Stores the contact ID.
     */
    private int id;

    /**
     * Stores the contact name.
     */
    private String name;

    /**
     * Stores the contact email.
     */
    private String email;

    /**
     * A class constructor that initializes the id, name, and email for the contact.
     * @param id contact id
     * @param name contact name
     * @param email contact email
     */
    public Contacts(int id, String name, String email){
        setId(id);
        setName(name);
        setEmail(email);
    }

    /**
     * Gets the id. A getter method that returns the contact id.
     * @return contact id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the contact id.
     * @param id contact id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name. A getter method that returns the contact name.
     * @return contact name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name. A setter method that sets the contact name.
     * @param name contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email. A getter method that returns the contact email.
     * @return contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email. A setter method that sets the contact email.
     * @param email contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
