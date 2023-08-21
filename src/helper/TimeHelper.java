package helper;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

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
        return startTime.before(otherEndTime) && endTime.after(otherStartTime);
    }


    public static boolean duringBusinessHours (Timestamp startTime, Timestamp endTime){

        ZoneId utc = ZoneId.of("UTC");
        ZoneId eastern = ZoneId.of("America/New_York");
        ZonedDateTime utcStartTime = ZonedDateTime.ofInstant(startTime.toInstant(), utc);
        ZonedDateTime utcEndTime = ZonedDateTime.ofInstant(endTime.toInstant(), utc);

        LocalDate date = utcStartTime.toLocalDate();
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);
        LocalDateTime openTime = LocalDateTime.of(date, start);
        LocalDateTime closeTime = LocalDateTime.of(date, end);
        ZonedDateTime estOpenTime = ZonedDateTime.of(openTime, eastern);
        ZonedDateTime estCloseTime = ZonedDateTime.of(closeTime, eastern);
        ZonedDateTime utcOpenTime = ZonedDateTime.ofInstant(estOpenTime.toInstant(), utc);
        ZonedDateTime utcCloseTime = ZonedDateTime.ofInstant(estCloseTime.toInstant(), utc);
        //ZonedDateTime convertedOpenTime = ZonedDateTime.ofInstant(utcOpenTime.toInstant(), local);
        //ZonedDateTime convertedCloseTime = ZonedDateTime.ofInstant(utcCloseTime.toInstant(), local);
        System.out.println("The utc start time is: " +utcStartTime);
        System.out.println("The utc end time is: " +utcEndTime);
        System.out.println("The business open time is: " + utcOpenTime);
        System.out.println("The business close time is: " + utcCloseTime);
        return utcStartTime.isAfter(utcOpenTime.minusNanos(1)) && utcEndTime.isBefore(utcCloseTime.plusNanos(1));

    }

}
