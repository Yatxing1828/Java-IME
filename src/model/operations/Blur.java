package model.operations;

import model.Matrix;

/**
 * Represents the blur operation, which blurs an image with a preset 3x3 filter.
 */
public class Blur extends FilterOperation {

  /**
   * Constructs a new Blur operation.
   */
  public Blur() {
    super(new Matrix(new float[]{
        0.0625f, 0.125f, 0.0625f,
        0.125f, 0.25f, 0.125f,
        0.0625f, 0.125f, 0.0625f}, 3, 3));
  }
}
