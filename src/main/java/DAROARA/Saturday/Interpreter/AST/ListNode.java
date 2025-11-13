package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class ListNode extends Node{
    private final String list;
    public ListNode(Token token) {
        super(token);
        this.list = token.getValue().replaceAll("[\\[\\]]", "");

    }
    public String getIndex(int index) {
        List<Object> listValues = getListValues();

        if (index < 0 || index >= listValues.size()) {
            throw new IndexOutOfBoundsException("Index " + index +" out of bounds" );
        }

        return String.valueOf(listValues.get(index));
    }

//    public String append(String value) {
//
//    }

    private List<Object> getListValues() {
        String[] parts = list.split("\\s*,\\s*");
        List<Object> values = new ArrayList<>();
        for (String part : parts) {
            if (part.matches("\\d+")) {
                values.add(Integer.parseInt(part));
            } else if (part.matches("\"[^\"]*\"|'[^']*'")) {
                values.add(part.substring(1, part.length() -1));
            }else {
                values.add(part);
            }
        }
        return values;
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {

        return getListValues();
    }

}
