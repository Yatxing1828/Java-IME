package view.swing;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;

import model.ImageChannel;
import model.operations.Blur;
import model.operations.Brighten;
import model.operations.Downscale;
import model.operations.FlipHorizontal;
import model.operations.FlipVertical;
import model.operations.GreyScale;
import model.operations.Sepia;
import model.operations.Sharpen;

/**
 * Class that represents the panel container for operations.
 */
public class OperationPanel extends JPanel {
  private final JButton blur = new JButton("Blur");
  private final JButton sharpen = new JButton("Sharpen");

  private final JSpinner brightenSpinner = new JSpinner(new SpinnerNumberModel(0.0, -1.0, 1.0, 1.0 / 256.0));
  private final JButton brighten = new JButton("Brighten");

  private final JButton flipHorizontal = new JButton("Flip Horizontal");
  private final JButton flipVertical = new JButton("Flip Vertical");

  private final JComboBox<ImageChannel> greyScaleComboBox = new JComboBox<>(ImageChannel.values());
  private final JButton greyScale = new JButton("Filter");

  private final JButton sepia = new JButton("Sepia Filter");


  private final JSpinner downscaleHeightSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 1.0 / 1024.0));
  private final JSpinner downscaleWidthSpinner = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 1.0 / 1024.0));
  private final JButton downscale = new JButton("Downscale");

  /**
   * Constructs an OperationPanel.
   */
  public OperationPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;

    this.add(this.blur, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.sharpen, c);

    c.gridwidth = 1;
    this.add(this.brightenSpinner, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.brighten, c);

    c.gridwidth = 1;
    this.add(this.flipHorizontal, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.flipVertical, c);

    c.gridwidth = 1;
    this.add(this.greyScaleComboBox, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.greyScale, c);

    this.add(this.sepia, c);

    c.gridwidth = 1;
    this.add(this.downscaleHeightSpinner, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    this.add(this.downscaleWidthSpinner, c);
    this.add(this.downscale, c);

    this.setBorder(BorderFactory.createTitledBorder("Operations"));
  }

  /**
   * Adds an OperationPanelListener to the panel that listens for operations.
   *
   * @param opl the OperationPanelListener.
   * @throws IllegalArgumentException if the given OperationPanelListener is null.
   */
  public void addOperationPanelListener(OperationPanelListener opl) throws IllegalArgumentException {
    if (opl == null) {
      throw new IllegalArgumentException("OPL cannot be null");
    }
    this.blur.addActionListener(e -> opl.transform(new Blur()));
    this.sharpen.addActionListener(e -> opl.transform(new Sharpen()));
    this.brighten.addActionListener(e -> opl.transform(new Brighten(((Double) this.brightenSpinner.getValue()).floatValue()))); // Always works
    this.flipHorizontal.addActionListener(e -> opl.transform(new FlipHorizontal()));
    this.flipVertical.addActionListener(e -> opl.transform(new FlipVertical()));
    this.greyScale.addActionListener(e -> opl.transform(new GreyScale((ImageChannel) OperationPanel.this.greyScaleComboBox.getSelectedItem()))); // Always works
    this.sepia.addActionListener(e -> opl.transform(new Sepia()));
    this.downscale.addActionListener(e -> opl.transform(new Downscale(((Double) this.downscaleHeightSpinner.getValue()).floatValue(), ((Double) this.downscaleWidthSpinner.getValue()).floatValue())));
  }
}
