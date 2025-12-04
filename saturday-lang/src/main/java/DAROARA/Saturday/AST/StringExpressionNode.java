package DAROARA.Saturday.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * StringExpressionNode represents string concatenation and repetition expressions.
 * Supports:
 *  - String concatenation: +
 *  - String repetition: *
 *  - Variables
 *  - Full operator precedence (* before +)
 *
 * Example:
 *   "hello" + "world" * 3
 */
public class StringExpressionNode extends Node {

    private final List<String> tokens;     // String values, variables, or numbers
    private final List<String> operators;  // + or *

    public StringExpressionNode(List<String> tokens, List<String> operators) {
        super(null);
        this.tokens = new ArrayList<>(tokens);
        this.operators = new ArrayList<>(operators);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "StringExpression:");
        System.out.println(indent + "  Tokens: " + tokens);
        System.out.println(indent + "  Operators: " + operators);
    }

    /**
     * Evaluate string expression with proper operator precedence.
     * Supports:
     * - String concatenation: +
     * - String repetition: *
     */
    @Override
    public Object evaluate(Environment env) {
        // First, evaluate all tokens (resolve variables, handle string literals)
        List<Object> values = new ArrayList<>();

        for (String token : tokens) {
            // Check if it's a string literal
            if ((token.startsWith("\"") && token.endsWith("\"")) ||
                    (token.startsWith("'") && token.endsWith("'"))) {
                // Remove quotes
                values.add(token.substring(1, token.length() - 1));
            }
            // Check if it's a number
            else if (token.matches("-?\\d+(\\.\\d+)?")) {
                values.add(Double.parseDouble(token));
            }
            // Otherwise, it's a variable - look it up in environment
            else {
                Object value = env.get(token);
                if (value == null) {
                    throw new RuntimeException("Undefined variable: " + token);
                }
                values.add(value);
            }
        }

        // Make copies for processing
        List<Object> workingValues = new ArrayList<>(values);
        List<String> workingOperators = new ArrayList<>(operators);

        // STEP 1 — Handle * (string repetition) first - higher precedence
        for (int i = 0; i < workingOperators.size(); i++) {
            String op = workingOperators.get(i);
            if (op.equals("*")) {
                Object left = workingValues.get(i);
                Object right = workingValues.get(i + 1);

                String result = performMultiplication(left, right);

                // Replace the operation with result
                workingValues.set(i, result);
                workingValues.remove(i + 1);
                workingOperators.remove(i);
                i--; // Adjust index after removal
            }
        }

        // STEP 2 — Handle + (string concatenation)
        for (int i = 0; i < workingOperators.size(); i++) {
            String op = workingOperators.get(i);
            if (op.equals("+")) {
                Object left = workingValues.get(i);
                Object right = workingValues.get(i + 1);

                String result = performConcatenation(left, right);

                // Replace the operation with result
                workingValues.set(i, result);
                workingValues.remove(i + 1);
                workingOperators.remove(i);
                i--; // Adjust index after removal
            }
        }

        // Final result
        if (workingValues.size() != 1) {
            throw new RuntimeException("Invalid expression evaluation");
        }

        return workingValues.get(0);
    }

    private String performMultiplication(Object left, Object right) {
        String stringPart;
        int repeatCount;

        // Determine which operand is string and which is number
        if (left instanceof String && right instanceof Number) {
            stringPart = (String) left;
            repeatCount = ((Number) right).intValue();
        } else if (left instanceof Number && right instanceof String) {
            stringPart = (String) right;
            repeatCount = ((Number) left).intValue();
        } else {
            throw new RuntimeException("Multiplication (*) requires one string and one number. Got: " +
                    left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
        }

        if (repeatCount < 0) {
            throw new RuntimeException("Cannot repeat string negative times: " + repeatCount);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < repeatCount; i++) {
            result.append(stringPart);
        }
        return result.toString();
    }

    private String performConcatenation(Object left, Object right) {
        String leftStr = convertToString(left);
        String rightStr = convertToString(right);
        return leftStr + rightStr;
    }

    private String convertToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Number) {
            return obj.toString();
        } else {
            return String.valueOf(obj);
        }
    }
}