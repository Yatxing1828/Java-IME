package model;

import java.util.Arrays;

/**
 * A simple data container which computes a histogram of the reds, greens, blues, and intensities
 * of an image upon construction.
 */
public class Histogram {
  private final int[] reds = new int[256]; // Fills each array with 0s
  private final int[] greens = new int[256];
  private final int[] blues = new int[256];
  private final int[] intensities = new int[256];

  private final int max;

  /**
   * Constructs a new Histogram from the provided image source.
   *
   * @param image the image to compute the histograms of
   * @throws IllegalArgumentException if image is null
   */
  public Histogram(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    for (int i = 0; i < image.getHeight(); ++i) {
      for (int j = 0; j < image.getWidth(); ++j) {
        Pixel p = image.getPixel(i, j);
        ++this.reds[Math.round(p.getRed() * 255)];
        ++this.greens[Math.round(p.getGreen() * 255)];
        ++this.blues[Math.round(p.getBlue() * 255)];
        ++this.intensities[Math.round(p.getIntensity() * 255)];
      }
    }

    // Compute the max once and for all, since it's rather expensive
    this.max = Math.max(Arrays.stream(reds).max().orElse(0),
            Math.max(Arrays.stream(greens).max().orElse(0),
                    Math.max(Arrays.stream(blues).max().orElse(0),
                            Arrays.stream(intensities).max().orElse(0))));
  }

  private static int validateIndex(int i) throws IndexOutOfBoundsException {
    if (i < 0 || i > 255) {
      throw new IllegalArgumentException("Index must be between 0 and 255");
    }
    return i;
  }

  /**
   * Gets the number of pixels in the image that have the specified red value.
   *
   * @param i the red value to retrieve the count for
   * @return the count
   * @throws IndexOutOfBoundsException if the value is less than 0 or greater than 255
   */
  public int getRedCount(int i) throws IndexOutOfBoundsException {
    return this.reds[validateIndex(i)];
  }

  /**
   * Gets the number of pixels in the image that have the specified green value.
   *
   * @param i the green value to retrieve the count for
   * @return the count
   * @throws IndexOutOfBoundsException if the value is less than 0 or greater than 255
   */
  public int getGreenCount(int i) throws IndexOutOfBoundsException {
    return this.greens[validateIndex(i)];
  }

  /**
   * Gets the number of pixels in the image that have the specified blue value.
   *
   * @param i the blue value to retrieve the count for
   * @return the count
   * @throws IndexOutOfBoundsException if the value is less than 0 or greater than 255
   */
  public int getBlueCount(int i) throws IndexOutOfBoundsException {
    return this.blues[validateIndex(i)];
  }

  /**
   * Gets the number of pixels in the image that have the specified intensity value.
   *
   * @param i the intensity value to retrieve the count for
   * @return the count
   * @throws IndexOutOfBoundsException if the value is less than 0 or greater than 255
   */
  public int getIntensityCount(int i) throws IndexOutOfBoundsException {
    return this.intensities[validateIndex(i)];
  }

  /**
   * Returns the highest singular count of any channel value in the image.
   *
   * @return the highest count
   */
  public int getMaxCount() {
    return this.max;
  }
}
