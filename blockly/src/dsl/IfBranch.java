package dsl;

import java.util.List;

public final class IfBranch {
  private final Expr condition;
  private final List<Stmt> body;

  public IfBranch(Expr condition, List<Stmt> body) {
    this.condition = condition;
    this.body = body;
  }

  public Expr getCondition() {
    return condition;
  }

  public List<Stmt> getBody() {
    return body;
  }
}
