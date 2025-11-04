package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    // 🔹 Comments & whitespace
    COMMENT("#.*"),
    WHITESPACE("[ \\t\\r\\n]+"),

    // 🔹 Keywords
    KEYWORD("\\b(if|else|while|for|print|range)\\b"),

    // 🔹 Literals
    NUMBER("\\b\\d+\\b"),
    STRING("\"[^\"]*\"|'[^'\\n]*'"),
    LIST("\\[.*?\\]"),

    // 🔹 Identifiers & expressions
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    EXPRESSION("(?:(?:\\d+|[a-zA-Z_][a-zA-Z0-9_]*)\\s*[+\\-*/]\\s*(?:\\d+|[a-zA-Z_][a-zA-Z0-9_]*))"),

    // 🔹 Operators & assignment
    OPERATOR("==|!=|<=|>=|\\+|-|\\*|/"),
    ASSIGN("="),

    // 🔹 Structural tokens
    PUNCTUATION("[:.,;{}']"),
    LPAREN("\\("),
    RPAREN("\\)"),
    COLON(":"),

    // 🔹 Indentation & line control (for Python-like syntax)
    NEWLINE("\\n+"),
    INDENT(""),
    DEDENT(""),

    // 🔹 End of file & unknowns
    EOF(""),
    UNKNOWN(".");  // any unrecognized single character                        // any single unknown character


    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
