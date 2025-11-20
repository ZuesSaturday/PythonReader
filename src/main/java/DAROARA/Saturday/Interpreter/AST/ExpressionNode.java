package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

/**
 * Represents an expression in the AST.
 * Supports arithmetic (+, -, *, /) and simple comparison (<, >, ==) operations.
 */
public class ExpressionNode extends Node {
    private final List<String> numbers;
    private final List<String> operators;

    /**
     * Constructs an ExpressionNode from parsed numbers and operators.
     *
     * @param numbers   List of numbers or variable names as strings
     * @param operators List of operators as strings (arithmetic or comparison)
     */
    public ExpressionNode(List<String> numbers, List<String> operators) {
        super(null);
        this.numbers = numbers;
        this.operators = operators;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public List<String> getOperators() {
        return operators;
    }

    /**
     * Evaluates the expression using the provided environment.
     * Currently supports:
     * - Single arithmetic operations between two numbers
     * - Single comparison operations between two numbers
     *
     * @param env The environment for variable lookup
     * @return The computed value (Number or Boolean)
     */
    @Override
    public Object evaluate(Environment env) {
        if (numbers.size() == 1 && operators.isEmpty()) {
            String token = numbers.get(0);
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException e) {
                Object val = env.get(token);
                if (val == null) throw new RuntimeException("Undefined variable: " + token);
                return val;
            }
        }

        if (numbers.size() == 2 && operators.size() == 1) {
            double left = parseValue(numbers.get(0), env);
            double right = parseValue(numbers.get(1), env);
            String op = operators.get(0);

            return switch (op) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                case ">" -> left > right;
                case "<" -> left < right;
                case "==" -> left == right;
                default -> throw new RuntimeException("Unsupported operator: " + op);
            };
        }

        throw new RuntimeException("Complex expressions not implemented: " + numbers + " " + operators);
    }

    private double parseValue(String token, Environment env) {
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            Object val = env.get(token);
            if (val instanceof Number num) {
                return num.doubleValue();
            } else {
                throw new RuntimeException("Cannot use non-numeric variable in expression: " + token);
            }
        }
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "Expression:");
        System.out.println(indent + "  Numbers: " + numbers);
        System.out.println(indent + "  Operators: " + operators);
    }
}
