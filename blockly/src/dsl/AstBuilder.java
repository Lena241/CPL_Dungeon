package dsl;

import coderunner.Direction;
import java.util.ArrayList;
import java.util.List;

public class AstBuilder extends DungeonDSLBaseVisitor<Object> {

  public Program buildProgram(DungeonDSLParser.ProgramContext ctx) {
    List<Stmt> stmts = new ArrayList<>();
    for (var stmtCtx : ctx.statement()) {
      Stmt stmt = (Stmt) visit(stmtCtx);
      stmts.add(stmt);
    }
    return new Program(stmts);
  }

  @Override
  public Object visitMoveStmt(DungeonDSLParser.MoveStmtContext ctx) {
    return new MoveStmt();
  }

  @Override
  public Object visitRotateStmt(DungeonDSLParser.RotateStmtContext ctx) {
    String dirText = ctx.direction().getText();

    Direction dir = switch (dirText) {
      case "links"  -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };

    return new RotateStmt(dir);
  }
}
