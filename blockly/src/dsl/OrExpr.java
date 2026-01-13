package dsl;

public final class OrExpr implements Expr {
  private final Expr left;
  private final Expr right;

  public OrExpr(Expr left, Expr right) {
    this.left = left;
    this.right = right;
  }

  public Expr getLeft() {
    return left;
  }

  public Expr getRight() {
    return right;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    // short-circuit like Java
    return left.eval(ctx) || right.eval(ctx);
  }

  @Override
  public String toString() {
    return "OrExpr(" + left + ", " + right + ")";
  }
}
