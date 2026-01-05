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

    } else if (stmt instanceof PickupStmt) {
      BlocklyCommands.pickup();

    } else if (stmt instanceof RepeatStmt r) {
      for (int i = 0; i < r.getTimes(); i++) {
        for (Stmt s : r.getBody()) execute(s);
      }

    }
    else {
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
