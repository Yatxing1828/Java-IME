package controller;

/**
 * Represents an application controller for image editing. The editor may be textual or graphical.
 */
public interface ImageEditor {
  /**
   * Starts the controller execution loop until the user quits or a problem is encountered.
   */
  void exec();
}
