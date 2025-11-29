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

  public List<CommandNode> parseProgram() {
    List<CommandNode> commands = new ArrayList<>();

    while (current.type != TokenType.EOF) {
      commands.add(parseCommand());
      if (current.type == TokenType.NEWLINE) eat(TokenType.NEWLINE);
    }
    return commands;
  }

  private CommandNode parseCommand() {
    String name = current.text; // move oder turn_left oder turn_right
    eat(TokenType.IDENTIFIER);
    eat(TokenType.LEFT_PAREN);
    eat(TokenType.RIGHT_PAREN);
    return new CommandNode(name);
  }
}
