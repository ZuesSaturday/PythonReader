package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.Factory.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Lexer.*;

import java.util.List;

public class ProgramParser {
    private final TokenStream tokens;
    private final StatementParser stmtParser;

    public ProgramParser(String code) {
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokenList = lexer.tokenize();
        this.tokens = new TokenStream(tokenList);
        this.stmtParser = new StatementParser(tokens);
    }

    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode();
        while (!tokens.isAtEnd()) {
            program.addStatement(stmtParser.parseStatement());
        }
        return program;
    }

    public static void main(String[] args) {
        String code = """
                print(a[])
                """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode progNode = parser.parseProgram();
        Environment env = new Environment();
        progNode.evaluate(env);

    }

}
