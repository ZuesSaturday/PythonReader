package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class IfNode extends Node{
    private final Node condition;
    private final List<Node> body;

    public IfNode(Node condition,List<Node> body) {
        this.condition = condition;
        this.body = body;

    }
    private Node getCondition(){
        return condition;
    }

    public List<Node> getBody() {
        return body;
    }

//    public List<Node> getElseBody() {
//        return elseBody;
//    }

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
}
