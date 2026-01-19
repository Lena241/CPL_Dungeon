package dsl.auxiliary;

public class Value {

  private final Object value;
  private final ValueType type;

  public Value(Object value, ValueType type){
    this.value = value;
    this.type = type;
  }

  public Object getValue(){
    return this.value;
  }

  public ValueType getType(){
    return this.type;
  }

  public static Value IntegerValue(Integer value){
    return new Value(value, ValueType.Integer);
  }
  public static Value BooleanValue(Boolean value){
    return new Value(value, ValueType.Boolean);
  }

  public static Integer GetInteger(Value value){
    if(value.getType() != ValueType.Integer){
      throw new IllegalArgumentException("Wrong Type!");
    }

    return (Integer) value.getValue();
  }

  public static Boolean GetBoolean(Value value){
    if(value.getType() != ValueType.Boolean){
      throw new IllegalArgumentException("Wrong Type!");
    }

    return (Boolean) value.getValue();
  }

}
