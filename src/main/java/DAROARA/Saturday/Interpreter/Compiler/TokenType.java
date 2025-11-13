package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    COMMENT("#.*"),
    INDENT("    "),
    WHITESPACE("[ \\t\\r\\n]+"),
    KEYWORD("\\b(if|else|while|for|print|range)\\b"),
    INDEXING("\\[(\\d+|[a-zA-Z_][a-zA-Z0-9_]*)(:(\\d+|[a-zA-Z_][a-zA-Z0-9_]*))?(:(\\d+|[a-zA-Z_][a-zA-Z0-9_]*))?\\]"),
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),
    NUMBER("\\b\\d+\\b"),
    LIST("\\[[^,\\[\\]]+,[^,\\[\\]]+(?:,[^,\\[\\]]+)*\\]"),
    STRING("\"[^\"]*\"|'[^'\\n]*'"),
    COMOP("==|!=|<=|>=|<|>"),           // comparison operators
    ASSIGN("="),
    OPERATOR("[+\\-*/]"),               // arithmetic operators
    COLON("^:$"),
    PUNCTUATION("[.,;{}']"),
    LPAREN("\\("),
    RPAREN("\\)"),
    NEWLINE("\\n+"),
    UNKNOWN("."),
    EOF(""),
    DEDENT(""); // any unrecognized single character                        // any single unknown character


    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
