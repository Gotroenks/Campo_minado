package Campo_minado_interface.model;

import java.util.*;

public class Field {

    private final int line;
    private final int column;
    private boolean mined;
    private boolean open;
    private boolean checked;

    private final List<Field> neighbors = new ArrayList<>();
    private final Set<IObserverField> observers = new HashSet<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void register(IObserverField event) {
        observers.add(event);
    }
    
    public void notify(EEventField ev) {
        observers.forEach(t -> t.event(this, ev));
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
        }
        else if(delta == 2 && diagonal) {
            neighbors.add(neighbor);
        }
        return diffLine;
    }

    public void turned() {
        if (!open) {
            checked = !checked;

            if(checked)
                notify(EEventField.CHECKED);
            else
                notify(EEventField.UNCHECKED);
        }
    }

    public boolean open() {
        if(!open && !checked) {
            open = true;
            if(mined) {
                notify(EEventField.EXPLODE);
                return true;
            }

            setOpen(true);

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
        if(open)
            notify(EEventField.OPEN);
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

    public int neighborMines() {
        return (int) neighbors.stream().filter(t -> t.mined).count();
    }

    public void reset() {
        open = false;
        mined = false;
        checked = false;
        notify(EEventField.RESET);
    }
}
