package model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A simple data class which represents a mutable pixel.
 */
public class Pixel {
  private final float red;
  private final float green;
  private final float blue;

  /**
   * Constructs a Pixel.
   *
   * @param red   float for red value of rgb.
   * @param green float for green value of rgb.
   * @param blue  float for blue value of rgb.
   */
  public Pixel(float red, float green, float blue) {
    this.red = validateValue(red);
    this.green = validateValue(green);
    this.blue = validateValue(blue);
  }

  /**
   * Alernate constructor that creates a Pixel of grey scale.
   *
   * @param gray passed in float to set all r, g, b fields to.
   */
  public Pixel(float gray) {
    this.red = validateValue(gray);
    this.green = gray;
    this.blue = gray;
  }

  private static float validateValue(float value) {
    if (value < 0 || value > 1) {
      throw new IllegalArgumentException("Value cannot be less than 0 or greater than 1");
    }
    return value;
  }

  /**
   * Utility method which clamps the argument to 0 <= value <= 1.
   *
   * @param value the value to clamp
   * @return the clamped value
   */
  public static float clamp(float value) {
    return Math.max(0, Math.min(value, 1));
  }

  public float getRed() {
    return this.red;
  }

  public float getGreen() {
    return this.green;
  }

  public float getBlue() {
    return this.blue;
  }

  public float getIntensity() {
    return (this.red + this.green + this.blue) / 3;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pixel pixel = (Pixel) o;
    return Float.compare(pixel.getRed(), getRed()) == 0
            && Float.compare(pixel.getGreen(), getGreen()) == 0
            && Float.compare(pixel.getBlue(), getBlue()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRed(), getGreen(), getBlue());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Pixel.class.getSimpleName() + "[", "]")
            .add("red=" + red)
            .add("green=" + green)
            .add("blue=" + blue)
            .toString();
  }
}
