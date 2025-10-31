package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class StringNode extends Node{
    private final String str;

    public StringNode(Token token) {
        super(token);
        this.str = token.getValue();
    }

    @Override
    public Object evaluate(Environment env) {
        return str;
    }
}
