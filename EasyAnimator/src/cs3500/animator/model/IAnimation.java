package cs3500.animator.model;

import java.util.List;
import java.util.Map;

/**
 * Interface for the model representation of an animation.
 */
public interface IAnimation {

  /**
   * Generates a textual description of the entire animation.
   *
   * @return String of a table that contains a comprehensive textual description of an animation.
   */
  String toString();

  /**
   * Add a new shape name to the animation.
   *
   * @param name : the name of the shape.
   * @param type : the type of the shape (e.g. Rectangle, Ellipse).
   */
  void addShape(String name, String type);

  /**
   * Return the shape type of a given shape name.
   *
   * @param name name of the shape.
   * @return type of the shape.
   */
  ShapeType getShapeType(String name);

  void addMotion(String id, Motion s);

  void addMotionList(String id, List<Motion> s);

  Map<String, List<Motion>> getAnimationDescription();

  /**
   * get the "Cel" at given time unit t.
   *
   * @param timeUnit time to get "Cel" at
   * @return Current state of animation frame at a given time in the form of a Map with shape ids
   * and the corresponding current states of the shapes.
   */
  Map<String, IShape> getFrameAt(int timeUnit);

  /**
   * Represents the bounds of the x, y and time in an animation.
   */
  class Bounds {

    public int minX;
    public int minY;
    public int maxX;
    public int maxY;
    public int maxT;

    /**
     * Constructor for Bounds class that takes in all relevant parameters to establish the limits of
     * the animation - size of canvas and time of animation.
     *
     * @param minX minimum x coordinate on canvas.
     * @param minY minimum y coordinate on canvas.
     * @param maxX maximum x coordinate on canvas.
     * @param maxY minimum y coordinate on canvas.
     * @param maxT maximum amount of time for animation.
     */
    public Bounds(int minX, int minY, int maxX, int maxY, int maxT) {
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;
      this.maxT = maxT;
    }
  }

  /**
   * Gets the bounds of the animation frame.
   *
   * @return Bounds of animation.
   */
  Bounds getAnimationBounds();

  boolean overlapWith(IShape shape1, IShape shape2);
}

