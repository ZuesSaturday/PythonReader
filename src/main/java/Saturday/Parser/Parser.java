package Saturday.Parser;

import Saturday.AST.AssignmentNode;
import Saturday.AST.PrintNode;
import Saturday.AST.ProgramNode;
import Saturday.Compiler.Lexer;
import Saturday.Compiler.Token;
import Saturday.Compiler.TokenType;

import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int currentIndex = 0;

    public Parser(String code) {
        Lexer lexer = new Lexer(code);
        this.tokens = lexer.tokenize();
    }

    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode();

        while (currentIndex < tokens.size() && currentToken().getType() != TokenType.EOF) {
            program.addStatement(parseStatement());
        }
        return program;
    }

    private Object parseStatement() {
        Token token = currentToken();

        if (token.getType() == TokenType.IDENTIFIER) {
            return parseAssignment();
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("print")) {
            return parsePrint();
        } else {
            throw new ParseException("Unexpected token: " + token);
        }
    }

    private AssignmentNode parseAssignment() {
        Token identifier = expect(TokenType.IDENTIFIER);
        expect(TokenType.ASSIGN);
        Token value = expect(TokenType.NUMBER);
        return new AssignmentNode(identifier, value);
    }

    private PrintNode parsePrint() {
        Token printToken = expect(TokenType.KEYWORD); // print
        expect(TokenType.LPAREN);                      // (
        Token inside = expect(TokenType.IDENTIFIER);  // a
        expect(TokenType.RPAREN);                     // )

        return new PrintNode(printToken, inside);
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
            return nextToken();
        }
        throw new ParseException("Expected " + type + " but got " + currentToken().getType());
    }
}
