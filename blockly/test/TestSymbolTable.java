import com.ibm.icu.impl.Assert;
import dsl.DungeonDslParserFacade;
import dsl.auxiliary.SymbolTable;
import dsl.auxiliary.Value;
import dsl.auxiliary.ValueType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSymbolTable {

  SymbolTable symbolTable;
  SymbolTable parentSymbolTable;

  @BeforeEach
  public void setUp() {
    this.parentSymbolTable = new SymbolTable();
    this.symbolTable = new SymbolTable(this.parentSymbolTable);
  }
  @Test
  public void TestSymbolTableAddAndResolveCase1(){
    // flag1 == true  and flag2 == true  and flag3 == false
    // preparation
    Value value = Value.IntegerValue(null);
    this.symbolTable.add("a",Value.IntegerValue(2));
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(this.symbolTable.resolve("a") == null);
  }
  @Test
  public void TestSymbolTableAddAndResolveCase2(){
    // flag1 == true  and flag2 == false and flag3 == false
    Value value = Value.IntegerValue(null);
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(this.symbolTable.resolve("a") == null);
  }
  @Test
  public void TestSymbolTableAddAndResolveCase3(){
    // flag1 == false and flag2 == true  and flag3 == false
    Value value = Value.IntegerValue(1);
    this.symbolTable.add("a",Value.IntegerValue(2));
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(Value.GetInteger(this.symbolTable.resolve("a")) == 1);
    this.symbolTable.add("a",Value.IntegerValue(1));
  }
  @Test
  public void TestSymbolTableAddAndResolveCase4(){
    // flag1 == false and flag2 == false and flag3 == false
    Value value = Value.IntegerValue(1);
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(Value.GetInteger(this.symbolTable.resolve("a")) == 1);
    // flag3 == true
  }
  @Test
  public void TestSymbolTableAddAndResolveCase1OwnerIsParent(){
    // flag1 == true  and flag2 == true  and flag3 == false
    // preparation
    Value value = Value.IntegerValue(null);
    this.parentSymbolTable.add("a",Value.IntegerValue(2));
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(this.symbolTable.resolve("a")== null);
  }
  @Test
  public void TestSymbolTableAddAndResolveCase3OwnerIsParent(){
    // flag1 == false and flag2 == true  and flag3 == false
    Value value = Value.IntegerValue(1);
    this.parentSymbolTable.add("a",Value.IntegerValue(2));
    // test
    this.symbolTable.add("a", value);
    // validation
    Assert.assrt(Value.GetInteger(this.symbolTable.resolve("a")) == 1);
  }
  @Test
  public void TestSymbolTableAddCase5(){
    // flag3 == true
    // Preparation
    Value value = Value.IntegerValue(1);
    // Test & Validation
    try{
      this.symbolTable.add("", value);
      Assert.assrt(false);
    } catch (Exception e){
      Assert.assrt(true);
    }
  }
}
