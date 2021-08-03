package cs3500.animator.model;

import java.awt.Color;

/**
 * Model representation of a Rectangle Shape.
 */
public class Rectangle extends Shape {

  /**
   * Constructor for Rectangle that takes in all relevant information needed to create a rectangle
   * Shape.
   *
   * @param width    width of rectangle.
   * @param height   height of rectangle.
   * @param position position of rectangle on canvas.
   * @param angle    angle that the rectangle is oriented at.
   * @param color    color of the rectangle.
   */
  public Rectangle(int width, int height, Position2D position, int angle, Color color) {
    super(width, height, position, angle, color);
  }

  public Rectangle(int width, int height) {
    super(width, height);
  }

  @Override
  public Rectangle clone() throws CloneNotSupportedException {
    return (Rectangle) super.clone();
  }

  @Override
  public ShapeType getShapeType() {
    return ShapeType.Rectangle;
  }
}
