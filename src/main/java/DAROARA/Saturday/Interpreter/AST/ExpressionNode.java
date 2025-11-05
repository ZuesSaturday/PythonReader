package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class ExpressionNode extends Node{
    private final Node left;
    private final Node right;
    private final String operator;

    public ExpressionNode(Token opertotoken, Node left,Node right){
        super(opertotoken);
        this.left = left;
        this.right = right;
        this.operator = opertotoken.getValue();
        addChild(left);
        addChild(right);
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public Object evaluate(Environment env) {

        Object leftValue = left.evaluate(env);
        Object rightValue = right.evaluate(env);
        int v = 0;
        if (leftValue instanceof Number && rightValue instanceof Number) {
            float a = ((Number) leftValue).floatValue();
            float b = ((Number) rightValue).floatValue();

            switch (operator) {
                case "+" -> {
                    v = (int) (a + b);
                    System.out.println(v);
                    return v;
                }
                case "-" -> {
                    v = (int) (a - b);
                    return v;
                }
                case "/" -> {
                    v = (int) (a/b);
                    return v;
                }

                case "*" -> {
                    v = (int) (a*b);
                    return v;
                }

                default -> throw new RuntimeException("Unsupported operator");
            }
        }
        if (operator.equals("+")) {
            System.out.println(leftValue.toString() + rightValue.toString());
            return leftValue.toString() + rightValue.toString();
        }
        throw new RuntimeException("Invalid expression operands");
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"ExpressionNode (" +token.getValue() +")");
        left.printTree(indent+" ");
        right.printTree(indent+" ");
    }
}
