package dsl.ast;

public class RotateNode extends AstNode {
  public final String direction;

  public RotateNode(String direction) {
    this.direction = direction;
  }
}
