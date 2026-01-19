package dsl.auxiliary;

import java.util.HashMap;

public class SymbolTable {

  // the symbol table data.
  private final HashMap<String, Value> symbolTable = new HashMap<>();

  // the next object in the SymbolTable hierarchy.
  private final SymbolTable parent;

  /**
   * Creates a new SymbolTable with the specified hierarchy.
   *
   * @param parent the next object in the SymbolTable hierarchy.
   */
  public SymbolTable(SymbolTable parent){
    this.parent = parent;
  }
  /**
   * Creates a new SymbolTable with the specified parent hierarchy.
   */
  public SymbolTable(){
    this(null);
  }

   /**
   * Adding variableSymbol, value tuple based on three properties
   * Flag/properties Definitions:
   *  flag1 = Value is null
   *  flag2 = this object or a predecessor contains variable name
   *  flag3 = variable name is empty
   * |-------|-------|-------|----------------------------------------------------------------------------------------|
   * | flag1 | flag2 | flag3 | Behavior                                                                               |
   * |-------|-------|-------|----------------------------------------------------------------------------------------|
   * | true  | true  | false | Remove the variable symbol at the first occurrence in the hierarchy (ascending)        |
   * | true  | false | false | No action taken                                                                        |
   * | false | true  | false | Overwrite the variable symbol at the first occurrence in the hierarchy (ascending)     |
   * | false | false | false | Create the passed tuple in the current object                                          |
   * | *     | *     | true  | Exception                                                                              |
   *
   * @param variableSymbol symbol, which represents the variable
   * @param value variable value
   */
  public void add(String variableSymbol, Value value) {

    boolean flag3 = variableSymbol.isEmpty();

    if (flag3) {
      throw new IllegalArgumentException("Try to add invalid variable!");
    }

    boolean flag1 = (value == null || value.getValue() == null);
    SymbolTable owner = this.getOwner(variableSymbol);
    boolean flag2 = (owner != null);

    if (flag1 && flag2){
      owner.symbolTable.remove(variableSymbol);
    } else if (!flag1 && flag2){
      owner.symbolTable.put(variableSymbol, value);
    } else if (!flag1 && !flag2){
      this.symbolTable.put(variableSymbol, value);
    }
  }
  /**
   * Checks if {@code variableSymbol} appears in any of the symbol tables
   * of the hierarchy (ascending) and returns the corresponding symbol table.
   * Traverses symbol tables from current scope upward through parent
   * scopes until the first matching symbol is found.
   *
   * @param variableSymbol the variable symbol to search for
   * @return the first {@link SymbolTable} containing the symbol,
   *         or {@code null} if not found anywhere in the hierarchy
   */
  private SymbolTable getOwner(String variableSymbol){
    if (this.symbolTable.containsKey(variableSymbol)){
      return this;
    }
    else if(this.parent != null){
      return this.parent.getOwner(variableSymbol);
    } else {
      return null;
    }
  }

  /**
   * Resolves the given {@code variableSymbol} and returns its associated value.
   *
   * <p>Traverses the symbol table hierarchy (ascending) and returns the value
   * of the first matching symbol. If {@code variableSymbol} is not defined
   * in any symbol table of the hierarchy, this method returns {@code null}.</p>
   *
   * @param variableSymbol the symbol whose value should be resolved
   * @return the associated value, or {@code null} if the symbol is not defined
   */
  public Value resolve(String variableSymbol){

    if (variableSymbol.isEmpty()) {
      throw new IllegalArgumentException("Try to resolve invalid variable!");
    }

    Value result = null;

    if (symbolTable.containsKey(variableSymbol)){
      result = symbolTable.get(variableSymbol);
    } else if (this.parent != null) {
      result = this.parent.resolve(variableSymbol);
    }

    return result;
  }
}
