package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Expr {
  public abstract Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleTypes);

  public Boolean evalAsBoolean(ConditionContext ctx, SymbolTable symbolTable){
    return (Boolean) this.eval(ctx, symbolTable, new ArrayList<>(List.of(ValueType.Boolean))).getValue();
  }

  public Integer evalAsInteger(ConditionContext ctx, SymbolTable symbolTable){
    return (Integer) this.eval(ctx, symbolTable, new ArrayList<>(List.of(ValueType.Integer))).getValue();
  }
}
