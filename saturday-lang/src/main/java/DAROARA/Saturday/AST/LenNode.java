package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class LenNode extends Node{
    private Node iter;
    public LenNode(Token LenToken, Node iter){
        super(LenToken);
        this.iter = iter;
        addChild(iter);
    }

    @Override
    public Object evaluate(Environment env) {
        Object o = iter.evaluate(env);

        if (o instanceof StringNode) {
            return ((StringNode) o).getListValues().size();
        }
        if (o instanceof ListNode) {
            return ((ListNode) o).getListValues().size();
        }
        throw new RuntimeException("len is not supported for value: "+o);
    }
}
