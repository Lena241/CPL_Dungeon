package dsl;

import coderunner.BlocklyCommands;
import coderunner.Direction;
import dsl.auxiliary.ExpressionResolver;
import dsl.auxiliary.SymbolTable;
import dsl.statements.*;

import java.util.List;

public class ScriptInterpreter implements ConditionContext {

  private final int sleepAfterEachStmtMillis;

  public ScriptInterpreter() {
    this(0);
  }

  public ScriptInterpreter(int sleepAfterEachStmtMillis) {
    this.sleepAfterEachStmtMillis = sleepAfterEachStmtMillis;
  }

  public void run(Program program) {
    SymbolTable symbolTable = new SymbolTable();
    for (Stmt stmt : program.statements()) {
      execute(stmt, symbolTable);
      sleepIfNeeded();
    }
  }

  private void execute(Stmt stmt, SymbolTable symbolTable) {
    if (stmt instanceof MoveStmt) {
      BlocklyCommands.move();
      return;
    }

    if (stmt instanceof RotateStmt rotateStmt) {
      BlocklyCommands.rotate(rotateStmt.direction());
      return;
    }

    if (stmt instanceof PickupStmt) {
      BlocklyCommands.pickup();
      return;
    }

    if (stmt instanceof UseStmt useStmt) {
      BlocklyCommands.interact(useStmt.direction());
      return;
    }

    if (stmt instanceof PushStmt) {
      BlocklyCommands.push();
      return;
    }

    if (stmt instanceof PullStmt) {
      BlocklyCommands.pull();
      return;
    }

    if (stmt instanceof ShootFireballStmt) {
      BlocklyCommands.shootFireball();
      return;
    }

    if (stmt instanceof SetVariableStmt setVariableStmt) {
      String variableName = setVariableStmt.getVariableName();
      Integer variableValue =
        ExpressionResolver.ResolveExpression(setVariableStmt.getVariableValue(), symbolTable);
      symbolTable.add(variableName, variableValue);
      return;
    }

    if (stmt instanceof IfStmt i) {
      boolean matched = false;

      for (IfBranch b : i.getBranches()) {
        if (b.getCondition().eval(this)) {
          matched = true;

          // One scope for the chosen branch
          SymbolTable branchScope = new SymbolTable(symbolTable);

          for (Stmt s : b.getBody()) {
            execute(s, branchScope);
            sleepIfNeeded();
          }
          break;
        }
      }

      if (!matched && i.getElseBody() != null) {
        SymbolTable elseScope = new SymbolTable(symbolTable);

        for (Stmt s : i.getElseBody()) {
          execute(s, elseScope);
          sleepIfNeeded();
        }
      }


      return;
    }

    if (stmt instanceof RepeatStmt r) {
      // One scope for the whole repeat block
      SymbolTable loopScope = new SymbolTable(symbolTable);

      for (int i = 0; i < r.getGoal(); i++) {
        for (Stmt s : r.getBody()) {
          execute(s, loopScope);
          sleepIfNeeded();
        }
      }

      return;
    }

    if (stmt instanceof WhileStmt w) {

      final int MAX_ITERATIONS = 10_000;
      int iterations = 0;

      // IMPORTANT: one scope for the whole while-loop
      SymbolTable whileScope = new SymbolTable(symbolTable);

      while (w.getCondition().eval(this)) {
        if (iterations++ > MAX_ITERATIONS) {
          throw new RuntimeException(
            "While loop exceeded max iterations (possible infinite loop).");
        }

        for (Stmt s : w.getBody()) {
          execute(s, whileScope);
          sleepIfNeeded();
        }
      }

      return;
    }

    if (stmt instanceof SwitchStmt switchStmt) {
      String variableValue = Integer.toString(symbolTable.resolve(switchStmt.getVariableSymbol()));
      List<Stmt> statements = switchStmt.getCaseStatementsByVariableValue(variableValue);

      SymbolTable switchScope = new SymbolTable(symbolTable);

      for (Stmt s : statements) {
        execute(s, switchScope);
        sleepIfNeeded();
      }

      return;
    }

    throw new IllegalArgumentException("Unknown statement: " + stmt);
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

  @Override
  public boolean isWall(Direction dir) {
    return BlocklyCommands.isWall(dir);
  }

  @Override
  public boolean isFloor(Direction dir) {
    return BlocklyCommands.isFloor(dir);
  }

  @Override
  public boolean isPit(Direction dir) {
    return BlocklyCommands.isPit(dir);
  }

}
