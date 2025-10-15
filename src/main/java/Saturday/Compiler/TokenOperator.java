package Saturday.Compiler;

public class TokenOperator extends Token {
    protected TokenOperator(String value) {
        super(TokenType.OPERATOR, value);
    }
}
