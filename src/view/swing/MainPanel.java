package view.swing;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.Histogram;
import model.IImage;
import model.Pixel;

/**
 * Represents the main container of the application.
 */
public class MainPanel extends JPanel {
  private final FilePanel filePanel;
  private final ScalePanel scalePanel;
  private final ImagePane imagePane;
  private final HistogramCanvas histogramCanvas;
  private final OperationPanel operationPanel;

  /**
   * Constructs the main panel.
   */
  public MainPanel() {
    this.filePanel = new FilePanel();
    this.imagePane = new ImagePane();
    this.scalePanel = new ScalePanel(this.imagePane);
    this.histogramCanvas = new HistogramCanvas();
    this.operationPanel = new OperationPanel();

    this.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;

    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.filePanel, c);
    this.add(this.scalePanel, c);

    c.gridwidth = 1;
    c.gridheight = GridBagConstraints.REMAINDER;
    c.weightx = 1;
    c.weighty = 1;
    this.add(this.imagePane, c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    c.gridheight = 1;
    c.weightx = 0;
    c.weighty = 0;
    this.add(this.histogramCanvas, c);

    c.gridheight = GridBagConstraints.REMAINDER;
    this.add(this.operationPanel, c);
  }

  /**
   * Adds a FilePanelListener to the panel.
   *
   * @param fpl the FilePanelListener.
   */
  public void addFilePanelListener(FilePanelListener fpl) {
    this.filePanel.addFilePanelListener(fpl);
  }

  /**
   * Adds a OperationPanelListener to the panel.
   *
   * @param opl the OperationPanelListener.
   */
  public void addOperationPanelListener(OperationPanelListener opl) {
    this.operationPanel.addOperationPanelListener(opl);
  }

  /**
   * Sets the panel's image to the passed in image.
   *
   * @param image the passed in image.
   */
  public void setImage(IImage image) {
    BufferedImage bi;
    if (image == null) {
      bi = null;
    } else {
      bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
      for (int i = 0; i < image.getHeight(); ++i) {
        for (int j = 0; j < image.getWidth(); ++j) {
          Pixel p = image.getPixel(i, j);
          bi.setRGB(j, i, new Color(p.getRed(), p.getGreen(), p.getBlue()).getRGB());
        }
      }
    }
    this.imagePane.setImage(bi);
    this.histogramCanvas.setHistogram(image == null ? null : new Histogram(image));
  }
}
