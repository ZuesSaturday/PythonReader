package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

public class LiteralNode extends Node{

    private Float Floatvalue;
    private int Intvalue;
    private double Doublevalue;
    public LiteralNode(Token value) {
        super(value);
//        if (value.getValue().contains(".")){
//            String[] parts = value.getValue().split("\\.");
//            if (parts[1].equalsIgnoreCase("0")){
//                this.Floatvalue = Float.parseFloat(value.getValue());
//            }else if (parts[1].equalsIgnoreCase("00")){
//                this.Doublevalue = Double.parseDouble(value.getValue());
//            }else {
//                this.Intvalue = Integer.parseInt(value.getValue());
//            }
//        }

        this.Intvalue = Integer.parseInt(value.getValue());
    }

    /**
     * @param env
     * @return
     */
    @Override
    public Object evaluate(Environment env) {
        return Intvalue;
    }
}
