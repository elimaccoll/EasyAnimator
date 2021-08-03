import cs3500.animator.controller.AnimationController;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Entry point for the animation program that allows for three supported views, text, visual, and
 * svg. Takes command-line arguments from the user to determine the type of view to display the
 * animation, input file, the speed of the animation, and the output file of the animation (svg).
 * Only the input file and view inputs are mandatory: the default speed is 1 tick per second, and
 * the default output is System.out.
 */
public final class Excellence {

  /**
   * Helper method for main to create popup windows for errors in the command line inputs and then
   * exit the program.
   *
   * @param msg String error message to be displaying in popup window.
   */
  private static void popupErrorAndExit(String msg) {
    JOptionPane optionPane = new JOptionPane("Command line failed!", JOptionPane.ERROR_MESSAGE);
    JDialog dialog = optionPane.createDialog(msg);
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
    System.exit(1);
  }

  /**
   * Main method used to run animations.
   *
   * @param args User's command line input to run an animation based on their arguments. Must
   *             include -in "inputFile" -view "viewType" can also include -speed speedValue and
   *             -out "outputFile" for the svg view.
   */
  public static void main(String[] args) {
    // Part of assignment 7 - creates the programmatically generated animation 'animation1.txt' so
    // that it can be used by the program.  Takes in size of the array to sort, and the maximum
    // value of an element in the array. (Run main to generate file, then test a view).
    GenerateAnimation.generateAnimation(10, 200);

    String inputFile = null;
    String output = null;
    String viewName = null;
    int speed = 1;
    if ((args.length % 2) != 0) {
      throw new IllegalArgumentException("Arguments must be provided in pairs.");
    }

    try {
      for (int i = 0; i < args.length; i += 2) {
        switch (args[i]) {
          case "-in":
            inputFile = args[i + 1];
            break;
          case "-out":
            output = args[i + 1];
            break;
          case "-view":
            viewName = args[i + 1];
            break;
          case "-speed":
            speed = Integer.parseInt(args[i + 1]);
            break;
          default:
            throw new IllegalArgumentException("Unsupported command line option " + args[i] + ".");
        }
      }
      if (inputFile == null || viewName == null) {
        throw new IllegalArgumentException(
            "Animation file (-in) and view name (-view) must be provided.");
      }
    } catch (IllegalArgumentException exp) {
      exp.printStackTrace();
      popupErrorAndExit(exp.getMessage());
    }

    Appendable appendable = null;
    InputStream in = Excellence.class.getResourceAsStream("/inputs/" + inputFile);

    Readable readable = new InputStreamReader(in);
    FileWriter writer = null;
    try {
      if (output != null) {
        writer = new FileWriter(output);
        appendable = writer;
      } else {
        appendable = new PrintStream(System.out);
      }
      AnimationController controller = new AnimationController(readable, appendable);
      controller.animate(speed, viewName);
      if (writer != null) {
        writer.close();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      popupErrorAndExit("Failed to open output file " + output + ".");
    }
    System.out.println("\nCompleted.");
  }

}
