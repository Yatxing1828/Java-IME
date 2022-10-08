package model.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IImage;
import model.Pixel;

/**
 * Represents the image reader responsible for PPM image files.
 */
public class PPMImageReader extends AbstractImageReader {

  /**
   * Constructs a new PPMImageReader to read the specified filename. The file is not opened, but its
   * extension is checked at construction time.
   *
   * @param filename the path to the PPM file
   * @throws IllegalArgumentException if the file extension is unsupported
   */
  public PPMImageReader(String filename) throws IllegalArgumentException {
    super(filename);
  }

  @Override
  protected String[] getSuffixes() {
    // Avoid creating a new collection every time getSuffixes is called
    return new String[]{"ppm"};
  }

  @Override
  public IImage read(BuilderConstructor constructor) throws IOException {
    try (Scanner sc = new Scanner(new FileInputStream(this.getFilename()))
            .useDelimiter("(\\p{javaWhitespace}|(#.*))+")) {
      if (!sc.next().equals("P3")) {
        throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
      }
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      IImage.Builder builder = constructor.construct(height, width);
      for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
          builder.setPixel(i, j, new Pixel(readPixelComponent(sc, maxValue),
                  readPixelComponent(sc, maxValue),
                  readPixelComponent(sc, maxValue)));

        }
      }
      if (sc.hasNext()) {
        throw new IOException("The image file is invalid");
      }
      return builder.build();
    }
  }

  @Override
  public void write(IImage image) throws IOException {
    try (PrintStream ps = new PrintStream(this.getFilename())) {
      ps.println("P3");
      ps.println(image.getWidth() + " " + image.getHeight() + " " + 255);
      for (int i = 0; i < image.getHeight(); ++i) {
        for (int j = 0; j < image.getWidth(); ++j) {
          Pixel pixel = image.getPixel(i, j);
          ps.println(Math.round(pixel.getRed() * 255) + " " + Math.round(pixel.getRed() * 255) + " "
                  + Math.round(pixel.getRed() * 255));
        }
      }
    }
  }

  private float readPixelComponent(Scanner sc, int maxValue) throws IOException {
    int value;
    try {
      value = sc.nextInt();
    } catch (NoSuchElementException e) {
      throw new IOException("The image file is invalid");
    }
    if (value < 0 || value > maxValue) {
      throw new IOException("The image file is invalid");
    }
    return (float) value / maxValue;
  }
}
