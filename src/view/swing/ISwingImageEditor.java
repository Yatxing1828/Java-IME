package view.swing;

import model.IImage;

/**
 * Represents the java swing image editor.
 */
public interface ISwingImageEditor {
  
  void showImageEditor();

  void addFilePanelListener(FilePanelListener fpl);

  void addOperationPanelListener(OperationPanelListener opl);

  void setImage(IImage image);
}
