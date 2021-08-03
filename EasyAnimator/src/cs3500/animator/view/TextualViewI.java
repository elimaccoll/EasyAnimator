package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Motion;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents a textual view of an animation by printing a table that provides a comprehensive
 * description of all the shapes and motions within an animation.
 */
public final class TextualViewI implements IAnimationView {

  private final IAnimation model;
  private final Appendable ap;
  private final int canvas_x;
  private final int canvas_y;
  private final int canvas_width;
  private final int canvas_height;
  private final int speed;

  /**
   * Constructor for TextualViewI that takes in the necessary parameters in order to create the
   * textual description of the animation.  The Shape information is provided by the model, the
   * canvas details are given by x, y, width, and height, and the speed is from the command line
   * input.
   *
   * @param model  IAnimation model that the textual view will be a description of.
   * @param ap     Appendable to print the table of the textual description of the animation.
   * @param x      x coordinate of the canvas.
   * @param y      y coordinate of the canvas.
   * @param width  width of the canvas.
   * @param height height of the canvas.
   * @param speed  speed of the animation.
   */
  public TextualViewI(IAnimation model, Appendable ap, int x, int y, int width, int height,
      int speed) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be greater than 0.");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0.");
    }
    this.model = model;
    this.ap = ap;
    this.canvas_x = x;
    this.canvas_y = y;
    this.canvas_width = width;
    this.canvas_height = height;
    this.speed = speed;
  }

  /**
   * Creates the table of the comprehensive textual description of an animation.
   *
   * @return String of table of textual description of animation.
   */
  public String toString() {
    StringBuilder builder = new StringBuilder("");
    builder.append(
        String.format("canvas %d %d %d %d\n", canvas_x, canvas_y, canvas_width, canvas_height));

    Map<String, List<Motion>> description = model.getAnimationDescription();
    for (String name : description.keySet()) {
      builder.append(
          String.format("shape %s %s\n", name, model.getShapeType(name).toString().toLowerCase()));
      List<Motion> motions = description.get(name);
      for (Motion motion : motions) {
        IShape s1 = motion.getStartShape();
        IShape s2 = motion.getEndShape();
        String string = String
            .format("motion %s %.2f %d %d %d %d %d %d %d %.2f %d %d %d %d %d %d %d\n",
                name, (double) motion.getStartTime() / speed, s1.getX(), s1.getY(), s1.getWidth(),
                s1.getHeight(), s1.getColor().getRed(), s1.getColor().getGreen(),
                s1.getColor().getBlue(),
                (double) motion.getEndTime() / speed, s2.getX(), s2.getY(), s2.getWidth(),
                s2.getHeight(), s2.getColor().getRed(), s2.getColor().getGreen(),
                s2.getColor().getBlue());
        builder.append(string);
      }
    }
    return builder.toString();
  }

  @Override
  public void render() throws IOException {
    String string = toString();
    this.ap.append(string);
  }
}
