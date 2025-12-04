package DAROARA.Saturday.Interpreter;

import DAROARA.Saturday.AST.Statements.FunctionNode;

public class RuntimeFunction extends RuntimeValue {

    private final FunctionNode declaration;
    private final Environment closure;

    public RuntimeFunction(FunctionNode declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public Object getValue() {
        return declaration;
    }

    @Override
    public String typeName() {
        return "function";
    }

    public FunctionNode getDeclaration() {
        return declaration;
    }

    public Environment getClosure() {
        return closure;
    }
}
