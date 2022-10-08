package model.reader;

import java.io.IOException;

import model.IImage;

/**
 * An ImageReader implementation which combines the ImageIOImageReader's and PPMImageReader's
 * functionality.
 */
public class CompositeImageReader implements ImageReader {
  private final ImageReader reader;

  /**
   * Constructs a new ImageReader to read the specified filename.
   * The file is not opened, but its extension is checked at construction time.
   *
   * @param filename the path to the image file
   * @throws IllegalArgumentException if the file extension is unsupported
   */
  public CompositeImageReader(String filename) throws IllegalArgumentException {
    ImageReader reader;
    try {
      reader = new PPMImageReader(filename);
    } catch (IllegalArgumentException e) {
      // The following throws an IllegalArgumentException if the extension is still unsupported
      reader = new ImageIOImageReader(filename);
    }
    this.reader = reader;
  }

  @Override
  public IImage read(BuilderConstructor constructor) throws IOException {
    return this.reader.read(constructor);
  }

  @Override
  public void write(IImage image) throws IOException {
    this.reader.write(image);
  }
}
