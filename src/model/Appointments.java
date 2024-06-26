package model;

import DAO.ContactsDAO;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


/**A class that stores and retrieves various appointment information.*/
public class Appointments {
    /**
     * Stores the appointment ID.
     */
    private int id;

    /**
     * Stores the appointment title.
     */
    private String title;

    /**
     * Stores the appointment description.
     */
    private String description;

    /**
     * Stores the appointment type.
     */
    private String type;

    /**
     * Stores the appointment location.
     */
    private String location;

    /**
     * Stores the appointment start time.
     */
    private Timestamp startTime;

    /**
     * Stores the appointment end time.
     */
    private Timestamp endTime;

    /**
     * Stores the customer ID.
     */
    private int customerId;

    /**
     * Stores the user ID.
     */
    private int userId;

    /**
     * Stores the contact ID.
     */
    private int contactId;

    /**
     * Stores the contact name for display.
     */
    private String contactDisplay;



    /**
     * A class constructor that initializes the id, title, description, type, location, start time, and
     * end time. It also initializes the customer id, user id, and contact id associated with the appointment.
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param type appointment type
     * @param location appointment location
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param customerId customer id associated with the appointment
     * @param userId user id associated with the appointment
     * @param contactId contact id associated with the appointment
     */
    public Appointments(int id, String title, String description, String location, String type,
                        Timestamp startTime, Timestamp endTime, int customerId, int userId, int contactId){
        setId(id);
        setTitle(title);
        setDescription(description);
        setType(type);
        setLocation(location);
        setStartTime(startTime);
        setEndTime(endTime);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
        contactDisplay =  ContactsDAO.selectContactsById(contactId).getName();

    }

    /**
     * Gets the id. A getter method that returns the appointment id.
     * @return appointment id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id. A setter method that sets the appointment id.
     * @param id appointment id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title. A getter method that returns the appointment title.
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title. A setter method that sets the appointment title.
     * @param title appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description. A getter method that returns the appointment description.
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description. A setter method that sets the appointment description.
     * @param description appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the type. A getter method that returns the appointment type.
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type. A setter method that sets the appointment type.
     * @param type appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the location. A getter method that returns the appointment location.
     * @return appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location. A setter method that sets the appointment location.
     * @param location appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the start time. A getter method that returns the appointment start time.
     * @return appointment start time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time. A setter method that sets the appointment start time.
     * @param startTime appointment start time
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time. A getter method that returns the appointment end time.
     * @return appointment end time
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time. A setter method that sets the appointment end time.
     * @param endTime appointment end time
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the customer id. A getter method that returns the customer id associated with the appointment.
     * @return appointment customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer id. A setter method that sets the customer id associated with the appointment.
     * @param customerId appointment customer Id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the user id. A getter method that returns the user id associated with the appointment.
     * @return appointment user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id. A setter method that sets the user id associated with the appointment.
     * @param userId appointment user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the contact id. A getter method that returns the contact id associated with the appointment.
     * @return appointment contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id. A setter method that sets the contact id associated with the appointment.
     * @param contactId appointment contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the contact display. A getter method that returns the contact name associated with the appointment for
     * display purposes.
     * @return appointment contact name
     */
    public String getContactDisplay() {
        return contactDisplay;
    }

    /**
     * Returns various appointment details for display. A method override that displays the appointment id, the start
     * date, and time when printing out or displaying an Appointments object.
     * @return the appointment id, start date, and time
     */
    @Override
    public String toString() {
        ResourceBundle rb = ResourceBundle.getBundle("helper/Lang", Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm");
        String displayStart = startTime.toLocalDateTime().format(formatter).replace(
                "at", rb.getString("at"));

        return rb.getString("Appointment ID") + ": " + id + " " + rb.getString("on") + " "+ displayStart;
    }
}
