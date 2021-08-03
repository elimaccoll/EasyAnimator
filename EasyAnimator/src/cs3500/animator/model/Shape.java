package cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Model representation of a shape.  This is an abstract class that is extended to create specific
 * types of shapes (Rectangle and Ellipse).  Every shape has dimensions, a position, an angle of
 * orientation, a color, and a type (Rectangle, Ellipse).  Also contains a method to check if two
 * Shapes are overlapping.
 */
public abstract class Shape implements Cloneable, IShape {

  private double height;
  private double width;
  private int angle;
  private Position2D position;
  private Color color;

  /**
   * Constructor for Shape that takes in all the necessary parameters to create a Shape and is
   * called within each specific shape class (Rectangle and Ellipse) that extends this class.
   *
   * @param width    width of shape.
   * @param height   height of shape.
   * @param position position of shape in the form of a Position2D.
   * @param angle    angle of orientation of shape.
   * @param color    color of shape.
   */
  public Shape(int width, int height, Position2D position, int angle, Color color) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Cannot be zero or less");
    }
    this.width = width;
    this.height = height;
    this.position = position;
    this.angle = angle;
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
  }

  /**
   * Additional Constructor for Shape that only takes the dimensions of the shape and sets default
   * values for the remaining fields.
   *
   * @param width width of shape.
   * @param height height of shape.
   */
  public Shape(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Cannot be zero or less");
    }
    this.width = width;
    this.height = height;
    this.position = new Position2D(0, 0);
    this.color = Color.black;
    this.angle = 0;
  }

  @Override
  public void setHeight(int height) throws IllegalArgumentException {
    if (height <= 0) {
      throw new IllegalArgumentException("Height cannot be zero less");
    }
    this.height = height;
  }

  @Override
  public int getHeight() {
    return (int) Math.round(this.height);
  }

  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    if (width <= 0) {
      throw new IllegalArgumentException("Width cannot be zero less");
    }
    this.width = width;
  }

  @Override
  public int getWidth() {
    return (int) Math.round(this.width);
  }

  @Override
  public void setPosition(Position2D position) throws NullPointerException {
    this.position = Objects.requireNonNull(position);
  }

  @Override
  public void setPositionXY(double x, double y) {
    this.position.setX(x);
    this.position.setY(y);
  }

  @Override
  public Position2D getPosition() {
    return new Position2D(this.position.getX(), this.position.getY());
  }

  @Override
  public int getX() {
    return (int) Math.round(this.getPosition().getX());
  }

  @Override
  public int getY() {
    return (int) Math.round(this.getPosition().getY());
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void setColor(Color color) throws NullPointerException {
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public int getAngle() {
    return this.angle;
  }

  @Override
  public Shape clone() throws CloneNotSupportedException {
    return (Shape) super.clone();
  }

  @Override
  public int getMinimumX() {
    return getX();
  }

  @Override
  public int getMinimumY() {
    return getY();
  }

  @Override
  public int getMaximumX() {
    return getX() + (int) width;
  }

  @Override
  public int getMaximumY() {
    return getY() + (int) height;
  }

  @Override
  public boolean overlapWith(IShape that) {
    boolean no_overlap = (this.getMinimumX() > that.getMaximumX())
        || (this.getMaximumX() < that.getMinimumX())
        || (this.getMinimumY() > that.getMaximumY())
        || (this.getMaximumY() < that.getMinimumY());
    return !no_overlap;
  }
}
