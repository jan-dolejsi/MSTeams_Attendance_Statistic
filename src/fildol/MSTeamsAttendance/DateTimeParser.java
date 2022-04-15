package fildol.MSTeamsAttendance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

  public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("M/d/y, h:m:s a");

  public DateTimeParser() {}

  public static LocalDateTime parseDateTime(String startTime) {
    return LocalDateTime.parse(startTime, FORMAT);
  }
}
