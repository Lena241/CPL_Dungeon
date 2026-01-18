package dsl;

import coderunner.BlocklyCommands;
import coderunner.Direction;
import dsl.statements.*;

public class ScriptInterpreter implements ConditionContext {

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

    } else if (stmt instanceof IfStmt i) {
      boolean matched = false;

      for (IfBranch b : i.getBranches()) {
        if (b.getCondition().eval(this)) {
          matched = true;
          for (Stmt s : b.getBody()) execute(s);
          break;
        }
      }

      if (!matched && i.getElseBody() != null) {
        for (Stmt s : i.getElseBody()) execute(s);
      }
    }

    else if (stmt instanceof RepeatStmt r) {
      for (int i = 0; i < r.getTimes(); i++) {
        for (Stmt s : r.getBody()) execute(s);
      }

    } else if (stmt instanceof UseStmt u) {
      BlocklyCommands.interact(u.direction());

    } else if (stmt instanceof PushStmt) {
      BlocklyCommands.push();

    } else if (stmt instanceof PullStmt) {
      BlocklyCommands.pull();

    } else if (stmt instanceof ShootFireballStmt) {
      BlocklyCommands.shootFireball();
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

  @Override
  public boolean isActive(Direction dir) {
    return BlocklyCommands.active(dir);
  }
}
