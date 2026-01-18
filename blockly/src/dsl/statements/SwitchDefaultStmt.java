package dsl.statements;

import java.util.List;

public class SwitchDefaultStmt implements Stmt{

  private final List<dsl.statements.Stmt> statements;

  public SwitchDefaultStmt(List<dsl.statements.Stmt> statements){
      this.statements = statements;
  }

  public List<Stmt> getStatements(){
    return this.statements;
  }
}
