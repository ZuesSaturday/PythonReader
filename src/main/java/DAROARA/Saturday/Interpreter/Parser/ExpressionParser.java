package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.ExpressionNode;
import DAROARA.Saturday.Interpreter.AST.IdentifierNode;
import DAROARA.Saturday.Interpreter.AST.Node;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.ArrayList;
import java.util.List;

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
        List<String> numbers = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        numbers.add(firstValue.getValue());

        while (tokens.peek().getType() == TokenType.OPERATOR || tokens.peek().getType() == TokenType.COMOP) {
            Token op = tokens.consume();
            operators.add(op.getValue());

            Token next = tokens.consume(); // next number or identifier
            numbers.add(next.getValue());
        }

        return new ExpressionNode(numbers, operators);
    }
}
