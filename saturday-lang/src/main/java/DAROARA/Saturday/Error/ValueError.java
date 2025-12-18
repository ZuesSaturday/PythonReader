package DAROARA.Saturday.Error;

public class ValueError extends RuntimeException {
    public ValueError(String massage) {
        super("TypeError: " + massage);
    }
}
