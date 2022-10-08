package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IImage;
import model.ImageChannel;
import model.StridedImage;
import model.operations.Blur;
import model.operations.Brighten;
import model.operations.FlipHorizontal;
import model.operations.FlipVertical;
import model.operations.GreyScale;
import model.operations.IOperation;
import model.operations.Sepia;
import model.operations.Sharpen;
import model.reader.CompositeImageReader;

/**
 * A text-based image manipulation program controller.
 */
public class TextualImageEditor implements ImageEditor {
  private final Map<String, IImage> images = new HashMap<>();
  private final InputStream input;
  private final OutputStream output;

  /**
   * Instantiates a new controller with the specified input and output streams.
   *
   * @param input  the stream to collect user input from
   * @param output the stream to print feedback to
   * @throws IllegalArgumentException if either argument is null
   */
  public TextualImageEditor(InputStream input, OutputStream output)
          throws IllegalArgumentException {
    if (input == null || output == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.input = input;
    this.output = output;
  }

  /**
   * Instantiates a new controller using STDIN and STDOUT for the input and output streams,
   * respectively.
   */
  public TextualImageEditor() {
    this(System.in, System.out);
  }

  /**
   * Launches the editor using a console window for input and output.
   *
   * @param args either empty, or contains the path of a script to be executed
   */
  public static void main(String... args) {
    TextualImageEditor editor;

    if (args.length == 0) {
      editor = new TextualImageEditor();

    } else if (args.length == 1) {
      InputStream input;
      try {
        input = new FileInputStream(args[0]);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(1);
        return;
      }
      editor = new TextualImageEditor(input, System.out);

    } else {
      System.out.println("Invalid argument(s).");
      System.exit(1);
      return; // Needed because System.exit is not special-cased for initialization short-circuiting
    }

    editor.exec();
  }

  private void transformHelper(String name, String destName, IOperation op, PrintStream ps) {
    IImage image = this.images.get(name);
    if (image == null) {
      ps.println("Source image does not exist.");
      return;
    }
    this.images.put(destName, image.transform(op));
  }

  private void loadSaveHelper(String command, Scanner sc, PrintStream ps) {
    String path = sc.next();
    String name = sc.next();
    switch (command) {
      case "load":
        try {
          this.images.put(name, new CompositeImageReader(path).read(StridedImage.Builder::new));
        } catch (IOException e) {
          ps.println("An IO error occurred.");
          return;
        }
        break;
      case "save":
        IImage image = this.images.get(name);
        if (image == null) {
          ps.println("Source image does not exist.");
          return;
        }
        try {
          new CompositeImageReader(path).write(image);
        } catch (IOException e) {
          ps.println("An IO error occurred.");
          return;
        }
        break;
      default:
        ps.println("Unknown command.");
    }
  }

  /**
   * Processes the GreyScale-related commands.
   *
   * @param command the command string
   * @param sc      the Scanner for input
   * @param ps      the PrintStream for output
   */
  private void greyScaleHelper(String command, Scanner sc, PrintStream ps) {
    String name = sc.next();
    String destName = sc.next();
    ImageChannel channel;
    switch (command) {
      case "red-component":
        channel = ImageChannel.RED;
        break;
      case "green-component":
        channel = ImageChannel.GREEN;
        break;
      case "blue-component":
        channel = ImageChannel.BLUE;
        break;
      case "value-component":
        channel = ImageChannel.VALUE;
        break;
      case "luma-component":
        channel = ImageChannel.LUMA;
        break;
      case "intensity-component":
        channel = ImageChannel.INTENSITY;
        break;
      default:
        ps.println("Unknown command.");
        return;
    }
    IOperation op = new GreyScale(channel);

    this.transformHelper(name, destName, op, ps);
  }

  /**
   * Process commands that don't take any arguments besides source and destination.
   *
   * @param command the command string
   * @param sc      the Scanner for input
   * @param ps      the PrintStream for output
   */
  private void preMadeHelper(String command, Scanner sc, PrintStream ps) {
    String name = sc.next();
    String destName = sc.next();
    IOperation op;
    switch (command) {
      case "horizontal-flip":
        op = new FlipHorizontal();
        break;
      case "vertical-flip":
        op = new FlipVertical();
        break;
      case "blur":
        op = new Blur();
        break;
      case "sharpen":
        op = new Sharpen();
        break;
      case "sepia":
        op = new Sepia();
        break;
      default:
        ps.println("Unknown command.");
        return;
    }

    this.transformHelper(name, destName, op, ps);
  }

  /**
   * Process the "brighten" command.
   *
   * @param command the command string
   * @param sc      the Scanner for input
   * @param ps      the PrintStream for output
   */
  private void brightenHelper(String command, Scanner sc, PrintStream ps) {
    float amount;
    try {
      amount = sc.nextFloat();
    } catch (InputMismatchException e) {
      ps.println("Amount must be a number.");
      return;
    }
    if (amount < -1 || amount > 1) {
      ps.println("Amount must be between 0 and 1.");
    }

    String name = sc.next();
    String destName = sc.next();
    IOperation op;
    if (command.equals("brighten")) {
      op = new Brighten(amount);
    } else {
      ps.println("Unknown command.");
      return;
    }

    this.transformHelper(name, destName, op, ps);
  }

  @Override
  public void exec() {
    PrintStream ps = new PrintStream(this.output);
    try (Scanner sc = new Scanner(this.input)) {
      while (sc.hasNextLine()) {
        // Process commands line-by-line to avoid lots of Assignment 3 weirdness
        String line = sc.nextLine();
        Scanner lsc = new Scanner(line);
        String command;
        try {
          command = lsc.next();
        } catch (NoSuchElementException ignored) {
          // Line is just blank, so no need to output anything
          continue;
        }
        if (command.equals("quit")) {
          break;
        }

        try {
          if (command.equals("load") || command.equals("save")) {
            this.loadSaveHelper(command, lsc, ps);
          } else if (command.endsWith("-component")) {
            this.greyScaleHelper(command, lsc, ps);
          } else if (command.endsWith("-flip") || command.equals("blur")
                  || command.equals("sharpen") || command.equals("sepia")) {
            this.preMadeHelper(command, lsc, ps);
          } else if (command.equals("brighten") || command.equals("darken")) {
            this.brightenHelper(command, lsc, ps);
          } else {
            ps.println("Unknown command.");
          }
        } catch (NoSuchElementException e) {
          ps.println("Incorrect number of arguments.");
        }
      }
    }
  }
}
