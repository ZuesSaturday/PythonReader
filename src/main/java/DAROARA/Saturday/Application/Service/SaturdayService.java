package DAROARA.Saturday.Application.Service;

import DAROARA.Saturday.Application.Model.RunResult;
import DAROARA.Saturday.Interpreter.AST.ProgramNode;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Interpreter.Parser.ProgramParser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Service
public class SaturdayService {

    public RunResult execute(String code) {
        String output = "";
        String error = "";

        // Capture System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        try {
            // Use new modular parser
            ProgramParser parser = new ProgramParser(code);
            ProgramNode progNode = parser.parseProgram();
            Environment env = new Environment();

            // Evaluate the program
            progNode.evaluate(env);

            // Flush and get output
            System.out.flush();
            output = baos.toString();
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            System.setOut(oldOut); // restore
        }

        return new RunResult(output.trim(), error);
    }
}
