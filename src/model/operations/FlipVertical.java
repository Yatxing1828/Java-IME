package model.operations;

import model.IImage;
import model.Pixel;
import model.StridedImage;

/**
 * Represents an image transformation that flips the image vertically.
 */
public class FlipVertical implements IOperation {

  @Override
  public IImage apply(IImage img) {
    IImage.Builder builder = new StridedImage.Builder(img.getHeight(), img.getWidth());
    for (int i = 0; i < (img.getHeight() + 1) / 2; i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int mirror = img.getHeight() - 1 - i;
        Pixel top = img.getPixel(i, j);
        Pixel bottom = img.getPixel(mirror, j);
        builder.setPixel(i, j, bottom);
        builder.setPixel(mirror, j, top);
      }
    }
    return builder.build();
  }
}
