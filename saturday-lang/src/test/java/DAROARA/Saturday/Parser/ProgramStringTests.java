package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramStringTests {
    @Test
    void SimpleString() {
        String code = """
            List = [1,2,3,4]
            String = "Saturday"
            Int = List[1]
            print(Int)
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

        program.printTree("");
        Object out = program.evaluate(new Environment());
//        assertEquals("String",out);

    }
}
