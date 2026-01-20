package dsl.expr;

import coderunner.Direction;
import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.List;

public final class ActiveExpr extends Expr {
  private final Direction direction;

  public ActiveExpr(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {
    var result =  new Value(ctx.isActive(direction), ValueType.Boolean);
    if (possibleResultTypes.contains(ValueType.Boolean)) {
      return result;
    } else {
      throw new IllegalArgumentException("Wrong Type!");
    }
  }

  @Override
  public String toString() {
    return "ActiveExpr(" + direction + ")";
  }
}
