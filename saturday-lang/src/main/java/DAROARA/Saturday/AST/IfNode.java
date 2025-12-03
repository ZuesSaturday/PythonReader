package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class IfNode extends Node {

    private final Node condition;
    private final BlockNode thenBlock;
    private BlockNode elseBlock;  // optional

    public IfNode(Token ifToken, Node condition, BlockNode thenBlock) {
        super(ifToken);
        this.condition = condition;
        this.thenBlock = thenBlock;
        addChild(condition);
        addChild(thenBlock);
    }

    public void setElseBlock(BlockNode elseBlock) {
        this.elseBlock = elseBlock;
        addChild(elseBlock);
    }

    @Override
    public Object evaluate(Environment env) {
        Object condValue = condition.evaluate(env);
        boolean isTrue = isTruthy(condValue);

        if (isTrue) {
            return thenBlock.evaluate(env);
        } else if (elseBlock != null) {
            return elseBlock.evaluate(env);
        }

        return null;
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean b) return b;
        if (value instanceof Number n) return n.doubleValue() != 0;
        return true;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "IfNode:");
        System.out.println(indent + "  Condition:");
        condition.printTree(indent + "    ");
        System.out.println(indent + "  Then:");
        thenBlock.printTree(indent + "    ");
        if (elseBlock != null) {
            System.out.println(indent + "  Else:");
            elseBlock.printTree(indent + "    ");
        }
    }
}
