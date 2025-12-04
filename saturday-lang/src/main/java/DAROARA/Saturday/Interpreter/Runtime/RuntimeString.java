package DAROARA.Saturday.Interpreter.Runtime;

public class RuntimeString extends RuntimeValue {

    private final String value;

    public RuntimeString(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String typeName() {
        return "string";
    }
}
