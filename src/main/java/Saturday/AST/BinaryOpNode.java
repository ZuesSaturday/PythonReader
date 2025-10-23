package Saturday.AST;

import Saturday.Compiler.TokenType;

public class BinaryOpNode extends Node{
    private int leftOP;
    private final TokenType type = TokenType.ASSIGN;
    private int rightOP;
}
