package Saturday.AST;

import Saturday.Compiler.Token;
import Saturday.Compiler.TokenType;
import Saturday.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected Token token;
    protected List<Node> children;

    public Node(Token token) {
        this.token = token;
        this.children = new ArrayList<>();
    }

    public  Token getToken() {
        return token;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        if (child!= null) {
            this.children.add(child);
        }
    }

    public void printTree(String indent) {
        System.out.println(indent + getClass().getSimpleName() + ": "+ (token != null ? token.getValue(): ""));
        for (Node child : children){
            child.printTree(indent + " ");
        }
    }

    public abstract Object evaluate(Environment env);
}
