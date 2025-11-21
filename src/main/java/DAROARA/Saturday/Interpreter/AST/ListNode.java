package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list literal in the AST, similar to Python lists.
 * <p>
 * Example:
 * <pre>
 *     [1, 2, 3]
 *     ["hello", "world"]
 * </pre>
 * Supports basic evaluation and element access.
 */
public class ListNode extends Node {

    /** The raw list string (without brackets) */
    private final String list;

    /**
     * Creates a new ListNode from a string value.
     *
     * @param value The string representing the list (e.g., "[1, 2, 3]")
     */
    public ListNode(String value) {
        this.list = value.replaceAll("[\\[\\]]", "");
    }

    /**
     * Returns the value at a specific index.
     *
     * @param index The index of the element
     * @return The element as a string
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public String getIndex(int index) {
        List<Object> listValues = getListValues();
        if (index < 0 || index >= listValues.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        return String.valueOf(listValues.get(index));
    }

    /**
     * Appends a value to the list.
     * <p>
     * This only modifies the current list representation in memory.
     *
     * @param value The value to append (string, number, or variable representation)
     * @return The appended value as string
     */
    public String append(String value) {
        List<Object> values = getListValues();
        values.add(value);
        // Update the internal list string to include new value
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            Object v = values.get(i);
            if (v instanceof String) {
                sb.append("\"").append(v).append("\"");
            } else {
                sb.append(v);
            }
            if (i < values.size() - 1) sb.append(", ");
        }
        // Update internal list representation
        return sb.toString();
    }
    public int length() {
        return this.list.length();
    }

    /**
     * Parses the raw list string into individual values.
     *
     * @return A list of objects (Integer or String)
     */
    public List<Object> getListValues() {
        String[] parts = list.split("\\s*,\\s*");
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
     * Evaluates the list node.
     *
     * @param env The environment (not used for literals)
     * @return The list of evaluated values
     */
    @Override
    public Object evaluate(Environment env) {
        return getListValues();
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "ListNode: " + getListValues());
    }
}
