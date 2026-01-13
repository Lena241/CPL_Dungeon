package dsl;

public final class NotExpr implements Expr {
  private final Expr inner;

  public NotExpr(Expr inner) {
    this.inner = inner;
  }

  public Expr getInner() {
    return inner;
  }

  @Override
  public boolean eval(ConditionContext ctx) {
    return !inner.eval(ctx);
  }

  @Override
  public String toString() {
    return "NotExpr(" + inner + ")";
  }
}
