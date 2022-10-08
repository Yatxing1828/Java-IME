package model.operations;

import model.ImageChannel;
import model.Pixel;

/**
 * Represents an image transformation that changes the image color to greyscale depending on the
 * specified channel/variation.
 */
public class GreyScale extends PerPixelOperation {
  private final ImageChannel channel;

  /**
   * Constructs a new GreyScale operation according to the specified variation.
   *
   * @param channel represents the variation (red, green, blue, luma, intensity, or value)
   */
  public GreyScale(ImageChannel channel) {
    if (channel == null) {
      throw new IllegalArgumentException("Channel cannot be null.");
    }
    this.channel = channel;
  }

  @Override
  protected Pixel applyPixel(Pixel pixel) {
    float value;
    switch (this.channel) {
      case RED:
        value = pixel.getRed();
        break;
      case GREEN:
        value = pixel.getGreen();
        break;
      case BLUE:
        value = pixel.getBlue();
        break;
      case VALUE:
        value = Math.max(Math.max(pixel.getRed(), pixel.getGreen()), pixel.getBlue());
        break;
      case INTENSITY:
        value = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
        break;
      case LUMA:
        value = Pixel.clamp(0.2126f * pixel.getRed() + 0.7512f * pixel.getGreen()
                + 0.0722f * pixel.getBlue());
        break;
      default:
        throw new UnsupportedOperationException("This channel variant is not supported.");
    }
    return new Pixel(value, value, value);
  }
}
