package dsl.statements;

import java.util.List;

public final class RepeatStmt implements Stmt {
  private final int goal;
  private final List<Stmt> body;

  /**
   *
   * @param goal if ID is equal to goal, dont execute function body
   * @param body function body
   */
  public RepeatStmt(int goal, List<Stmt> body) {
    this.goal = goal;
    this.body = body;
  }

  public int getGoal() {
    return goal;
  }

  public List<Stmt> getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "RepeatStmt(times=" + goal + ", body=" + body + ")";
  }
}
