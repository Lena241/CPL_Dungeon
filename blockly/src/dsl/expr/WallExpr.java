package dsl.expr;

import coderunner.Direction;
import dsl.ConditionContext;

public final class WallExpr implements Expr {
  private final Direction dir;

  public WallExpr(Direction dir) {
    this.dir = dir;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return ctx.isWall(dir);
  }

  @Override
  public String toString() {
    return "WallExpr(" + dir + ")";
  }
}
