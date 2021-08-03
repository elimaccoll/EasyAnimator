import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.IAnimationController;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.Animation;
import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Rectangle;
import cs3500.animator.view.CompositeView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

/**
 * Test class for HW7.  Tests Controller inputs and listeners.
 */
public class Hw7Tests {

  Reader in = new StringReader("");
  StringBuilder out = new StringBuilder();
  IAnimationController controller = new AnimationController(in, out);

  IShape shape1 = new Rectangle(50, 100, new Position2D(200, 200), 0, Color.red);
  IShape shape2 = new Rectangle(50, 100, new Position2D(10, 200), 0, Color.red);
  IShape shape3 = new Rectangle(50, 100, new Position2D(300, 300), 0, Color.red);
  Motion motion1 = new Motion(shape1, shape2, 1, 10);
  Motion motion2 = new Motion(shape2, shape3, 10, 50);
  Motion motion3 = new Motion(shape3, shape3, 50, 51);

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    IAnimationController c = new AnimationController(null, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    IAnimationController c = new AnimationController(in, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerAnimateInvalidSpeed() {
    controller.animate(-10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerAnimateInvalidViewName() {
    controller.animate(10, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerAnimateNullViewName() {
    controller.animate(10, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCanvasWidth() {
    Reader in = new StringReader("canvas 0 0 -100 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void tesInvalidCanvasHeight() {
    Reader in = new StringReader("canvas 0 0 100 -100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingCanvasWidth() {
    Reader in = new StringReader("canvas 0 0 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingCanvasHeight() {
    Reader in = new StringReader("canvas 0 0 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingCanvasX() {
    Reader in = new StringReader("canvas 0 100 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingCanvasY() {
    Reader in = new StringReader("canvas 0 100 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingCanvas() {
    Reader in = new StringReader("0 0 100 100\rshape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShapeName() {
    Reader in = new StringReader("canvas 0 0 100 100\rshape s1 nothing\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingShapeName() {
    Reader in = new StringReader("canvas 0 0 100 100\rshape s1 \n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingShape() {
    Reader in = new StringReader("canvas 0 0 100 100\r s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testMissingMotion() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + " s1 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "svg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionShapeMismatch() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s2 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "text");
  }

  @Test(expected = IllegalStateException.class)
  public void testMotionMissingShape() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "visual");
  }

  @Test(expected = IllegalStateException.class)
  public void testMotionNotEnoughArguments() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s1 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "visual");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionTooManyArguments() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s1 7 0 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidNonNumberArgument() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s1 zero 0 0 10 10 255 0 0 10 0 0 10 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShapeWidth() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 -1 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShapeHeight() {
    Reader in = new StringReader("canvas 0 0 100 100\r shape s1 rectangle\n "
        + "motion s1 0 0 0 10 10 255 0 0 10 0 0 10 -1 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test
  public void testNoShapes() {
    Reader in = new StringReader("canvas 0 0 100 100");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
    assertEquals("", out.toString());
  }

  @Test
  public void testShapeAddedNoMotion() {
    Reader in = new StringReader("canvas 0 0 100 100\nshape s1 rectangle");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
    assertEquals("", out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionsInvalidDimensionsChange() {
    Reader in = new StringReader("canvas 0 0 100 100\n"
        + "shape s1 rectangle\n" + "shape s2 ellipse\n"
        + "motion s1 0 0 0 10 10 255 0 0  10 0 0 10 10 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 25 5 255 0 0\n"
        + "motion s1 0 0 0 10 10 255 0 0  10 0 0 -15 5 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 20 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionsInvalidTimeInputted() {
    Reader in = new StringReader("canvas 0 0 100 100\n"
        + "shape s1 rectangle\n" + "shape s2 ellipse\n"
        + "motion s1 -1 0 0 10 10 255 0 0  10 0 0 10 10 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 25 5 255 0 0\n"
        + "motion s1 0 0 0 10 10 255 0 0  10 0 0 15 5 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 20 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionsInvalidTimeChange() {
    Reader in = new StringReader("canvas 0 0 100 100\n"
        + "shape s1 rectangle\n" + "shape s2 ellipse\n"
        + "motion s1 0 0 0 10 10 255 0 0  10 0 0 10 10 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 25 5 255 0 0\n"
        + "motion s1 0 0 0 10 10 255 0 0  -10 0 0 15 5 255 0 0\n"
        + "motion s2 0 0 0 25 5 255 0 0  10 0 0 20 10 255 0 0");
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
  }

  @Test
  public void testEventLister() {
    Reader in = new StringReader("canvas 0 0 100 100");
    IAnimation mockModel = new Animation();
    mockModel.addShape("a", "Rectangle");
    mockModel.addShape("b", "Rectangle");
    mockModel.addMotion("a", motion1);
    mockModel.addMotion("a", motion2);
    mockModel.addMotion("b", motion3);
    CompositeView mockView = new CompositeView(mockModel, out, 10, 20, 100, 200, 10);
    mockView.createDrawPanel();
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
    ActionEvent mockEvent = new ActionEvent(mockModel, 30, "speed");
    mockView.actionPerformed(mockEvent);
    assertEquals("canvas 0 0 100 100\n"
        + "shape a rectangle\n"
        + "motion a 1 200 200 50 100 255 0 0  10 10 200 500 10 255 0 0\n"
        + "motion a 10 10 200 50 100 255 0 0  50 300 300 50 100 255 0 \n"
        + "shape b rectangle\n"
        + "motion b 50 300 300 50 100 255 0 0  51 50 50 300 300 255 0 0",mockView.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEventListerError() {
    IAnimation mockModel = new Animation();
    mockModel.addMotion("a", motion1);
    mockModel.addMotion("a", motion2);
    mockModel.addMotion("b", motion3);
    mockModel.addShape("a", "Rectangle");
    mockModel.addShape("b", "Rectangle");
    CompositeView mockView = new CompositeView(mockModel, out, 3, 3, 300, 20, 5);
    mockView.createDrawPanel();
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
    ActionEvent mockEvent = new ActionEvent(mockModel, -20, "speed");
    mockView.actionPerformed(mockEvent);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEventListerInvalidError() {
    IAnimation mockModel = new Animation();
    mockModel.addMotion("a", motion1);
    mockModel.addMotion("a", motion2);
    mockModel.addMotion("b", motion3);
    mockModel.addShape("a", "Rectangle");
    mockModel.addShape("b", "Rectangle");
    CompositeView mockView = new CompositeView(mockModel, out, 35, 40, 12, 42, 30);
    mockView.createDrawPanel();
    IAnimationController controller = new AnimationController(in, out);
    controller.animate(10, "interactive");
    ActionEvent mockEvent = new ActionEvent(mockModel, 0, "loop");
    mockView.actionPerformed(mockEvent);
  }
}
