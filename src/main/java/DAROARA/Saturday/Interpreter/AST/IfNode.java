package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class IfNode extends Node{
    private final Node condition;
    private final List<Node> body;

    public IfNode(Node condition,List<Node> body) {
        this.condition = condition;
        this.body = body;
        addChild(condition);
    }
    private Node getCondition(){
        return condition;
    }

    public List<Node> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "IfNode(condition=" + condition + ", body=" + body + ")";
    }
    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return null;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"IfNode ("+token.getValue()+")");
        condition.printTree(indent+" ");
    }
}
