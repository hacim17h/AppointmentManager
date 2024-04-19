# Appointment Manager
This software for basic Create, Read, Update, and Delete operations for 
customers and appointments in an established database. It is also used to generate simple 
reports and gives useful alerts when appointments are nearby. 

IDE: IntelliJ IDEA 2023.1.2 (Community Edition) Build #IC-231.9011.34, built on May 16, 2023

Java SDK Version: Java SDK 17.0.8

JavaFX version: javafx-sdk-17.0.8 

MySQL Connector Driver Version Number: mysql-connector-java-8.0.25

Report Description: The report generates the total number of customers in the database based
upon the country they reside in. When selecting a country, the country is shown as well as the
number of customers in that country and only that country. 

## How to run the program
**0.** After setting up the database, open the project folder in intellij under File -> Open.

**1.** Once the project loads goto File -> Project Structure -> Libraries.

**2.** If you see a red squiggle under the mysql-connector-java-8.0.25 which indicates the file is not 
found. If this is already properly configured, you can skip to step 5.

**2a.** Click mysql-connector-java-8.0.25. Then, Go over to the classes area and select the plus 
button above. 

**3.** Navigate to your mysql-connector-java-8.0.25.jar and once you've selected it, press okay. It 
should now populate with the correct directory for mysql-connector-java-8.0.25.jar. 

**3a.** If there was an old directory with a red square icon indicating that it is not working, you can 
remove it by clicking on the directory and pressing the minus button.

**4.** Once those are complete, hit apply and press ok.

**5.** If the green play icon in intellij is not available, click "Add Configuration". If already
configured, goto step 10.

**6.** Once the Run/Debug Configurations menu shows up, click the plus button in the top left and 
select "Application".

**7.** Once the new screen displays, in the name field you can just type "AppointmentManager". In the
main class area, click the icon to the right of the text field and then select "Main" for the
main class in the Choose Main Class window and hit OK.

**8.** Click the "Modify Options" drop down on the top right and in the menu that appears select "Add VM Options".

**9.** Once the VM Options have appeared copy paste this line into the VM options text field:

--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics

Hit apply and then OK.

**10.** The run configuration should be complete. Press the play icon to run the program in intellij.


**11.** When the software begins, Log in with the following credentials: 

username: test
password: test

Please be sure to have your keyboard set to a region that can support those characters because 
the username and password are case sensitive and to not have any leading or trailing spaces.

**12.** Once logged in, you will be greeted with a message alerting you of any upcoming appointments.

**13.** Here the rest of the functionality can be accessed with the various menu's. To add a customer, 
access the view / manage customers menu. To add an apppointment, access the view / manage 
appointments menu. To look at various reports, access the generate report menu.

Every section will have options that are labeled and will allow you to nagivate through the program.
When finished you can return to the main menu and press the exit button. 
