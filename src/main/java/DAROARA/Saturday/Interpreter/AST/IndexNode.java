package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class IndexNode extends Node{

    private final Node container;
    private final List<String> indices;

    public IndexNode(Token token, Node container) {
        super(token);
        this.container = container;
        this.indices = parseIndices(token.getValue());
        addChild(container);
    }

    private List<String> parseIndices(String bracketContent) {
        String content = bracketContent.replaceAll("^\\[|\\]$","");
        String[] parts = content.split(":");
        List<String> res = new ArrayList<>();
        for (String p : parts) {
            res.add(p.trim());
        }
        return res;
    }
    @Override
    public Object evaluate(Environment env) {
        Object value = container.evaluate(env);
        for (String idxStr : indices){
            if (!(value instanceof List<?>list)) {
                throw new RuntimeException("Cannot index non-list-type " + value);
            }
            int idx;
            if (idxStr.matches("\\d+")) {
                idx = Integer.parseInt(idxStr);
            } else if (env.exists(idxStr)) {
                idx = (int) env.get(idxStr);
            }else {
                throw new RuntimeException("Invalid index: " + idxStr + " (not a number or variable)");        }
            value = list.get(idx);
        }
        return value;
    }

    public String toString() {
        return "ListNode: " + indices +"\n " +container;
    }

    public List<String> getIndices() {
        return indices;
    }
}
