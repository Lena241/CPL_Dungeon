package dsl;

import coderunner.Direction;

public final class ActiveExpr implements Expr {
  private final Direction direction;

  public ActiveExpr(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return ctx.isActive(direction);
  }

  @Override
  public String toString() {
    return "ActiveExpr(" + direction + ")";
  }
}
