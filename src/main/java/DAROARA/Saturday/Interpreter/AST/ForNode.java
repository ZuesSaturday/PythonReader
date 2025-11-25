package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class ForNode extends Node{

    private final Node variable;
    private final Node sequence;
    private final BlockNode insideBlock;

    public ForNode(Token token, Node variable,Node sequence,BlockNode insideBlock) {
        super(token);
        this.variable = variable;
        this.sequence = sequence;
        this.insideBlock = insideBlock;

        addChild(variable);
        addChild(sequence);
        addChild(insideBlock);
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {

        Object seqValue = sequence.evaluate(env);

        String varName = variable.getToken().getValue();

        if (seqValue instanceof List listNode) {
            for (Object item : listNode) {
                env.set(varName,item);
                insideBlock.evaluate(env);
            }
            return null;
        }
        else if (seqValue instanceof String) {
            String[] parts = ((String) seqValue).split("");
            for (Object item : parts) {
                env.set(varName,item);
                insideBlock.evaluate(env);
            }
            return null;
        }
        else if (seqValue instanceof RangeNode range) {
            for (int value : range) {
                env.set(varName,value);
                insideBlock.evaluate(env);
            }
            return null;
        }
        throw new TypeError("'"+seqValue+"'"+" object is not iterable");
    }
}
