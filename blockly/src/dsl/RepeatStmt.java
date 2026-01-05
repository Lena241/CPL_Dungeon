package dsl;

import java.util.List;

public final class RepeatStmt implements  Stmt {
  private final int times;
  private final List<Stmt> body;

  public RepeatStmt(int times, List<Stmt> body) {
    this.times = times;
    this.body = body;
  }

  public int getTimes() {
    return times;
  }

  public List<Stmt> getBody() {
    return body;
  }

  @Override
  public String toString() {
    return "RepeatStmt(times=" + times + ", body=" + body + ")";
  }
}
