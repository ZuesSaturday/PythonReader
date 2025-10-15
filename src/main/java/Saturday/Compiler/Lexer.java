package Saturday.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String input;
    private int currentPosition;

    public Lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);
//            if (Character.isWhitespace(currentChar)) {
//                currentPosition++;
//                continue;
//            }

            Token token = nextToken();
            if (token != null) {
                tokens.add(token);
            } else {
                throw new RuntimeException("Unknown character: " + currentChar);
            }
        }
        return tokens;
    }

    private Token nextToken() {
        if (currentPosition >= input.length()) {
            return null;
        }

        String[] tokenPatterns = {
                "if|else|while|for",          // Keywords
                "[a-zA-Z_][a-zA-Z0-9_]*",     // Identifiers
                "\"[^\"\\n]*\"",              // Literals
                "[+-/*=<>!]",                 // Operators
                "[.,;(){}]",                  // Punctuation
                "\\b\\d+\\b",                 // Number
                "#.*",                        // Comment
                "[ \\t\\r\\n]+",              // Whitespace
                "."                           // Unknown
        };

        TokenType[] tokenTypes = {
                TokenType.KEYWORD,
                TokenType.IDENTIFIER,
                TokenType.LITERAL,
                TokenType.OPERATOR,
                TokenType.PUNCTUATION,
                TokenType.NUMBER,
                TokenType.COMMENT,
                TokenType.WHITESPACE,
                TokenType.UNKNOWN
        };

        for (int i = 0; i < tokenPatterns.length; i++) {
            Pattern pattern = Pattern.compile("^" + tokenPatterns[i]);
            Matcher matcher = pattern.matcher(input.substring(currentPosition));

            if (matcher.find()) {
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(tokenTypes[i], value);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String code = "if (x > 10) { y = x + 5; }";
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

//    public List<Token> tokenize(Expression expression) {
//        State state = State.INITIAL;
//        StringBuilder currentToken = new StringBuilder();
//        ArrayList<Token> tokens = new ArrayList<>();
//
//        while (expression.hasNext()) {
//            final Character currentChar = getValidNextCharacter(expression);
//
//            switch (state) {
//                case INITIAL:
//                    if (Grammer.isWhitespace(currentChar)) {
//                        break;
//                    } else if (Grammer.isDigit(currentChar)) {
//                        state = State.NUMBER;
//                        currentToken.append(currentChar);
//                    } else if (Grammer.isOperator(currentChar) && !tokens.isEmpty()) {
//                        state = State.OPERATOR;
//                        currentToken.append(currentChar);
//                    } else {
//                        state = State.INVALID;
//                        currentToken.append(currentChar);
//                    }
//                    break;
//                case NUMBER:
//                    if (Grammer.isDigit(currentChar)) {
//                        currentToken.append(currentChar);
//                    } else {
//                        tokens.add(new TokenNumber(currentToken.toString()));
//                        currentToken.setLength(0);
//                        state = State.INITIAL;
//                    }
//                    break;
//                case OPERATOR:
//                    tokens.add(new TokenOperator(currentToken.toString()));
//                    currentToken.setLength(0);
//                    state = State.INITIAL;
//                    continue;
//
//                case INVALID:
//                    throw new InvalidExpressionExcaption(String.format(MESSAGE_ERROR, currentToken));
//
//            }
//        }
//        finalizeToken(state, currentToken, tokens);
//        return tokens;
//    }
//
//    private Character getValidNextCharacter(Expression expression) {
//
//    }
//
//    private static void finalizeToken(State state, StringBuilder currentToken, ArrayList<Token> tokens) {
//        if (State.INVALID == state|| State.OPERATOR == state) {
//            throw new InvalidExpressionExcaption(String.format(MESSAGE_ERROR, currentToken));
//        }
//    }
}
