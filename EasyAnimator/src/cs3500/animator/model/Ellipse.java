package cs3500.animator.model;

import java.awt.Color;

/**
 * Model representation of an Ellipse Shape.
 */
public class Ellipse extends Shape {

  /**
   * Constructor for Ellipse that takes in all relevant information needed to create an ellipse
   * Shape.
   *
   * @param width    width of ellipse.
   * @param height   height of ellipse.
   * @param position position of ellipse on canvas.
   * @param angle    angle that the ellipse is oriented at.
   * @param color    color of the ellipse.
   */
  public Ellipse(int width, int height, Position2D position, int angle, Color color) {
    super(width, height, position, angle, color);
  }

  public Ellipse(int width, int height) {
    super(width, height);
  }

  @Override
  public Ellipse clone() throws CloneNotSupportedException {
    return (Ellipse) super.clone();
  }

  @Override
  public ShapeType getShapeType() {
    return ShapeType.Ellipse;
  }
}
