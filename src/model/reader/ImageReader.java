package model.reader;

import java.io.IOException;

import model.IImage;

/**
 * Represents an image reader that reads files and writes to files.
 */
public interface ImageReader {

  /**
   * Reads from a file via input stream and returns an IImage representation.
   *
   * @param constructor the passed in BuilderConstructor.
   * @return the IImage representation of the read file.
   * @throws IOException if the data was not successfully transmitted through input/output stream.
   */
  IImage read(BuilderConstructor constructor) throws IOException;

  /**
   * Writes to a file with the passed in image.
   *
   * @param image the passed in image.
   * @throws IOException if the data was not successfully transmitted through input/output stream.
   */
  void write(IImage image) throws IOException;

  /**
   * Represents builder and constructor for an image reader.
   */
  @FunctionalInterface
  interface BuilderConstructor {
    IImage.Builder construct(int height, int width);
  }
}
