package DAROARA.Saturday.Interpreter;

public class RuntimeBoolean extends RuntimeValue {

    private final boolean value;

    public RuntimeBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String typeName() {
        return "boolean";
    }
}
