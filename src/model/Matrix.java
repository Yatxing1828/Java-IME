package model;

import java.util.Arrays;

/**
 * A simple data class representing a two-dimensional array of floats.
 */
public class Matrix {
  private final float[] data;
  private final int height;
  private final int width;

  /**
   * Constructs a Matrix.
   *
   * @param data   float array representing the data.
   * @param height height of the matrix.
   * @param width  width of the matrix.
   * @throws IllegalArgumentException if height & width are negative, and if dimensions don't match.
   */
  public Matrix(float[] data, int height, int width) throws IllegalArgumentException {
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Height and width cannot be negative");
    }
    if (data.length != height * width) {
      throw new IllegalArgumentException("Data length does not match matrix dimensions");
    }
    this.data = Arrays.copyOf(data, data.length);
    this.height = height;
    this.width = width;
  }

  /**
   * Gets the float representing the data at the specified row and col.
   *
   * @param row row value.
   * @param col col value.
   * @return the float at row, col.
   * @throws IndexOutOfBoundsException if the given row or col are out of bounds.
   */
  public float get(int row, int col) throws IndexOutOfBoundsException {
    if (row < 0 || row >= this.height) {
      throw new IndexOutOfBoundsException("Row is out of range");
    }
    if (col < 0 || col >= this.width) {
      throw new IndexOutOfBoundsException("Column is out of range");
    }
    return this.data[this.width * row + col];
  }

  /**
   * Gets the height of the matrix.
   *
   * @return an int representing the height.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the width of the matrix.
   *
   * @return an int representing the width.
   */
  public int getWidth() {
    return this.width;
  }
}
