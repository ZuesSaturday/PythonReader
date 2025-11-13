package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.ArrayList;
import java.util.List;

public class StatementParser {
    private final TokenStream tokens;
    private final ExpressionParser exprParser;

    public StatementParser(TokenStream tokens) {
        this.tokens = tokens;
        this.exprParser = new ExpressionParser(tokens);
    }

    public Node parseStatement() {
        Token token = tokens.peek();
        return switch (token.getType()) {
            case IDENTIFIER -> {
                Node primary = new IdentifierNode(tokens.consume());
                // Parse indexing if it follows
                if (tokens.peek().getType() == TokenType.INDEXING) {
                    primary = parseIndexing(primary);
                }
                // Check if this is an assignment
                if (tokens.peek().getType() == TokenType.ASSIGN) {
                    Token assign = tokens.consume();
                    Node value = exprParser.parseExpression();
                    yield new AssignmentNode(assign, primary, value);
                }
                yield primary;
            }
            case KEYWORD -> parseKeywordStatement();
            default -> throw new RuntimeException("Unexpected token: " + token);
        };
    }

    private Node parseAssignment() {

        Token id = tokens.consume();
        Token assign = tokens.consume();
        Node value = exprParser.parseExpression();
        return new AssignmentNode(assign, new IdentifierNode(id), value);
    }

    private Node parseKeywordStatement() {
        Token keyword = tokens.consume();
        if (keyword.getValue().equals("print")) {
            tokens.match(TokenType.LPAREN);
            Node expr = exprParser.parseExpression();
            tokens.match(TokenType.RPAREN);
            return new PrintNode(keyword, expr);
        } else if (keyword.getValue().equals("if")) {
            Node condition = exprParser.parseExpression();
            if (!tokens.match(TokenType.COLON))
                throw new RuntimeException("Expected ':' after if condition");
            if (!tokens.match(TokenType.INDENT))
                throw new RuntimeException("Expected indentation after ':'");

            List<Node> body = new ArrayList<>();
            while (!tokens.match(TokenType.DEDENT)) {
                body.add(parseStatement());
            }
            return new IfNode(keyword, condition, body);
        }
        throw new RuntimeException("Unsupported keyword: " + keyword.getValue());
    }

    public Node parseIndexing(Node primary) {
        if (tokens.peek().getType() == TokenType.INDEXING) {
            Token indexToken = tokens.consume();
            return new IndexNode(indexToken,primary);
        }
        return primary;
    }
}
