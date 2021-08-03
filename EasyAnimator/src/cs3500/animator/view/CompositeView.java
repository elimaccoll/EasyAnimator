package cs3500.animator.view;

import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IAnimation.Bounds;
import cs3500.animator.model.Motion;
import cs3500.animator.util.SlomoReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents an interactive view of an animation that utilizes Java Swing to display the animation
 * and create a GUI to interact with the animation.
 */
public class CompositeView extends AbstractViews implements ActionListener, ChangeListener,
    ItemListener {

  protected boolean paused = false;
  protected boolean looping = false;
  protected boolean fill = true;
  protected JLabel buttonPress;
  protected JLabel speedDisplay;
  protected JSlider speedSlider;
  protected JButton start;
  protected Hashtable<Integer, JLabel> speedLabels;
  protected Readable slomoRd;

  protected JPanel toolbar;

  @Override
  public void createDrawPanel() {
    this.drawPanel = new DrawPanelComposite(this, this.model, this.speed, this.bounds,
        this.slomoRd);
  }

  /**
   * Constructor for CompositeView that takes in the parameters required to create an interactive
   * representation of the animation.  The Shape information is obtained through the model, the
   * canvas details are given by x, y, w (width), and h (height), and the speed of the animation
   * comes from the user command-line input.
   *
   * @param model   IAnimation model that the composite view is being created for.
   * @param ap      Appendable to display textual information.
   * @param x       x coordinate of canvas.
   * @param y       y coordinate of canvas.
   * @param w       width of canvas.
   * @param h       height of canvas.
   * @param speed   speed of animation.
   * @param slomoRd readable for slow motion input.
   */
  public CompositeView(IAnimation model, Appendable ap, int x, int y, int w, int h, int speed,
      Readable slomoRd) {
    super(model, ap, x, y, w, h, speed);

    this.slomoRd = slomoRd;
    ((DrawPanelComposite) drawPanel).setSlomoInput(slomoRd);
    UIManager.put("ToolTip.background", Color.ORANGE);
    UIManager.put("ToolTip.foreground", Color.BLACK);
    UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));

    this.toolbar();
    this.add(toolbar, BorderLayout.NORTH);
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
    start = new JButton("Start");
    start.setActionCommand("start");
    start.addActionListener(this);
    start.setToolTipText("Click to start animation");

    // Pause button
    JButton pause = new JButton("Pause");
    pause.setActionCommand("pause");
    pause.addActionListener(this);
    pause.setToolTipText("Click to pause animation");

    // Resume button
    JButton play = new JButton("Resume");
    play.setActionCommand("play");
    play.addActionListener(this);
    play.setToolTipText("Click to resume animation");

    // Restart button
    JButton restart = new JButton("Restart");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    restart.setToolTipText("Click to re-start animation");

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

    // Toggle discrete time check box
    JCheckBox discreteCB = new JCheckBox("Discrete");
    discreteCB.setSelected(false);
    discreteCB.setActionCommand("discrete");
    discreteCB.addItemListener(this);

    JPanel fillChkBoxPanel = new JPanel();
    fillChkBoxPanel.setBorder(BorderFactory.createTitledBorder("Fill/Draw"));

    JCheckBox fillCB = new JCheckBox("Fill");
    fillCB.setSelected(true);
    fillCB.setActionCommand("fill");
    fillCB.addActionListener(this);
    JPanel buttonPanel = new JPanel();

    // Text display panel that displays what button was pressed.
    buttonPress = new JLabel("Press a button!");
    buttonPress.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Adding buttons to toolbar
    buttonPanel.add(buttonPress);
    buttonPanel.add(start);
    buttonPanel.add(play);
    buttonPanel.add(pause);

    JPanel speedPanel = new JPanel();
    speedPanel.add(speedSlider);

    // Speed display
    speedDisplay = new JLabel("Speed: " + this.speed);
    speedPanel.add(speedDisplay);

    JPanel chkBoxPanel = new JPanel();
    chkBoxPanel.add(loopCB);
    chkBoxPanel.add(discreteCB);
    chkBoxPanel.add(fillCB);

    this.toolbar = new JPanel();
    toolbar.setLayout(new BorderLayout());
    toolbar.add(BorderLayout.NORTH, buttonPanel);
    toolbar.add(BorderLayout.CENTER, speedPanel);
    toolbar.add(BorderLayout.SOUTH, chkBoxPanel);
    toolbar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

      /*
        Draw/Fill check box:
         */

    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.add(toolbar);
    this.add(scrollPane);
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
          JOptionPane.showMessageDialog(this, "Animation not started. "
              + "Press 'Start' to begin.");
        }
        if (drawPanel.isOver()) {
          JOptionPane.showMessageDialog(this, "Animation is over. "
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
          JOptionPane.showMessageDialog(this, "Animation not started. "
              + "Press 'Start' to begin.");
        }
        if (drawPanel.isOver()) {
          JOptionPane.showMessageDialog(this, "Animation is over. "
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
            JOptionPane.showMessageDialog(this, "Animation is already looping.");
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
      case "discrete":
        drawPanel.discrete(speedSlider.getValue() + 1);
        if (paused) {
          drawPanel.tm.stop();
        } else {
          drawPanel.tm.start();
        }
        btn = "Discrete time set";
        break;
      case "fill":
        drawPanel.fill();
        this.fill = !this.fill;
        btn = "fill set";
        break;
      default:
        break;
    }
    buttonPress.setText(btn);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    String who = ((JCheckBox) e.getItemSelectable()).getActionCommand();
    System.out.println("who " + who);
    switch (who) {
      case "discrete":
        ((DrawPanelComposite) drawPanel).discrete(e.getStateChange() == ItemEvent.SELECTED);
        break;
      default:
        System.out.println("Unsupported event : " + who);
    }

  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (drawPanel.isDiscrete()) {
      JOptionPane.showMessageDialog(this, "Cannot change speed in discrete time.");
    } else {
      int newSpeed = speedSlider.getValue() + 1;
      speedDisplay.setText("Speed: " + (newSpeed - 1));
      buttonPress.setText("Speed Changed");
      drawPanel.speed(newSpeed);
      if (paused) {
        drawPanel.tm.stop();
      } else {
        drawPanel.tm.start();
      }
    }
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
    private List<SlomoReader.SlowMotion> slowMotions = new ArrayList();
    private boolean fastFwd = false;
    private List<Integer> orderedFrames = null;

    DrawPanelComposite(JFrame frame, IAnimation model, int speed, Bounds bounds, Readable slomoRd) {
      super(frame, model, speed, bounds);
      //tm.setDelay(1000/speed);
    }
//
    private int getNextFrame() {
      ++count;
      if (!fastFwd) {
        return count;
      } else {
        // find the first start/end frame number in the future, and move current frame to it
        for (int frame : orderedFrames) {
          if (frame < count) {
            continue;
          } else {
            count = frame;
            break;
          }
        }
        return count;
      }
    }

    private void setSlomoInput(Readable slomoRd) {
      if (slomoRd != null) {
        slowMotions = (new SlomoReader()).parseFile(slomoRd);
      }
    }

    private boolean slowMotionMode(int frame) {
      if (slowMotions != null) {
        for (SlomoReader.SlowMotion slow : slowMotions) {
          if (slow.getStartFrame() <= frame && frame < slow.getEndFrame()) {
            return true;
          }
        }
      }
      return false;
    }

    public void discrete(boolean fastFwd) {
      this.fastFwd = fastFwd;
      if (this.fastFwd && this.orderedFrames == null) {
        orderedFrames = getFastForwardFrames();
      }
    }

    private List<Integer> getFastForwardFrames() {
      Map<String, List<Motion>> animation = model.getAnimationDescription();
      List<Integer> frames = new ArrayList<>();
      for (List<Motion> motions : animation.values()) {
        for (Motion motion : motions) {
          frames.add(motion.getStartTime());
          frames.add(motion.getEndTime());
        }
      }
      // remove possible duplicates
      frames = frames.stream().distinct().collect(Collectors.toList());
      Collections.sort(frames);
      return frames;
    }

    private int discreteCounter = 0;


    @Override
    public void actionPerformed(ActionEvent e) {

      if (started) {
        int frameNum = getNextFrame();
        if ((!fastFwd) && slowMotionMode(frameNum)) {
          tm.setDelay(1000);
        } else {
          tm.setDelay(1000 / speed);
        }

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
          if (discrete) {
            shapes = model.getFrameAt(bounds.maxT);
            repaint();
          }
        } else {
          this.done = false;
            shapes = model.getFrameAt(frameNum);
            repaint();
        }
      }
    }
  }
}
