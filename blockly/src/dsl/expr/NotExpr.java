package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.ArrayList;
import java.util.List;

public final class NotExpr extends Expr {
  private final Expr inner;

  public NotExpr(Expr inner) {
    this.inner = inner;
  }

  public Expr getInner() {
    return inner;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {
    ValueType resultType = ValueType.Boolean;
    if (!possibleResultTypes.contains(resultType)) {
      throw new IllegalArgumentException("Wrong Type!");
    }
    return new Value(!(Boolean)inner.eval(ctx, symbolTable, new ArrayList<>(List.of(ValueType.Boolean))).getValue(), resultType);
  }

  @Override
  public String toString() {
    return "NotExpr(" + inner + ")";
  }
}
