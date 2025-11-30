package dsl.interpreter;

import dsl.ast.AstNode;
import dsl.ast.MoveNode;
import dsl.ast.RotateNode;
import systems.HeroActions;

import java.util.List;

public class Interpreter {

  private final HeroActions hero;

  public Interpreter(HeroActions heroActions) {
    this.hero = heroActions;
  }

  public void run(List<AstNode> program) {
    for (AstNode node : program) {
      if (node instanceof MoveNode) {
        hero.move();
      }
      if (node instanceof RotateNode rotate) {
        if (rotate.direction.equals("left")) hero.turnLeft();
        else hero.turnRight();
      }
    }
  }

}
