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

        while (tokens.peek().getType() == TokenType.LBRACKET) {
            tokens.consume();
            String indexValue = tokens.consume().getValue();
            tokens.expect(TokenType.RBRACKET);
            tokens.consume();
            result = new IndexNode(result,indexValue);
        }
        return result;
    }
}
