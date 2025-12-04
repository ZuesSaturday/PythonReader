package DAROARA.Saturday.Parser;

import DAROARA.Saturday.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Parser.ProgramParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramAssignmentTests {
    @Test
    void NumberVariableAssignment() {
        String code = """
            int = 2
            """;

        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

//        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertTrue(out.toString().equals("2"));

    }

    @Test
    void StringVariableAssignment() {
        String code = """
            String = "PythonReader"
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

//        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertTrue(out.toString().equals("PythonReader"));
    }

    @Test
    void ListVariableAssignment() {
        String code = """
            String = [1,2,3,4]
            """;
        ProgramParser parser = new ProgramParser(code);
        ProgramNode program = parser.parseProgram();

//        program.printTree("");
        Object out = program.evaluate(new Environment());
        assertTrue(out.toString().equals("[1, 2, 3, 4]"));
    }
}
