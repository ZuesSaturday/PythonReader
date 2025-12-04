package DAROARA.Saturday.Interpreter.Runtime;

import DAROARA.Saturday.AST.Expressions.LiteralNode;

public class RuntimeNumber extends RuntimeValue {

    private final LiteralNode value;

    public RuntimeNumber(String value) {
        this.value = new LiteralNode(value);
    }

    @Override
    public LiteralNode getValue() {
        return value;
    }

    @Override
    public String typeName() {
        return "number";
    }

}
