package Saturday.AST;

import Saturday.Compiler.Token;

import java.beans.Expression;

public class AssignmentNode extends Node{


    public AssignmentNode(Token token) {
        super(token);
    }

    /**
     * @param env
     * @param <Environment>
     * @return
     */
    @Override
    public <Environment> Object evaluate(Environment env) {
        return null;
    }
}
