package dsl;

import coderunner.Direction;

public interface ConditionContext {
  boolean isActive(Direction dir);
  boolean isWall(Direction dir);
  boolean isFloor(Direction dir);
  boolean isPit(Direction dir);
}
