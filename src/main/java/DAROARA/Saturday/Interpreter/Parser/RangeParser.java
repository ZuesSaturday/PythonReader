package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.RangeNode;
import DAROARA.Saturday.Interpreter.AST.Node;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

public class RangeParser {

    private final TokenStream tokens;

    public RangeParser(TokenStream tokens) {
        this.tokens = tokens;
    }

    public Node parseRange() {
        Token rangeTok = tokens.consume();  // "range"

        tokens.expect(TokenType.LPAREN);
        tokens.consume();

        int start = 0;
        int end;
        int step = 1;

        // First value must be NUMBER
        tokens.expect(TokenType.NUMBER);
        Token first = tokens.consume();

        // CASE 1: range(N)
        if (tokens.peek().getType() == TokenType.RPAREN) {
            end = Integer.parseInt(first.getValue());
            tokens.consume(); // RPAREN
            return new RangeNode(rangeTok, start, end, step);
        }

        // Otherwise MUST be a comma
        tokens.expect(TokenType.COMMA);
        tokens.consume();

        // CASE 2: range(A, B)
        tokens.expect(TokenType.NUMBER);
        Token second = tokens.consume();
        start = Integer.parseInt(first.getValue());
        end = Integer.parseInt(second.getValue());

        // CASE 2 END: range(A, B)
        if (tokens.peek().getType() == TokenType.RPAREN) {
            tokens.consume();
            return new RangeNode(rangeTok, start, end, step);
        }

        // Must be comma for step
        tokens.expect(TokenType.COMMA);
        tokens.consume();

        // CASE 3: range(A, B, C)
        tokens.expect(TokenType.NUMBER);
        Token third = tokens.consume();
        step = Integer.parseInt(third.getValue());

        if (step == 0) {
            throw new RuntimeException("range() step cannot be zero");
        }

        tokens.expect(TokenType.RPAREN);
        tokens.consume();

        return new RangeNode(rangeTok, start, end, step);
    }
}
