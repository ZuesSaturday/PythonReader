package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;

public class IfNode extends Node{
    private final Node condition;
    private final List<Node> body;

    public IfNode(Token ifToken, Node condition, List<Node> body) {
        super(ifToken);
        this.condition = condition;
        this.body = body;
        addChild(condition);
        for (Node stmt: body) addChild(stmt);
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
        Object cond = condition.evaluate(env);
        if (cond instanceof Boolean && (Boolean) cond){
            Object result = null;
            for (Node st:body){
                result = st.evaluate(env);
            }
            return result;
        }
        return null;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent +"IfNode");
        condition.printTree(indent+" ");
        for (Node stmt: body) {
            stmt.printTree(indent+" ");
        }
    }
}
