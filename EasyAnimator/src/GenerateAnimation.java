import static java.lang.Math.abs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Programmatically generates an animation to sort a random array of blocks in ascending height
 * using the selection sort algorithm.
 */
public final class GenerateAnimation {

  int[] arr;
  String[] shapes;
  Map<String, Integer> xPositions;
  Map<String, Integer> yPositions;
  Map<String, Integer> rValues;
  Map<String, Integer> gValues;
  Map<String, Integer> bValues;
  Map<String, Integer> heights;
  int width = 20; // Arbitrarily picked width of each shape
  int tallestShape; // Height of the tallest shape in the animation
  int heightGap = 30; // Arbitrarily picked height gap above of each shape when moving
  int time1;
  int time2;
  FileWriter animation;

  /**
   * Constructor for the GenerateAnimation class.  When called, the animation is automatically
   * created.  It will first print the initial array at time 0, and once the animation starts, it
   * will use selection sort to put the shapes in ascending height order.
   *
   * @param arr Array to be sorted with selection sort in ascending height order.
   */
  GenerateAnimation(int[] arr) {
    this.arr = Arrays.copyOf(arr, arr.length);
    try {
      animation = new FileWriter(new File("src/inputs", "animation1.txt"));
      int size = this.arr.length;
      shapes = new String[size];
      xPositions = new HashMap<>();
      yPositions = new HashMap<>();
      rValues = new HashMap<>();
      gValues = new HashMap<>();
      bValues = new HashMap<>();
      heights = new HashMap<>();
      // Determining width of canvas
      int shapeGap = 10; // Arbitrarily picked space between each shape
      int totalWidth =
          (size - 1) * (width + shapeGap) + width; // Width of space that animation takes up
      int totalWidthPlusSpace = totalWidth + 200; // Leave 100 free space on each side of animation
      // Toolbar for interactive view takes around 600 width
      int canvasWidth = 700; // Default canvas width (has extra space on each side)
      // If the animation is wider than the toolbar, set the width accordingly
      if (totalWidthPlusSpace > canvasWidth) {
        canvasWidth = totalWidthPlusSpace;
      }
      // Determining x position of first shape
      int xPos = (canvasWidth / 2) - (totalWidth / 2);

      // Determining height of canvas
      this.tallestShape = this.arr[0];
      for (int i = 1; i < size; i++) {
        if (this.arr[i] > tallestShape) {
          tallestShape = this.arr[i];
        }
      }
      int totalHeight =
          (tallestShape * 3) + (2 * heightGap); // 30 extra space for shapes to around each other
      int canvasHeight = totalHeight + 200; // 100 extra room on top and bottom
      // Determining y position of shapes
      int yLevel = 50;
      yLevel = (canvasHeight / 2) - (tallestShape / 2);

      animation.write("canvas 0 0 " + canvasWidth + " " + canvasHeight + "\r");

      // Generating shapes and initial positions
      int shapeCount = 0;
      int r = 255;
      int g = 0;
      int b = 0;
      int height1 = this.arr[0];
      int colorShift = 510 / size;
      int height;
      for (int i = 0; i < size; i++) {
        shapeCount++;
        shapes[i] = "box" + shapeCount;
        height = this.arr[i];
        heights.put(shapes[i], height);
        int yPos = yLevel - height + height1;
        yPositions.put(shapes[i], yPos);
        animation.write("shape " + shapes[i] + " rectangle\r");
        int initTime1 = 0;
        int initTime2 = 10;
        writeHelper(shapes[i], height, initTime1, initTime2, xPos, xPos, yPos, yPos, r, g, b);
        xPositions.put(shapes[i], xPos);
        xPos += width + shapeGap;
        // Storing colors
        rValues.put(shapes[i], r);
        gValues.put(shapes[i], g);
        bValues.put(shapes[i], b);
        // Shifting colors
        if (r > 0) {
          r -= colorShift;
          g += colorShift;
          if (r < 0) {
            r = 0;
          }
          if (g > 255) {
            g = 255;
          }
        } else {
          g -= colorShift;
          b += colorShift;
          if (g < 0) {
            g = 0;
          }
          if (b > 255) {
            b = 255;
          }
        }
      }

      // Generating motions
      this.time1 = 10;
      this.time2 = 30;
      int x1;
      int x2;
      int y1;
      int y2;

      String shape;
      for (int i = 0; i < size - 1; i++) {
        int minAt = i;
        for (int j = i + 1; j < size; j++) {
          if (this.arr[j] < this.arr[minAt]) {
            minAt = j;
          }
        }
        if (i != minAt) {
          //  i shape moves up (-tallestShape - 30 gap)
          shape = this.shapes[i];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          x2 = x1;
          y1 = yPositions.get(shape);
          y2 = y1 - tallestShape - 30;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          yPositions.put(shape, y2);

          // minAt shape moves down (+tallestShape + 30 gap)
          shape = this.shapes[minAt];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          x2 = x1;
          y1 = yPositions.get(shape);
          y2 = y1 + tallestShape + 30;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          yPositions.put(shape, y2);
          printStillShapes(i, minAt);
          time1 += 20;
          time2 += 20;

          // shape i second move
          shape = this.shapes[i];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          // moves right
          x2 = x1 + abs(x1 - xPositions.get(this.shapes[minAt]));
          y1 = yPositions.get(shape);
          y2 = y1;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          int x1Temp = x1;
          xPositions.put(shape, x2);

          // shape minAt second move
          shape = this.shapes[minAt];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          // move left
          x2 = x1 - abs(x1 - x1Temp);
          y1 = yPositions.get(shape);
          y2 = y1;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          xPositions.put(shape, x2);
          printStillShapes(i, minAt);
          time1 += 20;
          time2 += 20;

          // i shape third move
          // down (+tallestShape + 30 gap)
          shape = this.shapes[i];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          x2 = x1;
          y1 = yPositions.get(shape);
          y2 = y1 + tallestShape + 30;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          yPositions.put(shape, y2);

          // minAt third move
          // up down (-tallestShape - 30 gap)
          shape = this.shapes[minAt];
          height = this.heights.get(shape);
          r = this.rValues.get(shape);
          g = this.gValues.get(shape);
          b = this.bValues.get(shape);
          x1 = xPositions.get(shape);
          x2 = x1;
          y1 = yPositions.get(shape);
          y2 = y1 - tallestShape - 30;
          writeHelper(shape, height, time1, time2, x1, x2, y1, y2, r, g, b);

          // update changed values
          yPositions.put(shape, y2);
          printStillShapes(i, minAt);
          time1 += 20;
          time2 += 20;

          int swap = this.arr[minAt];
          this.arr[minAt] = this.arr[i];
          this.arr[i] = swap;

          // update shape order
          String shapesTemp = this.shapes[minAt];
          this.shapes[minAt] = this.shapes[i];
          this.shapes[i] = shapesTemp;
        }
      }

      animation.close();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to file.");
    }
  }

  private void writeHelper(String shape, int height, int t1, int t2, int x1,
      int x2, int y1, int y2, int r, int g, int b) {
    try {
      animation.write("motion " + shape + " "
          + t1 + " " + x1 + " " + y1 + " " + width + " " + height + " " + r + " " + g
          + " " + b + " "
          + t2 + " " + x2 + " " + y2 + " " + width + " " + height + " " + r + " " + g
          + " " + b + "\r");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to file.");
    }
  }

  /**
   * Prints the shapes that are not moving while two other shapes are being swapped.
   * @param i         index of one shape being swapped
   * @param minAt     index of the other shape being swapped
   */
  private void printStillShapes(int i, int minAt) {
    for (int j = 0; j < this.arr.length; j++) {
      if (j != i && j != minAt) {
        String shape = this.shapes[j];
        int height = this.heights.get(shape);
        int r = this.rValues.get(shape);
        int g = this.gValues.get(shape);
        int b = this.bValues.get(shape);
        int x = xPositions.get(shape);
        int y = yPositions.get(shape);
        writeHelper(shape, height, time1, time2, x, x, y, y, r, g, b);
      }
    }
  }

  /**
   * Prints the array.  Used to check that the selection sort algorithm worked properly.
   */
  private void printArray() {
    for (int x : this.arr) {
      System.out.print(x + " ");
    }
    System.out.println();
  }

  /**
   * Helper method for generateAnimation that generates the random array of integers to be sorted.
   *
   * @param size           size of the array
   * @param maxShapeHeight maximum value of a given element in the array
   * @return The randomly generated array of integers.
   */
  private static int[] generateArray(int size, int maxShapeHeight) {
    int s = size;
    int mSH = maxShapeHeight;
    int[] arrRand = new int[s];
    for (int i = 0; i < s; i++) {
      int rand = (int) (Math.random() * mSH);
      if (rand < 1) {
        rand = 1;
      }
      arrRand[i] = rand;
    }
    return arrRand;
  }

  /**
   * Static method that generates the animation when ran.  It generates an array of the inputted
   * size to be sorted with selection sort, and takes in the max shape height (the largest value
   * being sorted).
   *
   * @param size           size of array to be generated
   * @param maxShapeHeight the maximum value of any given element in the array
   */
  public static void generateAnimation(int size, int maxShapeHeight) {
    // Randomly generates array based on size and maxShapeHeight
    int[] arr = generateArray(size, maxShapeHeight);
    // Generating the animation with a randomly generated array
    GenerateAnimation rand = new GenerateAnimation(arr);
    rand.printArray();
  }

  /**
   * Static method that generates the animation when ran.  Uses default parameters for size and max
   * shape height to generate the array of integers to be sorted with selection sort.
   */
  public static void generateAnimation() {
    int size = 20; // Default parameter for array size
    int maxShapeHeight = 100; // Default parameter for max shape height
    int[] arr = generateArray(size, maxShapeHeight);
    GenerateAnimation rand = new GenerateAnimation(arr);
    rand.printArray();


  }
}
