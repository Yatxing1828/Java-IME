package model;

import java.awt.image.BufferedImage;

/**
 * Represents an image which is either backed by a {@code javax.awt.Image} or can be converted to
 * one quickly.
 */
public interface ISwingImage extends IImage {
  /**
   * Returns a view of this image as a {@code javax.awt.Image}.
   *
   * @return the Image
   */
  BufferedImage getBufferedImage();

  /**
   * Builder that builds an ISwingImage.
   */
  interface Builder extends IImage.Builder {
    @Override
    ISwingImage build() throws IllegalStateException;
  }
}
