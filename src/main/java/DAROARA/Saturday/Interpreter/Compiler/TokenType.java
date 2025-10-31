package DAROARA.Saturday.Interpreter.Compiler;

public enum TokenType {

    COMMENT("#.*"),                        // comments first
    WHITESPACE("[ \\t\\r\\n]+"),           // whitespace
    KEYWORD("\\b(if|else|while|for|print|range)\\b"), // keywords
    IDENTIFIER("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),      // identifiers
    NUMBER("\\b\\d+\\b"),                  // numbers
    STRING("\"[^\"]*\"|'[^'\\n]*'"),       // string literals
    EXPRESSION("\\d+\\s*[+\\-*/]\\s*\\d+"), // simple arithmetic expressions
    ASSIGN("="),                           // assignment
    OPERATOR("==|!=|<=|>=|\\+|-|\\*|/"),   // operators
    PUNCTUATION("[.,;{}']"),               // punctuation
    LPAREN("\\("),                          // left parenthesis
    RPAREN("\\)"),                          // right parenthesis
    EOF(""),                                // end of file
    UNKNOWN(".");                           // any single unknown character


    private final String pattern;
    TokenType(String pattern){
        this.pattern = pattern;
    }
    public String getPattern() {
        return pattern;
    }
}
