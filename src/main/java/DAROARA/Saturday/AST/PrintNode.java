package DAROARA.Saturday.AST;

import DAROARA.Saturday.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

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
        System.out.println("printValue = "+value);
        return value;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"PrintNode ("  +token.getValue()+")");
        expression.printTree(indent + " ");
    }
}
