package dsl;

public class SetVariableStmt implements Stmt {

  private final String variable_name;
  private final ExpressionStmt value;

  public SetVariableStmt(String variable_name, ExpressionStmt value){
    this.variable_name = variable_name;
    this.value = value;
  }

  public String getVariableName(){
    return this.variable_name;
  }

  public ExpressionStmt getVariableValue(){
    return this.value;
  }

  @Override
  public String toString(){
    return "CreateVariableStmt(variable_name = " + this.variable_name + ", variable_value = " + this.value + ")";
  }
}
