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
        if (isTruthy(cond)){
            Object result = null;
            Environment localEnv = new Environment(env);
            for (Node st:body){
                result = st.evaluate(localEnv);
            }
            return result;
        }
        return null;
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Number) return ((Number) value).doubleValue() != 0;
        return true;
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
