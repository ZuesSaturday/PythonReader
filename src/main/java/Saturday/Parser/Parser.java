package Saturday.Parser;

import Saturday.Compiler.Lexer;
import Saturday.Compiler.Token;
import Saturday.Compiler.TokenType;

import java.util.List;

public class Parser {

    public static void main(String[] args) {
        String code = """
                a = 5
                b = 3
                print(a+b)
                """;
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
