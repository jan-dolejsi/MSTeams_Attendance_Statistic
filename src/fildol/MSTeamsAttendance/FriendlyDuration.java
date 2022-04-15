package fildol.MSTeamsAttendance;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendlyDuration {

  static Pattern pattern =
      Pattern.compile("^((\\d+)d)?\\s*((\\d+)h)?\\s*((\\d+)m)?\\s*((\\d+)s)?$");

  private FriendlyDuration() {}

  public static Duration parse(String durationString) {
    final Matcher m = pattern.matcher(durationString);
    final boolean success = m.find();
    if (!success) {
      throw new IllegalArgumentException("Invalid duration: " + durationString);
    }

    Duration d = Duration.ofDays(0);

    if (m.group(1) != null) {
      d = d.plusDays(Integer.parseInt(m.group(2)));
    }
    if (m.group(3) != null) {
      d = d.plusHours(Integer.parseInt(m.group(4)));
    }
    if (m.group(5) != null) {
      d = d.plusMinutes(Integer.parseInt(m.group(6)));
    }
    if (m.group(7) != null) {
      d = d.plusSeconds(Integer.parseInt(m.group(8)));
    }

    return d;
  }

  public static String toFriendlyString(Duration d) {
    final StringBuilder sb = new StringBuilder();

    if (d.getSeconds() == 0) {
      sb.append("0s");
    } else {
      appendAmount(sb, d.toDaysPart(), 'd'); // get(ChronoUnit.DAYS)
      appendAmount(sb, d.toHoursPart(), 'h');
      appendAmount(sb, d.toMinutesPart(), 'm');
      appendAmount(sb, d.toSecondsPart(), 's');
    }

    return sb.toString();
  }

  private static void appendAmount(StringBuilder sb, long amount, char unit) {
    if (amount > 0) {
      if (sb.length() > 0) {
        sb.append(' ');
      }
      sb.append(amount).append(unit);
    }
  }
}
