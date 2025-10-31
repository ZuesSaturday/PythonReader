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
        Token valueToken = parseInsideToken();

        IdentifierNode varNode = new IdentifierNode(identifierToken);
        Node valueNode = createExpressionNode(valueToken);
        return new AssignmentNode(asstoken,varNode,valueNode);
    }

    private PrintNode parsePrint() {
        Token printToken = expect(TokenType.KEYWORD); // print
        expect(TokenType.LPAREN);                      // (
        Node expression = parseExpression();
        expect(TokenType.RPAREN);                     // )

        System.out.println(expression);
        return new PrintNode(printToken, expression);
    }

    private Node parseExpression() {
        Node left = parsePrimary();

        while (check(TokenType.OPERATOR)) {
            Token operator = expect(TokenType.OPERATOR);
            Node right = parsePrimary();
            left = new ExpressionNode(operator, left, right);
        }

        return left;
    }

    private Node parsePrimary() {
        Token token = currentToken();

        if (check(TokenType.NUMBER)) {
            return new LiteralNode(expect(TokenType.NUMBER));
        } else if (check(TokenType.IDENTIFIER)) {
            return new IdentifierNode(expect(TokenType.IDENTIFIER));
        } else if (check(TokenType.STRING)) {
            return new StringNode(expect(TokenType.STRING));
        } else {
            throw new ParseException("Unexpected token in expression: " + token);
        }
    }


    private Token parseInsideToken() {
        if (check(TokenType.IDENTIFIER)) {
            return expect(TokenType.IDENTIFIER);  // a
        } else if (check(TokenType.EXPRESSION)) {
            return expect(TokenType.EXPRESSION); // 1+2
        } else if (check(TokenType.STRING)) {
            return expect(TokenType.STRING);  // "str"
        } else if (check(TokenType.NUMBER)) {
            return expect(TokenType.NUMBER);  // 5
        } else {
            throw new RuntimeException("Unexpected token inside print");
        }
    }

    private Node createExpressionNode(Token inside) {
        switch (inside.getType()) {
            case IDENTIFIER -> {
                return new IdentifierNode(inside);
            }
            case EXPRESSION -> {
                return parseExpressionNode(inside);
            }
            case STRING -> {
                return new StringNode(inside);
            }
            case NUMBER -> {
                return new LiteralNode(inside);
            }
            default -> throw new RuntimeException("Invalid token type");
        }
    }

    private Node parseExpressionNode(Token inside) {
        String exprValue = inside.getValue(); // "1+2" or "x+5"
        String operator = findOperator(exprValue);

        if (!operator.isEmpty()) {
            String[] parts = exprValue.split("" + operator); // split by operator symbol
            if (parts.length == 2) {
                Node leftNode = createOperandNode(parts[0].trim());
                Node rightNode = createOperandNode(parts[1].trim());
                return new ExpressionNode(new Token(TokenType.OPERATOR, operator), leftNode, rightNode);
            } else {
                throw new RuntimeException("Invalid expression format: " + exprValue);
            }
        } else {
            throw new RuntimeException("Unsupported expression: " + exprValue);
        }
    }

    private String findOperator(String exprValue) {
        if (exprValue.contains("+")) return "+";
        else if (exprValue.contains("-")) return "-";
        else if (exprValue.contains("*")) return "*";
        else if (exprValue.contains("/")) return "/";
        return "";
    }

    private Node createOperandNode(String operand) {
        if (operand.matches("\\d+")) {
            return new LiteralNode(new Token(TokenType.NUMBER, operand));
        } else {
            return new IdentifierNode(new Token(TokenType.IDENTIFIER, operand));
        }
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
            if (check(t)) return expect(t);
        }
        throw new RuntimeException("Expected one of " + types );
    }

    private Token peek() {
        return tokens.get(currentIndex); // `tokens` is your list of tokens, `current` is the index
    }

    private boolean check(TokenType type) {
        return peek().getType() == type;
    }

    public static void main(String[] args) {
        String code = """
                    a = 5
                    b = 5
                    print(a+b)
                    """;
        Lexer lexer = new Lexer(code);
        System.out.println(lexer.tokenize());

        Parser parser = new Parser(code);
        ProgramNode program = parser.parseProgram();
        Environment env = new Environment();
        program.printTree("");
        program.evaluate(env);

    }
}