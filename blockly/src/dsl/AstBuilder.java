package dsl;

import coderunner.Direction;
import dsl.antlr4.*;
import dsl.expr.*;
import dsl.statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
      case "links" -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };

    return new RotateStmt(dir);
  }

  @Override
  public Object visitShootFireballStmt(DungeonDSLParser.ShootFireballStmtContext ctx) {
    return new ShootFireballStmt();
  }

  @Override
  public Object visitPickupStmt(DungeonDSLParser.PickupStmtContext ctx) {
    return new PickupStmt();
  }

  @Override
  public Object visitUseStmt(DungeonDSLParser.UseStmtContext ctx) {
    String dirText = ctx.direction().getText();

    Direction dir = switch (dirText) {
      case "vorne" -> Direction.INFRONT;
      case "hinter" -> Direction.BEHIND;
      case "hier" -> Direction.HERE;
      case "links" -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };

    return new UseStmt(dir);
  }

  @Override
  public Object visitPushStmt(DungeonDSLParser.PushStmtContext ctx) {
    return new PushStmt();
  }

  @Override
  public Object visitPullStmt(DungeonDSLParser.PullStmtContext ctx) {
    return new PullStmt();
  }


  @Override
  public Object visitSetVariableStmt(DungeonDSLParser.SetVariableStmtContext ctx) {
    String variableName = ctx.VAR_NAME().getText();
    ExpressionStmt value = (ExpressionStmt) visitExpressionRootStmt(ctx.expressionRoot());
    return new SetVariableStmt(variableName, value);
  }

  public Object visitExpressionRootStmt(DungeonDSLParser.ExpressionRootContext ctx) {
    if (ctx.expressionFirstOrder() != null) {
      return visitExpressionFirstOrderStmt(ctx.expressionFirstOrder());
    }
    throw new IllegalArgumentException("Expression without value!");
  }

  public Object visitExpressionFirstOrderStmt(DungeonDSLParser.ExpressionFirstOrderContext ctx) {
    if (ctx.expressionFirstOrder().size() == 2) {
      var parts = ctx.expressionFirstOrder();
      ExpressionStmt left = (ExpressionStmt) visitExpressionFirstOrderStmt(parts.get(0));
      ExpressionStmt right = (ExpressionStmt) visitExpressionFirstOrderStmt(parts.get(1));
      return new ExpressionStmt(left, right, ctx.FIRST_ORDER_OPERATOR().getText());
    }
    if (ctx.expressionSecondOrder() != null) {
      return visitExpressionSecondOrderStmt(ctx.expressionSecondOrder());
    }
    throw new IllegalArgumentException("Expression without value!");
  }

  public Object visitExpressionSecondOrderStmt(DungeonDSLParser.ExpressionSecondOrderContext ctx) {
    if (ctx.expressionSecondOrder().size() == 2) {
      var parts = ctx.expressionSecondOrder();
      ExpressionStmt left = (ExpressionStmt) visitExpressionSecondOrderStmt(parts.get(0));
      ExpressionStmt right = (ExpressionStmt) visitExpressionSecondOrderStmt(parts.get(1));
      return new ExpressionStmt(left, right, ctx.SECOND_ORDER_OPERATOR().getText());
    }
    if (ctx.expressionLeaf() != null) {
      return visitExpressionLeafStmt(ctx.expressionLeaf());
    }
    throw new IllegalArgumentException("Expression without value!");
  }

  public Object visitExpressionLeafStmt(DungeonDSLParser.ExpressionLeafContext ctx) {
    if (ctx.VAR_NAME() != null) {
      return new ExpressionStmt(ctx.VAR_NAME().getText(), true);
    }
    if (ctx.INT() != null) {
      return new ExpressionStmt(ctx.INT().getText(), false);
    }
    if (ctx.expressionRoot() != null) {
      return visitExpressionRootStmt(ctx.expressionRoot());
    }
    throw new IllegalArgumentException("Expression without value!");
  }


  @Override
  public Object visitRepeatStmt(DungeonDSLParser.RepeatStmtContext ctx) {
    int times = Integer.parseInt(ctx.range().INT().getText());
    List<Stmt> body = ctx.block().statement().stream()
      .map(s -> (Stmt) visit(s))
      .collect(Collectors.toList());

    return new RepeatStmt(times, body);
  }

  @Override
  public Object visitWhileStmt(DungeonDSLParser.WhileStmtContext ctx) {
    Expr cond = (Expr) visit(ctx.condition());
    List<Stmt> body = buildBlock(ctx.block());
    return new WhileStmt(cond, body);
  }

  @Override
  public Object visitSwitchStmt(DungeonDSLParser.SwitchStmtContext ctx) {
    String variableSymbol = ctx.VAR_NAME().getText();
    List<CaseStmt> caseStmtList = new ArrayList<>();

    List<Stmt> defaultStatements = ctx.defaultStmt().statement().stream()
      .map(s -> (Stmt) visit(s))
      .toList();

    SwitchDefaultStmt switchDefaultStmt = new SwitchDefaultStmt(defaultStatements);

    for (int i = 0; i < ctx.caseStmt().size(); i++) {
      List<Stmt> caseStatements = ctx.caseStmt(i).statement().stream()
        .map(s -> (Stmt) visit(s))
        .toList();

      String variableValue = ctx.caseStmt(i).caseValueStmt().getText();
      caseStmtList.add(new CaseStmt(caseStatements, variableValue));
    }

    return new SwitchStmt(caseStmtList, switchDefaultStmt, variableSymbol);
  }


  @Override
  public Object visitIfStmt(DungeonDSLParser.IfStmtContext ctx) {
    List<IfBranch> branches = new ArrayList<>();

    int condCount = ctx.condition().size();
    int blockCount = ctx.block().size();

    for (int i = 0; i < condCount; i++) {
      Expr cond = (Expr) visit(ctx.condition(i));
      List<Stmt> body = buildBlock(ctx.block(i));
      branches.add(new IfBranch(cond, body));
    }

    List<Stmt> elseBody = null;
    if (blockCount > condCount) {
      elseBody = buildBlock(ctx.block(blockCount - 1));
    }

    return new IfStmt(branches, elseBody);
  }


  @Override
  public Object visitCondition(DungeonDSLParser.ConditionContext ctx) {
    return visit(ctx.orExpr());
  }

  @Override
  public Object visitOrExpr(DungeonDSLParser.OrExprContext ctx) {
    Expr left = (Expr) visit(ctx.andExpr(0));
    for (int i = 1; i < ctx.andExpr().size(); i++) {
      Expr right = (Expr) visit(ctx.andExpr(i));
      left = new BinaryExpr(left, right, "or");
    }
    return left;
  }

  @Override
  public Object visitAndExpr(DungeonDSLParser.AndExprContext ctx) {
    Expr left = (Expr) visit(ctx.notExpr(0));
    for (int i = 1; i < ctx.notExpr().size(); i++) {
      Expr right = (Expr) visit(ctx.notExpr(i));
      left = new BinaryExpr(left, right, "and");
    }
    return left;
  }

  @Override
  public Object visitNotExpr(DungeonDSLParser.NotExprContext ctx) {
    if (ctx.NOT() != null) {
      Expr inner = (Expr) visit(ctx.notExpr());
      return new NotExpr(inner);
    }
    if (ctx.predicate() != null) {
      return visit(ctx.predicate());
    }
    if (ctx.condition() != null) {
      return visit(ctx.condition());
    }
    throw new IllegalArgumentException("Ungültiger notExpr: " + ctx.getText());
  }

  @Override
  public Object visitPredicate(DungeonDSLParser.PredicateContext ctx) {
    if (ctx.BOOLEAN() != null) {
      boolean value = Boolean.parseBoolean(ctx.BOOLEAN().getText());
      return new BoolExpr(value);
    }

    // The rest needs a direction()
    Direction dir = (Direction) visit(ctx.direction());

    if (ctx.ACTIVE() != null) return new ActiveExpr(dir);
    if (ctx.WALL() != null) return new WallExpr(dir);
    if (ctx.FLOOR() != null) return new FloorExpr(dir);
    if (ctx.PIT() != null) return new PitExpr(dir);

    throw new IllegalArgumentException("Unbekanntes Predicate: " + ctx.getText());
  }

    List<Stmt> stmts = new ArrayList<>();
    for (var sCtx : blockCtx.statement()) {
      stmts.add((Stmt) visit(sCtx));
    }
    return stmts;
  }

  @Override
  public Object visitDirection(DungeonDSLParser.DirectionContext ctx) {
    String dirText = ctx.getText();
    return switch (dirText) {
      case "vorne" -> Direction.INFRONT;
      case "hinter" -> Direction.BEHIND;
      case "hier" -> Direction.HERE;
      case "links" -> Direction.LEFT;
      case "rechts" -> Direction.RIGHT;
      default -> throw new IllegalArgumentException("Unbekannte Richtung: " + dirText);
    };
  }
}
