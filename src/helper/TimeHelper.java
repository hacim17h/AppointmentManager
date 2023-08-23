package helper;

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

    /**
     * Determines if times are within business hours. The method takes a start time and time and compares
     * it to the business hours that are based upon ET. The time is compared in UTC and if the given times are within
     * the business hours it will return true.
     * @param startTime when the appointment starts
     * @param endTime when the appointment ends
     * @return if the times are within business hours or not
     */
    public static boolean duringBusinessHours (Timestamp startTime, Timestamp endTime){
        ZoneId eastern = ZoneId.of("America/New_York");
        LocalDate date = startTime.toLocalDateTime().toLocalDate();
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);
        LocalDateTime openTime = LocalDateTime.of(date, start);
        LocalDateTime closeTime = LocalDateTime.of(date, end);
        ZonedDateTime estOpenTime = ZonedDateTime.of(openTime, eastern);
        ZonedDateTime estCloseTime = ZonedDateTime.of(closeTime, eastern);
        Timestamp openTimestamp = Timestamp.from(estOpenTime.toInstant());
        Timestamp closedTimestamp = Timestamp.from(estCloseTime.toInstant());

        boolean duringSameDay = false;
        boolean duringPreviousDay = false;
        boolean duringNextDay = false;
        //Does comparisons for time zones that have extreme time differences where certain times go beyond a single day.
        if ((startTime.after(openTimestamp) || startTime.equals(openTimestamp)) &&
        (endTime.before(closedTimestamp) || endTime.equals(closedTimestamp))){
            duringSameDay = true;
        }
        if ((startTime.after(Timestamp.valueOf(openTimestamp.toLocalDateTime().minusDays(1))) ||
                startTime.equals(Timestamp.valueOf(openTimestamp.toLocalDateTime().minusDays(1)))) &&
                (endTime.before(Timestamp.valueOf(closedTimestamp.toLocalDateTime().minusDays(1))) ||
                        endTime.equals(Timestamp.valueOf(closedTimestamp.toLocalDateTime().minusDays(1))))){
            duringPreviousDay = true;
        }
        if ((startTime.after(Timestamp.valueOf(openTimestamp.toLocalDateTime().plusDays(1))) ||
                startTime.equals(Timestamp.valueOf(openTimestamp.toLocalDateTime().plusDays(1)))) &&
                (endTime.before(Timestamp.valueOf(closedTimestamp.toLocalDateTime().plusDays(1))) ||
                        endTime.equals(Timestamp.valueOf(closedTimestamp.toLocalDateTime().plusDays(1))))){
            duringNextDay = true;
        }

        return duringSameDay || duringPreviousDay || duringNextDay;

    }

}
