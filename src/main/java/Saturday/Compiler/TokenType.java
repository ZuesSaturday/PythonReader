package Saturday.Compiler;

public enum TokenType {

    COMMENT("#.*"),
    WHITESPACE("[ \\t\\r\\n]+"),
    KEYWORD("\\b(if|else|while|for|print|range)\\b"),
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    NUMBER("\\b\\d+\\b"),
    LITERAL("\"[^\"\\n]*\"|'[^'\\n]*'"),
    ASSIGN("="),
    OPERATOR("==|!=|<=|>=|\\+|-|\\*|/"),
    PUNCTUATION("[.,;{}']"),
    LPAREN("\\("),
    RPAREN("\\)"),
    EOF(""),
    UNKNOWN(".");

    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
