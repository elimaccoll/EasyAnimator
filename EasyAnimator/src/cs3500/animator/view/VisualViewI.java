package cs3500.animator.view;

import cs3500.animator.model.IAnimation;

import cs3500.animator.model.IAnimation.Bounds;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 * Represents a visual view of an animation that utilizes Java Swing to display the animation.  It
 * extends the AbstractViews class and uses it to generate a visual animation based on the draw
 * panel created in the overwritten createDrawPanel method.
 */
public class VisualViewI extends AbstractViews {

  /**
   * Constructor for VisualViewI that takes in the parameters required to create a visual
   * representation of the animation.  The Shape information is obtained through the model, the
   * canvas details are given by x, y, w (width), and h (height), and the speed of the animation
   * comes from the user command-line input.  Calls the AbstractView constructor to actually
   * generate prepare to render the animation.
   *
   * @param model IAnimation model that the visual view is being created for.
   * @param ap    Appendable to display textual information.
   * @param x     x coordinate of canvas.
   * @param y     y coordinate of canvas.
   * @param w     width of canvas.
   * @param h     height of canvas.
   * @param speed speed of animation.
   */
  public VisualViewI(IAnimation model, Appendable ap, int x, int y, int w, int h, int speed) {
    super(model, ap, x, y, w, h, speed);
  }

  @Override
  protected void createDrawPanel() {
    this.drawPanel = new DrawPanelVisual(null, this.model, this.speed, this.bounds);
  }

  /**
   * Representation of a draw panel for the visual view.  It extends AbstractDrawPanel and overrides
   * actionPerformed to handle the functionality of a visual view.
   */
  static class DrawPanelVisual extends AbstractDrawPanel {

    /**
     * Constructor for DrawPanelVisual that takes in the necessary information to create a draw
     * panel for a visual view using the AbstractDrawPanel constructor.
     *
     * @param frame  Frame that draw panel will be displayed on
     * @param model  The IAnimation model being drawn on the panel
     * @param speed  The speed of the animation from the user command line input
     * @param bounds The bounds of the canvas for the animation
     */
    DrawPanelVisual(JFrame frame, IAnimation model, int speed, Bounds bounds) {
      super(frame, model, speed, bounds);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      this.count++;
      if (count > bounds.maxT + 2) {
        tm.removeActionListener(this);
        this.count = 0;
      } else {
        shapes = model.getFrameAt(count);
        repaint();
      }
    }
  }
}
