package cs3500.animator.controller;

import cs3500.animator.model.Animation;
import cs3500.animator.model.IAnimation;
import cs3500.animator.view.AbstractViews;
import cs3500.animator.view.CompositeView;
//import cs3500.animator.view.CompositeViewSloMo;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.ViewFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cs3500.animator.util.AnimationReader.parseFile;

/**
 * Controller for an animation that uses the command inputs and logic from the Animation model
 * Builder class to create animations with the appropriate specifications.
 */
public class AnimationController implements IAnimationController {

  private final Readable rd;
  private final Readable slomoRd ;
  private final Appendable ap;

  /**
   * Constructor for AnimationController that takes in a Readable and Appendable for further use in
   * creating animation views.
   *
   * @param rd Readable used to take in shapes and motions from input file.
   * @param ap Appendable used to output text information for view types where that is required.
   */
  public AnimationController(Readable rd, Appendable ap, Readable slomoRd) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }
    this.rd = rd;
    this.slomoRd = slomoRd;
    this.ap = ap;
  }

  public AnimationController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }
    this.rd = rd;
    this.slomoRd = null;
    this.ap = ap;
  }


  @Override
  public void animate(int speed, String viewName) {
    if (viewName == null) {
      throw new IllegalArgumentException("viewName cannot be null.");
    }
    Animation.Builder builder = new Animation.Builder(speed, ap);
    try {

      IAnimation model = parseFile(rd, builder);

      ViewFactory factory = new ViewFactory(model, builder.getAp(), builder.getCanvasX(),
          builder.getCanvasY(), builder.getCanvasWidth(), builder.getCanvasHeight(),
          (int) builder.getSpeed(), slomoRd);

      ViewFactory.ViewName name = ViewFactory.ViewName.valueOf(viewName.toUpperCase());
      IAnimationView view = factory.createView(name);
      view.render();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid Readable inputted.");
    } catch (IOException e2) {
      throw new IllegalStateException("Failed to render animation.");
    }
  }

  @Override
  public void animateSloMo(int speed, String viewName, List<Integer> sloMoIntervals) {

//    Animation.Builder builder = new Animation.Builder(speed, ap);
//    List<Integer> copy = new ArrayList<>(sloMoIntervals);
//    IAnimation model = parseFile(rd, builder);
//    AbstractViews view = new CompositeView(model, builder.getAp(), builder.getCanvasX(),
//        builder.getCanvasY(), builder.getCanvasWidth(), builder.getCanvasHeight(),
//        (int) builder.getSpeed(),copy);
//
//    view.render();
  }
}
