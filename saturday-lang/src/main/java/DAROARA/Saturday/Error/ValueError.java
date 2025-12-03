package DAROARA.Saturday.Interpreter.AST;

public class ValueError extends RuntimeException {
    public ValueError(String massage) {
        super(massage);
    }
}
