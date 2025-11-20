package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the root of a program in the AST.
 * <p>
 * Contains a list of statements that make up the program.
 * Evaluating this node executes each statement in sequence.
 */
public class ProgramNode extends Node {

    /** List of statements in the program */
    private final List<Node> statements;

    /**
     * Creates a new ProgramNode with an empty list of statements.
     */
    public ProgramNode() {
        super(null);
        this.statements = new ArrayList<>();
    }

    /**
     * Adds a statement to the program.
     *
     * @param statement The statement node to add
     */
    public void addStatement(Node statement) {
        if (statement != null) {
            statements.add(statement);
            addChild(statement); // add to AST child list for traversal
        }
    }

    /**
     * Returns the list of statements in the program.
     *
     * @return List of statement nodes
     */
    public List<Node> getStatements() {
        return statements;
    }

    /**
     * Evaluates the program node by sequentially evaluating each statement.
     *
     * @param env The environment in which to execute the program
     * @return The result of the last executed statement, or null if no statements
     */
    @Override
    public Object evaluate(Environment env) {
        Object result = null;
        for (Node statement : statements) {
            result = statement.evaluate(env);
        }
        return result;
    }

    /**
     * Prints the AST representation of the program node and its statements.
     *
     * @param indent Indentation string for pretty-printing
     */
    @Override
    public void printTree(String indent) {
        System.out.println(indent + "ProgramNode:");
        for (Node statement : statements) {
            statement.printTree(indent + "  ");
        }
    }
}
