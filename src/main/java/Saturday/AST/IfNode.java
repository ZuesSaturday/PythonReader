package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Interpreter.Environment;

public class IfNode extends Node{
    public IfNode(Token token) {
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
