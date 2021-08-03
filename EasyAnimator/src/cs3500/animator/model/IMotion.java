package cs3500.animator.model;

/**
 * Interface for the model representation of a Motion.
 */
public interface IMotion {

  /**
   * Gets the initial state of the shape.
   *
   * @return IShape of the initial state of the shape.
   */
  IShape getStartShape();

  /**
   * Gets the end state of the shape.
   *
   * @return IShape of the end state of the shape.
   */
  IShape getEndShape();

  /**
   * Gets the start time of the motion.
   *
   * @return start time of motion.
   */
  int getStartTime();

  /**
   * Gets the end time of the motion.
   *
   * @return end time of motion.
   */
  int getEndTime();
}
