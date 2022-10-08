package view.swing;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * Class that represents the file panel in the application.
 */
public class FilePanel extends JPanel {

  private final JButton openButton;
  private final JButton saveButton;
  private final JButton saveAsButton;

  /**
   * Constructs a new file panel.
   */
  public FilePanel() {
    this.openButton = new JButton("Open...");
    this.saveButton = new JButton("Save");
    this.saveAsButton = new JButton("Save as...");

    this.add(this.openButton);
    this.add(this.saveButton);
    this.add(this.saveAsButton);
  }

  /**
   * Adds a FilePanelListener to listen for the open, save, and save-as buttons.
   *
   * @param fpl the FilePanelListener.
   * @throws IllegalArgumentException if the given FilePanelListener is null.
   */
  public void addFilePanelListener(FilePanelListener fpl) throws IllegalArgumentException {
    if (fpl == null) {
      throw new IllegalArgumentException("FPL cannot be null");
    }
    this.openButton.addActionListener(e -> {
      JFileChooser chooser = new ImageFileChooser(ImageFileChooser.Mode.READ);
      if (chooser.showOpenDialog(FilePanel.this) == JFileChooser.APPROVE_OPTION) {
        fpl.open(chooser.getSelectedFile());
      }
    });

    this.saveButton.addActionListener(e -> fpl.save());

    this.saveAsButton.addActionListener(e -> {
      JFileChooser chooser = new ImageFileChooser(ImageFileChooser.Mode.WRITE);
      if (chooser.showOpenDialog(FilePanel.this) == JFileChooser.APPROVE_OPTION) {
        fpl.saveAs(chooser.getSelectedFile());
      }
    });
  }
}
