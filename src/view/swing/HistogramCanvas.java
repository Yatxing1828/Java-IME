package view.swing;

import java.awt.*;
import java.util.function.IntUnaryOperator;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import model.Histogram;

/**
 * Class that represents the histogram display of the application. The histogram visualizes the
 * distribution of color in an image using a line chart in this case.
 */
public class HistogramCanvas extends JPanel {
  private Histogram histogram;

  /**
   * Constructs a histogram canvas for display.
   */
  public HistogramCanvas() {
    Border b = BorderFactory.createTitledBorder("Histogram");
    this.setBorder(BorderFactory.createTitledBorder("Histogram"));

    this.setMinimumSize(new Dimension(384, 384));
    this.setPreferredSize(new Dimension(384, 384));
  }

  private void paintComponentHelper(Graphics g, int i, IntUnaryOperator getter) {
    int y1 = Math.round(255 * (1 - (float) getter.applyAsInt(i) /
            this.histogram.getMaxCount())) + 64;
    int y2 = Math.round(255 * (1 - (float) getter.applyAsInt(i + 1)
            / this.histogram.getMaxCount())) + 64;
    g.drawLine(i + 64, y1, i + 65, y2);
  }

  /**
   * Displays the given histogram.
   *
   * @param histogram the given histogram.
   */
  public void setHistogram(Histogram histogram) {
    this.histogram = histogram;
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.histogram != null) {
      for (int i = 0; i < 255; ++i) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(63, 63, 258, 258);
        g.setColor(Color.RED);
        this.paintComponentHelper(g, i, this.histogram::getRedCount);
        g.setColor(Color.GREEN);
        this.paintComponentHelper(g, i, this.histogram::getGreenCount);
        g.setColor(Color.BLUE);
        this.paintComponentHelper(g, i, this.histogram::getBlueCount);
        g.setColor(Color.BLACK);
        this.paintComponentHelper(g, i, this.histogram::getIntensityCount);
      }
    }
  }
}
