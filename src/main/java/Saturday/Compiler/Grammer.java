package Saturday.Compiler;


import java.util.Arrays;

enum Grammer {
    ADDITION('+'),SUBTRACTION('-'),MULTIPLICATION('*'),DIVISION('/');

    private final char symbol;

    Grammer(char symbol) {
        this.symbol = symbol;
    }

    public static boolean isOperator(char character){
        return Arrays.stream(Grammer.values()).anyMatch(grammer -> grammer.symbol == character);
    }

    public static boolean isDigit(char character) {
        return Character.isDigit(character);
    }

    public static boolean isWhitespace(char character) {
        return Character.isWhitespace(character);
    }

    public static boolean isValidSymbol(char character) {
        return isOperator(character) || isWhitespace(character) || isDigit(character);
    }

}

