package DAROARA.Saturday.Interpreter.AST;

import DAROARA.Saturday.Interpreter.Compiler.Token;
import DAROARA.Saturday.Interpreter.Environment;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RangeNode extends Node implements Iterable<Integer> {

    private final int start;
    private final int end;
    private final int step;

    public RangeNode(Token token, int start, int end, int step) {
        super(token);
        this.start = start;
        this.end = end;
        this.step = step;

        if (step == 0) {
            throw new RuntimeException("range() step cannot be zero");
        }
    }

    @Override
    public Object evaluate(Environment env) {
        return this;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int current = start;

            @Override
            public boolean hasNext() {
                if (step > 0) {
                    return current < end;
                } else {
                    return current > end;
                }
            }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                int value = current;
                current += step;
                return value;
            }
        };
    }
}
