package model.operations;

import model.Matrix;
import model.Pixel;

/**
 * Represents a color transformation operation.
 */
public abstract class ColorTransformation extends PerPixelOperation {
  private final Matrix matrix;

  /**
   * Constructs a new ColorTransformation using the specified pixels as multipliers for each
   * component.
   *
   * @param matrix the matrix to perform the transformation with
   * @throws IllegalArgumentException if the matrix is not 3x3
   */
  protected ColorTransformation(Matrix matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    if (matrix.getHeight() != 3 || matrix.getWidth() != 3) {
      throw new IllegalArgumentException("Matrix must be 3x3");
    }
    this.matrix = matrix;
  }

  private float clampedDotProduct(Pixel pixel, int row) {
    return Pixel.clamp(pixel.getRed() * this.matrix.get(row, 0)
            + pixel.getGreen() * this.matrix.get(row, 1)
            + pixel.getBlue() * this.matrix.get(row, 2));
  }

  @Override
  protected Pixel applyPixel(Pixel value) {
    return new Pixel(
            clampedDotProduct(value, 0),
            clampedDotProduct(value, 1),
            clampedDotProduct(value, 2));
  }
}
