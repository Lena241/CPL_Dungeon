package dsl;

import java.util.List;

public class CaseStmt implements Stmt {

  private final List<Stmt> statements;
  private final String variableValue;

  public CaseStmt(List<Stmt> statements, String variableValue){
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
