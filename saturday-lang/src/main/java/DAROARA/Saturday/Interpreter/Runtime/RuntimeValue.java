package DAROARA.Saturday.Interpreter.Runtime;

public abstract class RuntimeValue {
    public abstract Object getValue();
    public abstract String typeName();

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
