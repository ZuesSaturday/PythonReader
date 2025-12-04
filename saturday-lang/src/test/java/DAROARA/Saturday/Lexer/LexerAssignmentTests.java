package DAROARA.Saturday.Lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerAssignmentTests {
    @Test
    void NumberVariableAssignment() {
        String code = """
            int = 2
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();

        assertEquals(4,tokens.size());
        assertEquals(TokenType.IDENTIFIER,tokens.get(0).getType());
        assertEquals(TokenType.ASSIGN,tokens.get(1).getType());
        assertEquals(TokenType.NUMBER,tokens.get(2).getType());
        assertEquals(TokenType.EOF,tokens.get(3).getType());
    }

    @Test
    void StringVariableAssignment() {
        String code = """
            String = "PythonReader"
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();

        assertEquals(4,tokens.size());
        assertEquals(TokenType.IDENTIFIER,tokens.get(0).getType());
        assertEquals(TokenType.ASSIGN,tokens.get(1).getType());
        assertEquals(TokenType.STRING,tokens.get(2).getType());
        assertEquals(TokenType.EOF,tokens.get(3).getType());
    }

    @Test
    void ListVariableAssignment() {
        String code = """
            String = [1,2,3,4]
            """;
        SaturdayLexer lexer = new SaturdayLexer(code);
        List<Token> tokens = lexer.tokenize();

        assertEquals(12,tokens.size());
        assertEquals(TokenType.IDENTIFIER,tokens.get(0).getType());
        assertEquals(TokenType.ASSIGN,tokens.get(1).getType());
        assertEquals(TokenType.LBRACKET,tokens.get(2).getType());
        assertEquals(TokenType.EOF,tokens.get(11).getType());
    }
}
