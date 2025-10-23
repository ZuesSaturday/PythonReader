package Saturday.AST;

import java.util.List;

public class ProgramNode extends Node{
    private List<Object> list;

    public void addStatement(Object o) {
        list.add(o);
        setList(list);
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
