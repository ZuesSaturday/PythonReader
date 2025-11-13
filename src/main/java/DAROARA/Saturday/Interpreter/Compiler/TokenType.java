package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    // Patterns (keep as is)
    COMMENT("#.*"),
    INDENT("    "),
    WHITESPACE("[ \\t\\r\\n]+"),
    KEYWORD("\\b(if|else|while|for|print|range)\\b"),
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    NUMBER("\\b\\d+\\b"),
    INDEXING("\\[(\\d+|[a-zA-Z_][a-zA-Z0-9_]*)(:(\\d+|[a-zA-Z_][a-zA-Z0-9_]*))?(:(\\d+|[a-zA-Z_][a-zA-Z0-9_]*))?\\]"),
    LIST("\\[(?:[^\\[\\],]+(?:,[^\\[\\],]+)*)?\\]"),
    STRING("\"[^\"]*\"|'[^'\\n]*'"),
    COMOP("==|!=|<=|>=|<|>"),
    ASSIGN("="),
    OPERATOR("[+\\-*/]"),
    COLON("^:$"),
    PUNCTUATION("[.,;{}']"),
    LPAREN("\\("),
    RPAREN("\\)"),
    NEWLINE("\\n+"),
    UNKNOWN("."),
    EOF(""),
    DEDENT("");



    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
