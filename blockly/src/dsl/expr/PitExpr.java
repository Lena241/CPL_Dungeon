package dsl.expr;

import coderunner.Direction;
import dsl.ConditionContext;

public final class PitExpr implements Expr {
  private final Direction dir;

  public PitExpr(Direction dir) {
    this.dir = dir;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return ctx.isPit(dir);
  }

  @Override
  public String toString() {
    return "PitExpr(" + dir + ")";
  }
}
