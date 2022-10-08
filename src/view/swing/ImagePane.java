package view.swing;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * Class that represents a scrollable view of the image.
 */
public class ImagePane extends JScrollPane {
  private final ImageIcon imageIcon;
  private float scale;
  private BufferedImage bi = null;

  /**
   * Constructs an ImagePane.
   */
  public ImagePane() {
    this.scale = 1;
    this.imageIcon = new ImageIcon();
    this.setViewportView(new JLabel(this.imageIcon));
  }

  /**
   * Sets the image of the pane by initializing this pane's BufferedImage with the passed in
   * BufferedImage.
   *
   * @param bi the passed in BufferedImage.
   */
  public void setImage(BufferedImage bi) {
    this.bi = bi;
    this.setIconImage();
  }

  /**
   * Sets the scale factor based on the given float value.
   *
   * @param scale the float value representing the scale factor.
   */
  public void setScale(float scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("Scale must be non-negative");
    }
    this.scale = scale;
    this.setIconImage();
  }

  /**
   * Sets the icon image of the pane.
   */
  private void setIconImage() {
    if (this.bi != null) {
      this.imageIcon.setImage(this.bi.getScaledInstance(Math.round(this.bi.getWidth() * this.scale),
              Math.round(this.bi.getHeight() * this.scale), Image.SCALE_DEFAULT));
    } else {
      this.imageIcon.setImage(null);
    }
    this.repaint();
    this.revalidate();
  }
}
