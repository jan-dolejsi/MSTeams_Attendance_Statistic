package fildol.MSTeamsAttendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class FriendlyDurationTest {

  @Test
  void zero() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("");

    // THEN
    assertEquals(0, duration.toDaysPart());
    assertEquals(0, duration.toHoursPart());
    assertEquals(0, duration.toMinutesPart());
    assertEquals(0, duration.toSecondsPart());
    assertEquals(0, duration.getSeconds());
    assertEquals("0s", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void parsesSeconds() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("1s");

    // THEN
    assertEquals(0, duration.toDaysPart());
    assertEquals(0, duration.toHoursPart());
    assertEquals(0, duration.toMinutesPart());
    assertEquals(1, duration.toSecondsPart());
    assertEquals(1, duration.getSeconds());
    assertEquals("1s", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void parsesMinutes() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("1m");

    // THEN
    assertEquals(0, duration.toDaysPart());
    assertEquals(0, duration.toHoursPart());
    assertEquals(1, duration.toMinutesPart());
    assertEquals(0, duration.toSecondsPart());
    assertEquals(60, duration.getSeconds());
    assertEquals("1m", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void parsesHours() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("1h");

    // THEN
    assertEquals(0, duration.toDaysPart());
    assertEquals(1, duration.toHoursPart());
    assertEquals(0, duration.toMinutesPart());
    assertEquals(0, duration.toSecondsPart());
    assertEquals(60 * 60, duration.getSeconds());
    assertEquals("1h", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void parsesDays() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("1d");

    // THEN
    assertEquals(1, duration.toDaysPart());
    assertEquals(0, duration.toHoursPart());
    assertEquals(0, duration.toMinutesPart());
    assertEquals(0, duration.toSecondsPart());
    assertEquals(24 * 60 * 60, duration.getSeconds());
    assertEquals("1d", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void parsesAll() {
    // GIVEN
    final String durationString = "1d 2h 3m 4s";
    final Duration duration = FriendlyDuration.parse(durationString);

    // THEN
    assertEquals(1, duration.toDaysPart());
    assertEquals(2, duration.toHoursPart());
    assertEquals(3, duration.toMinutesPart());
    assertEquals(4, duration.toSecondsPart());
    assertEquals(durationString, FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void normalizesSeconds() {
    // GIVEN
    final Duration duration = FriendlyDuration.parse("61s");

    // THEN
    assertEquals(0, duration.toDaysPart());
    assertEquals(0, duration.toHoursPart());
    assertEquals(1, duration.toMinutesPart());
    assertEquals(1, duration.toSecondsPart());
    assertEquals(61, duration.getSeconds());
    assertEquals("1m 1s", FriendlyDuration.toFriendlyString(duration));
  }

  @Test
  void throwsForInvalid() {
    // GIVEN
    try {
      final Duration duration = FriendlyDuration.parse("invalid");
    } catch (IllegalArgumentException ex) {
      return;
    }
    fail("did not throw");
  }

  @Test
  void addsDuration() {
    // GIVEN
    final Duration day = FriendlyDuration.parse("1d");
    final Duration second = FriendlyDuration.parse("1s");

    // WHEN
    final Duration sum = day.plus(second);

    // THEN
    assertEquals("1d 1s", FriendlyDuration.toFriendlyString(sum));
  }
}
