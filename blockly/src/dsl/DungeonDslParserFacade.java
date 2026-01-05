package dsl;

import org.antlr.v4.runtime.*;

public class DungeonDslParserFacade {

  public Program parse(String source) {
    CharStream input = CharStreams.fromString(source);
    dsl.DungeonDSLLexer lexer = new dsl.DungeonDSLLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    dsl.DungeonDSLParser parser = new dsl.DungeonDSLParser(tokens);

    dsl.DungeonDSLParser.ProgramContext tree = parser.program();

    AstBuilder builder = new AstBuilder();
    return builder.buildProgram(tree);
  }
}
