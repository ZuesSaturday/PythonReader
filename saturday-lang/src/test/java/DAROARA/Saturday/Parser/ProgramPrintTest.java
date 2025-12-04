package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Parser.ProgramParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramPrintTest {

    @Test
    void PrintHelloWorld() {
        String code = """
            print("Hello , World!")
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

//        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertEquals("Hello , World!",out);
    }
}
