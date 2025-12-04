package DAROARA.Saturday.Interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Builtins {

    private final Map<String, Function<RuntimeValue[], RuntimeValue>> functions = new HashMap<>();

    public Builtins(Environment env) {
        // len() builtin
        functions.put("len", args -> {
            if (args.length != 1)
                throw new RuntimeException("len() has one argument");

            RuntimeValue arg = args[0];

            if (arg instanceof RuntimeList list) {
                return new RuntimeNumber(list.size());
            }

            if (arg instanceof RuntimeString str) {
                return new RuntimeNumber(str.getValue().length());
            }

            throw new RuntimeException("len(): unsupported type " + arg.typeName());
        });
    }

    public RuntimeValue call(String name, RuntimeValue... args) {
        if (!functions.containsKey(name)) {
            throw new RuntimeException("Undefined builtin function: " + name);
        }
        return functions.get(name).apply(args);
    }
}
