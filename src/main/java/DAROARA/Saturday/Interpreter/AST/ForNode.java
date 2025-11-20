package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class ForNode extends Node{

    private final Node condition;
    private final BlockNode insideBlock;
    private final RangeNode rangeNode;

    public ForNode(Token token, Node condition,RangeNode rangeNode,BlockNode insideBlock) {
        super(token);
        this.condition = condition;
        this.insideBlock = insideBlock;
        this.rangeNode = rangeNode;
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return null;
    }
}
