package dsl;

import coderunner.Direction;

public final class UseStmt implements Stmt {
  private final Direction direction;

  public UseStmt(Direction direction) {
    this.direction = direction;
  }

  public Direction direction() {
    return direction;
  }

  @Override
  public String toString() {
    return "UseStmt{" + direction + "}";
  }
}
