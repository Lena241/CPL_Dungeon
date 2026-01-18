package dsl;

import coderunner.Direction;
import dsl.expr.ActiveExpr;
import dsl.expr.Expr;
import dsl.statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AstBuilder extends dsl.DungeonDSLBaseVisitor<Object> {

  public Program buildProgram(dsl.DungeonDSLParser.ProgramContext ctx) {
    List<Stmt> stmts = new ArrayList<>();
    for (var stmtCtx : ctx.statement()) {
      Stmt stmt = (Stmt) visit(stmtCtx);
      stmts.add(stmt);
    }
    return new Program(stmts);
  }

  @Override
  public Object visitMoveStmt(dsl.DungeonDSLParser.MoveStmtContext ctx) {
    return new MoveStmt();
  }

  @Override
  public Object visitRotateStmt(dsl.DungeonDSLParser.RotateStmtContext ctx) {
    String dirText = ctx.direction().getText();

    Direction dir = switch (dirText) {
      case "links"  -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };

    return new RotateStmt(dir);
  }

  @Override
  public Object visitShootFireballStmt(dsl.DungeonDSLParser.ShootFireballStmtContext ctx) {
    return new ShootFireballStmt();
  }

  @Override
  public Object visitPickupStmt(dsl.DungeonDSLParser.PickupStmtContext ctx) {
    return new PickupStmt();
  }

  @Override
  public Object visitRepeatStmt(dsl.DungeonDSLParser.RepeatStmtContext ctx) {
    int times = Integer.parseInt(ctx.INT().getText());

    List<Stmt> body = ctx.statement().stream().map(s -> (Stmt) visit(s)).collect(Collectors.toList());

    return new RepeatStmt(times, body);
  }

  @Override
  public Object visitUseStmt(dsl.DungeonDSLParser.UseStmtContext ctx) {
    String dirText = ctx.direction().getText();

    Direction dir = switch (dirText) {
      case "vorne" -> Direction.INFRONT;
      case "hinter" -> Direction.BEHIND;
      case "hier" -> Direction.HERE;
      case "links"  -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };
    return new UseStmt(dir);
  }

  @Override
  public Object visitIfStmt(dsl.DungeonDSLParser.IfStmtContext ctx) {
    List<IfBranch> branches = new ArrayList<>();

    // The grammar produces condition() list and block() list in order.
    // First k blocks belong to falls/sonst falls branches.
    // If there is an "sonst" block, it is the last block and has no condition.

    int condCount = ctx.condition().size();
    int blockCount = ctx.block().size();

    // Build branches: (condition[i], block[i]) for i in [0..condCount-1]
    for (int i = 0; i < condCount; i++) {
      Expr cond = (Expr) visit(ctx.condition(i));
      List<Stmt> body = buildBlock(ctx.block(i));
      branches.add(new IfBranch(cond, body));
    }

    // Optional else body: if there are more blocks than conditions
    List<Stmt> elseBody = null;
    if (blockCount > condCount) {
      elseBody = buildBlock(ctx.block(blockCount - 1));
    }

    return new IfStmt(branches, elseBody);
  }

  private List<Stmt> buildBlock(dsl.DungeonDSLParser.BlockContext blockCtx) {
    List<Stmt> stmts = new ArrayList<>();
    for (var sCtx : blockCtx.statement()) {
      stmts.add((Stmt) visit(sCtx));
    }
    return stmts;
  }

  @Override
  public Object visitPredicate(dsl.DungeonDSLParser.PredicateContext ctx) {
    // active(direction)
    Direction dir = (Direction) visit(ctx.direction());
    return new ActiveExpr(dir);
  }

  @Override
  public Object visitDirection(dsl.DungeonDSLParser.DirectionContext ctx) {
    String dirText = ctx.getText();
    return switch (dirText) {
      case "vorne" -> Direction.INFRONT;
      case "hinter" -> Direction.BEHIND;
      case "hier" -> Direction.HERE;
      case "links"  -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };
  }


  @Override
  public Object visitPushStmt(dsl.DungeonDSLParser.PushStmtContext ctx) {
    return new PushStmt();
  }

  @Override
  public Object visitPullStmt(dsl.DungeonDSLParser.PullStmtContext ctx) {
    return new PullStmt();
  }

}
