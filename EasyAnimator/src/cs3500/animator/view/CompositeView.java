package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IAnimation.Bounds;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents an interactive view of an animation that utilizes Java Swing to display the animation
 * and create a GUI to interact with the animation.
 */
public class CompositeView extends AbstractViews implements ActionListener, ChangeListener {

  private boolean paused = false;
  private boolean looping = false;
  private JLabel buttonPress;
  private JLabel speedDisplay;
  private JSlider speedSlider;
  private JButton start;
  private Hashtable<Integer, JLabel> speedLabels;

  private JPanel toolbar;

  @Override
  public void createDrawPanel() {
    this.drawPanel = new DrawPanelComposite(this.frame, this.model, this.speed, this.bounds);
  }

  /**
   * Constructor for CompositeView that takes in the parameters required to create an interactive
   * representation of the animation.  The Shape information is obtained through the model, the
   * canvas details are given by x, y, w (width), and h (height), and the speed of the animation
   * comes from the user command-line input.
   *
   * @param model IAnimation model that the composite view is being created for.
   * @param ap    Appendable to display textual information.
   * @param x     x coordinate of canvas.
   * @param y     y coordinate of canvas.
   * @param w     width of canvas.
   * @param h     height of canvas.
   * @param speed speed of animation.
   */
  public CompositeView(IAnimation model, Appendable ap, int x, int y, int w, int h, int speed) {
    super(model, ap, x, y, w, h, speed);
    this.toolbar();
    frame.add(toolbar, BorderLayout.NORTH);
  }

  /**
   * Helper method to create the labels displayed on the speed slide bar on the toolbar.
   */
  private void setSpeedSliderValues() {
    this.speedLabels.put(1, new JLabel("1"));
    this.speedLabels.put(20, new JLabel("20"));
    this.speedLabels.put(40, new JLabel("40"));
    this.speedLabels.put(60, new JLabel("60"));
    this.speedLabels.put(80, new JLabel("80"));
    this.speedLabels.put(100, new JLabel("100"));
  }

  /**
   * Creates the toolbar at the top of the window for CompositeView. Includes a 'Start' button,
   * which becomes 'Restart' once pressed, a 'Resume' button, a 'Pause' button, a speed slider, a
   * speed display, 'Loop' checkbox, and a text display panel.
   */
  private void toolbar() {
    // Start button
    this.start = new JButton("Start");
    this.start.setActionCommand("start");
    this.start.addActionListener(this);
    // Pause button
    JButton pause = new JButton("Pause");
    pause.setActionCommand("pause");
    pause.addActionListener(this);
    // Resume button
    JButton play = new JButton("Resume");
    play.setActionCommand("play");
    play.addActionListener(this);
    // Restart button
    JButton restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    // Speed slider
    speedSlider = new JSlider(1, 100, 1);
    speedLabels = new Hashtable<>();
    setSpeedSliderValues();
    speedSlider.setLabelTable(speedLabels);
    speedSlider.setPaintTrack(true);
    speedSlider.setPaintLabels(true);
    speedSlider.setMajorTickSpacing(20);
    speedSlider.setMinorTickSpacing(1);
    speedSlider.setSnapToTicks(true);
    speedSlider.setValue(speed);
    speedSlider.addChangeListener(this);

    // Toggle loop check box
    JCheckBox loopCB = new JCheckBox("Loop");
    loopCB.setSelected(false);
    loopCB.setActionCommand("loop");
    loopCB.addActionListener(this);
    JPanel tb = new JPanel();

    // Text display panel that displays what button was pressed.
    buttonPress = new JLabel("Press a button!");
    buttonPress.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Adding buttons to toolbar
    tb.add(buttonPress);
    tb.add(start);
    tb.add(play);
    tb.add(pause);
    tb.add(speedSlider);

    // Speed display
    speedDisplay = new JLabel("Speed: " + this.speed);
    tb.add(speedDisplay);

    tb.add(loopCB);
    this.toolbar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 1));
    this.toolbar.add(tb);
    toolbar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String btn = "";
    switch (e.getActionCommand()) {
      case "start":
        drawPanel.start();
        btn = "Started";
        this.start.setText("Restart");
        this.start.setActionCommand("restart");
        break;
      case "pause":
        if (!drawPanel.isStarted()) {
          JOptionPane.showMessageDialog(frame, "Animation not started. "
              + "Press 'Start' to begin.");
        }
        if (drawPanel.isOver()) {
          JOptionPane.showMessageDialog(frame, "Animation is over. "
              + "Press 'Restart' to play again.");
        } else if (paused) {
          btn = "Already Paused!";
        } else {
          this.paused = true;
          drawPanel.pause();
          btn = "Paused";
        }
        break;
      case "play":
        if (!drawPanel.isStarted()) {
          JOptionPane.showMessageDialog(frame, "Animation not started. "
              + "Press 'Start' to begin.");
        }
        if (drawPanel.isOver()) {
          JOptionPane.showMessageDialog(frame, "Animation is over. "
              + "Press restart to play again.");
        } else if (!paused) {
          btn = "Already Playing!";
        } else {
          this.paused = false;
          drawPanel.play();
          btn = "Playing";
        }
        break;
      case "restart":
        if (!drawPanel.isOver()) {
          if (this.looping) {
            JOptionPane.showMessageDialog(frame, "Animation is already looping.");
          } else {
            drawPanel.restart();
            btn = "Restart set";
          }
        } else {
          drawPanel.restart();
          btn = "Restart set";
        }
        break;
      case "loop":
        drawPanel.loop();
        this.looping = !this.looping;
        btn = "Looping set";
        break;
      default:
        break;
    }
    buttonPress.setText(btn);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    int newSpeed = speedSlider.getValue() + 1;
    speedDisplay.setText("Speed: " + (newSpeed - 1));
    buttonPress.setText("Speed Changed");
    drawPanel.speed(newSpeed, paused);
  }

  /**
   * Representation of a draw panel for the composite (interactive) view.  It extends
   * AbstractDrawPanel and overrides actionPerformed to handle the additional functionality of the
   * interactive view GUI.
   */
  public static class DrawPanelComposite extends AbstractDrawPanel {

    /**
     * Constructor for DrawPanelComposite that takes in the necessary information to create a draw
     * panel for a composite view using the AbstractDrawPanel constructor.
     *
     * @param frame  Frame that draw panel will be displayed on
     * @param model  The IAnimation model being drawn on the panel
     * @param speed  The speed of the animation from the user command line input
     * @param bounds The bounds of the canvas for the animation
     */
    DrawPanelComposite(JFrame frame, IAnimation model, int speed, Bounds bounds) {
      super(frame, model, speed, bounds);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (started) {
        ++count;
        if (count > bounds.maxT) {
          //System.out.println("listeners : " + tm.getActionListeners().length);
          for (ActionListener al : tm.getActionListeners()) {
            tm.removeActionListener(al);
          }
          count = 0;
          this.done = true;

          if (restarting) {
            this.done = false;
            this.restarting = false;
            tm.addActionListener(this);
          }
          if (looping) {
            this.done = false;
            tm.addActionListener(this);
          }
        } else {
          this.done = false;
          shapes = model.getFrameAt(count);
          repaint();
        }
      }
    }
  }
}
