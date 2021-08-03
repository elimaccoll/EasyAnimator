package cs3500.animator.model;

/**
 * Model representation of a motion in an animation.  A Motion is described with the initial state
 * of a shape, the end state of the shape, the start time of the motion, and the end time of the
 * motion.
 */
public class Motion implements IMotion {

  private final IShape startShape;
  private final IShape endShape;
  private final int startTime;
  private final int endTime;

  /**
   * Constructor for Motion class that takes in the necessary parameters to define a motion of a
   * shape in an animation.
   *
   * @param startShape Initial state of shape.
   * @param endShape   End state of shape.
   * @param startTime  Start time of the motion.
   * @param endTime    End time of the motion.
   */
  public Motion(IShape startShape, IShape endShape, int startTime, int endTime) {
    if (startTime < 0) {
      throw new IllegalArgumentException("Start time cannot be negative.");
    }
    if (startTime > endTime) {
      throw new IllegalArgumentException("End time cannot be before start time.");
    }
    if (startShape == null || endShape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    this.startShape = startShape;
    this.endShape = endShape;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public IShape getStartShape() {
    return this.startShape;
  }

  @Override
  public IShape getEndShape() {
    return this.endShape;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }
}
