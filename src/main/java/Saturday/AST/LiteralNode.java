package Saturday.AST;

import Saturday.Compiler.Token;

public class LiteralNode extends ExpressionNode{
    public LiteralNode(Token value,String value2) {
        super(value,value2);
    }
}
