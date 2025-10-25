package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Compiler.TokenType;
import Saturday.Interpreter.Environment;

public class BinaryOpNode extends Node{

    public BinaryOpNode(Token token) {
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
