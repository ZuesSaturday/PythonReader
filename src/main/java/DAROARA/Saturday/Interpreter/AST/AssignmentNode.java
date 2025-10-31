package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class AssignmentNode extends Node{
    private final Node identifier;
    private final Node value;

    public AssignmentNode(Token token,Node identifier, Node value) {
        super(token);
        this.identifier = identifier;
        this.value = value;

        addChild(identifier);
        addChild(value);
    }
    public Node getIdentifier() {
        return identifier;
    }

    /**
     * @param env
     * @return
     */
    public Object evaluate(Environment env) {
        Object val = value.evaluate(env);

        String varName = identifier.getToken().getValue();

        env.set(varName, val);

        return val;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"AssignmentNode (" +token.getValue() +")");
        identifier.printTree(indent+" ");
        value.printTree(indent+" ");
    }
}
