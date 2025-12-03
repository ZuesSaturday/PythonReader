package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.AST.Factory.ExpressionFactory;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

/**
 * Parses expressions from tokens into ExpressionNodes.
 */
public class ExpressionParser {
    private final TokenStream tokens;

    public ExpressionParser(TokenStream tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses an expression starting with the first token.
     *
     * @param firstValue The first token of the expression
     * @return ExpressionNode representing the parsed expression
     */
    public Node parseExpression(Token firstValue) {
        // Start with the first token
        StringBuilder expr = new StringBuilder(firstValue.getValue());

        // Keep consuming operators and numbers/identifiers
        while (tokens.peek().getType() == TokenType.OPERATOR ||
                tokens.peek().getType() == TokenType.COMOP) {

            // Consume operator
            Token op = tokens.consume();
            expr.append(op.getValue());

            // Consume the next number or identifier
            Token next = tokens.consume();
            if (next.getType() != TokenType.NUMBER &&
                    next.getType() != TokenType.IDENTIFIER&&
                    next.getType() != TokenType.STRING) {

                throw new RuntimeException("Expected number or identifier or String after operator, got " + next);
            }
            expr.append(next.getValue());

        }

        // Pass the full expression string to your ExpressionNode
        return ExpressionFactory.create(expr.toString());
    }
}
