package dsl.auxiliary;

import coderunner.ExpressionType;
import dsl.ExpressionStmt;

public class ExpressionResolver {


  public static Integer ResolveExpression(ExpressionStmt expressionStmt, SymbolTable symbolTable){
    return switch (expressionStmt.getExpressionType()) {
      case ExpressionType.ApplyOperator -> applyOperator(
        ExpressionResolver.ResolveExpression(expressionStmt.getLeftExpression(), symbolTable),
        ExpressionResolver.ResolveExpression(expressionStmt.getRightExpression(), symbolTable),
        expressionStmt.getOperator());
      case ExpressionType.ResolveExpression -> ExpressionResolver.ResolveExpression(expressionStmt.getExpression(), symbolTable);
      case ExpressionType.ContainsInteger -> Integer.parseInt(expressionStmt.getInteger());
      case ExpressionType.ResolveVariable -> symbolTable.resolve(expressionStmt.getVariable());
    };
  }

  private static int applyOperator(int leftInput, int rightInput, String operator){
    return switch (operator) {
      case "+" -> leftInput + rightInput;
      case "-" -> leftInput - rightInput;
      case "*" -> leftInput * rightInput;
      case "/" -> {
        if (rightInput == 0) {
          throw new IllegalArgumentException("No null-division possible!");
        }
        yield leftInput / rightInput;
      }
      default -> throw new IllegalArgumentException("operator not defined!");
    };
  }
}
