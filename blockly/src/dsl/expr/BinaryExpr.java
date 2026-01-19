package dsl.expr;

import dsl.ConditionContext;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BinaryExpr extends Expr {
  private final Expr left;
  private final Expr right;
  private final String op; // "and" oder "or"
  private final HashMap<String, List<ValueType>> OperatorToPossibleTypes;
  private final HashMap<String, ValueType> OperatorToOutputType;

  public BinaryExpr(Expr left, Expr right, String op) {
    this.left = left;
    this.right = right;
    this.op = op;

    this.OperatorToPossibleTypes = new HashMap<>();
    this.OperatorToOutputType = new HashMap<>();

    this.OperatorToPossibleTypes.put("and", new ArrayList<>(List.of(ValueType.Boolean)));
    this.OperatorToPossibleTypes.put("or", new ArrayList<>(List.of(ValueType.Boolean)));
    this.OperatorToPossibleTypes.put("+", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("-", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("*", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("/", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("==", new ArrayList<>(List.of(ValueType.Integer, ValueType.Boolean)));
    this.OperatorToPossibleTypes.put("<=", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put(">=", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put(">", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("<", new ArrayList<>(List.of(ValueType.Integer)));
    this.OperatorToPossibleTypes.put("!=", new ArrayList<>(List.of(ValueType.Integer)));

    this.OperatorToOutputType.put("and", ValueType.Boolean);
    this.OperatorToOutputType.put("or", ValueType.Boolean);
    this.OperatorToOutputType.put("+", ValueType.Integer);
    this.OperatorToOutputType.put("-", ValueType.Integer);
    this.OperatorToOutputType.put("*", ValueType.Integer);
    this.OperatorToOutputType.put("/", ValueType.Integer);
    this.OperatorToOutputType.put("==", ValueType.Boolean);
    this.OperatorToOutputType.put("!=", ValueType.Boolean);
    this.OperatorToOutputType.put(">=", ValueType.Boolean);
    this.OperatorToOutputType.put("<=", ValueType.Boolean);
    this.OperatorToOutputType.put(">", ValueType.Boolean);
    this.OperatorToOutputType.put("<", ValueType.Boolean);
  }

  @Override
  public Value eval(ConditionContext ctx, SymbolTable symbolTable, List<ValueType> possibleResultTypes) {

    List<ValueType> possibleTypes = this.OperatorToPossibleTypes.get(op);
    ValueType outputType = this.OperatorToOutputType.get(op);
    Value leftValue = left.eval(ctx, symbolTable, possibleTypes);
    Value rightValue = right.eval(ctx, symbolTable, possibleTypes);


    if (!possibleResultTypes.contains(outputType)) {
      throw new IllegalArgumentException("Wrong Type!");
    }

    return switch(op){
      case "and"
        -> Value.BooleanValue(Value.GetBoolean(leftValue) && Value.GetBoolean(rightValue));
      case "or"
        -> Value.BooleanValue(Value.GetBoolean(leftValue) || Value.GetBoolean(rightValue));

      case "+"
        -> Value.IntegerValue(Value.GetInteger(leftValue) + Value.GetInteger(rightValue));
      case "-"
        -> Value.IntegerValue(Value.GetInteger(leftValue) - Value.GetInteger(rightValue));
      case "*"
        -> Value.IntegerValue(Value.GetInteger(leftValue) * Value.GetInteger(rightValue));
      case "/"
        -> {
            if (Value.GetInteger(rightValue) == 0) {
              throw new IllegalArgumentException("No null-division possible!");
            }
            yield Value.IntegerValue(Value.GetInteger(leftValue) / Value.GetInteger(rightValue));
        }
      case "=="
        -> {
        if (leftValue.getType() != rightValue.getType()) {
          throw new IllegalArgumentException("Cannot compare variables of different types!");
        }
        yield Value.BooleanValue(leftValue.getType() == ValueType.Boolean ?
          Value.GetBoolean(leftValue) == Value.GetBoolean(rightValue):
          Value.GetInteger(leftValue).equals(Value.GetInteger(rightValue)));
      }
      case "!="
        -> {
        if (leftValue.getType() != rightValue.getType()) {
          throw new IllegalArgumentException("Cannot compare variables of different types!");
        }
        yield Value.BooleanValue(leftValue.getType() == ValueType.Boolean ?
          Value.GetBoolean(leftValue) != Value.GetBoolean(rightValue):
          !Value.GetInteger(leftValue).equals(Value.GetInteger(rightValue)));
      }
      case "<="
        -> Value.BooleanValue(Value.GetInteger(leftValue) <= Value.GetInteger(rightValue));
      case "<"
        -> Value.BooleanValue(Value.GetInteger(leftValue) < Value.GetInteger(rightValue));
      case ">"
        -> Value.BooleanValue(Value.GetInteger(leftValue) > Value.GetInteger(rightValue));
      case ">="
        -> Value.BooleanValue(Value.GetInteger(leftValue) >= Value.GetInteger(rightValue));

      default
        -> throw new IllegalStateException("Unknown op: " + op);
    };
  }

  public String getOp(){
    return this.op;
  }

  public Expr getLeft(){
    return this.left;
  }

  public Expr getRight(){
    return this.right;
  }

  @Override
  public String toString() {
    return "BinaryExpr(" + left + " " + op + " " + right + ")";
  }
}
