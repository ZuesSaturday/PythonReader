package DAROARA.Saturday.Error;

public class IndentationError extends RuntimeException {
    public IndentationError(String massage) {
        super("TypeError: " + massage);
    }
}
