package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This is an abstract class that is the representation of visual views, which includes
 * CompositeView and VisualViewI.  This class contains the base functionality of a VisualViewI and
 * is extended by CompositeView, which then incorporates the additional functionality that comes
 * along with the GUI.
 */
public abstract class AbstractViews implements IAnimationView {

  protected final IAnimation model;
  protected final int speed;
  protected final IAnimation.Bounds bounds;
  protected JFrame frame;
  protected AbstractDrawPanel drawPanel;
  protected JScrollPane scrollPane;

  /**
   * Abstract method that is overwritten in all views that extend this class (CompositeView and
   * VisualViewI) to create the appropriate corresponding draw panel for the type of view (either
   * DrawPanelVisual or DrawPanelComposite).
   */
  protected abstract void createDrawPanel();

  /**
   * Constructor for AbstractViews that takes in the parameters required to create a visual (not a
   * VisualViewI specifically) representation of the animation.  The Shape information is obtained
   * through the model, the canvas details are given by x, y, w (width), and h (height), and the
   * speed of the animation comes from the user command-line input.  This constructor is called by
   * extending view classes to create the corresponding view.  Contains the default functionality of
   * a non-interactive visual view.
   *
   * @param model IAnimation model that the visual view is being created for.
   * @param ap    Appendable to display textual information.
   * @param x     x coordinate of canvas.
   * @param y     y coordinate of canvas.
   * @param w     width of canvas.
   * @param h     height of canvas.
   * @param speed speed of animation.
   */
  public AbstractViews(IAnimation model, Appendable ap, int x, int y, int w, int h,
      int speed) {
    checkValidInputs(model, ap, w, h, speed);
    this.model = model;
    this.speed = speed;
    this.bounds = model.getAnimationBounds();
    this.frame = new JFrame(String.format("Animator. (%d, %d)  %d x %d", x, y, w, h));
    createDrawPanel();
    scrollPane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    drawPanel.setLocation(x, y);
    scrollPane.setLocation(x, y);
    scrollPane.getHorizontalScrollBar().setMinimum(x);
    scrollPane.getVerticalScrollBar().setMinimum(y);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(w + 57, h + 57);
    frame.setLocationRelativeTo(null);
    frame.add(scrollPane);
  }

  @Override
  public void render() {
    frame.setVisible(true);
  }

  /**
   * Helper method to check that view arguments are valid and not null.
   *
   * @param model IAnimation model being animated
   * @param ap    Appendable
   * @param w     width of canvas
   * @param h     height of canvas
   * @param speed speed of animation
   * @throws IllegalArgumentException if input is null or invalid.
   */
  protected void checkValidInputs(IAnimation model, Appendable ap, int w, int h, int speed)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be greater than 0.");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be greater than 0.");
    }
  }
}
