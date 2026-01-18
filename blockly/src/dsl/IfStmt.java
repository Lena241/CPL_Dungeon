package dsl;

import java.util.List;

public final class IfStmt implements Stmt {
  private final List<IfBranch> branches;
  private final List<Stmt> elseBody;

  public IfStmt(List<IfBranch> branches, List<Stmt> elseBody) {
    this.branches = branches;
    this.elseBody = elseBody;
  }

  public List<IfBranch> getBranches() {
    return branches;
  }

  public List<Stmt> getElseBody() {
    return elseBody;
  }

  @Override
  public String toString() {
    return "IfStmt(branches=" + branches + ", else=" + elseBody + ")";
  }
}
