package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.*;

import java.util.List;

public class IDExpr extends Expr {
  private final String variableSymbol;

  public IDExpr(String variableSymbol) {
    this.variableSymbol = variableSymbol;
  }

  public String getVariableSymbol() {
    return this.variableSymbol;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {

    var result = symbolTable.resolve(this.variableSymbol);
    if (possibleResultTypes.contains(result.getType())) {
      return result;
    } else {
      throw new IllegalArgumentException("Wrong Type!");
    }
  }


  @Override
  public String toString() {
    return "IDExpr(" + variableSymbol + ")";
  }
}
