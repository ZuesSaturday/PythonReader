package Saturday.Compiler;

public class TokenNumber extends Token{

    protected TokenNumber(String value) {
        super(TokenType.NUMBER, value);
    }

    public int getValueAsInt() {
        return Integer.parseInt(getValue());
    }
}
