package DAROARA.Saturday.Interpreter.Runtime;

import java.util.List;

public class RuntimeList extends RuntimeValue {

    private final List<RuntimeValue> elements;

    public RuntimeList(List<RuntimeValue> elements) {
        this.elements = elements;
    }

    @Override
    public List<RuntimeValue> getValue() {
        return elements;
    }

    @Override
    public String typeName() {
        return "list";
    }

}
