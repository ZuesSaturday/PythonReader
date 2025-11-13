package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class StringNode extends Node{
    private final String str;

    public StringNode(Token token) {
        super(token);
        this.str = token.getValue().replaceAll("^\"|\"$", "");
    }
    public ListNode Split(String value) {
        String regex = java.util.regex.Pattern.quote(value);
        String[] parts = str.split(regex);
        String lisStr = "["+String.join(",",parts)+"]";
        Token liToken = new Token(TokenType.LIST,lisStr);
        return new ListNode(liToken);
    }

    @Override
    public Object evaluate(Environment env) {
        return str;
    }

    public Object getIndex(int index) {
        List<Object> listValues = getListValues();

        if (index < 0 || index >= listValues.size()) {
            throw new IndexOutOfBoundsException("Index " + index +" out of bounds" );
        }

        return String.valueOf(listValues.get(index));
    }
    private List<Object> getListValues() {
        String[] parts = str.split("\\s*,\\s*");
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
}
