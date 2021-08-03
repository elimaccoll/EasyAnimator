package cs3500.animator.view;

import cs3500.animator.model.IAnimation;

/**
 * Factory class for creating supported views for animations based on the command inputs and canvas
 * specifications.
 */
public class ViewFactory {

  /**
   * Set of supported view types for an animation.
   */
  public enum ViewName {
    TEXT("TEXT"),
    VISUAL("VISUAL"),
    SVG("SVG"),
    INTERACTIVE("INTERACTIVE");

    private final String viewName;

    /**
     * Converts String of inputted view name to corresponding enum ViewName.
     *
     * @param viewName Desired view type for animation.
     */
    ViewName(String viewName) {
      this.viewName = viewName;
    }

    /**
     * Obtains view type of animation as an enum ViewName.
     *
     * @return enum ViewName of view type of animation.
     */
    public ViewName getViewName() {
      return valueOf(viewName);
    }

    /**
     * Obtains view type of animation as a String.
     *
     * @return String of view type of animation.
     */
    @Override
    public String toString() {
      return viewName;
    }
  }

  private IAnimation model;
  private Appendable ap;
  private final int x;
  private final int y;
  private final int w;
  private final int h;
  private final int speed;
  private final Readable slomoRd;

  /**
   * Constructor for ViewFactory that generates a supported view based on the inputted parameters.
   * Arguments are obtained from a combination of user command inputs and Animation model logic for
   * the canvas.
   *
   * @param model IAnimation model to make an animation of.
   * @param ap    Appendable used for text output when necessary (text and svg).
   * @param x     x coordinate of canvas.
   * @param y     y coordinate of canvas.
   * @param w     width of canvas.
   * @param h     height of canvas.
   * @param speed speed of animation.
   * @param slomoRd readable for slow motion input.
   */
  public ViewFactory(IAnimation model, Appendable ap, int x, int y, int w, int h, int speed, Readable slomoRd) {
    this.model = model;
    this.ap = ap;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.speed = speed;
    this.slomoRd=slomoRd;
  }

  /**
   * Method to create a view for an animation based on the user command input of view type. Utilizes
   * the fields initialized in the constructor to generate the view with the proper specifications.
   */
  public IAnimationView createView(ViewName name) {
    switch (name) {
      case TEXT:
        return new TextualViewI(model, ap, x, y, w, h, speed);
      case VISUAL:
        return new VisualViewI(model, ap, x, y, w, h, speed);
      case SVG:
        return new SVGViewI(model, ap, x, y, w, h, speed);
      case INTERACTIVE:
        return new CompositeView(model, ap, x, y, w, h, speed, slomoRd);
      default:
        throw new IllegalArgumentException("Unsupported view name: " + name);
    }
  }
}
