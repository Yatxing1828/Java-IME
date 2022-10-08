package view.swing;

import java.io.File;

/**
 * Interface that represents the listener for the file panel.
 */
public interface FilePanelListener {
  /**
   * Opens the given file.
   *
   * @param f the given file.
   */
  void open(File f);

  /**
   * Saves the current file.
   */
  void save();

  /**
   * Saves the current file to the designated file.
   *
   * @param f the designated file.
   */
  void saveAs(File f);
}
