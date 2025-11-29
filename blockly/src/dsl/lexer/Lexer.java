package dsl.lexer;

public class Lexer {

  private final String input;
  private int pos = 0;

  public Lexer(String input) {
    this.input = input;
  }

  private boolean isAtEnd() {
    return pos >= input.length();
  }

  private char peek() {
    return input.charAt(pos);
  }

  private void advance() {
    pos++;
  }

  public Token nextToken() {
    while (!isAtEnd()) {
      char c = peek();

      if (Character.isWhitespace(c)) {
        if (c == '\n') {
          advance();
          return new Token(TokenType.NEWLINE, "\\n");
        }
        advance();
        continue;
      }

      if (Character.isLetter(c) || c == '_') {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && (Character.isLetter(peek()) || Character.isDigit(peek()) || peek() == '_')) {
          sb.append(peek());
          advance();
        }
        return new Token(TokenType.IDENTIFIER, sb.toString());
      }


      if (c == '(') { advance(); return new Token(TokenType.LEFT_PAREN, "("); }
      if (c == ')') { advance(); return new Token(TokenType.RIGHT_PAREN, ")"); }

      throw new RuntimeException("Unexpected character: " + c);
    }

    return new Token(TokenType.EOF, "");
  }
}
