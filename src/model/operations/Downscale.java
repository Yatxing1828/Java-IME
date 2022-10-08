package model.operations;

import model.IImage;
import model.Pixel;
import model.StridedImage;

/**
 * Represents an image transformation that downscales an image, which proportionally decreases the
 * number of pixels in the image as it scales down.
 */
public class Downscale implements IOperation {
  private final float heightRatio;
  private final float widthRatio;

  /**
   * Constructs a Downscale operation.
   *
   * @param heightRatio the height ratio.
   * @param widthRatio  the width ratio.
   */
  public Downscale(float heightRatio, float widthRatio) {
    if (heightRatio < 0 || heightRatio > 1 || widthRatio < 0 || widthRatio > 1) {
      throw new IllegalArgumentException("Ratios must be between 0 and 1");
    }
    this.heightRatio = heightRatio;
    this.widthRatio = widthRatio;
  }

  @Override
  public IImage apply(IImage img) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    IImage.Builder builder = new StridedImage.Builder(
            Math.round(this.heightRatio * img.getHeight()),
            Math.round(this.widthRatio * img.getWidth()));
    for (int i = 0; i < builder.getHeight(); ++i) {
      for (int j = 0; j < builder.getWidth(); ++j) {
        float rawRow = i / this.heightRatio;
        float rawCol = j / this.widthRatio;

        int rowFloor = (int) rawRow;
        int colFloor = (int) rawCol;

        float red = 0;
        float green = 0;
        float blue = 0;

        for (int row = rowFloor; row < rowFloor + 2; ++row) {
          for (int col = colFloor; col < colFloor + 2; ++col) {
            // Odd case: when ratio is 1, rowFloor == i, so rowFloor + 1 can be invalid
            Pixel p = img.getPixel(Math.min(row, img.getHeight() - 1),
                    Math.min(col, img.getWidth() - 1));
            float weight = Math.abs(row - rawRow) * Math.abs(col - rawCol);
            red += p.getRed() * weight;
            green += p.getGreen() * weight;
            blue += p.getBlue() * weight;
          }
        }

        builder.setPixel(i, j, new Pixel(red, green, blue));
      }
    }
    return builder.build();
  }
}
