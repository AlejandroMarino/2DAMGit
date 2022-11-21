package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MainFecha {

    public static void main(String[] args) {
        // Create a date time object with time zone for current date time and default time zone:
        ZonedDateTime todayWithDefaultTimeZone=ZonedDateTime.now();
        DateTimeFormatter formatTodayWithZoneOffset=DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss Z");
        System.out.format("Formatted date time with default zone offset is %s\n", todayWithDefaultTimeZone.format(formatTodayWithZoneOffset));
        DateTimeFormatter formatTodayWithZoneName=DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss z");
        System.out.format("Formatted date time with default zone name is %s\n", todayWithDefaultTimeZone.format(formatTodayWithZoneName));
        DateTimeFormatter formatTodayWithZoneId=DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss VV");
        System.out.format("Formatted date time with default zone ID is %s\n", todayWithDefaultTimeZone.format(formatTodayWithZoneId));

        LocalDateTime.parse("2014-12-09T13:50:51.644000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        String fecha = LocalDateTime.parse("2014-04-29T14:18:17-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-SSSS")).toString();
        System.out.println(fecha);
    }
}
