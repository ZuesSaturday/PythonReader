package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.*;
import DAROARA.Saturday.Compiler.Lexer;
import DAROARA.Saturday.Compiler.Token;
import DAROARA.Saturday.Compiler.TokenType;
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
        Token inside = expect(TokenType.IDENTIFIER);  // a
        expect(TokenType.RPAREN);                     // )

        IdentifierNode expNode = new IdentifierNode(inside);
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

    public static void main(String[] args) {
        String code = """
                    a = 5
                    print(a)
                    """;

        Parser parser = new Parser(code);

        ProgramNode program = parser.parseProgram();
        Environment env = new Environment();
        program.printTree("");
        program.evaluate(env);

    }
}