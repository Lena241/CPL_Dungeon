package dsl.statements;

import java.util.List;

public class CaseStmt implements Stmt{

  private final List<dsl.statements.Stmt> statements;
  private final String variableValue;

  public CaseStmt(List<dsl.statements.Stmt> statements, String variableValue){
    this.statements = statements;
    this.variableValue = variableValue;
  }

  public List<Stmt> getStatements(){
    return this.statements;
  }

  public String getVariableValue(){
    return this.variableValue;
  }
}
