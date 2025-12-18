package DAROARA.Saturday.Error;

public class IndexError extends RuntimeException {
    public IndexError(String massage) {
        super("TypeError: " + massage);
    }
}
