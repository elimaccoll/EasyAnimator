package cs3500.animator.model;

import java.awt.Color;

/**
 * Interface for the model representation of a Shape. Every shape has dimensions (width and height),
 * a position (x and y), an angle of orientation, a color, and a type (Rectangle, Ellipse).
 */
public interface IShape {

  /**
   * Sets the height of a Shape.
   *
   * @param height height of shape.
   * @throws IllegalArgumentException if height is negative.
   */
  void setHeight(int height) throws IllegalArgumentException;

  /**
   * Gets the height of a Shape.
   *
   * @return height of shape.
   */
  int getHeight();

  /**
   * Sets the width of a Shape.
   *
   * @param width width of shape.
   * @throws IllegalArgumentException if width is negative.
   */
  void setWidth(int width) throws IllegalArgumentException;

  /**
   * Gets the width of a Shape.
   *
   * @return width of shape.
   */
  int getWidth();

  /**
   * Sets the position of a Shape using a Position2D.
   *
   * @param point position of shape.
   * @throws NullPointerException if the position is null.
   */
  void setPosition(Position2D point) throws NullPointerException;

  /**
   * Sets the position of a Shape using and x and y coordinate.
   *
   * @param x x coordinate of shape.
   * @param y y coordinate of shape.
   */
  void setPositionXY(double x, double y);

  /**
   * Gets the position of a Shape in the form of a Position2D.
   *
   * @return Position2D position of shape.
   */
  Position2D getPosition();

  /**
   * Gets the x coordinate of a Shape.
   *
   * @return x coordinate of shape.
   */
  int getX();

  /**
   * Gets the y coordinate of a Shape.
   *
   * @return y coordinate of shape.
   */
  int getY();

  /**
   * Gets the color of a Shape in the form of a Color.
   *
   * @return Color color of shape.
   */
  Color getColor();

  /**
   * Gets the angle of orientation of a Shape.
   *
   * @return angle of shape.
   */
  int getAngle();

  /**
   * Sets the color of a Shape using a Color.
   *
   * @param color color of shape.
   * @throws NullPointerException if color is null.
   */
  void setColor(Color color) throws NullPointerException;

  /**
   * Creates a copy of a Shape using clone().
   *
   * @return Shape copy of a shape.
   * @throws CloneNotSupportedException if Shape cannot be cloned.
   */
  public IShape clone() throws CloneNotSupportedException;

  /**
   * Gets the type of a Shape in the for of an enum ShapeType.  Overwritten in each specific Shape
   * class (Rectangle and Ellipse).
   *
   * @return ShapeType shape type of shape.
   */
  ShapeType getShapeType();

  /**
   * Gets the minimum x coordinate of a Shape.
   *
   * @return minimum x coordinate of shape.
   */
  int getMinimumX();

  /**
   * Gets the minimum y coordinate of a Shape.
   *
   * @return minimum y coordinate of shape.
   */
  int getMinimumY();

  /**
   * Gets the maximum x coordinate of a Shape (minimum x value + width of shape).
   *
   * @return maximum x coordinate of shape.
   */
  int getMaximumX();

  /**
   * Gets the maximum y coordinate of a Shape (minimum y value + height of shape).
   *
   * @return maximum y coordinate of shape.
   */
  int getMaximumY();

  /**
   * Checks if a Shape is overlapping with another Shape.
   *
   * @param shape Shape to check if it is overlapping with.
   * @return true if there is overlap, false if not.
   */
  boolean overlapWith(IShape shape);
}