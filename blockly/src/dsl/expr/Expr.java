package dsl.expr;

import dsl.ConditionContext;

public interface Expr {
  boolean eval(ConditionContext ctx);
}
