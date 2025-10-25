package Saturday.Interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment class stores variables and their values
 * during program execution.
 */

public class Environment {

    private Map<String, Object> variables;

    public Environment() {
        this.variables = new HashMap<>();
    }
    /**
     * Store or update a variable
     * */
    public void set(String name, Object value) {
        variables.put(name, value);
    }
    public Object get(String name) {
        if (!exists(name)) {
            throw new RuntimeException("Undefined variable: +" + name);
        }
        return variables.get(name);
    }

    public boolean exists(String name) {
        return variables.containsKey(name);
    }

    public void remove(String name) {
        variables.remove(name);
    }

    public void debug() {
        System.out.println("Environment:");
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            System.out.println(" " + entry.getKey() + " = " + entry.getValue());
        }
    }
}
