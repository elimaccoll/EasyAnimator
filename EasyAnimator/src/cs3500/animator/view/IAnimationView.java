package cs3500.animator.view;

import java.io.IOException;

/**
 * Interface for the representation of a view of an Animation.  Implemented by each type of view to
 * override the render method according to that view's functionality.
 */
public interface IAnimationView {

  /**
   * Renders the animation with the corresponding view type.
   *
   * @throws IOException for bad Appendable.
   */
  void render() throws IOException;

}
