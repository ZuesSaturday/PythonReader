package DAROARA.Saturday.Lexer;

import DAROARA.Saturday.Lexer.SaturdayLexer;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerListsTests {
    @Test
    void SimpleList() {
        String code = """
            [1,2,3,4]
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();
    }

    @Test
    void NestedList() {
        String code = """
            [[1,2],[3,4]]
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();
    }
}
