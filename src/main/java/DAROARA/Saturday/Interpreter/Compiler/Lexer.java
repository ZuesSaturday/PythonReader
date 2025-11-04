package DAROARA.Saturday.Interpreter.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int currentPosition;
    private final Stack<Integer> indentStack = new Stack<>();

    public Lexer(String input) {
        this.input = input.replaceAll("\r\n","\n");
        this.currentPosition = 0;
        indentStack.push(0);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentPosition < input.length()) {

            if (isAtLineStart()) {
                handleIndentation(tokens);
            }
            Token token = nextToken();

            if (token == null) {
                throw new RuntimeException("Unknown character: " + input.charAt(currentPosition));
            }
            if (!(token.getType() == TokenType.WHITESPACE)){
                tokens.add(token);
            }

        }

        while (indentStack.size() > 1) {
            indentStack.pop();
            tokens.add(new Token(TokenType.DEDENT,""));
        }
        tokens.add(new Token(TokenType.EOF,""));
        return tokens;

    }

    private boolean isAtLineStart() {
        return currentPosition == 0||
                (currentPosition>0 && input.charAt(currentPosition-1) =='\n');
    }

    private void handleIndentation(List<Token> tokens){
        int space = 0;
        int pos = currentPosition;

        while (pos<indentStack.peek() && input.charAt(pos) ==' ') {
            space++;
            pos++;
        }
        if (space > indentStack.peek()) {
            indentStack.push(space);
            tokens.add(new Token(TokenType.INDENT,""));
        }else {
            while (space < indentStack.peek()) {
                indentStack.pop();
                tokens.add(new Token(TokenType.DEDENT,""));
            }
        }
        currentPosition = pos;
    }

    private Token nextToken() {
        if (currentPosition >= input.length()) {
            return null;
        }
        String remaining = input.substring(currentPosition);

        for (TokenType type:TokenType.values()) {
            if (type == TokenType.INDENT || type == TokenType.DEDENT) continue;
            Pattern pattern = Pattern.compile("^" +type.getPattern());
            Matcher matcher = pattern.matcher(remaining);
            if (matcher.lookingAt()) {
                String value = matcher.group();
                if (type == TokenType.EXPRESSION) {
                    currentPosition += value.length();
                    return new Token(type,value);
                }
                if (type == TokenType.NUMBER || type == TokenType.IDENTIFIER) {
                    currentPosition += value.length();
                    return new Token(type,value);
                }
                currentPosition += value.length();
                return new Token(type,value);
            }
        }

        currentPosition++;
        return null;
    }

}
