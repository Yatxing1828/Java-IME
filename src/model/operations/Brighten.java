package model.operations;

import model.Pixel;

/**
 * Represents an image transformation that brightens (and darkens) each pixel by the specified
 * amount. A positive amount will brighten the image, while a negative amount will darken it.
 */
public class Brighten extends PerPixelChannelOperation {

  private final float amount;

  /**
   * Constructs a new Brighten operation with the specified offset amount.
   *
   * @param amount the offset amount
   * @throws IllegalArgumentException if value is not between -1 and 1
   */
  public Brighten(float amount) throws IllegalArgumentException {
    if (amount < -1 || amount > 1) {
      throw new IllegalArgumentException("Amount must be between -1 and 1.");
    }
    this.amount = amount;
  }

  /**
   * Adds the given value to the channel value from the current channel value to
   * make the pixel brighter.
   *
   * @param value current value of the channel.
   * @return the new channel value after brightening.
   */
  @Override
  public float applyPixelChannel(float value) {
    return Pixel.clamp(value + amount);
  }

}
