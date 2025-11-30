package systems;

import core.System;
import dsl.interpreter.Interpreter;
import dsl.lexer.Lexer;
import dsl.parser.Parser;

public class DslInterpreterSystem extends System {

  private final Interpreter interpreter = new Interpreter(new DungeonHeroActions());
  private String pendingCode = null;

  public DslInterpreterSystem() {
    super();
  }

  /**
   * Submit DSL code to be executed on the next game tick.
   */
  public void runDsl(String code) {
    this.pendingCode = code;
  }

  /**
   * Called every tick by the Dungeon game loop.
   */
  @Override
  public void execute() {
    if (pendingCode != null) {
      try {
        Lexer lexer = new Lexer(pendingCode);
        Parser parser = new Parser(lexer);
        var program = parser.parseProgram();

        interpreter.run(program);

      } catch (Exception e) {
        java.lang.System.err.println("DSL error: " + e.getMessage());
      }

      pendingCode = null; // reset after one execution
    }
  }
}
