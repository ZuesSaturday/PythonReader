package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class MembershipNode extends Node{
    private final Node element;
    private final Node iterable;

    public MembershipNode(Node element, Node iterable, Token token){
        super(token);
        this.element = element;
        this.iterable = iterable;
    }

    @Override
    public Object evaluate(Environment env) {
        Object elemVal = element.evaluate(env);
        Object iterVal = iterable.evaluate(env);

        if (iterVal instanceof Iterable<?> iter) {
            for (Object item: iter){
                if (item.equals(elemVal)) {
                    return true;
                }
            }
            return false;
        }
        if (iterVal instanceof String str) {
            return str.contains(elemVal.toString());
        }
        throw new RuntimeException("Right side of 'in' is not iterable.");
    }
}
