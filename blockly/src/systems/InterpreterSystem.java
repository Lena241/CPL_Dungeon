package systems;

import coderunner.BlocklyCommands;
import core.Game;
import core.System;

public class InterpreterSystem extends System {

  private static final String MOVE = "gehe";
  private static final String COMMAND_BREAKER = java.lang.System.lineSeparator();
  private static final int SLEEP_AFTER_EACH_LINE = 0;
  private static final String TURN_RIGHT = "drehe(rechts)";
  private static final String TURN_LEFT = "drehe(links)";
  private String[] code;


  // TODO Remove BlocklyCodeRunner and adapt util functions like isCodeRunning and stopCode;
  // these functions are not traditionally in an Interpreter, Blockly is dependent on them though

  @Override
  public void execute() {
    if (code != null) {
      for (String c : code) {
        // Interpreter should be the BlocklyCommandExecuteSystem ?
        if (c.equals(MOVE)) {
          Game.system(
              BlocklyCommandExecuteSystem.class,
              blocklyCommandExecuteSystem ->
                  blocklyCommandExecuteSystem.add(BlocklyCommands.Commands.HERO_MOVE));
        } else if (c.equals(TURN_RIGHT)) {
          Game.system(
              BlocklyCommandExecuteSystem.class,
              blocklyCommandExecuteSystem ->
                  blocklyCommandExecuteSystem.add(BlocklyCommands.Commands.HERO_TURN_RIGHT));
        } else if (c.equals(TURN_LEFT)) {
          Game.system(
              BlocklyCommandExecuteSystem.class,
              blocklyCommandExecuteSystem ->
                  blocklyCommandExecuteSystem.add(BlocklyCommands.Commands.HERO_TURN_LEFT));
        }
      }
      code = null;
    }
  }

  public void sendCode(String code) {
    sendCode(code, SLEEP_AFTER_EACH_LINE);
  }

  public void sendCode(String code, int sleepAfterEachLine) {
    this.code = code.split(COMMAND_BREAKER);
  }
}
