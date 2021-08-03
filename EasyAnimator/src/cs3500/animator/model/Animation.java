package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Model representation of an animation that contains a Map of shape ids and corresponding shapes in
 * addition to a Map for the list of motions to be carried out in the animation for each shape.
 */
public class Animation implements IAnimation {

  private final Map<String, List<Motion>> descriptions = new LinkedHashMap<>();
  private final Map<String, ShapeType> shapeTypes = new HashMap<>();

  /**
   * Builder class for the model representation of an Animation that allows contains the logic for
   * generating an appropriate canvas.
   */
  public static class Builder implements AnimationBuilder<IAnimation> {

    private int canvas_x;
    private int canvas_y;
    private int canvas_width;
    private int canvas_height;

    public int getCanvasX() {
      return canvas_x;
    }

    public int getCanvasY() {
      return canvas_y;
    }

    public int getCanvasWidth() {
      return canvas_width;
    }

    public int getCanvasHeight() {
      return canvas_height;
    }

    public double getSpeed() {
      return speed;
    }

    public Appendable getAp() {
      return ap;
    }

    private final double speed;
    private final Appendable ap;
    private final StringBuilder builder = new StringBuilder();
    IAnimation animation = new Animation();

    /**
     * Constructor for the Builder class that takes in the command input for speed and an Appendable
     * for the comprehensive textual description of a animation.
     *
     * @param speed Desired speed of animation.
     * @param ap    Appendable used to output text description of an animation.
     */
    public Builder(int speed, Appendable ap) {
      if (speed <= 0) {
        throw new IllegalArgumentException("Speed must be greater than 0.");
      }
      if (ap == null) {
        throw new IllegalArgumentException("Appendable cannot be null.");
      }
      this.speed = speed;
      this.ap = ap;
    }

    @Override
    public IAnimation build() {
      try {
        //ap.append(animation.toString());
        ap.append(builder.toString());
      } catch (IOException exp) {
        throw new IllegalStateException("Bad Appendable.");
      } catch (NullPointerException e) {
        throw new IllegalStateException("Null Appendable.");
      }
      return animation;
    }

    @Override
    public AnimationBuilder<IAnimation> setBounds(int x, int y, int width, int height) {
      //System.out.println("setBounds");
      if (width <= 0 || height <= 0) {
        throw new IllegalArgumentException("Canvas dimensions must be greater than 0.");
      }
      this.canvas_x = x;
      this.canvas_y = y;
      this.canvas_width = width;
      this.canvas_height = height;
      return this;
    }

    @Override
    public AnimationBuilder<IAnimation> declareShape(String name, String type) {
      //System.out.println("declareShape");
      animation.addShape(name, type);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimation> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {
      if (animation.getShapeType(name) == null) {
        throw new IllegalArgumentException("Attempted to move shape that does not exist.");
      }
      //System.out.println("addMotion");
      ShapeType type = animation.getShapeType(name);
      switch (type) {
        case Rectangle:
          IShape shape1 = new Rectangle(w1, h1, new Position2D(x1, y1), 0, new Color(r1, g1, b1));
          IShape shape2 = new Rectangle(w2, h2, new Position2D(x2, y2), 0, new Color(r2, g2, b2));
          animation.addMotion(name, new Motion(shape1, shape2, t1, t2));
          break;
        case Ellipse:
          shape1 = new Ellipse(w1, h1, new Position2D(x1, y1), 0, new Color(r1, g1, b1));
          shape2 = new Ellipse(w2, h2, new Position2D(x2, y2), 0, new Color(r2, g2, b2));
          animation.addMotion(name, new Motion(shape1, shape2, t1, t2));
          break;
        case Plus:
          shape1 = new Plus(w1, h1, new Position2D(x1, y1), 0, new Color(r1, g1, b1));
          shape2 = new Plus(w2, h2, new Position2D(x2, y2), 0, new Color(r2, g2, b2));
          animation.addMotion(name, new Motion(shape1, shape2, t1, t2));
          break;
        default:
          throw new IllegalArgumentException("Unsupported shape type " + type);
      }
      return this;
    }
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();

    int linecount = 0;
    for (String id : descriptions.keySet()) {
      List<Motion> listMotion = descriptions.get(id);
      if (linecount++ != 0) {
        str.append("\n");
      }

      str.append("shape ").append(id).append(" ")
          .append(listMotion.get(0).getStartShape().getShapeType().toString().toLowerCase())
          .append("\n");
      for (int j = 0; j < listMotion.size(); j++) {
        str.append("motion");
        str.append(" ");
        str.append(id);
        str.append(" ");
        str.append(listMotion.get(j).getStartTime());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getX());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getY());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getWidth());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getHeight());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getColor().getRed());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getColor().getGreen());
        str.append(" ");
        str.append(listMotion.get(j).getStartShape().getColor().getBlue());

        str.append("  ");

        str.append(listMotion.get(j).getEndTime());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getX());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getY());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getWidth());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getHeight());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getColor().getRed());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getColor().getGreen());
        str.append(" ");
        str.append(listMotion.get(j).getEndShape().getColor().getBlue());
        if (j != listMotion.size() - 1) {
          str.append("\n");
        }
      }
    }
    return str.toString();
  }

  @Override
  public void addShape(String id, String type) {
    String capType = type.substring(0, 1).toUpperCase() + type.substring(1);
    this.shapeTypes.put(id, ShapeType.valueOf(capType));
  }

  @Override
  public ShapeType getShapeType(String id) {
    return shapeTypes.get(id);
  }

  @Override
  public void addMotion(String id, Motion s) {
    List<Motion> segList = new ArrayList<Motion>();
    if (descriptions.get(id) != null) {
      segList = descriptions.get(id);
    }
    segList.add(s);
    descriptions.putIfAbsent(id, segList);
  }

  @Override
  public void addMotionList(String id, List<Motion> s) {
    descriptions.putIfAbsent(id, s);
  }

  @Override
  public Map<String, List<Motion>> getAnimationDescription() {
    return this.descriptions;
  }

  /**
   * Helper method for linear interpolation.  Used in tweening method to assist with computations.
   *
   * @param x1 x coordinate of start shape.
   * @param y1 y coordinate of start shape.
   * @param x2 x coordinate of end shape.
   * @param y2 y coordinate of end shape.
   * @param x  change in x.
   * @return resulting y value.
   */
  public double linearInterp(double x1, double y1, double x2, double y2, double x) {
    double y = y1 + (y2 - y1) / (x2 - x1) * (x - x1);
    return y;
  }

  /**
   * Helper method for getFrameAt that contains logic for "tweening" as described in the assignment
   * description for producing shapes within an animation.
   *
   * @param motion   Motion of the shape.
   * @param timeUnit Time unit to obtain "Cel" at.
   * @return Shape that represents the state of the initial shape during its motion at given time.
   */
  IShape tweening(Motion motion, int timeUnit) {
    int t1 = motion.getStartTime();
    int t2 = motion.getEndTime();
    IShape shape1 = motion.getStartShape();
    IShape shape2 = motion.getEndShape();
    double frac = ((double) timeUnit - t1) / (t2 - t1);
    Shape newShape = null;
    try {
      newShape = (Shape) motion.getStartShape().clone();
    } catch (CloneNotSupportedException exp) {
      throw new IllegalStateException("Shape clone failed");
    }
    if (t1 == t2) {
      return newShape;
    }

    double newX = linearInterp(t1, shape1.getX(), t2, shape2.getX(), timeUnit);
    double newY = linearInterp(t1, shape1.getY(), t2, shape2.getY(), timeUnit);
    double newW = linearInterp(t1, shape1.getWidth(), t2, shape2.getWidth(), timeUnit);
    double newH = linearInterp(t1, shape1.getHeight(), t2, shape2.getHeight(), timeUnit);
    int newR = (int) linearInterp(t1, shape1.getColor().getRed(), t2, shape2.getColor().getRed(),
        timeUnit);
    int newG = (int) linearInterp(t1, shape1.getColor().getGreen(), t2,
        shape2.getColor().getGreen(), timeUnit);
    int newB = (int) linearInterp(t1, shape1.getColor().getBlue(), t2, shape2.getColor().getBlue(),
        timeUnit);
    newShape.setPosition(new Position2D(newX, newY));
    newShape.setWidth((int) newW);
    newShape.setHeight((int) newH);
    newShape.setColor(new Color(newR, newG, newB));
    return newShape;
  }

  @Override
  public Map<String, IShape> getFrameAt(int timeUnit) {
    Map<String, IShape> frame = new LinkedHashMap<>();
    for (String name : descriptions.keySet()) {
      List<Motion> motions = descriptions.get(name);
      for (Motion motion : motions) {
        if (motion.getStartTime() <= timeUnit && timeUnit <= motion.getEndTime()) {
          IShape shape = tweening(motion, timeUnit);
          frame.put(name, shape);
        }
      }
    }
    return frame;
  }

  @Override
  public Bounds getAnimationBounds() {
    int minX = 9999;
    int minY = 9999;
    int maxX = 0;
    int maxY = 0;
    int maxT = 0;
    for (List<Motion> motions : descriptions.values()) {
      for (Motion motion : motions) {
        IShape shape1 = motion.getStartShape();
        IShape shape2 = motion.getEndShape();
        if (shape1.getMinimumX() < minX) {
          minX = shape1.getMinimumX();
        }
        if (shape2.getMinimumX() < minX) {
          minX = shape2.getMinimumX();
        }
        if (shape1.getMinimumY() < minY) {
          minY = shape1.getMinimumY();
        }
        if (shape2.getMinimumY() < minY) {
          minY = shape2.getMinimumY();
        }

        if (shape1.getMaximumX() > maxX) {
          maxX = shape1.getMaximumX();
        }
        if (shape2.getMaximumX() > maxX) {
          maxX = shape2.getMaximumX();
        }
        if (shape1.getMaximumY() > maxY) {
          maxY = shape1.getMaximumY();
        }
        if (shape2.getMaximumY() > maxY) {
          maxY = shape2.getMaximumY();
        }

        if (motion.getEndTime() > maxT) {
          maxT = motion.getEndTime();
        }
      }
    }
    return new Bounds(minX, minY, maxX, maxY, maxT);
  }

  @Override
  public boolean overlapWith(IShape shape1, IShape shape2) {
    return shape1.overlapWith(shape2);
  }
}
