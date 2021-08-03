package cs3500.animator.model;

/**
 * Represents different types of supported shapes.  Currently supports Rectangle and Ellipse.
 */
public enum ShapeType {
  Rectangle("Rectangle"),
  Ellipse("Ellipse"),
  Plus("Plus");

  private final String shapeType;

  /**
   * Sets the ShapeType of a Shape from a String of the shape type.
   *
   * @param shapeType String of shape type.
   */
  ShapeType(String shapeType) {
    this.shapeType = shapeType;
  }

  /**
   * Gets the shape type of a Shape in the form of a ShapeType.
   *
   * @return ShapeType shape type of Shape.
   */
  public ShapeType getShapeType() {
    return valueOf(shapeType);
  }

  @Override
  public String toString() {
    return shapeType;
  }
}