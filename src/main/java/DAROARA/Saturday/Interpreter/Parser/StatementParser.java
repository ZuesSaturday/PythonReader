package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.ArrayList;
import java.util.List;

public class StatementParser {
    private final TokenStream tokens;
    private final ExpressionParser exprParser;
    private final ListParser listParser;
    private final IndexParser indexParser;

    public StatementParser(TokenStream tokens) {
        this.tokens = tokens;
        this.exprParser = new ExpressionParser(tokens);
        this.listParser = new ListParser(tokens) ;
        this.indexParser = new IndexParser(tokens);
    }

    public Node parseStatement() {
        Token token = tokens.peek();
        return switch (token.getType()) {
            case IDENTIFIER -> {
                Node primary = new IdentifierNode(tokens.consume());

                if (tokens.peek().getType() == TokenType.LBRACKET) {
                    primary = indexParser.parseIndexing(primary);
                }

                if (tokens.peek().getType() == TokenType.ASSIGN) {
                    Token assign = tokens.consume();

                    Node value;
                    TokenType next = tokens.peek().getType();

                    if (next == TokenType.IDENTIFIER){

                        Node rhs = new IdentifierNode(tokens.consume());
                        if (tokens.peek().getType()==TokenType.LBRACKET){
                            rhs = indexParser.parseIndexing(rhs);
                        }
                        value = rhs;
                    }else if(next == TokenType.LBRACKET){
                        value = listParser.parseList();
                    }else {
                        throw new RuntimeException("Invalid assignment value at " +tokens.peek().getLine());
                    }
                    yield new AssignmentNode(assign, primary, value);
                }
                yield primary;
            }
            case KEYWORD -> parseKeywordStatement();
            case LBRACKET -> listParser.parseList();
            case NUMBER -> {
                Token num = tokens.consume();

                if (tokens.expect(TokenType.OPERATOR)||tokens.expect(TokenType.COMOP))
                    yield exprParser.parseExpression(num);
                else {
                    yield new LiteralNode(num);
                }
            }
            default -> throw new RuntimeException("Unexpected token: " + token);
        };
    }

    private Node parseKeywordStatement() {
        Token keyword = tokens.consume();

        switch (keyword.getValue()) {

            case "print" -> {
                tokens.expect(TokenType.LPAREN);
                tokens.consume();
                Node expr = exprParser.parsePrimary();
                tokens.expect(TokenType.RPAREN);
                tokens.consume();
                return new PrintNode(keyword, expr);
            }

            case "if" -> {
                if (tokens.peek().getType() == TokenType.IDENTIFIER){
                    Token f = tokens.consume();
                    Node condition;
                    if (tokens.expect(TokenType.COMOP)) {
                        condition = exprParser.parseExpression(f);
                    }else {
                        throw new RuntimeException("Condition -after variable"+tokens.peek().getType());
                    }
                    tokens.expect(TokenType.COLON);
                    tokens.expect(TokenType.INDENT);

                    List<Node> body = new ArrayList<>();
                    while (!tokens.match(TokenType.DEDENT)) {
                        body.add(parseStatement());
                    }

                    return new IfNode(keyword, condition, body);
                }
            }

            default -> throw new RuntimeException(
                    "Unsupported keyword: " + keyword.getValue()
            );
        }
        return null;
    }

}
