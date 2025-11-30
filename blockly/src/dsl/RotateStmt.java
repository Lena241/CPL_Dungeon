package dsl;

import coderunner.Direction;

public final class RotateStmt implements Stmt {

  private final Direction direction;

  public RotateStmt(Direction direction) {
    this.direction = direction;
  }

  public Direction direction() {
    return direction;
  }

  @Override
  public String toString() {
    return "RotateStmt{" + direction + '}';
  }
}
