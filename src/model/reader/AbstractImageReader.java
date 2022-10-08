package model.reader;

/**
 * Represents an implementation of an ImageReader of one or more file types.
 */
public abstract class AbstractImageReader implements ImageReader {
  private final String filename;
  private final String suffix;

  /**
   * Constructs a new ImageReader to read the specified filename.
   * The file is not opened, but its extension is checked at construction time.
   *
   * @param filename the path to the image file
   * @throws IllegalArgumentException if the file extension is unsupported
   */
  public AbstractImageReader(String filename) throws IllegalArgumentException {
    this.filename = filename;

    int beginIndex = filename.lastIndexOf('.');
    if (beginIndex == -1) {
      throw new IllegalArgumentException("The filename does not have a suffix");
    }
    this.suffix = filename.substring(beginIndex + 1);

    for (String s : this.getSuffixes()) {
      if (s.equalsIgnoreCase(this.suffix)) {
        return;
      }
    }

    throw new IllegalArgumentException("The suffix is not supported by this reader");
  }

  /**
   * Returns an immutable collection of file suffixes supported by this reader.
   *
   * @return the collection of suffixes
   */
  protected abstract String[] getSuffixes();

  /**
   * Returns the filename that was received and validated at construction time.
   *
   * @return the filename
   */
  protected String getFilename() {
    return this.filename;
  }

  protected String getSuffix() {
    return this.suffix;
  }
}
