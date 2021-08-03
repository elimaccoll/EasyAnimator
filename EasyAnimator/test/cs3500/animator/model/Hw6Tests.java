package cs3500.animator.model;

import static org.junit.Assert.assertEquals;

import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.SVGViewI;
import cs3500.animator.view.TextualViewI;
import java.awt.Color;
import java.io.IOException;
import org.junit.Test;

/**
 * Test class for HW6 tests. Includes tests for SVG and Textual views of animations.
 */
public class Hw6Tests {

  private Animation.Builder builder;
  private IAnimation model;
  private Appendable ap;

  // Rectangles
  IShape shape1 = new Rectangle(50, 100, new Position2D(200, 200), 0, Color.red);
  IShape shape2 = new Rectangle(50, 100, new Position2D(10, 200), 0, Color.red);
  IShape shape3 = new Rectangle(50, 100, new Position2D(300, 300), 0, Color.red);
  IShape shape4 = new Rectangle(25, 100, new Position2D(300, 300), 0, Color.red);
  IShape shape5 = new Rectangle(50, 100, new Position2D(200, 200), 0, Color.red);
  // Ellipses
  IShape shape6 = new Ellipse(25, 40, new Position2D(50, 100), 0, Color.blue);
  IShape shape7 = new Ellipse(25, 40, new Position2D(200, 55), 0, Color.blue);
  IShape shape8 = new Ellipse(50, 100, new Position2D(200, 300), 0, Color.red);
  IShape shape9 = new Ellipse(50, 100, new Position2D(200, 300), 0, Color.green);
  // Rectangle motions
  Motion motion1 = new Motion(shape1, shape2, 1, 10);
  Motion motion2 = new Motion(shape2, shape3, 10, 50);
  Motion motion3 = new Motion(shape3, shape3, 50, 51);
  // Ellipse motions
  Motion motion4 = new Motion(shape6, shape7, 1, 70);
  Motion motion5 = new Motion(shape7, shape8, 70, 100);
  Motion motion6 = new Motion(shape8, shape9, 100, 110);

  @Test
  public void testTextualViewRectangle() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    model.addMotion("a", motion1);
    model.addMotion("a", motion2);
    model.addMotion("b", motion3);
    model.addShape("a", "Rectangle");
    model.addShape("b", "Rectangle");
    IAnimationView view = new TextualViewI(model, ap, 10, 20, 100, 200, 10);
    String expected = "canvas 10 20 100 200\n"
        + "shape a rectangle\n"
        + "motion a 0.10 200 200 50 100 255 0 0 1.00 10 200 50 100 255 0 0\n"
        + "motion a 1.00 10 200 50 100 255 0 0 5.00 300 300 50 100 255 0 0\n"
        + "shape b rectangle\n"
        + "motion b 5.00 300 300 50 100 255 0 0 5.10 300 300 50 100 255 0 0\n";
    assertEquals(expected, view.toString());
  }

  @Test
  public void testTextualViewRectangleAndEllipse() {
    this.ap = new StringBuilder();
    this.builder = new Animation.Builder(10, ap);
    this.model = this.builder.build();
    model.addMotion("a", motion1);
    model.addMotion("a", motion2);
    model.addMotion("b", motion4);
    model.addShape("a", "Rectangle");
    model.addShape("b", "Ellipse");
    IAnimationView view = new TextualViewI(model, builder.getAp(), 10, 20, 100, 200, 10);
    String expected = "canvas 10 20 100 200\n"
        + "shape a rectangle\n"
        + "motion a 0.10 200 200 50 100 255 0 0 1.00 10 200 50 100 255 0 0\n"
        + "motion a 1.00 10 200 50 100 255 0 0 5.00 300 300 50 100 255 0 0\n"
        + "shape b ellipse\n"
        + "motion b 0.10 50 100 25 40 0 0 255 7.00 200 55 25 40 0 0 255\n";
    assertEquals(expected, view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewNullModel() {
    this.ap = new StringBuilder();
    this.model = null;
    IAnimationView view = new TextualViewI(model, ap, 10, 20, 100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewNullAppendable() {
    this.ap = null;
    this.model = new Animation();
    IAnimationView view = new TextualViewI(model, ap, 10, 20, 100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewInvalidWidth() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new TextualViewI(model, ap, 10, 20, -100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewInvalidHeight() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new TextualViewI(model, ap, 10, 20, 100, -10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewInvalidSpeed() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new TextualViewI(model, ap, 10, 20, 100, 200, -10);
  }

  @Test
  public void testSVGViewRectangles() throws IOException {
    this.ap = new StringBuilder();
    this.builder = new Animation.Builder(10, ap);
    this.model = this.builder.build();
    model.addMotion("a", motion1);
    model.addMotion("a", motion2);
    model.addMotion("b", motion3);
    IAnimationView view = new SVGViewI(model, builder.getAp(), 10, 20, 100, 200, 10);
    view.render();
    String expected = "<svg width=\"110\" height=\"220\" version=\"1.1\"\n"
        + " xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"a\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" "
        + "visibility=\"hidden\" >\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"1ms\" attributeName=\"visibility\" "
        + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"x\" "
        + "from=\"200\" to=\"10\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"x\" "
        + "from=\"10\" to=\"300\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"y\" "
        + "from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "<rect id=\"b\" x=\"300\" y=\"300\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" "
        + "visibility=\"hidden\" >\n"
        + "<animate attributeType=\"xml\" begin=\"5000ms\" dur=\"1ms\" attributeName=\"visibility\""
        + " from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>";
    assertEquals(expected, builder.getAp().toString());
  }

  @Test
  public void testSVGViewRectangleAndEllipse() throws IOException {
    this.ap = new StringBuilder();
    this.builder = new Animation.Builder(10, ap);
    this.model = this.builder.build();
    model.addMotion("a", motion1);
    model.addMotion("a", motion2);
    model.addMotion("b", motion4);
    IAnimationView view = new SVGViewI(model, builder.getAp(), 10, 20, 100, 200, 10);
    view.render();
    String expected = "<svg width=\"110\" height=\"220\" version=\"1.1\"\n"
        + " xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"a\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" "
        + "visibility=\"hidden\" >\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"1ms\" attributeName=\"visibility\" "
        + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"900ms\" attributeName=\"x\" from=\""
        + "200\" to=\"10\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"x\" "
        + "from=\"10\" to=\"300\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"4000ms\" attributeName=\"y\" "
        + "from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "<ellipse id=\"b\" cx=\"62\" cy=\"120\" rx=\"12\" ry=\"20\" fill=\"rgb(0,0,255)\" "
        + "visibility=\"hidden\" >\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"1ms\" attributeName=\"visibility\" "
        + "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"6900ms\" attributeName=\"cx\" "
        + "from=\"62\" to=\"212\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"100ms\" dur=\"6900ms\" attributeName=\"cy\" "
        + "from=\"120\" to=\"75\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>";
    assertEquals(expected, builder.getAp().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewNullModel() {
    this.ap = new StringBuilder();
    this.model = null;
    IAnimationView view = new SVGViewI(model, ap, 10, 20, 100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewNullAppendable() {
    this.ap = null;
    this.model = new Animation();
    IAnimationView view = new SVGViewI(model, ap, 10, 20, 100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewInvalidWidth() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new SVGViewI(model, ap, 10, 20, -100, 200, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewInvalidHeight() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new SVGViewI(model, ap, 10, 20, 100, -10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSVGViewInvalidSpeed() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    IAnimationView view = new SVGViewI(model, ap, 10, 20, 100, 200, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMotion() {
    this.ap = new StringBuilder();
    this.model = new Animation();
    model.addMotion("a", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlappingMotions() {
    IShape e1 = new Ellipse(50, 100, new Position2D(100, 100), 0, Color.red);
    IShape e2 = new Ellipse(50, 100, new Position2D(200, 200), 0, Color.red);
    IShape r1 = new Rectangle(100, 100, new Position2D(300, 300), 0, Color.green);
    IShape r2 = new Rectangle(100, 100, new Position2D(200, 200), 0, Color.green);
    Motion motionOverlap1 = new Motion(e1, e2, 1, 10);
    Motion motionOverlap2 = new Motion(r1, r2, 1, 10);
    this.ap = new StringBuilder();
    this.model = new Animation();
    model.addMotion("a", motionOverlap1);
    model.addMotion("b", motionOverlap2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConsecutiveShapeMotions() {
    IShape e1 = new Ellipse(50, 100, new Position2D(100, 100), 0, Color.red);
    IShape e2 = new Ellipse(50, 100, new Position2D(200, 200), 0, Color.red);
    IShape e3 = new Ellipse(100, 200, new Position2D(200, 200), 0, Color.red);
    Motion m1 = new Motion(e1, e2, 1, 10);
    Motion m2 = new Motion(e1, e3, 10, 20);
    this.ap = new StringBuilder();
    this.model = new Animation();
    model.addMotion("a", m1);
    model.addMotion("a", m2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShapeMorph() {
    IShape e1 = new Ellipse(50, 100, new Position2D(100, 100), 0, Color.red);
    IShape r1 = new Rectangle(100, 100, new Position2D(300, 300), 0, Color.green);
    Motion m1 = new Motion(e1, r1, 1, 100);
    this.ap = new StringBuilder();
    this.model = new Animation();
    model.addMotion("a", m1);
  }
}
