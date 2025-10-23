package Saturday.AST;

import Saturday.Compiler.TokenType;

public class BinaryOpNode extends ExpressionNode{
    private int leftOP;
    private TokenType type;
    private int rightOP;
    public BinaryOpNode(LiteralNode leftOP , TokenType type,LiteralNode rightOP){
        super(leftOP,type,rightOP);
    }
}
