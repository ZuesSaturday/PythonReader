package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Compiler.Lexer;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramStringTests {
    @Test
    void SimpleString() {
        String code = """
            "String"
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertEquals("String",out);

    }
}
