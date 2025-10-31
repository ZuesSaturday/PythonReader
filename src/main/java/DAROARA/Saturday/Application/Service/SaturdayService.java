package DAROARA.Saturday.Application.Service;

import DAROARA.Saturday.Interpreter.AST.ProgramNode;
import DAROARA.Saturday.Application.Model.RunResult;
import DAROARA.Saturday.Interpreter.Compiler.Lexer;
import DAROARA.Saturday.Interpreter.Environment;
import DAROARA.Saturday.Interpreter.Parser.Parser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Service
public class SaturdayService {
    public RunResult execute(String code) {
        String output = "";
        String error = "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        try {
            Lexer lexer = new Lexer(code);
            Parser parser = new Parser(code);
            ProgramNode progNode = parser.parseProgram();
            Environment env = new Environment();

            progNode.evaluate(env);

            System.out.flush();
            output = baos.toString();
        } catch (Exception e) {
            error = e.getMessage();
        }finally {
            System.setOut(oldOut);
        }

        return new RunResult(output.trim(),error);
    }
}
