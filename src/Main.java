import controller.TextualImageEditor;
import controller.swing.SwingImageEditor;

/**
 * An over-arching main class for this project, allowing access to both the textual and graphical
 * editors through CLI.
 */
public class Main {
  /**
   * Starts the program according to the parameters.
   *
   * <p>If there are no arguments, the Swing-based GUI editor is launched.
   *
   * <p>If "-text" is the only argument, the text-based editor is launched.
   *
   * <p>If "-file" is the first argument, the second argument will be executed as a script.
   *
   * @param args must follow one of the above patterns
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      SwingImageEditor.main(args);
    } else if (args.length == 1 && args[0].equals("-text")) {
      TextualImageEditor.main();
    } else if (args.length == 2 && args[0].equals("-file")) {
      TextualImageEditor.main(args[1]);
    } else {
      System.out.println("Invalid argument(s).");
      System.exit(-1);
    }

  }
}
