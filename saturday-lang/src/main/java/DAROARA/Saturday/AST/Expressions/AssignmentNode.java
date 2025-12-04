package DAROARA.Saturday.AST.Expressions;

import DAROARA.Saturday.AST.Node;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

/**
 * Represents an assignment statement in the AST.
 * <p>
 * Example:
 * <pre>
 *     x = 5
 *     y = x + 2
 * </pre>
 * The left-hand side (identifier) is assigned the evaluated value of the right-hand side (value node).
 */
public class AssignmentNode extends Node {

    /** The variable being assigned to (left-hand side) */
    private final Node identifier;

    /** The expression or value being assigned (right-hand side) */
    private final Node value;

    /**
     * Creates a new AssignmentNode.
     *
     * @param token      The token representing the assignment operator ('=')
     * @param identifier The left-hand side node (usually an IdentifierNode)
     * @param value      The right-hand side node (expression or literal)
     */
    public AssignmentNode(Token token, Node identifier, Node value) {
        super(token);
        this.identifier = identifier;
        this.value = value;

        // Add children for tree traversal / printing
        addChild(identifier);
        addChild(value);
    }

    /**
     * Returns the left-hand side identifier node.
     *
     * @return the identifier node
     */
    public Node getIdentifier() {
        return identifier;
    }

    public Node getValue() {
        return value;
    }

    /**
     * Evaluates the assignment node.
     * <p>
     * Steps:
     * <ol>
     *     <li>Evaluates the value node.</li>
     *     <li>Stores the result in the environment under the identifier's name.</li>
     *     <li>Returns the assigned value.</li>
     * </ol>
     *
     * @param env The execution environment storing variable values.
     * @return The value assigned to the variable.
     * @throws RuntimeException if the identifier is invalid or evaluation fails.
     */
    @Override
    public Object evaluate(Environment env) {
        Object val = value.evaluate(env);

        // Get variable name from identifier token
        String varName = identifier.getToken().getValue();

        // Store value in environment
        env.set(varName, val);

        return val;
    }

    /**
     * Prints the assignment node and its children for debugging or tree visualization.
     *
     * @param indent Indentation string for pretty-printing the tree
     */
    @Override
    public void printTree(String indent) {
        System.out.println(indent + "AssignmentNode (" + token.getValue() + ")");
        identifier.printTree(indent + "  ");
        value.printTree(indent + "  ");
    }
}
