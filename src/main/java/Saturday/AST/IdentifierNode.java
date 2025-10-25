package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Interpreter.Environment;

public class IdentifierNode extends Node{

    private  final String name;

    public IdentifierNode(Token identifier) {
        super(identifier);
        this.name = token.getValue();
    }

    public String getName() {
        return name;
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return env.get(name);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "IdentifierNode ("+name+")");
    }
}
