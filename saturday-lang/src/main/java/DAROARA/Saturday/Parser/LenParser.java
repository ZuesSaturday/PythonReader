package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.*;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Lexer.TokenType;

public class LenParser {
    private TokenStream tokens;
    private ListParser liparser;

    public LenParser(TokenStream tokens){
        this.tokens = tokens;
    }

    public Node parseLen(Token lenToken) {
         tokens.expect(TokenType.LPAREN);
         tokens.consume();
         Token cur = tokens.consume();
        Node id = null;
        if (cur.getType() == TokenType.IDENTIFIER) {
            id = new VarAccessNode(cur);
        }
        if (cur.getType() == TokenType.STRING) {
            id = new StringNode(cur);
        }
        if (cur.getType() == TokenType.LBRACKET) {
            id = liparser.parseList();
        }
        return new LenNode(lenToken,id);
    }
}
