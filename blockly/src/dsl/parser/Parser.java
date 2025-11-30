package dsl.parser;

import dsl.lexer.*;
import dsl.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

  private final Lexer lexer;
  private Token current;

  public Parser(Lexer lexer) {
    this.lexer = lexer;
    this.current = lexer.nextToken();
  }

  private void eat(TokenType type) {
    if (current.type != type) {
      throw new RuntimeException("Expected " + type + " but got " + current.type);
    }
    current = lexer.nextToken();
  }

  public List<AstNode> parseProgram() {
    List<AstNode> nodes = new ArrayList<>();

    while (current.type != TokenType.EOF) {
      if (current.type == TokenType.NEWLINE) {
        eat(TokenType.NEWLINE);
        continue;
      }
      nodes.add(parseStatement());
    }
    return nodes;
  }

  private AstNode parseStatement() {
    if (current.text.equals("move")) {
      return parseMove();
    }
    if (current.text.equals("rotate")) {
      return parseRotate();
    }
    throw new RuntimeException("Unrecognized statement" + current.text);
  }

  private AstNode parseRotate() {
    eat(TokenType.IDENTIFIER);
    eat(TokenType.LEFT_PAREN);

    String direction = current.text;
    if (!direction.equals("right") && !direction.equals("left")) {
      throw new RuntimeException("Unrecognized direction" + direction);
    }
    eat(TokenType.IDENTIFIER);

    eat(TokenType.RIGHT_PAREN);
    return new RotateNode(direction);
  }

  private AstNode parseMove() {
    eat(TokenType.IDENTIFIER);
    eat(TokenType.LEFT_PAREN);
    eat(TokenType.RIGHT_PAREN);
    return new MoveNode();
  }
}
