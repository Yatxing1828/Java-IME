package view.swing;

import model.operations.IOperation;

/**
 * Interface that represents a listener for the operation panel in the application.
 */
public interface OperationPanelListener {

  /**
   * Applies the supplied transformation.
   *
   * @param op the supplied transformation.
   */
  void transform(IOperation op);
}
