package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.Compiler.Lexer;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;
import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.List;


public class Parser {

    private final List<Token> tokens;
    private int currentIndex = 0;

    public Parser(String code) {
        Lexer lexer = new Lexer(code);
        this.tokens = lexer.tokenize();
//        System.out.println(tokens);
    }

    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode();

        while (currentToken().getType() != TokenType.EOF) {
//            System.out.println(tokens.size());
            Node stmt = parseStatement();
            program.addStatement(stmt);
        }
        return program;
    }

    private Node parseStatement() {
        Token token = currentToken();

        if (token.getType() == TokenType.IDENTIFIER) {
            return parseAssignment();
        }
        else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("print")) {
            return parsePrint();
        } else {
            throw new ParseException("Unexpected token: " + token);
        }
    }

    private AssignmentNode parseAssignment() {

        Token identifierToken = expect(TokenType.IDENTIFIER);//a
        Token asstoken = expect(TokenType.ASSIGN);
        Token valueToken = expect(TokenType.NUMBER);

        IdentifierNode varNode = new IdentifierNode(identifierToken);
        LiteralNode valueNode = new LiteralNode(valueToken);
        return new AssignmentNode(asstoken,varNode,valueNode);
    }

    private PrintNode parsePrint() {
        Token printToken = expect(TokenType.KEYWORD); // print
        expect(TokenType.LPAREN);                      // (
        Token inside = null;
        if (match(TokenType.IDENTIFIER)) {
            inside = expect(TokenType.IDENTIFIER);  // a
        } else if (match(TokenType.EXPRESSION)) {
            inside = expect(TokenType.EXPRESSION);
        } else if (match(TokenType.STRING)) {
            inside = expect(TokenType.STRING);
        }else if (match(TokenType.NUMBER)){
            inside = expect(TokenType.NUMBER);
        } else {
            throw new RuntimeException("Unexpected token inside print: "+ inside);
        }
        expect(TokenType.RPAREN);                     // )

        Node expNode;
        switch (inside.getType()) {
            case IDENTIFIER -> {
                expNode = new IdentifierNode(inside);
                break;
            }
            case EXPRESSION -> {
                String exprValue = inside.getValue(); // e.g. "1+2" or "x+5"

                // Match operator in the expression
                String operator = "";
                if (exprValue.contains("+")) operator = "+";
                else if (exprValue.contains("-")) operator = "-";
                else if (exprValue.contains("*")) operator = "*";
                else if (exprValue.contains("/")) operator = "/";

                if (!operator.isEmpty()) {
                    String[] parts = exprValue.split("" + operator); // split by operator symbol
                    if (parts.length == 2) {
                        String left = parts[0].trim();
                        String right = parts[1].trim();

                        Node leftNode;
                        Node rightNode;

                        // Determine if operand is number or identifier
                        if (left.matches("\\d+")) {
                            leftNode = new LiteralNode(new Token(TokenType.NUMBER, left));
                        } else {
                            leftNode = new IdentifierNode(new Token(TokenType.IDENTIFIER, left));
                        }

                        if (right.matches("\\d+")) {
                            rightNode = new LiteralNode(new Token(TokenType.NUMBER, right));
                        } else {
                            rightNode = new IdentifierNode(new Token(TokenType.IDENTIFIER, right));
                        }

                        expNode = new ExpressionNode(new Token(TokenType.OPERATOR, operator), leftNode, rightNode);
                    } else {
                        throw new RuntimeException("Invalid expression format: " + exprValue);
                    }
                } else {
                    throw new RuntimeException("Unsupported expression: " + exprValue);
                }
                break;
            }

            case STRING -> {
                expNode = new StringNode(inside);
                break;
            }
            case NUMBER -> {
                expNode = new LiteralNode(inside);
                break;
            }
            default -> throw new RuntimeException("Invalid token type");
        }
        return new PrintNode(printToken, expNode);
    }

    private Token currentToken() {
        return tokens.get(currentIndex);
    }

    private Token nextToken() {
        currentIndex++;
        return currentToken();
    }

    private boolean match(TokenType type) {
        if (currentToken().getType() == type) {
            nextToken();
            return true;
        }
        return false;
    }

    private Token expect(TokenType type) {
        if (currentToken().getType() == type) {
            Token n = currentToken();
            nextToken();
            return n;
        }
        throw new ParseException("Expected " + type + " but got " + currentToken().getType());
    }

    private Token expectAny(TokenType... types) {
        for (TokenType t : types) {
            if (match(t)) return expect(t);
        }
        throw new RuntimeException("Expected one of " + types );
    }


//    public static void main(String[] args) {
//        String code = """
//                    a = 5
//                    print(a)
//                    """;
//
//        Parser parser = new Parser(code);
//
//        ProgramNode program = parser.parseProgram();
//        Environment env = new Environment();
//        program.printTree("");
//        program.evaluate(env);
//
//    }
}