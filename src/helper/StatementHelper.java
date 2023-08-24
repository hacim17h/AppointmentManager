package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**A functional interface that has a method that allows for simple lambdas to help set values of the prepared
 * statement*/
public interface StatementHelper {

    /**
     * An abstract method that sets the object no matter what its type is in prepared statements.
     * @param statement the selected prepared statement
     * @param index the selected index
     * @param item the object that will be set in the prepared statement
     * @throws SQLException catches various errors related to SQL and database interactions
     */
    void setItem (PreparedStatement statement, int index, Object item) throws SQLException;
}
