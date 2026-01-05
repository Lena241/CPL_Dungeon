package dsl;

import coderunner.Direction;
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
  public Object visitPickupStmt(dsl.DungeonDSLParser.PickupStmtContext ctx) {
    return new PickupStmt();
  }

  @Override
  public Object visitRepeatStmt(dsl.DungeonDSLParser.RepeatStmtContext ctx) {
    int times = Integer.parseInt(ctx.INT().getText());

    List<Stmt> body = ctx.statement().stream().map(s -> (Stmt) visit(s)).collect(Collectors.toList());

    return new RepeatStmt(times, body);
  }
}
