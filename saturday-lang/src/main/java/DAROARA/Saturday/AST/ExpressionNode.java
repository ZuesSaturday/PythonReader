package DAROARA.Saturday.AST;

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

    public ExpressionNode(List<String> numbers, List<String> operators) {
        super(null);
        this.numbers = numbers;
        this.operators = operators;
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
                case "!=" -> left != right;
                default -> throw new RuntimeException("Unsupported operator: " + op);
            };
        }

        // STEP 5 — Final result (number)
        return values.get(0);
    }
}