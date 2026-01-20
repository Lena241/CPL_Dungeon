package dsl.statements;

import dsl.expr.Expr;

public class SetVariableStmt implements Stmt{

  private final String variable_name;
  private final Expr value;

  public SetVariableStmt(String variable_name, Expr value){
    this.variable_name = variable_name;
    this.value = value;
  }

  public String getVariableName(){
    return this.variable_name;
  }

  public Expr getVariableValue(){
    return this.value;
  }

  @Override
  public String toString(){
    return "CreateVariableStmt(variable_name = " + this.variable_name + ", variable_value = " + this.value + ")";
  }
}
