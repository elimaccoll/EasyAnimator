package cs3500.animator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A helper to read tempo slow motion data from input file.
 */
public class SlomoReader {

  public static final class SlowMotion {

    int startFrame;
    int endFrame;
    int speed;

    public SlowMotion(int start, int end, int speed) {
      this.startFrame = start;
      this.endFrame = end;
      this.speed = speed;
    }

    public int getStartFrame() {
      return startFrame;
    }

    public int getEndFrame() {
      return endFrame;
    }

    public int getSpeed() {
      return speed;
    }
  }

  private List<SlowMotion> slowMotions = new ArrayList<>();

  public List<SlowMotion> parseFile(Readable readable) {
    if (readable == null) {
      return slowMotions;
    }
    Objects.requireNonNull(readable, "Must have non-null readable source");
    //Objects.requireNonNull(builder, "Must provide a non-null AnimationBuilder");
    Scanner s = new Scanner(readable);
    // Split at whitespace, and ignore # comment lines
    s.useDelimiter(Pattern.compile("(\\p{Space}+|#.*)+"));
    while (s.hasNext()) {
      String word = s.next();
      switch (word) {
        case "#":
          s.nextLine();
          break;
        case "slow-motion":
          readTempo(s);
          break;
        default:
          throw new IllegalStateException("Unexpected keyword: " + word + s.nextLine());
      }
    }
    return slowMotions;
  }

  private void readTempo(Scanner s) {
    int[] vals = new int[4];
    String[] fieldNames = {"from", "to", "speed"};
    for (int i = 0; i < 3; i++) {
      vals[i] = getInt(s, "Tempo", fieldNames[i]);
    }
    slowMotions.add(new SlowMotion(vals[0], vals[1], vals[2]));
  }

  private static int getInt(Scanner s, String label, String fieldName) {
    if (s.hasNextInt()) {
      return s.nextInt();
    } else if (s.hasNext()) {
      throw new IllegalStateException(
          String.format("%s: expected integer for %s, got: %s", label, fieldName, s.next()));
    } else {
      throw new IllegalStateException(
          String.format("%s: expected integer for %s, but no more input available",
              label, fieldName));
    }
  }
}
