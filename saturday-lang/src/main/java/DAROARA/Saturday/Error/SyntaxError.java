package DAROARA.Saturday.Error;

public class SyntaxError extends RuntimeException {
    public SyntaxError(String massage) {
        super("TypeError: " + massage);
    }
}
