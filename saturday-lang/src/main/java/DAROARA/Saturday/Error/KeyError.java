package DAROARA.Saturday.Error;

public class KeyError extends RuntimeException {
    public KeyError(String massage) {
        super("TypeError: " + massage);
    }
}
