package dsl;

import java.util.List;

public final class Program {

  private final List<Stmt> statements;

  public Program(List<Stmt> statements) {
    this.statements = List.copyOf(statements);
  }

  public List<Stmt> statements() {
    return statements;
  }

  @Override
  public String toString() {
    return "Program{" + statements + '}';
  }
}
