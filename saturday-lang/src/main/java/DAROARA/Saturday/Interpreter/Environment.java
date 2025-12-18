package DAROARA.Saturday.Interpreter;

import DAROARA.Saturday.Error.NameError;
import DAROARA.Saturday.Interpreter.Runtime.RuntimeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Environment class stores variables and their values
 * during program execution.
 */

public class Environment {

    private final Map<String, Object> variables;
    private final Set<String> constants;
    private final Environment parent;
    private final Map<String, Function<RuntimeValue[], RuntimeValue>> functions = new HashMap<>();


    public Environment() {
        this(null);
    }
    public Environment(Environment parent) {
        this.variables = new HashMap<>();
        this.constants = new HashSet<>();
        this.parent = parent;
    }
    /**
     * Store or update a variable
     * */
    public void set(String name, Object value) {
        if (constants.contains(name)) {
            throw  new RuntimeException("Cannot modify constant: " + name);
        }
        if (variables.containsKey(name) || parent == null) {
            variables.put(name,value);
        }else {
            parent.set(name,value);
        }

    }

    public void defineConstant(String name, Object value) {
        if (variables.containsKey(name)) {
            throw  new RuntimeException("Variable already exists: "+name);
        }
        variables.put(name,value);
        constants.add(name);
    }
    public Object get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (parent != null) {
            return parent.get(name);
        }
        throw new NameError("Undefined variable: "+name);
    }

    public boolean exists(String name) {
        if (variables.containsKey(name))return true;
        return parent != null && parent.exists(name);
    }

    public void remove(String name) {
        if (constants.contains(name)){
            throw new RuntimeException("Cannot remove constant: "+name);
        }
        variables.remove(name);
    }

    public String dump() {
        StringBuilder sb = new StringBuilder("Environment:\n");
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            sb.append(" " ).append(entry.getKey()).append( " = ").append(entry.getValue());
        }
        if (parent != null) {
            sb.append("Parent -> \n").append(parent.dump());
        }
        return sb.toString();
    }

    public void debug() {
        System.out.println("Environment:");
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            System.out.println(" " + entry.getKey() + " = " + entry.getValue());
        }
    }

    public void defineFunction(String name, Function<RuntimeValue[], RuntimeValue> fn) {
        this.functions.put(name, fn);
    }

    public Object getFunction(String name) {
        if (functions.containsKey(name)) return functions.get(name);
        if (parent != null) return parent.getFunction(name);
        throw new RuntimeException("Undefined function: " + name);
    }

}
