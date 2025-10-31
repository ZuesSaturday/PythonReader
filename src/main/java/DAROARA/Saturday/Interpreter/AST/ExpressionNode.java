package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class ExpressionNode extends Node{
    private Node left;
    private Node right;
    private String operator;

    public ExpressionNode(Token opertotoken, Node left,Node right){
        super(opertotoken);
        this.left = left;
        this.right = right;
        this.operator = opertotoken.getValue();
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

        if (leftValue instanceof Number && rightValue instanceof Number) {
            double a = ((Number) leftValue).doubleValue();
            double b = ((Number) rightValue).doubleValue();

            return switch (operator) {
                case "+"  -> a + b;
                case "-" -> a-b;
                default -> throw new RuntimeException("Unsupported operator");
            };
        }
        if (operator.equals("+")) {
            return leftValue.toString() + rightValue.toString();
        }
        throw new RuntimeException("Invalid expression operands");
    }

    @Override
    public void printTree(String indent) {
        super.printTree(indent);
    }
}
