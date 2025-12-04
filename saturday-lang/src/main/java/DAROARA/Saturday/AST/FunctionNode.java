package DAROARA.Saturday.AST;

import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class FunctionNode extends Node{
    public FunctionNode(Token token) {
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
