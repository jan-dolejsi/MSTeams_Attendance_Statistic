package fildol.MSTeamsAttendance;

import static java.lang.String.format;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

/** Meeting summary */
public class Meeting {

  /**
   * Fraction of meeting duration that is the threshold for participants to be counted in as
   * eligible (e.g. pass {@code 0.1} for {@code 10%})
   */
  final double requiredDurationFraction;

  private final Map<String, Participant> participants;
  private final List<Participant> eligibleParticipants = new ArrayList<>();
  private final Duration meetingDuration;

  public Meeting(Path csvFile, double requiredDurationFraction) throws IOException {
    if (requiredDurationFraction < 0 || requiredDurationFraction > 1) {
      throw new IllegalArgumentException(format("Invalid fraction: %f", requiredDurationFraction));
    }
    this.requiredDurationFraction = requiredDurationFraction;

    final ReportParser reportParser = new ReportParser(csvFile);
    reportParser.buildParticipant();
    this.participants = reportParser.getParticipants();
    this.meetingDuration = reportParser.getMeetingDuration();

    findEligibleAttendees();
  }

  private void findEligibleAttendees() {
    final double minAttendanceInSeconds =
        requiredDurationFraction * this.meetingDuration.getSeconds();

    for (Participant p : participants.values()) {
      if (p.getDuration().getSeconds() >= minAttendanceInSeconds) {
        eligibleParticipants.add(p);
      }
    }

    eligibleParticipants.sort(
        Comparator.<Participant>comparingLong(p -> p.getDuration().getSeconds()).reversed());
  }

  @Override
  public String toString() {
    return "There were "
        + getQualifyingParticipantCount()
        + " out of "
        + participants.size()
        + " participants that attended more than "
        + requiredDurationFraction * 100
        + "% of full meeting time of "
        + FriendlyDuration.toFriendlyString(meetingDuration);
  }

  public int getQualifyingParticipantCount() {
    return getEligibleParticipants().size();
  }

  public Collection<Participant> getEligibleParticipants() {
    return this.eligibleParticipants;
  }

  public Map<String, Participant> getParticipants() {
    return this.participants;
  }
}
