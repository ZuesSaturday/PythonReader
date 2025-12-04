package DAROARA.Saturday.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of statements (e.g., the body of an if, for, or function).
 */
public class BlockNode extends Node {

    private final List<Node> statements;

    public BlockNode() {
        super(null);
        this.statements = new ArrayList<>();
    }

    /**
     * Adds a statement to this block.
     *
     * @param statement The statement node to add
     */
    public void addStatement(Node statement) {
        if (statement != null) {
            statements.add(statement);
            addChild(statement);
        }
    }

    public List<Node> getStatements() {
        return statements;
    }

    /**
     * Evaluates all statements in the block in a new local environment.
     *
     * @param env The parent environment
     * @return The result of the last statement in the block, or null if empty
     */
    @Override
    public Object evaluate(Environment env) {
        Environment localEnv = new Environment(env);
        Object result = null;
        for (Node stmt : statements) {
            result = stmt.evaluate(localEnv);
        }
        return result;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "BlockNode:");
        for (Node stmt : statements) {
            stmt.printTree(indent + "  ");
        }
    }
}
