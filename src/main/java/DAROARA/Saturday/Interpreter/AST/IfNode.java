package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class IfNode extends Node{
    private final String condition;
    private final List<Node> body;
    private final List<Node> elseBody;

    public IfNode(String condition,List<Node> body,List<Node> elseBody) {
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
    }
    private String getCondition(){
        return condition;
    }

    public List<Node> getBody() {
        return body;
    }

    public List<Node> getElseBody() {
        return elseBody;
    }

    @Override
    public String toString() {
        return "IfNode(condition=" + condition + ", body=" + body + ", elseBody=" + elseBody + ")";
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
