import com.ibm.icu.impl.Assert;
import dsl.DungeonDslParserFacade;
import dsl.ExpressionStmt;
import dsl.auxiliary.ExpressionResolver;
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

    ExpressionStmt expressionStmt = new ExpressionStmt("a",true);
    this.symbolTable.add("a", 11);

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 11);
  }
  @Test
  public void TestExpressionResolverInteger(){
    // Preparation

    ExpressionStmt expressionStmt = new ExpressionStmt("9",false);

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 9);
  }
  @Test
  public void TestExpressionResolverExpression(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("8",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1);

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 8);
  }
  @Test
  public void TestExpressionResolverAddition(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("3",false);
    ExpressionStmt expressionStmt2 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1, expressionStmt2, "+");

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 5);
  }
  @Test
  public void TestExpressionResolverSubtraction(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt2 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1, expressionStmt2, "-");

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 0);
  }
  @Test
  public void TestExpressionResolverMultiplication(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt2 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1, expressionStmt2, "*");

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 4);
  }
  @Test
  public void TestExpressionResolverDivision(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt2 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1, expressionStmt2, "/");

    // Test
    Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);

    // Validation
    Assert.assrt(result == 1);

  }

  @Test
  public void TestExpressionResolverInvalidDivision(){
    // Preparation

    ExpressionStmt expressionStmt1 = new ExpressionStmt("2",false);
    ExpressionStmt expressionStmt2 = new ExpressionStmt("0",false);
    ExpressionStmt expressionStmt = new ExpressionStmt(expressionStmt1, expressionStmt2, "/");

    // Test & Validation
    try{
      Integer result = ExpressionResolver.ResolveExpression(expressionStmt, this.symbolTable);
      Assert.assrt(false);
    } catch (Exception e){
      Assert.assrt(true);
    }
  }

}
