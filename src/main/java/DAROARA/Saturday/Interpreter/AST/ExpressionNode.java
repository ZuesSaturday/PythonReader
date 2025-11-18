package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpressionNode extends Node {
    private final List<String> numbers;
    private final List<String> operators;

    public ExpressionNode(String expression) {
        this.numbers = new ArrayList<>();
        this.operators = new ArrayList<>();
        parseExpression(expression);

    }

    private void parseExpression(String expression){
        expression = expression.replaceAll("\\s+","");

        StringBuilder current = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            if (i+1 < expression.length()) {
                String twoChar = "" + expression.charAt(i) + expression.charAt(i+1);
                if (isComp(twoChar)) {
                    operators.add(twoChar);
                    i++; // skip next char
                    continue;
                }
            }
            char c = expression.charAt(i);
            if (isOperator(c)) {
                if (current.length()>0) {
                    numbers.add(current.toString());
                    current.setLength(0);
                }
                operators.add(String.valueOf(c));
            }else {
                current.append(c);
            }
        }
        if (current.length()>0){
            numbers.add(current.toString());
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/'; // extend for other operators
    }
    private boolean isComp(String c){
        return Objects.equals(c, "<") || Objects.equals(c, ">") || Objects.equals(c, "==");
    }
    public List<String> getNumbers() {
        return numbers;
    }

    public List<String> getOperators() {
        return operators;
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "Expression:");
        System.out.println(indent + " numbers: " + numbers);
        System.out.println(indent + " Operators: " + numbers);
    }

    @Override
    public Object evaluate(Environment env) {
        return null;
    }

}
