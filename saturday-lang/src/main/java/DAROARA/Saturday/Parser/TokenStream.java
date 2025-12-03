package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.List;

public class TokenStream {
    private final List<Token> tokens;
    private int currentIndex = 0;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token peek() {
        if (currentIndex >= tokens.size()) return new Token(TokenType.EOF, "");
        return tokens.get(currentIndex);
    }

    public Token consume() {
        Token t = peek();
        currentIndex++;
        return t;
    }

    public boolean match(TokenType type) {
        if (peek().getType() == type) {
            consume();
            return true;
        }
        return false;
    }

    public boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    public boolean expect(TokenType tokenType) {
        if (peek().getType() == tokenType) {
            return true;
        }
        return false;
    }
}
