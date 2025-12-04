package DAROARA.Saturday.AST.Statements;

//import Saturday.Compiler.Token;
//import Saturday.Interpreter.Environment;

import DAROARA.Saturday.AST.Node;
import DAROARA.Saturday.Lexer.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class WhileNode extends Node {
    public WhileNode(Token token) {
        super(token);
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return null;
    }
}
