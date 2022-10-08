package controller.swing;

import java.io.File;
import java.io.IOException;

import controller.ImageEditor;
import model.IImage;
import model.StridedImage;
import model.operations.IOperation;
import model.reader.CompositeImageReader;
import view.swing.FilePanelListener;
import view.swing.ISwingImageEditor;
import view.swing.OperationPanelListener;
import view.swing.SwingImageEditorFrame;

/**
 * JavaSwing-based GUI Image Manipulation program controller.
 */
public class SwingImageEditor implements ImageEditor, FilePanelListener, OperationPanelListener {
  private final ISwingImageEditor frame;

  private File file = null;
  private IImage image = null;

  /**
   * Instantiates a new controller.
   *
   * @param view the view to receive events from
   * @throws IllegalArgumentException if the view is null
   */
  public SwingImageEditor(ISwingImageEditor view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.frame = view;
    this.frame.addFilePanelListener(this);
    this.frame.addOperationPanelListener(this);
  }

  public static void main(String[] args) {
    SwingImageEditor editor = new SwingImageEditor(new SwingImageEditorFrame());
    editor.exec();
  }

  @Override
  public void exec() {
    this.frame.showImageEditor();
  }

  private void setImage(IImage image) {
    this.image = image;
    this.frame.setImage(image);
  }

  @Override
  public void open(File f) {
    try {
      this.file = f;
      this.setImage(new CompositeImageReader(f.getPath()).read(StridedImage.Builder::new));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void save() {
    this.saveAs(this.file);
  }

  @Override
  public void saveAs(File f) {
    if (this.image != null) {
      try {
        new CompositeImageReader(f.getPath()).write(this.image);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void transform(IOperation op) {
    if (this.image != null) {
      this.setImage(this.image.transform(op));
    }
  }
}
