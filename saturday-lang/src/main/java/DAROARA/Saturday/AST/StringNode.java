package DAROARA.Saturday.AST;

import DAROARA.Saturday.AST.Expressions.ListNode;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a string literal in the AST.
 * <p>
 * Supports evaluation, indexing, and splitting into lists similar to Python strings.
 * Example:
 * <pre>
 *     "hello world"
 *     "1,2,3"
 * </pre>
 */
public class StringNode extends Node {

    /** The string value stored by this node (without quotes) */
    private final String str;

    /**
     * Creates a new StringNode from a token.
     *
     * @param token The token representing the string literal
     */
    public StringNode(Token token) {
        super(token);
        this.str = token.getValue().replaceAll("^\"|\"$", "");
    }

    /**
     * Splits the string by the given delimiter and returns a ListNode.
     *
     * @param value The delimiter string
     * @return A ListNode containing the split parts
     */
    public ListNode split(String value) {
        String regex = java.util.regex.Pattern.quote(value);
        String[] parts = str.split(regex);
        String listStr = "[" + String.join(",", parts) + "]";
        return new ListNode(listStr);
    }

    /**
     * Returns the string value stored in this node.
     *
     * @param env The environment (not used for literals)
     * @return The string value
     */
    @Override
    public Object evaluate(Environment env) {
        return str;
    }

    /**
     * Returns a character at a specific index in the string.
     *
     * @param index The index of the character
     * @return The character as a string
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Object getIndex(int index) {
        if (index < 0 || index >= str.length()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        return String.valueOf(str.charAt(index));
    }

    /**
     * Converts the string into a list of values separated by commas.
     * <p>
     * Numbers are parsed as integers, quoted strings are stripped of quotes, and
     * other tokens are returned as strings.
     *
     * @return A list of objects representing the string components
     */
    public List<Object> getListValues() {
        String[] parts = str.split("\\s*,\\s*");
        List<Object> values = new ArrayList<>();
        for (String part : parts) {
            if (part.matches("\\d+")) {
                values.add(Integer.parseInt(part));
            } else if (part.matches("\"[^\"]*\"|'[^']*'")) {
                values.add(part.substring(1, part.length() - 1));
            } else {
                values.add(part);
            }
        }
        return values;
    }

    /**
     * Prints the string node for AST visualization.
     *
     * @param indent Indentation for pretty-printing
     */
    @Override
    public void printTree(String indent) {
        System.out.println(indent + "StringNode: \"" + str + "\"");
    }
}
