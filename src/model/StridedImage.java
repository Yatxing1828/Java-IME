package model;

import java.util.Arrays;

/**
 * A basic implementation of the IImage interface which uses a strided array of pixels as its
 * underlying data representation.
 */
public class StridedImage implements IImage {
  private final Pixel[] pixels;
  private final int height;
  private final int width;

  /**
   * Constructs a StridedImage.
   *
   * @param pixels the pixel array representing all pixels in the image.
   * @param height the height of the image.
   * @param width  the width of the image.
   * @throws IllegalArgumentException if pixels provided are null.
   */
  public StridedImage(Pixel[] pixels, int height, int width) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }
    for (Pixel pixel : pixels) {
      if (pixel == null) {
        throw new IllegalArgumentException("Not all pixels are initialized");
      }
    }
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Height and width cannot be negative");
    }
    if (pixels.length != height * width) {
      throw new IllegalArgumentException("Data array length does not match dimensions");
    }
    this.pixels = Arrays.copyOf(pixels, pixels.length);
    this.height = height;
    this.width = width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public Pixel getPixel(int row, int col) {
    if (row < 0 || row >= this.height) {
      throw new IndexOutOfBoundsException("Row is out of range");
    }
    if (col < 0 || col >= this.width) {
      throw new IndexOutOfBoundsException("Column is out of range");
    }
    return this.pixels[this.width * row + col];
  }

  /**
   * Inner builder class that builds a StridedImage.
   */
  public static class Builder implements IImage.Builder {
    private final Pixel[] pixels;
    private final int height;
    private final int width;

    /**
     * Constructs the builder to build a StridedImage.
     *
     * @param height image height.
     * @param width  image width.
     * @throws IllegalArgumentException if provided height or width are negative.
     */
    public Builder(int height, int width) throws IllegalArgumentException {
      if (height < 0 || width < 0) {
        throw new IllegalArgumentException("Arguments cannot be negative");
      }
      this.pixels = new Pixel[width * height];
      this.height = height;
      this.width = width;
    }

    @Override
    public int getHeight() {
      return this.height;
    }

    @Override
    public int getWidth() {
      return this.width;
    }

    @Override
    public void setPixel(int row, int col, Pixel pixel)
            throws IllegalArgumentException, IndexOutOfBoundsException {
      if (row < 0 || row >= this.height) {
        throw new IndexOutOfBoundsException("Row is out of range");
      }
      if (col < 0 || col >= this.width) {
        throw new IndexOutOfBoundsException("Column is out of range");
      }
      if (pixel == null) {
        throw new IllegalArgumentException("Pixel cannot be null");
      }
      this.pixels[this.width * row + col] = pixel;
    }

    @Override
    public StridedImage build() throws IllegalStateException {
      try {
        return new StridedImage(pixels, this.getHeight(), this.getWidth());
      } catch (IllegalArgumentException e) {
        // This only occurs when not all elements in the array have been initialized. This is
        // because the length of the data array, as well as the width and height, must be valid.
        throw new IllegalStateException(e);
      }
    }
  }
}
