package dsl.expr;

import coderunner.Direction;
import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.List;

public final class FloorExpr extends Expr {
  private final Direction dir;

  public FloorExpr(Direction dir) {
    this.dir = dir;
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {
    var result = new Value(ctx.isFloor(dir), ValueType.Boolean);
    if (possibleResultTypes.contains(result.getType())) {
      return result;
    } else {
      throw new IllegalArgumentException("Wrong Type!");
    }
  }

  @Override
  public String toString() {
    return "FloorExpr(" + dir + ")";
  }
}
