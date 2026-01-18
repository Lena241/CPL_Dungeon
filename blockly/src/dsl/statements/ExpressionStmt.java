package dsl.statements;


import coderunner.ExpressionType;

public class ExpressionStmt implements Stmt{

  private final ExpressionStmt leftExpression;
  private final ExpressionStmt rightExpression;
  private final String variable;
  private final String integer;
  private final ExpressionStmt expression;
  private final String operator;
  private final ExpressionType expressionType;

  public ExpressionStmt(ExpressionStmt leftInput, ExpressionStmt rightInput, String operator){
    this.variable = "";
    this.integer = "";
    this.operator = operator;
    this.leftExpression = leftInput;
    this.rightExpression = rightInput;
    this.expression = null;
    expressionType = ExpressionType.ApplyOperator;
  }

  public ExpressionStmt(String input, boolean containsVariable){
    if (containsVariable) {
      this.variable = input;
      this.integer = "";
      expressionType = ExpressionType.ResolveVariable;
    } else {
      this.integer = input;
      this.variable = "";
      expressionType = ExpressionType.ContainsInteger;
    }
    this.operator = "";
    this.leftExpression = null;
    this.rightExpression = null;
    this.expression = null;
  }

  public ExpressionStmt(ExpressionStmt input){
    this.variable = "";
    this.integer = "";
    this.operator = "";
    this.leftExpression = null;
    this.rightExpression = null;
    this.expression = input;
    expressionType = ExpressionType.ResolveExpression;
  }

  public String getOperator(){
    return this.operator;
  }

  public ExpressionType getExpressionType() {
    return expressionType;
  }

  public ExpressionStmt getLeftExpression(){
    return this.leftExpression;
  }

  public ExpressionStmt getRightExpression(){
    return this.rightExpression;
  }
  public String getVariable(){
    return this.variable;
  }
  public String getInteger(){
    return this.integer;
  }

  public ExpressionStmt getExpression(){
    return this.expression;
  }

  @Override
  public String toString(){
    return "ExpressionStmt(leftInput = " + (this.leftExpression != null ? this.leftExpression.toString() : "null") +
      ", rightInput = " + (this.rightExpression != null ? this.rightExpression.toString() : "null") +
      ", variable = " + this.variable +
      ", integer = " + this.integer +
      ", expression = " + (this.expression != null ? this.expression.toString() : "null" + ")");
  }
}
