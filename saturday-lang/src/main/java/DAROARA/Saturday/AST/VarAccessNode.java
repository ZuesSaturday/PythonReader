package DAROARA.Saturday.AST;

import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class VarAccessNode extends Node {

    private final String varName;

    public VarAccessNode(Token token) {
        super(token);
        this.varName = token.getValue(); // identifier name
    }

    @Override
    public Object evaluate(Environment env) {
        Object value = env.get(varName);
        if (value == null) {
            throw new RuntimeException("Undefined variable: " + varName);
        }
        return value;
    }
}
