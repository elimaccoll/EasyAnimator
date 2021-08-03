package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Map;
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
          g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          break;
        case Ellipse:
          g.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
          break;
        default:
          break;
      }
    }
    tm.start();
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
    if (frame == null) {
      throw new IllegalArgumentException("Frame is null.");
    }
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
    if (this.looping) {
      this.looping = false;
    } else {
      this.looping = true;
    }
    if (this.done) {
      tm.addActionListener(this);
    }
  }

  @Override
  public void speed(int newSpeed, boolean paused) {
    if (!done) {
      for (ActionListener al : tm.getActionListeners()) {
        tm.removeActionListener(al);
      }
      this.tm.stop();
      this.n = (int) (1000 / newSpeed);
      this.tm = new Timer(this.n, this);
      if (paused) {
        this.tm.stop();
      } else {
        this.tm.start();
      }
    }
  }

  @Override
  public boolean isOver() {
    return this.done;
  }

  @Override
  public boolean isStarted() {
    return this.started;
  }
}
