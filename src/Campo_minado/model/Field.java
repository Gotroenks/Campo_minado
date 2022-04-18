package Campo_minado.model;

import Campo_minado.controller.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;
    private boolean mined;
    private boolean open;
    private boolean checked;
    private final List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public boolean addNeighbors(Field neighbor) {
        boolean diffLine = line != neighbor.line;
        boolean diffColumn = column != neighbor.column;
        boolean diagonal = diffLine && diffColumn;

        int lineDelta = Math.abs(line - neighbor.line);
        int columnDelta = Math.abs(column - neighbor.column);
        int delta = lineDelta + columnDelta;

        if(delta == 1) {
            neighbors.add(neighbor);
            return true;
        }
        else if(delta == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        }
        return false;
    }

    public void turned() {
        if (!open)
            checked = !checked;
    }

    public boolean open() {
        if(!open && !checked) {
            open = true;
            if(mined)
                throw new Exceptions();
            if(securityNeighbors())
                neighbors.forEach(Field::open);
            return true;
        }
        return false;
    }

    public boolean securityNeighbors() {
        return neighbors.stream().noneMatch(t -> t.mined);
    }

    public void mined() {
        mined = true;
    }

    public boolean getMined() {
        return mined;
    }
    
    public boolean isChecked() {
        return checked;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
    public boolean isClosed() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean fieldClick() {
        boolean fieldOpen = !mined && open();
        boolean fieldCheck = mined && isClosed();

        return fieldOpen || fieldCheck;
    }

    public long neighborMines() {
        return neighbors.stream().filter(t -> t.mined).count();
    }

    public void reset() {
        open = false;
        mined = false;
        checked = false;
    }

    public String toString() {
        if(checked)
            return "\uD83D\uDDF8";
        else if (open && mined)
            return "X";
        else if (open && neighborMines() > 0)
            return Long.toString(neighborMines());
        else if (open)
            return " ";
        return "?";
    }
}
