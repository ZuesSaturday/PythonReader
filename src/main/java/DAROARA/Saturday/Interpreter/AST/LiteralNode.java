package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class LiteralNode extends Node{

    private Number value;
    public LiteralNode(Token value) {
        super(value);
        String tokenValue = token.getValue();
        if (tokenValue.contains(".")){
            try {
                this.value = Float.parseFloat(tokenValue);
            }catch (NumberFormatException e1) {
                try {
                    this.value = Double.parseDouble(tokenValue);
                } catch (NumberFormatException e2) {
                    throw new RuntimeException("Invalid numeric literal: "+ tokenValue);
                }
            }
        }else {
            try {
                this.value =  Integer.parseInt(tokenValue);
            }catch (NumberFormatException e){
                throw new RuntimeException("Invalid integer literal: "+ tokenValue);
            }
        }

    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return value;
    }
}
