package DAROARA.Saturday.AST;

import DAROARA.Saturday.Lexer.Token;
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
    public LiteralNode(String value) {

        if (value.contains(".")){
            try {
                this.value = Float.parseFloat(value);
            }catch (NumberFormatException e1) {
                try {
                    this.value = Double.parseDouble(value);
                } catch (NumberFormatException e2) {
                    throw new RuntimeException("Invalid numeric literal: "+ value);
                }
            }
        }else {
            try {
                this.value =  Integer.parseInt(value);
            }catch (NumberFormatException e){
                throw new RuntimeException("Invalid integer literal: "+ value);
            }
        }

    }

    public Number getValue() {
        return value;
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
