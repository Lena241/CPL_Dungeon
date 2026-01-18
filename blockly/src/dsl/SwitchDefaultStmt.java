package dsl;

import java.util.List;

public class SwitchDefaultStmt implements Stmt {

  private final List<Stmt> statements;

  public SwitchDefaultStmt(List<Stmt> statements){
      this.statements = statements;
  }

  public List<Stmt> getStatements(){
    return this.statements;
  }
}
