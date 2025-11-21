package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class StringExpressionNode extends Node {

    private final String leftValue;      // StringValu or identifiers
    private final String rightValue;
    private final String comparer;    // arithmetic or comparison comparers

    public StringExpressionNode(String left,String comparer,String right) {
        super(null);
        this.leftValue = left;
        this.rightValue = right;
        this.comparer = comparer;
    }

    /**
     * Evaluate expression with proper arithmetic precedence and comparisons.
     */
    @Override
    public Object evaluate(Environment env) {
        Object left = leftValue;
        Object right = rightValue;

        return switch (comparer) {
            case "==" -> Objects.equals(left, right);
            case "!=" -> !Objects.equals(left, right);
            default   -> throw new RuntimeException("Unsupported operator: " + comparer);
        };
    }
}
