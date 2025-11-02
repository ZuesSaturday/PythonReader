package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class ListNode extends Node{
    private final String list;
    public ListNode(Token token) {
        super(token);
        this.list = token.getValue().replaceAll("[\\[\\]]", "");
//        for (String values:list.split("\\s*,\\s*")) {
//            if (values.)
//        }

    }
    public String getIndex(int index) {
        String[] listValues = list.split("\\s*,\\s*");

        if (index < 0 || index >= listValues.length) {
            throw new IndexOutOfBoundsException();
        }

        return listValues[index].trim();
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return list;
    }
}
