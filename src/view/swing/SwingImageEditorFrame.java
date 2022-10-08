package view.swing;

import javax.swing.JFrame;

import model.IImage;

/**
 * Class that represents the SwingImageEditorFrame.
 */
public class SwingImageEditorFrame extends JFrame implements ISwingImageEditor {
  private final MainPanel panel;

  /**
   * Constructs the swing image editor frame.
   */
  public SwingImageEditorFrame() {
    super("Image Manipulation & Enhancement");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1024, 768);
    this.panel = new MainPanel();
    this.add(this.panel);
  }

  @Override
  public void showImageEditor() {
    this.setVisible(true);
  }

  @Override
  public void addFilePanelListener(FilePanelListener fpl) {
    this.panel.addFilePanelListener(fpl);
  }

  @Override
  public void addOperationPanelListener(OperationPanelListener opl) {
    this.panel.addOperationPanelListener(opl);
  }

  @Override
  public void setImage(IImage image) {
    this.panel.setImage(image);
  }
}
