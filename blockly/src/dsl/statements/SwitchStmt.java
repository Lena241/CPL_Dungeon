package dsl.statements;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SwitchStmt implements Stmt{

  Dictionary<String, CaseStmt> caseStmtDictionary;
  SwitchDefaultStmt switchDefaultStmt;
  String variableSymbol;
  public SwitchStmt(List<CaseStmt> caseStmtList, SwitchDefaultStmt defaultStmtContext, String variableSymbol){
    this.caseStmtDictionary = new Hashtable<>();
    for (int i = 0; i< caseStmtList.size(); i++){
      this.caseStmtDictionary.put(caseStmtList.get(i).getVariableValue(), caseStmtList.get(i));
    }
    this.variableSymbol = variableSymbol;
    this.switchDefaultStmt = defaultStmtContext;
  }

  public List<Stmt> getCaseStatementsByVariableValue(String variableValue) {
    CaseStmt caseStmt = this.caseStmtDictionary.get(variableValue);
    if (caseStmt != null)
    {
      return caseStmt.getStatements();
    } else if (this.switchDefaultStmt != null) {
      return this.switchDefaultStmt.getStatements();
    } else {
      return null;
    }
  }

  public String getVariableSymbol(){
    return this.variableSymbol;
  }
}
