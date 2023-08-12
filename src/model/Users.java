package model;

/**A class that stores and retrieves various user information.*/
public class Users {
    /**
     * Stores the user ID.
     */
    private int id;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Stores the password.
     */
    String password;

    /**
     * A class constructor that initializes the id, name, and email for the contact.
     * @param id user id
     * @param username username
     * @param password user password
     */
    public Users(int id, String username, String password){
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Gets the id. A getter method that returns the user id.
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the user id.
     * @param id user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username. A getter method that returns the username.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username. A setter method that sets the username.
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password. A getter method that returns the password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password. A setter method that returns the password.
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
