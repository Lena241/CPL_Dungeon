package dsl.interpreter;

import dsl.ast.CommandNode;
import systems.HeroActions;

import java.util.List;

public class Interpreter {

  private final HeroActions hero;

  public Interpreter(HeroActions heroActions) {
    this.hero = heroActions;
  }

  public void run (List<CommandNode> program) {
    for (CommandNode command : program) {
      // System.out.println("Executing command: " + command.name);
      switch (command.name) {
        case "move" -> hero.move();
        case "turn_left" -> hero.turnLeft();
        case "turn_right" -> hero.turnRight();
        default -> System.out.println("Unknown command: " + command.name);
      }
    }
  }

}
