package DAROARA.Saturday.Interpreter;

import DAROARA.Saturday.Interpreter.Functions.Type_Conversion.Len;
public class Builtins {

    public Builtins(Environment env) {

        env.defineFunction("len", Len::call
    );
    }

}
