package Saturday.AST;

import Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends Node{
    private final List<Node> statements;

    public ProgramNode() {
        super(null);
        this.statements = new ArrayList<>();
    }

    public void addStatement(Node statement) {
        if (statement != null){
            statements.add(statement);
            addChild(statement);
        }
    }

    public List<Node> getStatements() {
        return statements;
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        Object lastValue = null;
        for (Node statement : statements) {
            lastValue = statement.evaluate(env);
        }
        return lastValue;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "ProgramNode:");
        for (Node statement : statements) {
            statement.printTree(indent +" ");
        }
    }
}
