package Saturday.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int currentPosition;

    public Lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentPosition < input.length()) {
            Token token = nextToken();

            if (token == null) {
                throw new RuntimeException("Unknown character: " + input.charAt(currentPosition));
            }
            if (token.getType() != TokenType.WHITESPACE ){
                tokens.add(token);
            }
        }
        return tokens;
    }

    private Token nextToken() {
        if (currentPosition >= input.length()) {
            return null;
        }

        String reamining = input.substring(currentPosition);

        for (TokenType type:TokenType.values()) {
            Pattern pattern = Pattern.compile("^" +type.getPattern());
            Matcher matcher = pattern.matcher(reamining);
            if (matcher.find()) {
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(type,value);
            }
        }

        return null;
    }

}
