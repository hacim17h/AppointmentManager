package model;

/**A class that stores and retrieves various customer information.*/
public class Customers {
    /**
     * Stores the customer ID.
     */
    private int id;

    /**
     * Stores the customer name.
     */
    private String name;

    /**
     * Stores the address.
     */
    private String address;

    /**
     * Stores the postal code.
     */
    private String postalCode;

    /**
     * Stores the phone number.
     */
    private String phoneNum;

    /**
     * Stores the division ID.
     */
    private int divisionId;

    /**
     * A class constructor that initializes the id, name, address, postal code, phone number
     * and first-level divisions for the country.
     * @param id customer ID
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phoneNum customer phone number
     * @param divisionId division ID associated with the customer
     */
    public Customers(int id, String name, String address, String postalCode, String phoneNum, int divisionId){
        setId(id);
        setName(name);
        setAddress(address);
        setPostalCode(postalCode);
        setPhoneNum(phoneNum);
        setDivisionId(divisionId);
    }

    /**
     * Gets the id. A getter method that returns the customer id.
     * @return customer id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the customer id.
     * @param id customer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name. A getter method that returns the customer name.
     * @return customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name. A setter method that returns the customer name.
     * @param name customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address. A getter method that returns the customer address.
     * @return address the customers address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address. A setter method that returns the customer address.
     * @param address customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the address. A getter method that returns the customer address.
     * @return address the customers address
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code. A setter method that returns the customer postal code.
     * @param postalCode customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone number. A getter method that returns the customer phone number.
     * @return phoneNum the customers phone number
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the phone number. A setter method that returns the customer phone number.
     * @param phoneNum customer phone number
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Gets the first-level division ID. A getter method that returns the customer first-level division ID.
     * @return divisionId the customers first-level division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the first-level division ID. A setter method that returns the customer first-level division ID.
     * @param divisionId customer first-level division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
