package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.regex.Pattern;

public class ExpressionParser {
    private final TokenStream tokens;

    public ExpressionParser(TokenStream tokens) {
        this.tokens = tokens;
    }

    public Node parseExpression() {
        Node left = parsePrimary();

        while (tokens.peek().getType() == TokenType.COMOP || tokens.peek().getType() == TokenType.OPERATOR) {
            Token op = tokens.consume();
            Node right = parsePrimary();
            left = new ExpressionNode(op, left, right);
        }
        while (tokens.peek().getType() == TokenType.INDEXING) {
            Token code = tokens.consume();
            left = new IndexNode(code,left);
        }
        return left;
    }

    public Node parsePrimary() {
        Token token = tokens.consume();
        return switch (token.getType()) {
            case NUMBER -> new LiteralNode(token);
            case IDENTIFIER -> new IdentifierNode(token);
            case STRING -> new StringNode(token);
            case LIST -> new ListNode(token);
            default -> throw new RuntimeException("Unexpected token: " + token);
        };
    }
}
