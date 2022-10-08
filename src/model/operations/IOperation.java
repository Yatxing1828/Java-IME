package model.operations;

import model.IImage;

/**
 * Represents the operations that can be done on an image.
 */
public interface IOperation {

  /**
   * Applies the specified change/adjustment to the image.
   *
   * @param img the image to manipulate
   */
  IImage apply(IImage img);
}
