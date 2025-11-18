package DAROARA.Saturday.Interpreter.Compiler;

public class Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int column;

    public Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public Token(TokenType tokenType, String value) {
        this.type = tokenType;
        this.value = value;
        this.line = 0;
        this.column = 0;
    }

    public TokenType getType() { return type; }
    public String getValue() { return value; }
    public String getLine() { return "["+line+":"+column+"]"; }
//    public int getColumn() { return column; }

    @Override
    public String toString() {
        return type + "('" + value + "') at [" + line + ":" + column + "]";
    }
}
