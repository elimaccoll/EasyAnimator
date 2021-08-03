package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.Polygon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Abstract class that represents a JPanel draw panel for the visual view (VisualViewI) and
 * interactive view (CompositeView).  This is overwritten by all extending views in order for them
 * to override the actionPerformed method and implement the functionality specific to that view.
 */
public abstract class AbstractDrawPanel extends JPanel implements ActionListener,
    IAbstractDrawPanel {

  protected final JFrame container;
  protected int count;
  protected IAnimation model;
  protected int speed;
  protected IAnimation.Bounds bounds;
  Map<String, IShape> shapes;
  protected int n;
  protected Timer tm;
  private boolean fill = true;

  /**
   * Constructor for AbstractDrawPanel that takes in the necessary information to create a draw
   * panel for the extending view's draw panels (DrawPanelComposite and DrawPanelVisual).
   *
   * @param frame  Frame that draw panel will be displayed on
   * @param model  The IAnimation model being drawn on the panel
   * @param speed  The speed of the animation from the user command line input
   * @param bounds The bounds of the canvas for the animation
   */
  AbstractDrawPanel(JFrame frame, IAnimation model, int speed, IAnimation.Bounds bounds) {
    checkValidInputs(model, frame, bounds, speed);
    this.container = frame;
    this.model = model;
    this.speed = speed;
    this.bounds = bounds;
    this.shapes = model.getFrameAt(count);
    this.n = 1000 / speed;
    this.tm = new Timer(n, this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (IShape shape : shapes.values()) {
      g.setColor(shape.getColor());
      switch (shape.getShapeType()) {
        case Rectangle:
          if (fill) {
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          } else {
            g.drawRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          }
          break;
        case Ellipse:
          // x, y are corner position; width and height are diameters
          if (fill) {
            g.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          } else {
            g.drawOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          }
          break;
        case Plus:
          Polygon ps = (Polygon) shape;
          ps.setupXYCoords();
          int[] xs = ps.getXCoords();
          int[] ys = ps.getYCoords();
          if (fill) {
            g.fillPolygon(xs, ys, xs.length);
          } else {
            g.drawPolygon(xs, ys, xs.length);
          }
          break;
        default:
          break;
      }
    }
    tm.start();
  }

  public void fill() {
    this.fill = !this.fill;
  }


  /**
   * Helper method to ensure constructor arguments are valid and not null.
   *
   * @param model  IAnimation model being drawn.
   * @param frame  Frame the draw panel is on.
   * @param bounds Bounds of the canvas.
   * @param speed  Speed of the animation based on user command line input.
   */
  private void checkValidInputs(IAnimation model, JFrame frame, IAnimation.Bounds bounds,
      int speed) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    if (bounds == null) {
      throw new IllegalArgumentException("Bounds are null.");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0.");
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(bounds.maxX + 50, bounds.maxY + 50);
  }

  /**
   * Sets the Map of IShape shapes and their ids being animated.
   *
   * @param shapes Map of shape ids and IShape shapes.
   */
  protected void setShapes(Map<String, IShape> shapes) {
    this.shapes = shapes;
  }

  protected boolean looping = false;
  protected boolean done = false;
  protected boolean started = false;
  protected boolean restarting = false;
  protected boolean discrete = false;

  @Override
  public void start() {
    this.started = true;
  }

  @Override
  public void pause() {
    if (!done) {
      for (ActionListener al : tm.getActionListeners()) {
        tm.removeActionListener(al);
      }
      this.tm.stop();
    }
  }

  @Override
  public void play() {
    if (!done) {
      this.tm.addActionListener(this);
      this.tm.start();
    }
  }

  @Override
  public void restart() {
    if (!this.done && !this.restarting && !this.looping) {
      this.restarting = true;
    }
    if (this.done && !this.restarting) {
      tm.addActionListener(this);
    }
  }

  @Override
  public void loop() {
    this.looping = !this.looping;
    if (this.done) {
      tm.addActionListener(this);
    }
  }

  @Override
  public void speed(int newSpeed) {
    for (ActionListener al : tm.getActionListeners()) {
      tm.removeActionListener(al);
    }
    this.speed = newSpeed;
    this.tm.stop();
    this.n = (int) (1000 / newSpeed);
    this.tm = new Timer(this.n, this);
  }

  @Override
  public boolean isOver() {
    return this.done;
  }

  @Override
  public boolean isStarted() {
    return this.started;
  }

  @Override
  public void discrete(int currSpeed) {
    this.discrete = !this.discrete;
    if (discrete) {
      speed(100);
    } else {
      speed(currSpeed);
    }
  }

  @Override
  public boolean isDiscrete() {
    return this.discrete;
  }

  protected boolean sloMo = false;

  @Override
  public void sloMo(int currSpeed, int stopTime) {
    this.sloMo = !this.sloMo;
    if (discrete) {
      speed(1);
    } else {
      speed(currSpeed);
    }
  }
}
