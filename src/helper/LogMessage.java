package helper;

/**A functional interface that has a method that allows for simple lambdas to display different login
 * messages to the login activity file*/
@FunctionalInterface
public interface LogMessage {

    /**
     * An abstract method for obtaining a message.
     * @return the message string
     */
    public String getMessage();
}
