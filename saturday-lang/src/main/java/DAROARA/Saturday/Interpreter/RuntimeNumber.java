package DAROARA.Saturday.Interpreter;

public class RuntimeNumber extends RuntimeValue {

    private final double value;

    public RuntimeNumber(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String typeName() {
        return "number";
    }

    @Override
    public RuntimeValue add(RuntimeValue other) {
        if (other instanceof RuntimeNumber n) {
            return new RuntimeNumber(value + n.value);
        }
        // number + string → string concatenation
        return new RuntimeString(this.toString() + other.toString());
    }

    @Override
    public RuntimeValue subtract(RuntimeValue other) {
        if (other instanceof RuntimeNumber n) {
            return new RuntimeNumber(value - n.value);
        }
        throw new RuntimeException("Cannot subtract non-number");
    }

    @Override
    public RuntimeValue multiply(RuntimeValue other) {
        if (other instanceof RuntimeNumber n) {
            return new RuntimeNumber(value * n.value);
        }
        throw new RuntimeException("Cannot multiply non-number");
    }

    @Override
    public RuntimeValue divide(RuntimeValue other) {
        if (other instanceof RuntimeNumber n) {
            if (n.value == 0) throw new RuntimeException("Division by zero");
            return new RuntimeNumber(value / n.value);
        }
        throw new RuntimeException("Cannot divide non-number");
    }
}
