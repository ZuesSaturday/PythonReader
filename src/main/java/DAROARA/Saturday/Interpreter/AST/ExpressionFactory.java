package DAROARA.Saturday.Interpreter.AST;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpressionFactory creates the appropriate expression node based on the input.
 * Automatically detects whether it's a numeric expression or string expression.
 */
public class ExpressionFactory {

    /**
     * Creates the correct ExpressionNode depending on data type.
     * Supports:
     *   - Numeric expressions: arithmetic and comparison
     *   - String expressions: concatenation and repetition
     *
     * @param expression the expression string to parse
     * @return either ExpressionNode or StringExpressionNode
     */
    public static Node create(String expression) {
        List<String> tokens = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        // First, detect if this is likely a string expression
        if (isStringExpression(expression)) {
            parseStringExpression(expression, tokens, operators);
            return new StringExpressionNode(tokens, operators);
        } else {
            parseNumericExpression(expression, tokens, operators);
            return new ExpressionNode(tokens, operators);
        }
    }

    /**
     * Detects if the expression contains string operations or string literals
     */
    private static boolean isStringExpression(String expr) {
        return expr.contains("\"") || expr.contains("'") ||
                (expr.contains("+") && hasAdjacentStrings(expr)) ||
                (expr.contains("*") && hasStringMultiplication(expr));
    }

    private static boolean hasAdjacentStrings(String expr) {
        // Simple heuristic: if we have quoted strings with + between them
        return expr.matches(".*[\"'][ \\t]*\\+[ \\t]*[\"'].*");
    }

    private static boolean hasStringMultiplication(String expr) {
        // Heuristic: if we have quoted string with * and number, or vice versa
        return expr.matches(".*[\"'][ \\t]*\\*[ \\t]*\\d+.*") ||
                expr.matches(".*\\d+[ \\t]*\\*[ \\t]*[\"'].*");
    }

    /**
     * Parse numeric expressions with arithmetic and comparison operators
     */
    private static void parseNumericExpression(String expr, List<String> tokens, List<String> operators) {
        expr = expr.replaceAll("\\s+", "");
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            // Check 2-character operator
            if (i + 1 < expr.length()) {
                String twoChar = "" + expr.charAt(i) + expr.charAt(i + 1);
                if (twoChar.equals("==") || twoChar.equals("!=")) {
                    addToken(current, tokens);
                    operators.add(twoChar);
                    i++;
                    continue;
                }
            }

            char c = expr.charAt(i);

            // Check 1-character operators
            if ("+-*/<>".indexOf(c) >= 0) {
                addToken(current, tokens);
                operators.add(String.valueOf(c));
            } else {
                current.append(c);
            }
        }

        addToken(current, tokens);
    }

    /**
     * Parse string expressions with concatenation and repetition operators
     */
    private static void parseStringExpression(String expr, List<String> tokens, List<String> operators) {
        expr = expr.replaceAll("\\s+", "");
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = '"';

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            // Handle quotes for string literals
            if (c == '"' || c == '\'') {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteChar = c;
                    current.append(c);
                } else if (c == quoteChar) {
                    inQuotes = false;
                    current.append(c);
                    addToken(current, tokens);
                } else {
                    current.append(c);
                }
                continue;
            }

            // If we're inside quotes, just add the character
            if (inQuotes) {
                current.append(c);
                continue;
            }

            // Check for operators outside quotes
            if (c == '+' || c == '*') {
                addToken(current, tokens);
                operators.add(String.valueOf(c));
            } else {
                current.append(c);
            }
        }

        // Add any remaining token
        addToken(current, tokens);
    }

    private static void addToken(StringBuilder current, List<String> tokens) {
        if (current.length() > 0) {
            tokens.add(current.toString());
            current.setLength(0);
        }
    }

    /**
     * Test method to demonstrate the factory
     */
    public static void main(String[] args) {
        // Test numeric expressions
        Node numExpr = ExpressionFactory.create("1 + 2 * 3");
        numExpr.printTree("");

        // Test string expressions
        Node strExpr = ExpressionFactory.create("\"hello\" + \"world\" * 3");
        strExpr.printTree("");

        // Test comparison expressions
        Node compExpr = ExpressionFactory.create("x > 5 + 2");
        compExpr.printTree("");
    }
}