package DAROARA.Saturday.Error;

public class UnboundError extends RuntimeException {
    public UnboundError(String massage) {
        super("TypeError: " + massage);
    }
}
