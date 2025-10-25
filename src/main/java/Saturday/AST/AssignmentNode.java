package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Interpreter.Environment;

public class AssignmentNode extends Node{
    private Node identifier;
    private Node expression;

    public AssignmentNode(Token token,Node identifier, Node expression) {
        super(token);
        this.identifier = identifier;
        this.expression = expression;

        addChild(identifier);
        addChild(expression);
    }
    public Node getIdentifier() {
        return identifier;
    }

    /**
     * @param env
     * @return
     */
    public Object evaluate(Environment env) {
        Object value = expression.evaluate(env);

        String varName = identifier.getToken().getValue();

        env.set(varName, value);

        return value;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"AsssignmentNode (" +token.getValue() +")");
        identifier.printTree(indent+" ");
        expression.printTree(indent+" ");
    }
}
