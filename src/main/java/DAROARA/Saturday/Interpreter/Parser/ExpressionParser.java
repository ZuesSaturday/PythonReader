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

        if (tokens.peek().getType() == TokenType.COMOP || tokens.peek().getType() == TokenType.OPERATOR) {
            Token op = tokens.consume();
            Node right = parsePrimary();
            return new ExpressionNode(op, left, right);
        }
        return left;
    }

    public Node parsePrimary() {
        Token token = tokens.consume();
        return switch (token.getType()) {
            case NUMBER -> new LiteralNode(token);
            case IDENTIFIER -> new IdentifierNode(token);
            case STRING -> new StringNode(token);
            default -> throw new RuntimeException("Unexpected token: " + token);
        };
    }
}
