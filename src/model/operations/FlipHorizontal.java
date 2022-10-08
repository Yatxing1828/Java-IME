package model.operations;

import model.IImage;
import model.Pixel;
import model.StridedImage;

/**
 * Represents an image transformation that flips the image horizontally.
 */
public class FlipHorizontal implements IOperation {
  @Override
  public IImage apply(IImage img) {
    IImage.Builder builder = new StridedImage.Builder(img.getHeight(), img.getWidth());
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < (img.getWidth() + 1) / 2; j++) {
        int mirror = img.getWidth() - 1 - j;
        Pixel left = img.getPixel(i, j);
        Pixel right = img.getPixel(i, mirror);
        builder.setPixel(i, j, right);
        builder.setPixel(i, mirror, left);
      }
    }
    return builder.build();
  }
}
