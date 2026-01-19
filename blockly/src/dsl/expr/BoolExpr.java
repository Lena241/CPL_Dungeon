package dsl.expr;

import dsl.ConditionContext;

public final class BoolExpr implements Expr {
  private final boolean value;

  public BoolExpr(boolean value) {
    this.value = value;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return value;
  }

  @Override
  public String toString() {
    return "BoolExpr(" + value + ")";
  }
}
