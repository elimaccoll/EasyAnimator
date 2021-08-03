package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Motion;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents an SVG view of an animation by creating a textual description of the animation that is
 * compliant with the SVG file format.  The SVG file format is an XML-based format that is used to
 * describe the animations.
 */
public final class SVGViewI implements IAnimationView {

  private final IAnimation model;
  private final Appendable ap;
  private final int canvas_x;
  private final int canvas_y;
  private final int canvas_width;
  private final int canvas_height;
  protected final double msPerUnit;

  /**
   * Constructor for SVGViewI that takes in the necessary parameters in order to create the SVG
   * formatted description of the animation.  The Shape information is provided by the model, the
   * canvas details are given by x, y, width, and height, and the speed is from the command line
   * input.
   *
   * @param model  IAnimation model that the textual view will be a description of.
   * @param ap     Appendable to print the table of the textual description of the animation.
   * @param x      x coordinate of the canvas.
   * @param y      y coordinate of the canvas.
   * @param width  width of the canvas.
   * @param height height of the canvas.
   * @param speed  speed of the animation.
   */
  public SVGViewI(IAnimation model, Appendable ap, int x, int y, int width, int height, int speed) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be greater than 0.");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0.");
    }
    this.model = model;
    this.ap = ap;
    this.canvas_x = x;
    this.canvas_y = y;
    this.canvas_width = width;
    this.canvas_height = height;
    msPerUnit = 1000.0 / speed;
  }

  /**
   * Adapts motions for Rectangle class shapes into XML compliant SVG file format.
   *
   * @param name    Unique id of the Rectangle.
   * @param motions Corresponding list of motions of the Rectangle in the animation.
   * @throws IOException for bad appendable.
   */
  protected void rectangleSVG(String name, List<Motion> motions) throws IOException {
    ap.append(openRectangle(name, motions.get(0).getStartShape()));
    ap.append(appear((int) (motions.get(0).getStartTime() * msPerUnit)));
    for (Motion motion : motions) {
      int tstart = (int) (motion.getStartTime() * msPerUnit);
      int duration = (int) ((motion.getEndTime() - motion.getStartTime()) * msPerUnit);
      if (motion.getStartShape().getX() != motion.getEndShape().getX()) {
        ap.append(animateMotion("x", duration, tstart, motion.getStartShape().getX(),
            motion.getEndShape().getX()));
      }
      if (motion.getStartShape().getY() != motion.getEndShape().getY()) {
        ap.append(animateMotion("y", duration, tstart, motion.getStartShape().getY(),
            motion.getEndShape().getY()));
      }
      if (!motion.getStartShape().getColor().equals(motion.getEndShape().getColor())) {
        ap.append(animateMotion("fill", duration, tstart, motion.getStartShape().getColor(),
            motion.getEndShape().getColor()));
      }
      if (motion.getStartShape().getWidth() != motion.getEndShape().getWidth()) {
        ap.append(animateMotion("width", duration, tstart, motion.getStartShape().getWidth(),
            motion.getEndShape().getWidth()));
      }
      if (motion.getStartShape().getHeight() != motion.getEndShape().getHeight()) {
        ap.append(animateMotion("height", duration, tstart, motion.getStartShape().getHeight(),
            motion.getEndShape().getHeight()));
      }
    }
    ap.append(closeRectangle());
  }

  /**
   * Adapts motions for Ellipse class shapes into XML compliant SVG file format.
   *
   * @param name    Unique id of the Ellipse.
   * @param motions Corresponding list of motions of the Ellipse in the animation.
   * @throws IOException for bad appendable.
   */
  protected void ellipseSVG(String name, List<Motion> motions) throws IOException {
    ap.append(openEllipse(name, motions.get(0).getStartShape()));
    ap.append(appear((int) (motions.get(0).getStartTime() * msPerUnit)));
    for (Motion motion : motions) {
      int tstart = (int) (motion.getStartTime() * msPerUnit);
      int duration = (int) ((motion.getEndTime() - motion.getStartTime()) * msPerUnit);
      if (motion.getStartShape().getX() != motion.getEndShape().getX()) {
        ap.append(animateMotion("cx", duration, tstart,
            motion.getStartShape().getX() + motion.getStartShape().getWidth() / 2,
            motion.getEndShape().getX() + motion.getEndShape().getWidth() / 2));
      }
      if (motion.getStartShape().getY() != motion.getEndShape().getY()) {
        ap.append(animateMotion("cy", duration, tstart,
            motion.getStartShape().getY() + motion.getStartShape().getHeight() / 2,
            motion.getEndShape().getY() + motion.getEndShape().getHeight() / 2));
      }
      if (!motion.getStartShape().getColor().equals(motion.getEndShape().getColor())) {
        ap.append(animateMotion("fill", duration, tstart, motion.getStartShape().getColor(),
            motion.getEndShape().getColor()));
      }
    }
    ap.append(closeEllipse());
  }

  /**
   * Makes animation visible.
   *
   * @param time time that animation becomes visible at.
   * @return Code that makes the shape visible.
   */
  protected String appear(int time) {
    String string = String.format(
        "<animate attributeType=\"xml\" begin=\"%dms\" dur=\"1ms\" attributeName=\"visibility\" "
            + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />", time);
    return string + "\n";
  }

  @Override
  public void render() throws IOException {
    ap.append(openSVG());
    Map<String, List<Motion>> animation = model.getAnimationDescription();
    for (String name : animation.keySet()) {
      List<Motion> motions = animation.get(name);
      String type = motions.get(0).getStartShape().getShapeType().toString();
      if (type.equals("Rectangle")) {
        rectangleSVG(name, motions);
      } else if (type.equals("Ellipse")) {
        ellipseSVG(name, motions);
      } else {
        System.err.println("Unsupported shape : " + type);
      }
    }
    ap.append(closeSVG());
    String string = ap.toString();
    //System.out.println(string);
  }

  /**
   * Begins creating animation in XML compliant SVG file format.
   *
   * @return code to begin generating animation.
   */
  public String openSVG() {
    String string = String.format(
        "<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n xmlns=\"http://www.w3.org/2000/svg\">\n",
        this.canvas_width + this.canvas_x, this.canvas_height + this.canvas_y);
    return string;
  }

  /**
   * Closes generation of animation.
   *
   * @return code to close the creation of the animation.
   */
  public String closeSVG() {
    return "</svg>";
  }

  /**
   * Opens up creation of Rectangles within SVG view animation.
   *
   * @param name id of Rectangle s.
   * @param s    Rectangle being animated.
   * @return Code to open creation of Rectangle in SVG animation.
   */
  public String openRectangle(String name, IShape s) {
    String string = String.format(
        "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"rgb(%d,%d,%d)\" "
            + "visibility=\"hidden\" >",
        name, s.getX(), s.getY(), s.getWidth(), s.getHeight(), s.getColor().getRed(),
        s.getColor().getGreen(),
        s.getColor().getBlue());
    return string + "\n";
  }

  /**
   * Closes creation of Rectangle within SVG view animation.
   *
   * @return Code to close creation of Rectangle in SVG animation.
   */
  public String closeRectangle() {
    return "</rect>" + "\n";
  }

  /**
   * Opens up creation of Ellipse within SVG view animation.
   *
   * @param name id of Ellipse s.
   * @param s    Ellipse being animated.
   * @return Code to open creation of Ellipse in SVG animation.
   */
  public String openEllipse(String name, IShape s) {
    String string = String.format(
        "<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" fill=\"rgb(%d,%d,%d)\" "
            + "visibility=\"hidden\" >",
        name, s.getX() + s.getWidth() / 2, s.getY() + s.getHeight() / 2, s.getWidth() / 2,
        s.getHeight() / 2,
        s.getColor().getRed(), s.getColor().getGreen(), s.getColor().getBlue());
    return string + "\n";
  }

  /**
   * Implements motions of Shapes into SVG animation for position changes.
   *
   * @param attribute Style of Shape.
   * @param duration  Duration of animation.
   * @param tstart    Start time of animation.
   * @param from      Initial position of Shape.
   * @param to        End position of Shape.
   * @return Code for adding a motion into the SVG view animation for position changes.
   */
  public String animateMotion(String attribute, int duration, int tstart, int from, int to) {
    String string = String.format(
        "<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
            + "from=\"%d\" to=\"%d\" fill=\"freeze\" />",
        tstart, duration, attribute, from, to);
    return string + "\n";
  }

  /**
   * Implements motions of Shapes into SVG animation for color changes.
   *
   * @param attribute Style of Shape.
   * @param duration  Duration of animation.
   * @param tstart    Start time.
   * @param from      Initial Color of Shape.
   * @param to        End Color of Shape.
   * @return Code for adding a motion into the SVG view animation for color changes.
   */
  public String animateMotion(String attribute, int duration, int tstart, Color from, Color to) {
    String string = String.format(
        "<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
            + "from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" fill=\"freeze\" />",
        tstart, duration, attribute, from.getRed(), from.getGreen(), from.getBlue(), to.getRed(),
        to.getGreen(), to.getBlue());
    return string + "\n";
  }

  /**
   * Closes creation of Ellipse within SVG view animation.
   *
   * @return Code to close creation of Ellipse in SVG animation.
   */
  public String closeEllipse() {
    return "</ellipse>" + "\n";
  }
}
