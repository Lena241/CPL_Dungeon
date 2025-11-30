package coderunner;

import static coderunner.BlocklyCommands.move;
import static coderunner.BlocklyCommands.rotate;

public class VerySimpleDslRunner {

  public void execute(String code, int sleepAfterEachLineMillis) {
    String[] lines = code.split("\\R");

    for (String rawLine : lines) {
      String line = rawLine.trim();
      if (line.isEmpty()) {
        continue;
      }

      if (line.equals("gehen()")) {
        move();
      } else if (line.equals("drehen(links)")) {
        rotate(Direction.LEFT);
      } else if (line.equals("drehen(rechts)")) {
        rotate(Direction.RIGHT);
      } else {
        throw new IllegalArgumentException("Unbekannter DSL-Befehl: '" + line + "'");
      }

      if (sleepAfterEachLineMillis > 0) {
        try {
          Thread.sleep(sleepAfterEachLineMillis);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }
  }
}
