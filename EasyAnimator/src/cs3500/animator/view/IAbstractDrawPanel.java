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
   *
   * @param newSpeed desired new speed of animation.
   */
  void speed(int newSpeed);

  /**
   * Checks if the animation is over.
   *
   * @return Returns true if it over, false if not
   */
  boolean isOver();

  /**
   * Checks if the animation has started.
   *
   * @return Returns true if it has started, false if not
   */
  boolean isStarted();

  /**
   * Used for discrete time functionality.  Toggles playing the animation in discrete time. Created
   * for Extra Credit Assignment Level 2.
   *
   * @param currSpeed speed at the time when discrete time is enabled.  Used to set the animation
   *                  back to the proper time when discrete time is toggled off.
   */
  void discrete(int currSpeed);

  /**
   * Checks if the animation is playing in discrete time.
   *
   * @return Returns true if it is playing in discrete time, false if not.
   */
  boolean isDiscrete();

  void sloMo(int currSpeed, int stopTime);
}
