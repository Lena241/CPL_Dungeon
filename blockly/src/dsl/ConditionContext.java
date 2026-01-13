package dsl;

import coderunner.Direction;

public interface ConditionContext {
  boolean isActive(Direction dir);
}
