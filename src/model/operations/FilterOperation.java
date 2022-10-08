package model.operations;

import model.IImage;
import model.Matrix;
import model.Pixel;
import model.StridedImage;

/**
 * Represents an operation that filters an image.
 */
public abstract class FilterOperation implements IOperation {
  private final Matrix filter;
  private final int heightOffset;
  private final int widthOffset;

  /**
   * Constructs a filter operation.
   *
   * @param filter a matrix representing the filter to use on the image.
   * @throws IllegalArgumentException if the image is null or if the filter has even dimensions.
   */
  protected FilterOperation(Matrix filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (filter.getHeight() % 2 == 0 || filter.getWidth() % 2 == 0) {
      throw new IllegalArgumentException("Filter must have odd dimensions");
    }
    this.filter = filter;
    this.heightOffset = this.filter.getHeight() / 2;
    this.widthOffset = this.filter.getWidth() / 2;
  }

  /**
   * Applies the filter to the given image.
   *
   * @param img the image to manipulate.
   * @return the filtered image.
   * @throws IllegalArgumentException if the given image is null.
   */
  @Override
  public IImage apply(IImage img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    IImage.Builder builder = new StridedImage.Builder(img.getHeight(), img.getWidth());
    for (int row = 0; row < img.getHeight(); ++row) {
      for (int col = 0; col < img.getWidth(); ++col) {
        float red = 0;
        float green = 0;
        float blue = 0;
        for (int i = 0; i < this.filter.getHeight(); ++i) {
          for (int j = 0; j < this.filter.getWidth(); ++j) {
            float multiplier = this.filter.get(i, j);
            Pixel imgPixel;
            try {
              imgPixel = img.getPixel(row - this.heightOffset + i, col - this.widthOffset + j);
            } catch (IndexOutOfBoundsException e) {
              continue;
            }
            red += multiplier * imgPixel.getRed();
            green += multiplier * imgPixel.getGreen();
            blue += multiplier * imgPixel.getBlue();
          }
        }
        builder.setPixel(row, col,
                new Pixel(Pixel.clamp(red), Pixel.clamp(green), Pixel.clamp(blue)));
      }
    }
    return builder.build();
  }
}
