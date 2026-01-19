import com.ibm.icu.impl.Assert;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;
import dsl.expr.BinaryExpr;
import dsl.expr.Expr;
import dsl.expr.IDExpr;
import dsl.expr.IntExpr;
import dsl.auxiliary.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExpressionResolver {

  SymbolTable symbolTable;

  @BeforeEach
  public void setUp() {
    this.symbolTable = new SymbolTable();
  }
  @Test
  public void TestExpressionResolverVariable(){
    // Preparation

    Expr expressionStmt = new IDExpr("a");
    this.symbolTable.add("a", new Value(11, ValueType.Integer));

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 11);
  }
  @Test
  public void TestExpressionResolverInteger(){
    // Preparation

    Expr expressionStmt = new IntExpr(9);

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 9);
  }
  @Test
  public void TestExpressionResolverAddition(){
    // Preparation

    Expr expressionStmt1 = new IntExpr(3);
    Expr expressionStmt2 = new IntExpr(2);
    Expr expressionStmt = new BinaryExpr(expressionStmt1, expressionStmt2, "+");

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 5);
  }
  @Test
  public void TestExpressionResolverSubtraction(){
    // Preparation

    Expr expressionStmt1 = new IntExpr(2);
    Expr expressionStmt2 = new IntExpr(2);
    Expr expressionStmt = new BinaryExpr(expressionStmt1, expressionStmt2, "-");

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 0);
  }
  @Test
  public void TestExpressionResolverMultiplication(){
    // Preparation

    Expr expressionStmt1 = new IntExpr(2);
    Expr expressionStmt2 = new IntExpr(2);
    Expr expressionStmt = new BinaryExpr(expressionStmt1, expressionStmt2, "*");

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 4);
  }
  @Test
  public void TestExpressionResolverDivision(){
    // Preparation

    Expr expressionStmt1 = new IntExpr(2);
    Expr expressionStmt2 = new IntExpr(2);
    Expr expressionStmt = new BinaryExpr(expressionStmt1, expressionStmt2, "/");

    // Test
    Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);

    // Validation
    Assert.assrt(result == 1);

  }

  @Test
  public void TestExpressionResolverInvalidDivision(){
    // Preparation

    Expr expressionStmt1 = new IntExpr(2);
    Expr expressionStmt2 = new IntExpr(0);
    Expr expressionStmt = new BinaryExpr(expressionStmt1, expressionStmt2, "/");

    // Test & Validation
    try{
      Integer result = expressionStmt.evalAsInteger(null, this.symbolTable);
      Assert.assrt(false);
    } catch (Exception e){
      Assert.assrt(true);
    }
  }

}
