package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.List;

public final class BoolExpr extends Expr {
  private final boolean value;

  public BoolExpr(boolean value) {
    this.value = value;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {


    var result = new Value(value, ValueType.Boolean);
    if (possibleResultTypes.contains(result.getType())) {
      return result;
    } else {
      throw new IllegalArgumentException("Wrong Type!");
    }
  }

  @Override
  public String toString() {
    return "BoolExpr(" + value + ")";
  }
}
