package dsl;

import coderunner.BlocklyCommands;

public class ScriptInterpreter {

  private final int sleepAfterEachStmtMillis;

  public ScriptInterpreter() {
    this(0);
  }

  public ScriptInterpreter(int sleepAfterEachStmtMillis) {
    this.sleepAfterEachStmtMillis = sleepAfterEachStmtMillis;
  }

  public void run(Program program) {
    for (Stmt stmt : program.statements()) {
      execute(stmt);
      sleepIfNeeded();
    }
  }

  private void execute(Stmt stmt) {
    if (stmt instanceof MoveStmt) {
      BlocklyCommands.move();

    } else if (stmt instanceof RotateStmt rotateStmt) {
      BlocklyCommands.rotate(rotateStmt.direction());

    } else {
      throw new IllegalArgumentException("Unknown statement: " + stmt);
    }
  }

  private void sleepIfNeeded() {
    if (sleepAfterEachStmtMillis <= 0) return;

    try {
      Thread.sleep(sleepAfterEachStmtMillis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
