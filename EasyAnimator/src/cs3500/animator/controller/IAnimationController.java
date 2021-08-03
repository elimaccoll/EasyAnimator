package cs3500.animator.controller;

/**
 * Interface for the controller of an animation.
 */
public interface IAnimationController {

  /**
   * Runs the animation based on the command inputs of view type and speed as well as the canvas
   * specifications obtained from the Animation model builder.
   *
   * @param speed    Desired speed that animation will play at.
   * @param viewName Desired view type of the animation (text, visual, svg).
   */
  void animate(int speed, String viewName);
}
