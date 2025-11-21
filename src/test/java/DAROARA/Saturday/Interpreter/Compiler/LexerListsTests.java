package DAROARA.Saturday.Interpreter.Compiler;

import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerListsTests {
    @Test
    void SimpleList() {
        String code = """
            [1,2,3,4]
            """;
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();
    }

    @Test
    void NestedList() {
        String code = """
            [[1,2],[3,4]]
            """;
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();
    }
}
