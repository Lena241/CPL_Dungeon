
package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.List;

public final class IntExpr extends Expr {
  private final Integer value;

  public IntExpr(Integer value) {
    this.value = value;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {


    var result = new Value(value, ValueType.Integer);
    if (possibleResultTypes.contains(result.getType())) {
      return result;
    } else {
      throw new IllegalArgumentException("Wrong Type!");
    }
  }

  public Integer getValue(){
    return this.value;
  }

  @Override
  public String toString() {
    return "IntExpr(" + value + ")";
  }
}
