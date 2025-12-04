package DAROARA.Saturday.Interpreter.Functions.Type_Conversion;

import DAROARA.Saturday.AST.Expressions.ListNode;
import DAROARA.Saturday.Error.TypeError;
import DAROARA.Saturday.Interpreter.Runtime.RuntimeNumber;
import DAROARA.Saturday.Interpreter.Runtime.RuntimeValue;
import DAROARA.Saturday.AST.Expressions.StringNode;

public class Len {

    public static RuntimeValue call(RuntimeValue[] args) {

        if (args.length > 1) {
            throw new TypeError("len() takes exactly one argument ("+args.length+" given)");
        }

        Object value = args[0];

        // handle string
        if (value instanceof StringNode s) {
            return new RuntimeNumber(String.valueOf(s.getStr().length()));
        }

        // handle list
        if (value instanceof ListNode list) {
            return new RuntimeNumber(String.valueOf(list.getListValues().size()));
        }

        throw new TypeError("len() not supported for type: " + ((RuntimeValue) value).typeName());
    }
}
