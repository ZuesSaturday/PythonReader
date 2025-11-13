package DAROARA.Saturday.Interpreter.Compiler;

import DAROARA.Saturday.Interpreter.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Interpreter.Parser.ProgramParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String[] lines;
    private int currentLine;
    private int currentPosition;
    private final Stack<Integer> indentStack = new Stack<>();

    public Lexer(String input) {
        this.lines = input.split("\\r?\\n");
        this.currentLine = 0;
        this.currentPosition = 0;
        indentStack.push(0);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentLine < lines.length) {
            String line = lines[currentLine];
//            System.out.println(line);
            currentPosition = 0;
            if (line.trim().isEmpty()){
                currentLine++;
                continue;
            }
            int currentIndent = countLeadingSpaces(line);
            int prevIndent = indentStack.peek();
            if (currentIndent > prevIndent) {
                indentStack.push(currentIndent);
                tokens.add(new Token(TokenType.INDENT,line.substring(0,currentIndent)));
            } else if (currentIndent<prevIndent) {
                while (!indentStack.isEmpty() && indentStack.peek()> currentIndent) {
                    tokens.add(new Token(TokenType.DEDENT,""));
                }
            }
            while (currentPosition<line.length()){
                Token token = nextToken(line);
                if (token == null) {
                    throw new RuntimeException("Unknown character: " + line.charAt(currentPosition));
                }
                if (!(token.getType() == TokenType.WHITESPACE)){
                    tokens.add(token);
                }
            }
            currentLine++;
        }
        while (indentStack.size() >1){
            indentStack.pop();
            tokens.add(new Token(TokenType.DEDENT,""));
        }
        tokens.add(new Token(TokenType.EOF,""));
        return tokens;
    }

    private int countLeadingSpaces(String line) {
        int count = 0;
        for (char c:line.toCharArray()) {
            if (c==' ') count++;
            else if (c=='\t') count +=4;
            else break;
        }
        return count;
    }

    private Token nextToken(String line) {
        if (currentPosition >= line.length()) {
            return null;
        }
        String remaining = line.substring(currentPosition);

        for (TokenType type:TokenType.values()) {
            if (type == TokenType.INDENT|| type == TokenType.DEDENT || type == TokenType.EOF)
                continue;
            Pattern pattern = Pattern.compile("^" +type.getPattern());
            Matcher matcher = pattern.matcher(remaining);
            if (matcher.lookingAt()) {
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(type,value);
            }
        }

        currentPosition++;
        return null;
    }

}