package DAROARA.Saturday.Interpreter;

public abstract class RuntimeValue {
    public abstract Object getValue();
    public abstract String typeName();

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    // default numeric ops — child classes override when appropriate
    public RuntimeValue add(RuntimeValue other) {
        throw new RuntimeException("Cannot add " + typeName() + " to " + other.typeName());
    }
    public RuntimeValue subtract(RuntimeValue other) {
        throw new RuntimeException("Cannot subtract " + typeName());
    }
    public RuntimeValue multiply(RuntimeValue other) {
        throw new RuntimeException("Cannot multiply " + typeName());
    }
    public RuntimeValue divide(RuntimeValue other) {
        throw new RuntimeException("Cannot divide " + typeName());
    }
}
