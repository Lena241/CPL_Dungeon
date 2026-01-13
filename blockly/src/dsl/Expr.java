package dsl;

public interface Expr {
  boolean eval(ConditionContext ctx);
}
