package model.reader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IImage;
import model.Pixel;

/**
 * An ImageReader implementation which leverages ImageIO to read several filetypes depending on the
 * JDK implementation.
 */
public class ImageIOImageReader extends AbstractImageReader {
  /**
   * Constructs a new ImageIOImageReader to read the specified filename.
   * The file is not opened, but its extension is checked at construction time.
   *
   * @param filename the path to the image file
   * @throws IllegalArgumentException if the file extension is unsupported
   */
  public ImageIOImageReader(String filename) throws IllegalArgumentException {
    super(filename);
  }

  @Override
  protected String[] getSuffixes() {
    return ImageIO.getReaderFileSuffixes();
  }

  @Override
  public IImage read(BuilderConstructor constructor) throws IOException {
    BufferedImage image = ImageIO.read(new File(this.getFilename()));
    IImage.Builder builder = constructor.construct(image.getHeight(), image.getWidth());
    for (int i = 0; i < image.getHeight(); ++i) {
      for (int j = 0; j < image.getWidth(); ++j) {
        Color color = new Color(image.getRGB(j, i));
        builder.setPixel(i, j, new Pixel((float) color.getRed() / 255,
                (float) color.getGreen() / 255, (float) color.getBlue() / 255));
      }
    }
    return builder.build();
  }

  @Override
  public void write(IImage image) throws IOException {
    BufferedImage buffered = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_3BYTE_BGR);
    for (int i = 0; i < image.getHeight(); ++i) {
      for (int j = 0; j < image.getWidth(); ++j) {
        Pixel pixel = image.getPixel(i, j);
        buffered.setRGB(j, i, new Color(pixel.getRed(), pixel.getGreen(),
                pixel.getBlue()).getRGB());
      }
    }
    String formatName = ImageIO.getImageReadersBySuffix(this.getSuffix()).next().getFormatName();

    ImageIO.write(buffered, formatName, new File(this.getFilename()));
  }
}
