package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Interpreter.Environment;

public class PrintNode extends Node{
    private final Node expression;

    public PrintNode(Token token, Node expression ) {
        super(token);
        this.expression = expression;
        addChild(expression);
    }

    public Node getExpression() {
        return expression;
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        Object value = expression.evaluate(env);
        System.out.println(value);
        return value;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +" printNode ("  + token.getValue()+")");
        expression.printTree(indent + " ");
    }
}
