package helper;

import java.sql.Timestamp;

/**A class that helps with various time calculations and checks*/
public abstract class TimeHelper {
    /**
     * Returns if two times are overlapping. Takes the start and end of one time and compares it to the start
     * and end of another time. If they are overlapping it will return true and otherwise false.
     * @param startTime the start of the first time interval
     * @param endTime the end of the first time interval
     * @param otherStartTime the start of the second time interval
     * @param otherEndTime the end of the second time interval
     * @return true if the times overlap and false if they do not
     */
    public static Boolean isOverlapping(Timestamp startTime, Timestamp endTime,
                                        Timestamp otherStartTime, Timestamp otherEndTime){
        if(startTime.before(otherEndTime) && endTime.after(otherStartTime)){
            return true;
        }
        return false;
    }

}
