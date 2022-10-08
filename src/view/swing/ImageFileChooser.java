package view.swing;

import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class that represents the frame that shows where image files are selected/chosen.
 */
public class ImageFileChooser extends JFileChooser {

  /**
   * Constructs an ImageFileChooser.
   *
   * @param mode the mode that determines whether the selected file will be written to or read from.
   * @throws IllegalArgumentException if the specified mode is null.
   */
  public ImageFileChooser(Mode mode) throws IllegalArgumentException {
    if (mode == null) {
      throw new IllegalArgumentException("Mode cannot be null");
    }

    String[] suffixes;
    switch (mode) {
      case READ:
        suffixes = ImageIO.getReaderFileSuffixes();
        break;
      case WRITE:
        suffixes = ImageIO.getWriterFileSuffixes();
        break;
      default:
        // Should be unreachable
        throw new AssertionError();
    }
    suffixes = Arrays.copyOf(suffixes, suffixes.length + 1);
    suffixes[suffixes.length - 1] = "ppm";

    this.setFileFilter(new FileNameExtensionFilter("Images", suffixes));
  }

  /**
   * Enum representing the modes of the ImageFileChooser.
   */
  public enum Mode {
    READ, WRITE
  }
}
