package cs3500.animator.model;

import java.util.Objects;

/**
 * This class represents a 2D position.
 */
public final class Position2D {

  private double x;
  private double y;

  /**
   * Initialize this object to the specified position.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   */
  public Position2D(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Copy constructor for a Position2D.
   *
   * @param v position to be copied.
   */
  public Position2D(Position2D v) {
    this.setX(v.x);
    this.setY(v.y);
  }

  /**
   * Gets the x coordinate of this position.
   *
   * @return x coordinate of position.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate of this position.
   *
   * @return y coordinate of position.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the x coordinate of this object.
   *
   * @param x x coordinate.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Sets the y coordinate of this object.
   *
   * @param y y coordinate.
   */
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01)
        && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}