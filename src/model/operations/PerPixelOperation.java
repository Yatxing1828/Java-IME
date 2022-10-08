package model.operations;

import model.IImage;
import model.Pixel;
import model.StridedImage;

/**
 * Abstract class representing operations performed on pixels. It is abstracted because it applies
 * to all operations that require adjusting the values of the rgb channels themselves, such as
 * Brighten.
 */
public abstract class PerPixelOperation implements IOperation {
  /**
   * Applies the change to the image by adjusting the channel values of each pixel accordingly.
   *
   * @param img represents the image being changed.
   */
  @Override
  public IImage apply(IImage img) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    IImage.Builder builder = new StridedImage.Builder(img.getHeight(), img.getWidth());
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        builder.setPixel(i, j, this.applyPixel(img.getPixel(i, j)));
      }
    }
    return builder.build();
  }

  /**
   * Applies the appropriate adjustment to the channel value (r, g, or b).
   *
   * @param value the current value of the channel.
   * @return the new adjusted value of the channel.
   */
  protected abstract Pixel applyPixel(Pixel value);
}
