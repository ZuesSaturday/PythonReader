package Saturday.AST;

import Saturday.Compiler.Token;

import java.beans.Expression;

public class AssignmentNode extends Node{
    private IdentifierNode identifierNode;

    private Expression expression;
//
    public AssignmentNode(Token identifier, Token value) {
        super(new IdentifierNode(identifier), new LiteralNode(value));
    }

}
