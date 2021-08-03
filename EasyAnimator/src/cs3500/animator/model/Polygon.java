package cs3500.animator.model;

import java.awt.*;

abstract public class Polygon extends Shape {

  int[] xs;
  int[] ys;

  public Polygon(int width, int height, Position2D position, int angle, Color color) {
    super(width, height, position, angle, color);
    setupXYCoords();
  }

  public Polygon(int width, int height) {
    super(width, height);
    setupXYCoords();
  }

  @Override
  public Polygon clone() throws CloneNotSupportedException {
    Polygon newps = (Polygon) super.clone();
    return newps;
  }

  @Override
  public ShapeType getShapeType() {
    return ShapeType.Plus;
  }

  abstract public void setupXYCoords();

  public int[] getXCoords() {
    return this.xs;
  }

  public int[] getYCoords() {
    return this.ys;
  }
}