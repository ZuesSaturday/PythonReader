package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    COMMENT("#.*"),
    WHITESPACE("[ \\t\\r\\n]+"),
    KEYWORD("\\b(if|else|while|for|print|range)\\b"),
    EXPRESSION("(?:(?:\\d+|[a-zA-Z_][a-zA-Z0-9_]*)\\s*[+\\-*/]\\s*(?:\\d+|[a-zA-Z_][a-zA-Z0-9_]*))"),
    CONDITION("[^\\:]+"),
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    NUMBER("\\b\\d+\\b"),
    LIST("\\[.*?\\]"),
    STRING("\"[^\"]*\"|'[^'\\n]*'"),
    ASSIGN("="),
    OPERATOR("==|!=|<=|>=|\\+|-|\\*|/"),
    PUNCTUATION("[:.,;{}']"),
    LPAREN("\\("),
    RPAREN("\\)"),
    EOF(""),
    UNKNOWN(".");                             // any single unknown character


    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
