package DAROARA.Saturday.Lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerStringTests {
    @Test
    void SimpleString() {
        String code = """
            "String"
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();
        assertEquals(2,tokens.size());
        assertEquals(TokenType.STRING,tokens.get(0).getType());
        assertEquals(TokenType.EOF,tokens.get(1).getType());
    }
}
