package model;

import model.operations.IOperation;

/**
 * Represents the image, which is stored as a 3D integer array.
 */
public interface IImage {

  /**
   * Gets this image's height.
   *
   * @return integer representing image height.
   */
  int getHeight();

  /**
   * Gets this image's width.
   *
   * @return integer representing image width.
   */
  int getWidth();

  /**
   * Gets the value of the specified pixel's channel.
   *
   * @param row row value of the pixel.
   * @param col column value of the pixel.
   * @return returns the float representing the value of the specified channel.
   * @throws IndexOutOfBoundsException if row or col are out of range
   */
  Pixel getPixel(int row, int col) throws IndexOutOfBoundsException;

  /**
   * Performs the specified transformation on an image.
   *
   * @param op the transformation to be performed on the image.
   */
  default IImage transform(IOperation op) {
    if (op == null) {
      throw new IllegalArgumentException("Operation cannot be null");
    }
    return op.apply(this);
  }

  /**
   * Represents a builder that builds an IImage.
   */
  interface Builder {

    /**
     * Gets the height of the IImage.
     * @return an integer representing the height of the IImage.
     */
    int getHeight();

    /**
     * Gets the width of the IImage.
     * @return an integer representing the width of the IImage.
     */
    int getWidth();

    /**
     * Sets the pixel at the specified location to the specified value.
     *
     * @param row   the row index
     * @param col   the column index
     * @param pixel the Pixel value
     * @throws IllegalArgumentException  if pixel is null
     * @throws IndexOutOfBoundsException if row or col are out of range for this Builder
     */
    void setPixel(int row, int col, Pixel pixel)
            throws IllegalArgumentException, IndexOutOfBoundsException;

    /**
     * Constructs the resulting IImage.
     *
     * @return the image
     * @throws IllegalStateException if not all pixels have been initialized
     */
    IImage build() throws IllegalStateException;
  }
}
