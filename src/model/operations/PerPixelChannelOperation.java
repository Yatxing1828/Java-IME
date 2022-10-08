package model.operations;

import model.Pixel;

/**
 * Represents an image operation that involves an individual pixel.
 */
public abstract class PerPixelChannelOperation extends PerPixelOperation {
  @Override
  protected Pixel applyPixel(Pixel value) {
    return new Pixel(
            this.applyPixelChannel(value.getRed()),
            this.applyPixelChannel(value.getGreen()),
            this.applyPixelChannel(value.getBlue()));
  }

  protected abstract float applyPixelChannel(float value);
}
