package DAROARA.Saturday.Interpreter.Parser;
import DAROARA.Saturday.Interpreter.AST.ListNode;
import DAROARA.Saturday.Interpreter.AST.Node;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

public class ListParser {
    private final TokenStream tokens;

    public ListParser(TokenStream tokens) {
        this.tokens = tokens;
    }

    public Node parseList() {
        tokens.expect(TokenType.LBRACKET);  // consume '['
        StringBuilder elements = new StringBuilder();
        elements.append(tokens.consume().getValue());
        // Check for empty list
        if (tokens.peek().getType() != TokenType.RBRACKET) {
            elements.append(tokens.consume().getValue());  // parse first element
            while (tokens.peek().getType() == TokenType.COMMA) {
                elements.append(tokens.consume().getValue());          // consume comma
                elements.append(tokens.consume().getValue());  // parse next element
            }
        }

        tokens.expect(TokenType.RBRACKET);  // consume ']'
        elements.append(tokens.consume().getValue());
        return new ListNode(elements.toString());
    }

}
