package dsl.statements;

import dsl.expr.Expr;
import java.util.List;

public final class WhileStmt implements Stmt {
  private final Expr condition;
  private final List<Stmt> body;

  public WhileStmt(Expr condition, List<Stmt> body) {
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
