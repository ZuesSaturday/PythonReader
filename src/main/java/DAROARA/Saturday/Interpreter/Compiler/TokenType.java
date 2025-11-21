package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    COMMENT("#.*"),
    INDENT("    "),
    WHITESPACE("[ \\t\\r\\n]+"),
    KEYWORD("\\b(if|else|while|for|print)\\b"),
    RANGE("range"),
    IN("in"),
    AND("and"),
    OR("or"),
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    NUMBER("\\b\\d+\\b"),
    STRING("\"[^\"]*\"|'[^'\\n]*'"),
    COMOP("==|!=|<=|>=|<|>"),
    ASSIGN("="),
    OPERATOR("[+\\-*/]"),
    COLON(":"),
    COMMA(","),
    DOT("\\."),
    LPAREN("\\("),
    RPAREN("\\)"),
    LBRACKET("\\["),
    RBRACKET("\\]"),
    NEWLINE("\\n+"),
    EOF(""),
    DEDENT(""),
    UNKNOWN(".");

    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
