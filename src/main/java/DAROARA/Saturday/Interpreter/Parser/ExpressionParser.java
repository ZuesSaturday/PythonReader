package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.*;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

import java.util.regex.Pattern;

public class ExpressionParser {
    private final TokenStream tokens;
    private IndexParser indexParser;

    public ExpressionParser(TokenStream tokens) {
        this.tokens = tokens;
        this.indexParser = new IndexParser(tokens);
    }

    public Node parseExpression(Token firstValue) {

        StringBuilder expStr = new StringBuilder();
        expStr.append(firstValue.getValue());
        while (tokens.peek().getType() == TokenType.COMOP||tokens.peek().getType() == TokenType.OPERATOR) {


            expStr.append(tokens.consume().getValue());  // consume operator
            tokens.expect(TokenType.NUMBER);
            expStr.append(tokens.consume().getValue());  // number

        }
        return new ExpressionNode(expStr.toString());
    }

    public Node parsePrimary() {
        Token token = tokens.peek();
        switch (token.getType()) {
            case NUMBER -> {
                return new LiteralNode(tokens.consume());
            }
            case IDENTIFIER -> {

                Node rhs = new IdentifierNode(tokens.consume());
                System.out.println(tokens.peek().getType());
                if (tokens.peek().getType()==TokenType.LBRACKET){
                    rhs = indexParser.parseIndexing(rhs);
                }
                return rhs;
            }
            case STRING -> {
                return new StringNode(tokens.consume());
            }
            default -> throw new RuntimeException("Unexpected token: " + token);
        }
    }
}
