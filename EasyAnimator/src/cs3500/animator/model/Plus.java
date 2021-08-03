package cs3500.animator.model;

import java.awt.*;

public class Plus extends Polygon {

  public Plus(int width, int height, Position2D position, int angle, Color color) {
    super(width, height, position, angle, color);
  }

  @Override
  public void setupXYCoords() {
    int x = this.getWidth();
    int y = this.getHeight();
    int xstep1 = this.getWidth() / 4;
    int xstep3 = this.getWidth() * 3 / 4;
    int ystep1 = this.getHeight() / 4;
    int ystep3 = this.getHeight() * 3 / 4;

    xs = new int[]{xstep1, xstep3, xstep3, x, x, xstep3, xstep3, xstep1, xstep1, 0, 0, xstep1};
    ys = new int[]{0, 0, ystep1, ystep1, ystep3, ystep3, y, y, ystep3, ystep3, ystep1, ystep1};
    assert (xs.length == ys.length);
    for (int i = 0; i < xs.length; ++i) {
      xs[i] += this.getX();
      ys[i] += this.getY();
    }
  }
}
