package DAROARA.Saturday.AST;

import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an indexing operation in the AST, similar to Python's list or string indexing.
 * <p>
 * Examples:
 * <pre>
 *     arr[2]
 *     arr[i]
 *     myList[1:3]  // currently supports simple colon-separated indices
 *     "hello"[1]
 * </pre>
 * Supports multi-level indexing (e.g., arr[2][0]).
 */
public class IndexNode extends Node {

    /** The container node being indexed (list, string, etc.) */
    private final Node container;

    /** The list of index expressions (as strings) parsed from the brackets */
    private final List<String> indices;

    /**
     * Creates a new IndexNode.
     *
     * @param container The node representing the container being indexed
     * @param index     The string inside the brackets (e.g., "2", "i", "1:3")
     */
    public IndexNode(Node container, String index) {
        this.container = container;
        this.indices = parseIndices(index);
        addChild(container);
    }

    /**
     * Parses the string inside brackets into individual index strings.
     * <p>
     * Currently splits on ":" for slice-like access.
     *
     * @param bracketContent The raw string inside brackets
     * @return List of index strings
     */
    private List<String> parseIndices(String bracketContent) {
        String content = bracketContent.replaceAll("^\\[|\\]$", "");
        String[] parts = content.split(":");
        List<String> res = new ArrayList<>();
        for (String p : parts) {
            res.add(p.trim());
        }
        return res;
    }

    /**
     * Evaluates the index operation.
     * <p>
     * Supports:
     * <ul>
     *     <li>Lists: list.get(index)</li>
     *     <li>Strings: str.charAt(index)</li>
     *     <li>Index can be an integer literal or an integer variable from the environment</li>
     * </ul>
     *
     * @param env The environment storing variable values
     * @return The value retrieved by applying the indices
     * @throws RuntimeException if the container is not indexable or index is invalid
     */
    @Override
    public Object evaluate(Environment env) {
        Object value = container.evaluate(env);

        for (String idxStr : indices) {
            int idx;

            if (idxStr.matches("\\d+")) {
                idx = Integer.parseInt(idxStr);
            } else if (env.exists(idxStr)) {
                Object v = env.get(idxStr);
                if (v instanceof Integer i) {
                    idx = i;
                } else {
                    throw new RuntimeException("Index variable is not an integer: " + idxStr);
                }
            } else {
                throw new RuntimeException("Invalid index: " + idxStr + " (not a number or variable)");
            }

            if (value instanceof List<?> list) {
                value = list.get(idx);
            } else if (value instanceof String str) {
                if (idx < 0 || idx >= str.length()) {
                    throw new IndexOutOfBoundsException("String index out of range: " + idx);
                }
                value = String.valueOf(str.charAt(idx));
            } else {
                throw new RuntimeException("Cannot index non-list/non-string type: " + value);
            }
        }

        return value;
    }

    /**
     * Returns a string representation of this node for debugging.
     *
     * @return String describing the container and indices
     */
    @Override
    public String toString() {
        return "IndexNode: " + indices + "\n " + container;
    }

    /**
     * Returns the list of index strings for inspection.
     *
     * @return List of indices
     */
    public List<String> getIndices() {
        return indices;
    }
}
