package DAROARA.Saturday.Interpreter.Compiler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LexerPrintTest {

    @Test
    void PrintHelloWorld() {
        String code = """
            print("Hello , World!")
            """;
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();
        System.out.println(tokens);
        assertEquals(5,tokens.size());
        assertEquals(TokenType.KEYWORD,tokens.get(0).getType());
        assertEquals("print",tokens.get(0).getValue());
        assertEquals(TokenType.LPAREN,tokens.get(1).getType());
        assertEquals("(",tokens.get(1).getValue());
        assertEquals(TokenType.STRING,tokens.get(2).getType());
        assertEquals(TokenType.EOF, tokens.get(4).getType());
    }
}
