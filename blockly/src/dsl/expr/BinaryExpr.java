package dsl.expr;

import dsl.ConditionContext;

public final class BinaryExpr implements Expr {
  private final Expr left;
  private final Expr right;
  private final String op; // "and" oder "or"

  public BinaryExpr(Expr left, Expr right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return switch (op) {
      case "and" -> left.eval(ctx) && right.eval(ctx);
      case "or" -> left.eval(ctx) || right.eval(ctx);
      default -> throw new IllegalStateException("Unknown op: " + op);
    };
  }

  @Override
  public String toString() {
    return "BinaryExpr(" + left + " " + op + " " + right + ")";
  }
}
