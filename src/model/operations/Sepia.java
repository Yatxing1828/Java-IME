package model.operations;

import model.Matrix;

/**
 * Represents the Sepia color transformation on an image.
 */
public class Sepia extends ColorTransformation {

  /**
   * Constructs a new ColorTransformation using the specified pixels as multipliers for each
   * component.
   *
   * @throws IllegalArgumentException if the matrix is not 3x3
   */
  public Sepia() throws IllegalArgumentException {
    super(new Matrix(new float[]{
        0.393f, 0.769f, 0.189f,
        0.349f, 0.686f, 0.168f,
        0.272f, 0.534f, 0.131f}, 3, 3));
  }
}
