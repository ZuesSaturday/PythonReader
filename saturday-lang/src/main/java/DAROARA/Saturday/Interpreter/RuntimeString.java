package DAROARA.Saturday.Interpreter;

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

    @Override
    public RuntimeValue add(RuntimeValue other) {
        return new RuntimeString(value + other.toString());
    }

    @Override
    public RuntimeValue multiply(RuntimeValue other) {
        if (other instanceof RuntimeNumber n) {
            int times = (int) n.getValue().doubleValue();
            return new RuntimeString(value.repeat(times));
        }
        throw new RuntimeException("Cannot multiply string with non-number");
    }
}
