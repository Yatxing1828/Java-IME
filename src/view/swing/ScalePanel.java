package view.swing;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents the panel container for the image scaler.
 */
public class ScalePanel extends JPanel {
  private final ImagePane ip;
  private final JSlider slider;
  private final JSpinner spinner;

  /**
   * Constructs a ScalePanel.
   *
   * @param ip the given ImagePane.
   * @throws IllegalArgumentException if the given image pane is null.
   */
  public ScalePanel(ImagePane ip) throws IllegalArgumentException {
    if (ip == null) {
      throw new IllegalArgumentException("IP cannot be null");
    }
    this.ip = ip;

    GridBagLayout layout = new GridBagLayout();
    this.setLayout(layout);

    this.slider = new JSlider(1, 10000, 100);
    this.spinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 1));

    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    this.add(this.slider, c);

    c.weightx = 0;
    this.add(this.spinner, c);
    this.add(new JLabel("%"), c);

    this.setBorder(BorderFactory.createTitledBorder("Scale"));

    this.slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int value = slider.getValue();
        spinner.setValue(value);
        ip.setScale(value / 100.0f);
      }
    });

    this.spinner.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int value = (int) spinner.getValue();
        slider.setValue(value);
      }
    });
  }
}
