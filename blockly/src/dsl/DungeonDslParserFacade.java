package dsl;

import org.antlr.v4.runtime.*;

public class DungeonDslParserFacade {

  public Program parse(String source) {
    CharStream input = CharStreams.fromString(source);
    DungeonDSLLexer lexer = new DungeonDSLLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    DungeonDSLParser parser = new DungeonDSLParser(tokens);

    DungeonDSLParser.ProgramContext tree = parser.program();

    AstBuilder builder = new AstBuilder();
    return builder.buildProgram(tree);
  }
}
