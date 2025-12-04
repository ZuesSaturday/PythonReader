package DAROARA.Saturday.AST;

import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all AST (Abstract Syntax Tree) nodes.
 * <p>
 * Each node may have a token representing the source code element and a list of child nodes.
 * Subclasses must implement the {@link #evaluate(Environment)} method to define behavior.
 */
public abstract class Node {

    /** The token associated with this AST node (may be null for synthetic nodes) */
    protected Token token;

    /** List of child nodes for tree structure and traversal */
    protected List<Node> children;

    /**
     * Creates a new Node with an associated token.
     *
     * @param token The token representing this node
     */
    public Node(Token token) {
        this.token = token;
        this.children = new ArrayList<>();
    }

    /**
     * Creates a new Node without an associated token.
     * Useful for synthetic or abstract nodes that do not correspond directly to a token.
     */
    protected Node() {
        this.children = new ArrayList<>();
    }

    /**
     * Returns the token associated with this node.
     *
     * @return The token, or null if none
     */
    public Token getToken() {
        return token;
    }

    /**
     * Returns the list of child nodes.
     *
     * @return List of child nodes
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this node.
     *
     * @param child The child node to add (ignored if null)
     */
    public void addChild(Node child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    /**
     * Prints this node and its children in a tree-like format for debugging.
     *
     * @param indent String used to indent child nodes (e.g., "  ")
     */
    public void printTree(String indent) {
        System.out.println(indent + getClass().getSimpleName() + ": " +
                (token != null ? token.getValue() : ""));
        for (Node child : children) {
            child.printTree(indent + "  ");
        }
    }

    /**
     * Evaluates this node in the given environment.
     * <p>
     * Subclasses must implement this method to define execution behavior.
     *
     * @param env The environment storing variables and context
     * @return The result of evaluating this node
     */
    public abstract Object evaluate(Environment env);
}
