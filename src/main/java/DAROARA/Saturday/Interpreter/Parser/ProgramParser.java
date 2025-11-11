package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Lexer;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class ProgramParser {
    private final TokenStream tokens;
    private final StatementParser stmtParser;

    public ProgramParser(String code) {
        Lexer lexer = new Lexer(code);
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
                a = 5
                b = 5
                if a == b:
                    print(a)
                """;

        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

        program.printTree("");
        program.evaluate(new Environment());
    }
}
