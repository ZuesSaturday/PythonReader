package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.IndexNode;

import DAROARA.Saturday.Interpreter.AST.Node;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;

public class IndexParser {
    private final TokenStream tokens;

    public IndexParser(TokenStream tokens){
        this.tokens = tokens;
    }
    public Node parseIndexing(Node primary) {
        Node result = primary;
        StringBuilder indexValue = new StringBuilder();
        tokens.expect(TokenType.LBRACKET);
        tokens.consume();
        // Read digits or ':' for slicing
        while (true) {
            var type = tokens.peek().getType();
            if (type == TokenType.NUMBER || type == TokenType.COLON) {
                indexValue.append(tokens.consume().getValue());
            } else {
                break;
            }
        }
        tokens.expect(TokenType.RBRACKET);
        tokens.consume();
        return result;
    }
}
