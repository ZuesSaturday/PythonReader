package DAROARA.Saturday.AST.Statements;

import DAROARA.Saturday.AST.Node;
import DAROARA.Saturday.Error.NameError;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

/**
 * Represents an identifier (variable) in the AST.
 * <p>
 * Example:
 * <pre>
 *     x
 *     my_var
 * </pre>
 * This node holds the name of the variable and retrieves its value from the environment during evaluation.
 */
public class IdentifierNode extends Node {

    /** The name of the variable represented by this node */
    private final String name;

    /**
     * Creates a new IdentifierNode from a token.
     *
     * @param identifier The token representing the variable name
     */
    public IdentifierNode(Token identifier) {
        super(identifier);
        this.name = token.getValue();
    }

    /**
     * Returns the name of the identifier.
     *
     * @return The variable name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Evaluates the identifier by retrieving its value from the given environment.
     *
     * @param env The execution environment storing variable values
     * @return The value associated with this variable in the environment
     * @throws RuntimeException if the variable is undefined
     */
    @Override
    public Object evaluate(Environment env) {
        Object value = env.get(name);
        if (value == null) {
            throw new NameError("Undefined variable: " + name);
        }
        return value;
    }

    /**
     * Prints this node for debugging or AST visualization.
     *
     * @param indent Indentation string for tree formatting
     */
    @Override
    public void printTree(String indent) {
        System.out.println(indent + "IdentifierNode (" + name + ")");
    }
}
