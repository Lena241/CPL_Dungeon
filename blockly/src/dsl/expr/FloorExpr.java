package dsl.expr;

import coderunner.Direction;
import dsl.ConditionContext;

public final class FloorExpr implements Expr {
  private final Direction dir;

  public FloorExpr(Direction dir) {
    this.dir = dir;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return ctx.isFloor(dir);
  }

  @Override
  public String toString() {
    return "FloorExpr(" + dir + ")";
  }
}
