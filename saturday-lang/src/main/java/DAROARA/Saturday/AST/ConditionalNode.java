package DAROARA.Saturday.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class ConditionalNode extends Node{
    private final List<Node> conditionals;

    public ConditionalNode() {
        super(null);
        this.conditionals = new ArrayList<>();
    }

    public void addConditional(Node condition){
        if (condition!=null){
            conditionals.add(condition);
        }
    }

    public List<Node> getConditionals() {
        return conditionals;
    }

    @Override
    public Object evaluate(Environment env) {
        return null;
    }
}
