import coderunner.ExpressionType;
import com.ibm.icu.impl.Assert;
import dsl.*;
import dsl.expr.BinaryExpr;
import dsl.expr.IDExpr;
import dsl.expr.IntExpr;
import dsl.statements.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDungeonDslParserFacade {
  DungeonDslParserFacade dungeonDslParserFacade;

  /** Reset DungeonDslParserFacade before each test. */
  @BeforeEach
  public void setUp() {
    this.dungeonDslParserFacade = new DungeonDslParserFacade();
  }

  String shootFireballCommand = "feuerball()";

  @Test
  public void TestFireball(){
    Program output = this.dungeonDslParserFacade.parse(this.shootFireballCommand);
    Assert.assrt(output.statements().get(0).getClass() == ShootFireballStmt.class);
  }

  @Test
  public void TestCreateVariableIntegerExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = 2");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);
    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getClass() == IntExpr.class);
    Assert.assrt(((IntExpr)setVariableStmt.getVariableValue()).getValue() == 2);
  }
  @Test
  public void TestCreateVariableVariableExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);
    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getClass() == IDExpr.class);
    Assert.assrt(((IDExpr)setVariableStmt.getVariableValue()).getVariableSymbol().equals("b"));
  }
  @Test
  public void TestCreateVariableOperatorExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = 2 + b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);

    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getClass() == BinaryExpr.class);

    BinaryExpr binaryExpr = (BinaryExpr)setVariableStmt.getVariableValue();
    Assert.assrt(binaryExpr.getOp().equals("+"));
    Assert.assrt(binaryExpr.getLeft().getClass() == IntExpr.class);
    Assert.assrt(binaryExpr.getRight().getClass() == IDExpr.class);

    IntExpr left = (IntExpr) binaryExpr.getLeft();
    IDExpr right = (IDExpr) binaryExpr.getRight();
    Assert.assrt(left.getValue() == 2);
    Assert.assrt(right.getVariableSymbol().equals("b"));
  }
  @Test
  public void TestCreateVariableNestedOperatorExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = 3 * 2 + b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);

    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getClass() == BinaryExpr.class);

    BinaryExpr secondOrderOperatorBinaryExpr = (BinaryExpr)setVariableStmt.getVariableValue();
    Assert.assrt(secondOrderOperatorBinaryExpr.getOp().equals("+"));
    Assert.assrt(secondOrderOperatorBinaryExpr.getLeft().getClass() == BinaryExpr.class);
    Assert.assrt(secondOrderOperatorBinaryExpr.getRight().getClass() == IDExpr.class);

    IDExpr right = (IDExpr) secondOrderOperatorBinaryExpr.getRight();
    Assert.assrt(right.getVariableSymbol().equals("b"));

    BinaryExpr firstOrderOperatorBinaryExpr = (BinaryExpr) secondOrderOperatorBinaryExpr.getLeft();
    Assert.assrt(firstOrderOperatorBinaryExpr.getOp().equals("*"));
    Assert.assrt(firstOrderOperatorBinaryExpr.getLeft().getClass() == IntExpr.class);
    Assert.assrt(firstOrderOperatorBinaryExpr.getRight().getClass() == IntExpr.class);

    IntExpr left = (IntExpr) firstOrderOperatorBinaryExpr.getLeft();
    IntExpr right2 = (IntExpr) firstOrderOperatorBinaryExpr.getRight();
    Assert.assrt(left.getValue() == 3);
    Assert.assrt(right2.getValue() == 2);
  }

  @Test
  public void TestSwitchStatement(){
    String input = "a = 3\nswitch (a) : \n case 2:\n gehen() \n case 3:\n feuerball()\n default:\n schieben()\n end";
    Program output = this.dungeonDslParserFacade.parse(input);
    SwitchStmt switchStmt = (SwitchStmt) output.statements().get(1);
    Assert.assrt(switchStmt.getCaseStatementsByVariableValue("3").get(0).getClass() == ShootFireballStmt.class);
    Assert.assrt(switchStmt.getCaseStatementsByVariableValue("4").get(0).getClass() == PushStmt.class);
  }
}
