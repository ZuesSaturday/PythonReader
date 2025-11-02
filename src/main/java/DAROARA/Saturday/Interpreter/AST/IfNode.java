package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class IfNode extends Node{
    private final Node con;

    public IfNode(Token token, Node conditional) {
        super(token);
        this.con = conditional;
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
