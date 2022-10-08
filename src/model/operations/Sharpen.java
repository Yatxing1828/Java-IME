package model.operations;


import model.Matrix;

/**
 * Represents the sharpen image filter operation.
 */
public class Sharpen extends FilterOperation {

  /**
   * Constructs a new Sharpen operation.
   */
  public Sharpen() {
    super(new Matrix(new float[]{
        -0.125f, -0.125f, -0.125f, -0.125f, -0.125f,
        -0.125f, 0.25f, 0.25f, 0.25f, -0.125f,
        -0.125f, 0.25f, 1, 0.25f, -0.125f,
        -0.125f, 0.25f, 0.25f, 0.25f, -0.125f,
        -0.125f, -0.125f, -0.125f, -0.125f, -0.125f
    }, 5, 5));
  }


}
