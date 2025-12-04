package DAROARA.Saturday.Parser;


import DAROARA.Saturday.AST.*;
import DAROARA.Saturday.AST.Expressions.*;
import DAROARA.Saturday.AST.Statements.BlockNode;
import DAROARA.Saturday.AST.Statements.ForNode;
import DAROARA.Saturday.AST.Statements.IfNode;
import DAROARA.Saturday.AST.Statements.IdentifierNode;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Lexer.TokenType;

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
            case STRING -> {
                yield new StringNode(tokens.consume());
            }

            default -> throw new RuntimeException("Invalid assignment value at " + tokens.peek().getLine());
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

            case "for" -> parseFor(keyword);

            default -> throw new RuntimeException("Unsupported keyword: " + keyword.getValue());
        };
    }

    private Node parseFor(Token keyword) {
        tokens.expect(TokenType.IDENTIFIER);
        Node id = new IdentifierNode(tokens.consume());
        tokens.expect(TokenType.IN);
        Token in = tokens.consume();
        Token iter = tokens.peek();
        Node sequence = null;
        if (iter.getType() == TokenType.STRING){
            sequence = new StringNode(iter);
            tokens.consume();
        }
        if (iter.getType() == TokenType.LBRACKET) {
            sequence = listParser.parseList();
        }
        int start = 0;
        int stop;
        int step = 1;

        if (iter.getType() == TokenType.RANGE) {
            tokens.consume();
            tokens.expect(TokenType.RPAREN);
            tokens.consume();
            if (tokens.peek().getType() == TokenType.IDENTIFIER) {
                Node b = new VarAccessNode(tokens.consume());
                stop = Integer.parseInt(b.getToken().getValue());
            }else {
                stop = Integer.parseInt(tokens.consume().getValue());
            }

            if (tokens.peek().getType() == TokenType.COMMA) {
                tokens.consume();
                start = stop;
                stop = Integer.parseInt(tokens.consume().getValue());
                if (tokens.peek().getType() == TokenType.COMMA) {
                    tokens.consume();
                    step = Integer.parseInt(tokens.consume().getValue());
                }
            }
            tokens.expect(TokenType.RPAREN);
            tokens.consume();

            sequence = new RangeNode(iter,start,stop,step);
        }

        tokens.expect(TokenType.COLON);
        tokens.consume();

        tokens.expect(TokenType.INDENT);
        tokens.consume();

        BlockNode insideblock = new BlockNode();
        while (!tokens.match(TokenType.DEDENT)) {
            insideblock.addStatement(parseStatement());
        }
        return new ForNode(keyword,id,sequence,insideblock);

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
            TokenType optype = tokens.peek().getType();
            if (optype == TokenType.COMOP||optype== TokenType.OPERATOR) {
                String value = tokens.peek().getValue();
                if (value.equals("==")||value.equals("!=")||value.equals("+")||value.equals("*")) {
                    expr = exprParser.parseExpression(first);
                } else {
                    throw new RuntimeException(tokens.peek().getValue()+" cannot apply to string");
                }
            } else if (tokens.peek().getType() == TokenType.IN) {
                expr = parseMembership(first);
            }

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
        if (first.getType()==TokenType.STRING){
            if (tokens.peek().getType() == TokenType.COMOP) {
                String value = tokens.peek().getValue();
                if (value.equals("==")||value.equals("!=")||value.equals("+")||value.equals("*"))
                    return exprParser.parseExpression(first);
                else {
                    throw new RuntimeException(tokens.peek().getValue()+" cannot apply to string");
                }
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
        }else if (first.getType() == TokenType.STRING){
            left = new StringNode(first);
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
        }else if (next.getType()==TokenType.STRING) {
            iterable = new StringNode(tokens.consume());
        }else {
            throw  new RuntimeException(tokens.peek().getValue()+"is not iterable");
        }
        return new MembershipNode(left,iterable,inToken);
    }
}
