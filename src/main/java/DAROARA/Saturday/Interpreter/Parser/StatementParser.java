//package DAROARA.Saturday.Interpreter.Parser;
//
//import DAROARA.Saturday.Interpreter.AST.*;
//import DAROARA.Saturday.Interpreter.Compiler.Token;
//import DAROARA.Saturday.Interpreter.Compiler.TokenType;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StatementParser {
//    private final TokenStream tokens;
//    private final ExpressionParser exprParser;
//    private final ListParser listParser;
//    private final IndexParser indexParser;
//
//    public StatementParser(TokenStream tokens) {
//        this.tokens = tokens;
//        this.exprParser = new ExpressionParser(tokens);
//        this.listParser = new ListParser(tokens) ;
//        this.indexParser = new IndexParser(tokens);
//    }
//
//    public Node parseStatement() {
//        Token token = tokens.peek();
//        return switch (token.getType()) {
//            case IDENTIFIER -> {
//                Node primary = new IdentifierNode(tokens.consume());
//
//                if (tokens.peek().getType() == TokenType.LBRACKET) {
//                    primary = indexParser.parseIndexing(primary);
//                }
//
//                if (tokens.peek().getType() == TokenType.ASSIGN) {
//                    Token assign = tokens.consume();
//
//                    Node value;
//                    TokenType next = tokens.peek().getType();
//
//                    if (next == TokenType.IDENTIFIER){
//
//                        Node rhs = new IdentifierNode(tokens.consume());
//                        if (tokens.peek().getType()==TokenType.LBRACKET){
//                            rhs = indexParser.parseIndexing(rhs);
//                        }
//                        value = rhs;
//                    }else if(next == TokenType.LBRACKET){
//                        value = listParser.parseList();
//                    }else {
//                        throw new RuntimeException("Invalid assignment value at " +tokens.peek().getLine());
//                    }
//                    yield new AssignmentNode(assign, primary, value);
//                }
//                yield primary;
//            }
//            case KEYWORD -> parseKeywordStatement();
//            case LBRACKET -> listParser.parseList();
//            case NUMBER -> {
//                Token num = tokens.consume();
//
//                if (tokens.expect(TokenType.OPERATOR)||tokens.expect(TokenType.COMOP))
//                    yield exprParser.parseExpression(num);
//                else {
//                    yield new LiteralNode(num);
//                }
//            }
//            default -> throw new RuntimeException("Unexpected token: " + token);
//        };
//    }
//
//    private Node parseKeywordStatement() {
//        Token keyword = tokens.consume();
//
//        switch (keyword.getValue()) {
//
//            case "print" -> {
//                tokens.expect(TokenType.LPAREN);
//                tokens.consume();
//                Node expr = exprParser.parsePrimary();
//                tokens.expect(TokenType.RPAREN);
//                tokens.consume();
//                return new PrintNode(keyword, expr);
//            }
//
//            case "if" -> {
//                if (tokens.peek().getType() == TokenType.IDENTIFIER){
//                    Token f = tokens.consume();
//                    Node condition;
//                    if (tokens.expect(TokenType.COMOP)) {
//                        condition = exprParser.parseExpression(f);
//                    }else {
//                        throw new RuntimeException("Condition -after variable"+tokens.peek().getType());
//                    }
//                    tokens.expect(TokenType.COLON);
//                    tokens.expect(TokenType.INDENT);
//
//                    List<Node> body = new ArrayList<>();
//                    while (!tokens.match(TokenType.DEDENT)) {
//                        body.add(parseStatement());
//                    }
//
//                    return new IfNode(keyword, condition, body);
//                }
//            }
//
//            default -> throw new RuntimeException(
//                    "Unsupported keyword: " + keyword.getValue()
//            );
//        }
//        return null;
//    }
//
//}
package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;
import DAROARA.Saturday.Interpreter.AST.VarAccessNode;

public class StatementParser {
    private final TokenStream tokens;
    private final ExpressionParser exprParser;
    private final ListParser listParser;
    private final IndexParser indexParser;
    private final RangeParser rangeParser;

    public StatementParser(TokenStream tokens) {
        this.tokens = tokens;
        this.exprParser = new ExpressionParser(tokens);
        this.listParser = new ListParser(tokens);
        this.indexParser = new IndexParser(tokens);
        this.rangeParser = new RangeParser(tokens);
    }

    public Node parseStatement() {
        Token token = tokens.peek();

        return switch (token.getType()) {

            case IDENTIFIER -> parseIdentifierStatement();

            case KEYWORD -> parseKeywordStatement();

            case LBRACKET -> listParser.parseList();

            case NUMBER -> parseNumberStatement();

            default -> throw new RuntimeException("Unexpected token: " + token);
        };
    }

    /**
     * Handles:    x = ...
     *             x[index] = ...
     *             x or x[index]
     */
    private Node parseIdentifierStatement() {
        Node primary = new IdentifierNode(tokens.consume());

        // Optional indexing
        if (tokens.peek().getType() == TokenType.LBRACKET) {
            primary = indexParser.parseIndexing(primary);
        }

        // Assignment: x = something
        if (tokens.peek().getType() == TokenType.ASSIGN) {
            Token assign = tokens.consume(); // '='

            Node value = parseAssignmentValue();
            return new AssignmentNode(assign, primary, value);
        }

        // Just identifier / indexed identifier
        return primary;
    }

    /**
     * Python-like assignment value:
     *    identifier
     *    identifier[index]
     *    list
     *    number expression
     *    identifier expression
     */
    private Node parseAssignmentValue() {
        TokenType next = tokens.peek().getType();

        return switch (next) {
            case IDENTIFIER -> {
                Node rhs = new IdentifierNode(tokens.consume());
                if (tokens.peek().getType() == TokenType.LBRACKET) {
                    rhs = indexParser.parseIndexing(rhs);
                }
                yield rhs;
            }
            case LBRACKET -> listParser.parseList();

            case NUMBER -> {
                Token first = tokens.consume();

                // Check if expression follows
                if (tokens.peek().getType() == TokenType.OPERATOR ||
                        tokens.peek().getType() == TokenType.COMOP) {

                    yield exprParser.parseExpression(first);
                }
                yield new LiteralNode(first);
            }

            default -> throw new RuntimeException("Invalid assignment value at line " + tokens.peek().getLine());
        };
    }

    /**
     * Handles NUMBER or NUMBER expression
     */
    private Node parseNumberStatement() {
        Token num = tokens.consume();

        if (tokens.peek().getType() == TokenType.OPERATOR ||
                tokens.peek().getType() == TokenType.COMOP) {

            return exprParser.parseExpression(num);
        }

        return new LiteralNode(num);
    }


    /**
     * Python-style keywords:
     *    print(expr)
     *    if expr:
     *        block
     */
    private Node parseKeywordStatement() {
        Token keyword = tokens.consume();

        return switch (keyword.getValue()) {

            case "print" -> parsePrint(keyword);

            case "if" -> parseIf(keyword);

            default -> throw new RuntimeException("Unsupported keyword: " + keyword.getValue());
        };
    }


    private Node parsePrint(Token keyword) {
        tokens.expect(TokenType.LPAREN);
        tokens.consume();

        // First value (NUMBER, IDENTIFIER, STRING)
        Token first = tokens.consume();

        Node expr;

        // Handle IDENTIFIER inside print, including indexing
        if (first.getType() == TokenType.IDENTIFIER) {
            Node id = new IdentifierNode(first);

            if (tokens.peek().getType() == TokenType.LBRACKET) {
                id = indexParser.parseIndexing(id);
            }

            // If operator follows → full expression
            if (tokens.peek().getType() == TokenType.OPERATOR ||
                    tokens.peek().getType() == TokenType.COMOP) {

                expr = exprParser.parseExpression(first);
            } else {
                expr = id;
            }
        }

        // NUMBER: may be a full expression
        else if (first.getType() == TokenType.NUMBER) {
            if (tokens.peek().getType() == TokenType.OPERATOR ||
                    tokens.peek().getType() == TokenType.COMOP) {

                expr = exprParser.parseExpression(first);
            } else {
                expr = new LiteralNode(first);
            }
        }

        // STRING: literal only
        else if (first.getType() == TokenType.STRING) {
            expr = new StringNode(first);
        }

        else {
            throw new RuntimeException("Invalid print expression at line " + first.getLine());
        }

        tokens.expect(TokenType.RPAREN);
        tokens.consume();

        return new PrintNode(keyword, expr);
    }


    private Node parseIf(Token keyword) {
        // Parse condition
        Node condition =parseIfCondition();

        // Expect colon
        tokens.expect(TokenType.COLON);
        tokens.consume();

        // Parse the 'then' block
        tokens.expect(TokenType.INDENT);
        tokens.consume();

        BlockNode thenBlock = new BlockNode();
        while (!tokens.match(TokenType.DEDENT)) {
            thenBlock.addStatement(parseStatement());
        }

        IfNode ifNode = new IfNode(keyword, condition, thenBlock);

        // Optional 'else' block
        if (tokens.peek().getType() == TokenType.KEYWORD &&
                tokens.peek().getValue().equals("else")) {
            tokens.consume(); // consume 'else'
            tokens.expect(TokenType.COLON);
            tokens.consume();

            tokens.expect(TokenType.INDENT);
            tokens.consume();

            BlockNode elseBlock = new BlockNode();
            while (!tokens.match(TokenType.DEDENT)) {
                elseBlock.addStatement(parseStatement());
            }

            ifNode.setElseBlock(elseBlock);
        }

        return ifNode;
    }


    private Node parseIfCondition() {
        Token first = tokens.consume();

        // identifier or number
        if (first.getType() == TokenType.IDENTIFIER || first.getType() == TokenType.NUMBER) {
            if (tokens.peek().getType() == TokenType.COMOP) {
                return exprParser.parseExpression(first);
            } else if (tokens.peek().getType() == TokenType.IN) {
                return parseMembership(first);
            }
        }

        throw new RuntimeException("Invalid if-condition start at line " + first.getLine());
    }

    private Node parseMembership(Token first) {
        Node left;
        if (first.getType() == TokenType.IDENTIFIER){
            left = new VarAccessNode(tokens.consume());
        }else {
            left = new LiteralNode(first);
        }
        Token inToken = tokens.consume();
        Token next = tokens.peek();

        Node iterable;
        if (next.getType() == TokenType.IDENTIFIER) {
            iterable = new VarAccessNode(tokens.consume());
        } else if (next.getType() == TokenType.LBRACKET) {

            iterable = listParser.parseList();
        } else if (next.getType()==TokenType.RANGE) {
            iterable = rangeParser.parseRange();
        }else {
            throw  new RuntimeException(tokens.peek().getValue()+"is not iterable");
        }
        return new MembershipNode(left,iterable,inToken);
    }
}
