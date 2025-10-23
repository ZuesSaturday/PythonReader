package Saturday.AST;

import Saturday.Compiler.Token;

public class IdentifierNode extends ExpressionNode{
    public IdentifierNode(Token identifier) {
        super(identifier);
    }
}
