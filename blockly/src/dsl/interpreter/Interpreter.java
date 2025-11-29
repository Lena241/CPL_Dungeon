package dsl.interpreter;

import dsl.ast.CommandNode;

import java.util.List;

public class Interpreter {

  public void run (List<CommandNode> program) {
    for (CommandNode command : program) {
      System.out.println("Executing command: " + command.name);
      // Later: heroActions.call(command.name);
    }
  }

}
