package DAROARA.Saturday.AST;

import DAROARA.Saturday.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class UnaryOpNode extends Node{
    public UnaryOpNode(Token token) {
        super(token);
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
