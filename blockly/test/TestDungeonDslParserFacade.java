import coderunner.ExpressionType;
import com.ibm.icu.impl.Assert;
import dsl.*;
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
    Assert.assrt(setVariableStmt.getVariableValue().getInteger().equals("2"));
    Assert.assrt(setVariableStmt.getVariableValue().getExpressionType() == ExpressionType.ContainsInteger);
  }
  @Test
  public void TestCreateVariableVariableExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);
    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getVariable().equals("b"));
    Assert.assrt(setVariableStmt.getVariableValue().getExpressionType() == ExpressionType.ResolveVariable);
  }
  @Test
  public void TestCreateVariableOperatorExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = 2 + b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);
    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getInteger().equals("2"));
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getExpressionType() == ExpressionType.ContainsInteger);
    Assert.assrt(setVariableStmt.getVariableValue().getRightExpression().getVariable().equals("b"));
    Assert.assrt(setVariableStmt.getVariableValue().getRightExpression().getExpressionType() == ExpressionType.ResolveVariable);
    Assert.assrt(setVariableStmt.getVariableValue().getExpressionType() == ExpressionType.ApplyOperator);
  }
  @Test
  public void TestCreateVariableNestedOperatorExpression(){
    Program output = this.dungeonDslParserFacade.parse("a = 3 * 2 + b");
    Assert.assrt(output.statements().get(0).getClass() == SetVariableStmt.class);
    SetVariableStmt setVariableStmt = (SetVariableStmt) output.statements().get(0);
    Assert.assrt(setVariableStmt.getVariableName().equals("a"));
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getExpressionType() == ExpressionType.ApplyOperator);
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getLeftExpression().getExpressionType() == ExpressionType.ContainsInteger);
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getLeftExpression().getInteger().equals("3"));
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getRightExpression().getExpressionType() == ExpressionType.ContainsInteger);
    Assert.assrt(setVariableStmt.getVariableValue().getLeftExpression().getRightExpression().getInteger().equals("2"));
    Assert.assrt(setVariableStmt.getVariableValue().getRightExpression().getVariable().equals("b"));
    Assert.assrt(setVariableStmt.getVariableValue().getRightExpression().getExpressionType() == ExpressionType.ResolveVariable);
    Assert.assrt(setVariableStmt.getVariableValue().getExpressionType() == ExpressionType.ApplyOperator);
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
