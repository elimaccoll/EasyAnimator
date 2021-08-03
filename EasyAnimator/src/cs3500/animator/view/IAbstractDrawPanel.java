package cs3500.animator.view;

/**
 * Interface for AbstractDrawPanel the contains the methods utilized by the extending views.
 */
public interface IAbstractDrawPanel {

  /**
   * Used by CompositeView for 'Start' button functionality.
   */
  void start();

  /**
   * Used by CompositeView for 'Pause' button functionality.
   */
  void pause();

  /**
   * Used by CompositeView for 'Resume' button functionality.
   */
  void play();

  /**
   * Used by CompositeView for 'Restart' button functionality.
   */
  void restart();

  /**
   * Used by CompositeView for 'Loop' checkbox functionality.
   */
  void loop();

  /**
   * Used by CompositeView for speed slide bar functionality.
   */
  void speed(int newSpeed, boolean paused);

  /**
   * Returns true if animation is over, false if not.
   */
  boolean isOver();

  /**
   * Returns true if animation has started, false if not.
   */
  boolean isStarted();
}
