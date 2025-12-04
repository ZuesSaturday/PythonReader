package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Parser.ProgramParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramListsTests {
    @Test
    void SimpleList() {
        String code = """
            [1,2,3,4]
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertTrue(out.toString().equals("[1, 2, 3, 4]"));
    }

    @Test
    @Disabled
    void NestedList() {
        String code = """
            [[1,2],[3,4]]
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

//        program.printTree("");
        program.evaluate(new Environment());
    }
}
