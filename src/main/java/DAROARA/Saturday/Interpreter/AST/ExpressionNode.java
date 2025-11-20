package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpressionNode represents any arithmetic or comparison expression.
 * Supports:
 *  - Arithmetic: +, -, *, /
 *  - Comparison: <, >, ==
 *  - Variables
 *  - Full operator precedence
 *
 * Example:
 *   "1 + 2 * 3 > x"
 */
public class ExpressionNode extends Node {

    private final List<String> numbers;      // numbers or identifiers
    private final List<String> operators;    // arithmetic or comparison operators

    public ExpressionNode(String expression) {
        super(null);
        this.numbers = new ArrayList<>();
        this.operators = new ArrayList<>();
        parseExpression(expression);
    }

    /**
     * Parse the expression into tokens: numbers/identifiers + operators.
     * Handles 2-char comparison operators (==).
     */
    private void parseExpression(String expr) {
        expr = expr.replaceAll("\\s+", "");
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {

            // Check 2-character operator
            if (i + 1 < expr.length()) {
                String two = "" + expr.charAt(i) + expr.charAt(i + 1);
                if (two.equals("==")) {
                    addToken(current);
                    operators.add(two);
                    i++;
                    continue;
                }
            }

            char c = expr.charAt(i);

            // Check 1-character operators
            if ("+-*/<>".indexOf(c) >= 0) {
                addToken(current);
                operators.add(String.valueOf(c));
            } else {
                current.append(c);
            }
        }

        addToken(current);
    }

    private void addToken(StringBuilder current) {
        if (current.length() > 0) {
            numbers.add(current.toString());
            current.setLength(0);
        }
    }

    public List<String> getNumbers() { return numbers; }
    public List<String> getOperators() { return operators; }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "Expression:");
        System.out.println(indent + " Numbers:   " + numbers);
        System.out.println(indent + " Operators: " + operators);
    }

    /**
     * Evaluate expression with proper arithmetic precedence and comparisons.
     */
    @Override
    public Object evaluate(Environment env) {

        // STEP 1 — Convert identifiers into numeric values
        List<Double> values = new ArrayList<>();
        for (String token : numbers) {
            try {
                values.add(Double.parseDouble(token));
            } catch (NumberFormatException e) {
                Object v = env.get(token);
                if (v instanceof Number num) {
                    values.add(num.doubleValue());
                } else {
                    throw new RuntimeException("Undefined variable or non-numeric: " + token);
                }
            }
        }

        // STEP 2 — Handle * and / first
        for (int i = 0; i < operators.size(); i++) {
            String op = operators.get(i);
            if (op.equals("*") || op.equals("/")) {
                double a = values.get(i);
                double b = values.get(i + 1);

                double result = op.equals("*") ? a * b : a / b;

                values.set(i, result);
                values.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        // STEP 3 — Handle + and -
        for (int i = 0; i < operators.size(); i++) {
            String op = operators.get(i);
            if (op.equals("+") || op.equals("-")) {
                double a = values.get(i);
                double b = values.get(i + 1);

                double result = op.equals("+") ? a + b : a - b;

                values.set(i, result);
                values.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        // STEP 4 — Handle comparisons (<, >, ==)
        if (!operators.isEmpty()) {
            String op = operators.get(0);
            double left = values.get(0);
            double right = values.get(1);

            return switch (op) {
                case "<" -> left < right;
                case ">" -> left > right;
                case "==" -> left == right;
                default -> throw new RuntimeException("Unsupported operator: " + op);
            };
        }

        // STEP 5 — Final result (number)
        return values.get(0);
    }
}
