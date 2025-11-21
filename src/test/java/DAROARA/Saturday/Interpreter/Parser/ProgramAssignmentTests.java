package DAROARA.Saturday.Interpreter.Parser;

import DAROARA.Saturday.Interpreter.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Compiler.Lexer;
import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Compiler.TokenType;
import DAROARA.Saturday.Interpreter.Environment;
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
