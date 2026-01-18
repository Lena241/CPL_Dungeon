package dsl;

import coderunner.BlocklyCommands;
import dsl.auxiliary.ExpressionResolver;
import dsl.auxiliary.SymbolTable;

import java.util.List;
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
    SymbolTable symbolTable = new SymbolTable();
    for (Stmt stmt : program.statements()) {
      execute(stmt, symbolTable);
      sleepIfNeeded();
    }
  }


  private void execute(Stmt stmt, SymbolTable symbolTable) {
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
          SymbolTable childSymbolTable = new SymbolTable(symbolTable);
          matched = true;
          for (Stmt s : b.getBody()) execute(s, childSymbolTable);
          break;
        }
      }

      if (!matched && i.getElseBody() != null) {
        SymbolTable childSymbolTable = new SymbolTable(symbolTable);
        for (Stmt s : i.getElseBody()) execute(s, childSymbolTable);
      }

  } else if (stmt instanceof RepeatStmt r) {
    SymbolTable childSymbolTable = new SymbolTable(symbolTable);
      for (int i = 0; i < r.getTimes(); i++) {
        for (Stmt s : r.getBody()) execute(s, childSymbolTable);
      }

    } else if (stmt instanceof UseStmt u) {
      BlocklyCommands.interact(u.direction());

    } else if (stmt instanceof PushStmt) {
      BlocklyCommands.push();

    } else if (stmt instanceof PullStmt) {
      BlocklyCommands.pull();

    } else if (stmt instanceof ShootFireballStmt) {
      BlocklyCommands.shootFireball();

    } else if (stmt instanceof SetVariableStmt setVariableStmt) {
      String variableName = setVariableStmt.getVariableName();
      Integer variableValue = ExpressionResolver.ResolveExpression(setVariableStmt.getVariableValue(), symbolTable);
      symbolTable.add(variableName, variableValue);

    } else if (stmt instanceof SwitchStmt switchStmt){
      String variableValue = Integer.toString(symbolTable.resolve(switchStmt.getVariableSymbol()));
      List<Stmt> statements = switchStmt.getCaseStatementsByVariableValue(variableValue);
      SymbolTable childSymbolTable = new SymbolTable(symbolTable);
      for (Stmt s : statements) {
        execute(s, childSymbolTable);
      }

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

  @Override
  public boolean isActive(Direction dir) {
    return BlocklyCommands.active(dir);
  }
}
