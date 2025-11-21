package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

public class ExpressionFactory {

    /**
     * Creates the correct ExpressionNode depending on data type.
     * Supports:
     *   numeric comparison
     *   numeric arithmetic
     *   string comparison
     *
     * @param left  the first value token
     * @param op    the operator token
     * @param right the second value token
     */
    public static Node create(Token left, Token op, Token right) {

        boolean leftIsString  = left.getType() == TokenType.STRING;
        boolean rightIsString = right.getType() == TokenType.STRING;

        boolean leftIsNumber  = left.getType() == TokenType.NUMBER;
        boolean rightIsNumber = right.getType() == TokenType.NUMBER;

        String operator = op.getValue();

        // --- STRING EXPRESSIONS ---
        if (leftIsString || rightIsString) {

            // Only strings allowed on both sides
            if (!(leftIsString && rightIsString)) {
                throw new RuntimeException("Cannot compare string with non-string");
            }

            if (!operator.equals("==") && !operator.equals("!=")) {
                throw new RuntimeException(operator + " not allowed for strings");
            }

            // Remove quotes for evaluation
            String l = left.getValue().replace("\"", "");
            String r = right.getValue().replace("\"", "");

            return new StringExpressionNode(l, operator, r);
        }

        // --- NUMERIC EXPRESSIONS ---
        if (leftIsNumber && rightIsNumber) {
            // Build a simple string expression "5 > 3"
            String expr = left.getValue() + operator + right.getValue();
            return new ExpressionNode(expr);
        }

        // --- IDENTIFIERS ---
        if (left.getType() == TokenType.IDENTIFIER || right.getType() == TokenType.IDENTIFIER) {
            // ExpressionNode can resolve identifiers
            String expr = left.getValue() + operator + right.getValue();
            return new ExpressionNode(expr);
        }

        throw new RuntimeException("Invalid expression");
    }
}
